package nl.wur.ssb.RDFSimpleCon.data;

import java.util.HashMap;

import nl.wur.ssb.RDFSimpleCon.RDFSimpleCon;

public class Domain
{
	private RDFSimpleCon conn;
	private HashMap<String,Property> propMap = new HashMap<String,Property>();	
	private HashMap<String,RDFType> typeMap = new HashMap<String,RDFType>();
	public Domain(RDFSimpleCon conn)
	{
		this.conn = conn;
	}
	public RDFSimpleCon getConn()
	{
		return this.conn;
	}
	
	public void addProperty(String iri,Property prop) 
	{
		if(this.propMap.containsKey(iri))
			throw new RuntimeException("Property " + iri + " already defined");
		this.propMap.put(iri,prop);
	}
	public Property getProperty(String iri)
	{
		iri = this.getConn().expand(iri);
		if(!this.propMap.containsKey(iri))
			throw new RuntimeException("Property" + iri + "not defined");
		return this.propMap.get(iri);
	}
	
	public void addType(String iri,RDFType type) 
	{
		if(this.typeMap.containsKey(iri))
			throw new RuntimeException("Type " + iri + " already defined");
		this.typeMap.put(iri,type);
	}
	public RDFType getType(String iri)
	{
		iri = this.getConn().expand(iri);
		if(!this.typeMap.containsKey(iri))
			throw new RuntimeException("Type" + iri + "not defined");
		return this.typeMap.get(iri);
	}
}
