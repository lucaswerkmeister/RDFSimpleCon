package nl.wur.ssb.RDFConnection;

import java.util.HashMap;
import java.util.Iterator;

import com.hp.hpl.jena.rdf.model.RDFNode;

public class ResultIterator implements Iterator<ResultLine>
{
	private Iterator<HashMap<String,RDFNode>> resultSet;

	public ResultIterator(Iteration<HashMap<String,RDFNode>> resultSet)
	{
		this.resultSet = resultSet.iterator();
	}

	@Override
	public boolean hasNext()
	{
		return resultSet.hasNext();
	}

	@Override
	public ResultLine next()
	{
		return new ResultLine(resultSet.next());
	}

	@Override
	public void remove()
	{

	}

}
