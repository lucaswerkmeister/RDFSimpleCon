package nl.wur.ssb.RDFConnection;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.jena.atlas.web.auth.SimpleAuthenticator;
import org.apache.jena.riot.RDFDataMgr;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.shared.PrefixMapping;
import com.hp.hpl.jena.tdb.TDBFactory;

public class RDFConnection
{
	private Model localDb;
	private String server;
	private int port;
	private String finalLocation;
	private String graph;
	private SimpleAuthenticator authen;
	private boolean eachThreadSeperate = false;
	private int counter = 0;
	private HashMap<Long,Integer> threadMap = new HashMap<Long,Integer>();
	private int maxThreadCount = 1;
	
	public RDFConnection(String config, String tmpDir) throws Exception
	{
		try
		{
	    if(config.indexOf("[") != -1)
	    {
	  	  Matcher temp = Pattern.compile("(.*)\\[(.*)\\]").matcher(config);
	  	  if(temp.matches() == false)
	  	  	throw new Exception("invalid config string: " + config);
	  	  config = temp.group(1);
	  	  graph = temp.group(2);
	    }
	    if(config.isEmpty())
	    {
	    	Dataset dataset = createEmptyStore(tmpDir);
			  if(graph == null)
				  localDb = dataset.getDefaultModel();
			  else
				  localDb = dataset.getNamedModel(graph);
	    }
	    else if(config.startsWith("file://"))
			{
				File file = new File(config.substring("file://".length()));
			  Dataset dataset = null;
				if(file.isDirectory())
				{
				  dataset = TDBFactory.createDataset(file.toString());
				}
				else
				{
					dataset = createEmptyStore(tmpDir);
					RDFDataMgr.read(dataset,file.toString());
				}
			  if(graph == null)
				  localDb = dataset.getDefaultModel();
			  else
				  localDb = dataset.getNamedModel(graph);
			}
			else
			{
			  String username = null;
			  String pass = null;
			  String graph = "";
		    if(config.indexOf("@") != -1)
		    {
		    	String temp[] = config.split("@");
		    	String temp2[] = temp[0].split(":");
		  	  if(temp2.length != 2)
		  		  throw new Exception("invalid config string: " + config);
		  	  username = temp2[0];
		  	  pass = temp2[1];
		  	  config = temp[1];
		    }

		    String server = config;
		    setServerGraph(server,graph);
		    if(username != null)
		    	this.setAuthen(username,pass);
			}
		}
		catch(Throwable th)
		{
			throw new Exception("invalid config string: " + config,th);
		}	  
	}
	
	private Dataset createEmptyStore(String tmpDir)
	{
  	if(tmpDir == null)
  		return TDBFactory.createDataset();
  	else
  		return TDBFactory.createDataset(tmpDir);
	}
	public RDFConnection(String config) throws Exception
	{
		this(config,null);
	}
	
	/*public RDFConnection(String dir,String graph,boolean local)
	{
		Dataset dataset = TDBFactory.createDataset(dir);
		localDb = dataset.getNamedModel(graph);
	}
	
	public RDFConnection(String server,String graph)
	{
    this.setServerGraph(server,graph);
	}*/
	
	private void setServerGraph(String server,String graph)
	{
		Matcher matcher = Pattern.compile("http://(.+):([\\d]+)/(.*)").matcher(server);
		if(!matcher.matches())
		{
			matcher = Pattern.compile("http://(.+)/(.*)").matcher(server);
			matcher.matches();
			this.server = matcher.group(1);
			this.port = 80;
			this.finalLocation = matcher.group(2);
		}
		else
		{
			this.server = matcher.group(1);
			this.port = Integer.parseInt(matcher.group(2));
			this.finalLocation = matcher.group(3);
		}
		this.graph = graph;
	}
	
	public void enableEachThreadSeperatePort(int threadCount)
	{
		this.eachThreadSeperate = true;
		this.maxThreadCount = threadCount;
	}
	
  public void setAuthen(String user,String pass)
  {
  	authen = new SimpleAuthenticator(user,pass.toCharArray());
  }
	
