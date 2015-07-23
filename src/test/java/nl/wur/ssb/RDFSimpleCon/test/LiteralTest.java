package nl.wur.ssb.RDFSimpleCon.test;

import static org.junit.Assert.assertEquals;
import nl.wur.ssb.RDFSimpleCon.RDFSimpleCon;

import org.junit.Test;

import com.hp.hpl.jena.query.ResultSet;

public class LiteralTest
{
  @Test
  public void testString() throws Exception {
  	RDFSimpleCon con = new RDFSimpleCon("");
  	con.addLit("http://test.com/testSubject","http://test.com/testPred","teststring");
  	ResultSet result = con.runQueryDirect("SELECT * WHERE { ?x ?y \"teststring\"}"); //^^<http://www.w3.org/2001/XMLSchema#string>}
    assertEquals(true,result.hasNext());
  //	result = con.runQueryDirect("SELECT * WHERE { ?x ?y \"teststring\"^^<http://www.w3.org/2001/XMLSchema#string>}"); //^^<http://www.w3.org/2001/XMLSchema#string>}
  //  assertEquals(true,result.hasNext());
  }
}
