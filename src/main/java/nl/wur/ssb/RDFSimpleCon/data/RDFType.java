package nl.wur.ssb.RDFSimpleCon.data;

import java.util.HashMap;

public class RDFType
{
	private Domain domain;
	private String iri;
	//private HashMap<String,RDFType> parents = new HashMap<String,RDFType>();
	
	public RDFType(Domain domain,String iri)
	{
		this(domain,iri,true);
	}
	
	public RDFType(Domain domain,String iri,boolean check)
	{
		this.domain = domain;
		this.iri = domain.getConn().expand(iri);
		domain.addType(this.iri,this,check);
		domain.getConn().add(this.iri,"rdf:type","owl:Class");
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