	private QueryExecution createQuery(String queryFile,Object ... args)  throws Exception
	{
    Object toPass[] = args;
 //   if(this.server != null)
  //  {
    	toPass = new Object[args.length + 1];
      System.arraycopy(args,0,toPass,1,args.length);
      toPass[0] = this.graph;
   // }
	  Query query = this.getQuery(queryFile,toPass);
	
		if(this.server != null)
		{
	    int port = this.port;
	    if(this.eachThreadSeperate)
	    {
	  	  port = this.getThreadPortNum();
	    }
	    String server = "http://" + this.server + ":" + port + "/" + this.finalLocation;
  		QueryExecution qe = QueryExecutionFactory.sparqlService(server,query,authen);
	  	qe.setTimeout(7,TimeUnit.DAYS);
		  return qe;
		}
		else
		{
		  return QueryExecutionFactory.create(query,this.localDb,null);
		}
	}
	
	private int getThreadPortNum() throws Exception
	{
		long threadId = Thread.currentThread().getId();
		if(this.threadMap.containsKey(threadId))
			return this.threadMap.get(threadId) + this.port;
		int newCount = this.counter++;
		if(newCount > this.maxThreadCount)
			throw new Exception("max thread count reached");
		this.threadMap.put(threadId,newCount);
		return newCount + this.port;
	}
	
	public Iterable<ResultLine> runQuery(String queryFile,boolean preload,Object ... args) throws Exception
	{
		queryFile = "queries/" + queryFile;
		QueryExecution qe = createQuery(queryFile,args);
		long millis = System.currentTimeMillis();
		ResultSet result = qe.execSelect();
		ResultIteratorRaw walker = new ResultIteratorRaw(result);// new Iteration<HashMap<String,RDFNode>>
		if(preload == false)
		{
			return new Iteration<ResultLine>(new ResultIterator(new Iteration<HashMap<String,RDFNode>>(walker)));
		}
		else
		{
			LinkedList<HashMap<String,RDFNode>> res = new LinkedList<HashMap<String,RDFNode>>();
			for(HashMap<String,RDFNode> item : new Iteration<HashMap<String,RDFNode>>(walker))
			{
				res.add(item);
			}
			qe.close();
			System.out.println("time: " + (System.currentTimeMillis() - millis) + " for query " + queryFile); 
			return new Iteration<ResultLine>(new ResultIterator(new Iteration<HashMap<String,RDFNode>>(res.iterator())));
		}
	}
	
	private Query getQuery(String file,Object ... args)
	{
		try
		{
			String header = this.readFile("queries/header.txt");
			String content = this.readFile(file);
			String query = header + content;
			query = String.format(query,args);			
			return QueryFactory.create(query);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	private String readFile(String file) throws IOException
	{
		File inFile = new File(file);
		FileInputStream input = new FileInputStream(inFile);
		byte all[] = new byte[(int)inFile.length()];
		input.read(all);
		String string = new String(all);		
		input.close();
		return string;
	}
	
	public String expand(String in)
	{
		return localDb.expandPrefix(in);
	}
	
	public void add(String subj,String pred,String obj)
	{
		synchronized(this)
		{
		  this.localDb.add(this.localDb.createResource(expand(subj)),this.localDb.createProperty(expand(pred)),this.localDb.createResource(expand(obj)));
		}
	}
	public void addLit(String subj,String pred,String obj)
	{
		synchronized(this)
		{
  		this.localDb.add(this.localDb.createResource(expand(subj)),this.localDb.createProperty(expand(pred)),this.localDb.createTypedLiteral(obj));
		}
	}
	public void add(String subj,String pred,int val)
	{
		synchronized(this)
		{
		  this.localDb.add(this.localDb.createResource(expand(subj)),this.localDb.createProperty(expand(pred)),this.localDb.createTypedLiteral(val));
	  }
	}
	public void add(String subj,String pred,boolean val)
	{
		synchronized(this)
		{
		  this.localDb.add(this.localDb.createResource(expand(subj)),this.localDb.createProperty(expand(pred)),this.localDb.createTypedLiteral(val));
	  }	
	}
	public void add(String subj,String pred,float val)
	{
		synchronized(this)
		{
		  this.localDb.add(this.localDb.createResource(expand(subj)),this.localDb.createProperty(expand(pred)),this.localDb.createTypedLiteral(val));
 	  }
	}
	public void add(String subj,String pred,double val)
	{
		synchronized(this)
		{
		  this.localDb.add(this.localDb.createResource(expand(subj)),this.localDb.createProperty(expand(pred)),this.localDb.createTypedLiteral(val));
  	}	
	}
	
	/*public boolean bgp(String subj,String pred,String obj)
	{
		
	}*/
	
	public PrefixMapping setNsPrefix(String prefix,String iri)
	{
		return this.localDb.setNsPrefix(prefix,iri);
	}
	
	public void close()
	{
		this.localDb.close();
	}
}
