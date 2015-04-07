package nl.wur.ssb.RDFConnection.data;

import java.util.HashMap;

import nl.wur.ssb.RDFConnection.RDFConnection;

public class Domain
{
	private RDFConnection conn;
	private HashMap<String,Property> propMap = new HashMap<String,Property>();	
	private HashMap<String,RDFType> typeMap = new HashMap<String,RDFType>();
	public Domain(RDFConnection conn)
	{
		this.conn = conn;
	}
	public RDFConnection getConn()
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
