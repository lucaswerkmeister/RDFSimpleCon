package nl.wur.ssb.RDFSimpleCon.data;

import nl.wur.ssb.RDFSimpleCon.RDFSimpleCon;

public class Property
{
	//private Domain domain;
	private String iri;
	public Property(Domain domain,String iri)
	{
		this(domain,iri,true);
	}
	
	public Property(Domain domain,String iri,boolean check)
	{
		//this.domain = domain;
		this.iri = domain.getConn().expand(iri);
		domain.addProperty(this.iri,this,check);
		domain.getConn().add(this.iri,"rdf:type","rdf:Property");
	}

	
	public String getRDFString()
	{
		return this.iri;
	}
}

