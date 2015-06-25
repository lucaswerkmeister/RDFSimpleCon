package nl.wur.ssb.RDFSimpleCon;

import org.apache.jena.riot.Lang;

public enum RDFFormat
{
	//"RDF/XML", "RDF/XML-ABBREV", "N-TRIPLE", "TURTLE", (and "TTL") and "N3
	RDF_XML, RDF_XML_ABBREV, N_TRIPLE, TURTLE, N3;
	
	public String toString()
	{
		switch(this){
			case RDF_XML: return "RDF/XML";
			case RDF_XML_ABBREV: return "RDF/XML-ABBREV";
			case N_TRIPLE: return "N-TRIPLE";
			case TURTLE: return "TURTLE";
			case N3: return "N3";
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
		}
		return null;	
	}
	
	public static RDFFormat getFormat(String in)
	{
		if(in.equals("RDF/XML"))
			return RDF_XML;
		if(in.equals("RDF/XML-ABBREV"))
			return RDF_XML_ABBREV;
		if(in.equals("N-TRIPLE"))
			return N_TRIPLE;
		if(in.equals("TURTLE"))
			return TURTLE;
		if(in.equals("N3"))
			return N3;		
	  throw new RuntimeException("Unknow rdf format: " + in);
	}

}
