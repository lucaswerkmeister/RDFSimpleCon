package nl.wur.ssb.RDFConnection;

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

}
