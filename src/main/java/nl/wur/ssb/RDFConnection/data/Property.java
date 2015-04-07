package nl.wur.ssb.RDFConnection.data;

import nl.wur.ssb.RDFConnection.RDFConnection;

public class Property
{
	//private Domain domain;
	private String iri;
	public Property(Domain domain,String iri)
	{
		//this.domain = domain;
		this.iri = domain.getConn().expand(iri);
		domain.addProperty(iri,this);
		domain.getConn().add(iri,"rdf:type","rdf:Property");
	}
	
	public String getRDFString()
	{
		return this.iri;
	}
}

