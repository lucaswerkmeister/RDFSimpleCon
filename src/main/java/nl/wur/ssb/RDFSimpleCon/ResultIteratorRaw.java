package nl.wur.ssb.RDFSimpleCon;

import java.util.HashMap;
import java.util.Iterator;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;

class ResultIteratorRaw implements Iterator<HashMap<String,RDFNode>>
{
	private ResultSet resultSet;

	ResultIteratorRaw(ResultSet resultSet)
	{
		this.resultSet = resultSet;
	}

	@Override
	public boolean hasNext()
	{
		return resultSet.hasNext();
	}

	@Override
	public HashMap<String,RDFNode> next()
	{
		QuerySolution sol = resultSet.next();
		HashMap<String,RDFNode> map = new HashMap<String,RDFNode>();
		for(String name : new Iteration<String>(sol.varNames()))
		{
			map.put(name,sol.get(name));
		}
		return map;
	}

	@Override
	public void remove()
	{

	}

}
