package nl.wur.ssb.RDFSimpleCon;

import java.util.HashMap;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.RDFNode;

public class ResultLine
{
	HashMap<String,RDFNode> line;	
	public ResultLine(HashMap<String,RDFNode> line)
	{
		this.line = line;
	}
	public RDFNode getNode(String var)
	{
		return this.line.get(var);
	}
	public Literal getLiteral(String var)
	{
	  RDFNode node = line.get(var);
	  if(node == null)
	  	return null;
	  return node.asLiteral();
	}
	public String getIRI(String var)
	{
	  RDFNode node = line.get(var);
	  if(node == null)
	  	return null;
	  return node.asResource().getURI();
	}
	
	public String asString(String var)
	{
	  RDFNode node = line.get(var);
	  if(node == null)
	  	return null;
	  if(node.isLiteral())
	  	return node.asLiteral().getString();
	  else if(node.isResource())
	  	return node.asResource().getURI();
	  else
	  	return node.asNode().toString();
	}
	
	public String getLitString(String var)
	{
	  RDFNode node = line.get(var);
	  if(node == null)
	  	return null;
	  return node.asLiteral().getString();
	}
	
	public int getLitInt(String var) throws Exception
	{
	  RDFNode node = line.get(var);
	  if(node == null)
	  	throw new Exception("int lit == null");
	  return node.asLiteral().getInt();
	}
	
	public int getLitInt(String var, int def)
	{
	  RDFNode node = line.get(var);
	  if(node == null)
	  	return def;
	  return node.asLiteral().getInt();
	}
	
	public double getLitDouble(String var) throws Exception
	{
	  RDFNode node = line.get(var);
	  if(node == null)
	  	throw new Exception("int lit == null");
	  return node.asLiteral().getDouble();
	}
	
	public double getLitDouble(String var, double def)
	{
	  RDFNode node = line.get(var);
	  if(node == null)
	  	return def;
	  return node.asLiteral().getDouble();
	}
	
	public boolean getLitBoolean(String var) throws Exception
	{
	  RDFNode node = line.get(var);
	  if(node == null)
	  	throw new Exception("int lit == null");
	  return node.asLiteral().getBoolean();
	}
	
	public boolean getLitBoolean(String var, boolean def)
	{
	  RDFNode node = line.get(var);
	  if(node == null)
	  	return def;
	  return node.asLiteral().getBoolean();
	}
	
}
