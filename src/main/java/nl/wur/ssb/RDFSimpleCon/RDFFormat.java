package nl.wur.ssb.RDFSimpleCon;

import org.apache.jena.riot.Lang;

public enum RDFFormat
{
	//"RDF/XML", "RDF/XML-ABBREV", "N-TRIPLE", "TURTLE", (and "TTL") and "N3
	RDF_XML, RDF_XML_ABBREV, N_TRIPLE, TURTLE, N3, JSONLD;
	
	public String toString()
	{
		switch(this){
			case RDF_XML: return "RDF/XML";
			case RDF_XML_ABBREV: return "RDF/XML-ABBREV";
			case N_TRIPLE: return "N-TRIPLE";
			case TURTLE: return "TURTLE";
			case N3: return "N3";
			case JSONLD: return "JSON-LD";
		}
		return "null";
	}
	
	public Lang getLang()
	{
		switch(this){
			case RDF_XML: return Lang.RDFXML;
			case RDF_XML_ABBREV: return Lang.RDFXML;
			case N_TRIPLE: return Lang.NT;
			case TURTLE: return Lang.TURTLE;
			case N3: return Lang.N3;
			case JSONLD: return Lang.JSONLD;
		}
		return null;	
	}
	
	public static RDFFormat getFormat(String in)
	{
		if(in.equals("RDF/XML") || in.toLowerCase().equals("rdf"))
			return RDF_XML;
		if(in.equals("RDF/XML-ABBREV"))
			return RDF_XML_ABBREV;
		if(in.equals("N-TRIPLE") || in.toLowerCase().equals("nt"))
			return N_TRIPLE;
		if(in.equals("TURTLE") || in.toLowerCase().equals("ttl"))
			return TURTLE;
		if(in.equals("N3") || in.toLowerCase().equals("n3"))
			return N3;		
		if(in.equals("JSON-LD") || in.toLowerCase().equals("jsonld"))
			return JSONLD;		
	  throw new RuntimeException("Unknow rdf format: " + in);
	}

}
