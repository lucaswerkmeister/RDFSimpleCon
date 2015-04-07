package nl.wur.ssb.RDFConnection.data;

import java.util.HashMap;

public class RDFType
{
	private Domain domain;
	private String iri;
	//private HashMap<String,RDFType> parents = new HashMap<String,RDFType>();
	public RDFType(Domain domain,String iri)
	{
		this.domain = domain;
		this.iri = domain.getConn().expand(iri);
		domain.addType(iri,this);
		domain.getConn().add(iri,"rdf:type","owl:Class");
	}
	
	public void addSubClassOf(RDFType type)
	{
		this.domain.getConn().add(iri,"rdfs:subClassOf",type.getRDFString());
	//	this.parents.put(type.iri,type);
	}
	
	public String getRDFString()
	{
		return this.iri;
	}
}
