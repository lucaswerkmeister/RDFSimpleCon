package nl.wur.ssb.RDFConnection.data;


public class RDFObject
{
	private String iri; 
	private Domain domain;
	//private RDFType type;
	public RDFObject(Domain domain,String iri,RDFType type)
	{
		this.iri = iri;
		this.domain = domain;
		//this.type = type;
		
		domain.getConn().add(iri,"rdf:type",type.getRDFString());
	}
	public RDFObject(Domain domain,String iri,String type)
	{
		this(domain,iri,domain.getType(type));
	}
	public void add(Property prop,RDFObject object)
	{
		this.domain.getConn().add(this.iri,prop.getRDFString(),object.iri);
	}
	public void add(String prop,RDFObject object)
	{
		this.add(domain.getProperty(prop),object);
	}
	
	public void addLit(Property prop,String lit)
	{
		this.domain.getConn().addLit(this.iri,prop.getRDFString(),lit);
	}
	public void addLit(String prop,String lit)
	{
		this.addLit(domain.getProperty(prop),lit);
	}
	
	public void add(Property prop,int lit)
	{
		this.domain.getConn().add(this.iri,prop.getRDFString(),lit);
	}
	public void add(String prop,int lit)
	{
		this.add(domain.getProperty(prop),lit);
	}
	
	public void add(Property prop,double lit)
	{
		this.domain.getConn().add(this.iri,prop.getRDFString(),lit);
	}
	public void add(String prop,double lit)
	{
		this.add(domain.getProperty(prop),lit);
	}
	
	public void add(Property prop,float lit)
	{
		this.domain.getConn().add(this.iri,prop.getRDFString(),lit);
	}
	public void add(String prop,float lit)
	{
		this.add(domain.getProperty(prop),lit);
	}
	
	public void add(Property prop,boolean lit)
	{
		this.domain.getConn().add(this.iri,prop.getRDFString(),lit);
	}
	public void add(String prop,boolean lit)
	{
		this.add(domain.getProperty(prop),lit);
	}
}
