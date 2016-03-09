package nl.wur.ssb.RDFSimpleCon;

import java.util.HashMap;
import java.util.Iterator;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.rdf.model.RDFNode;

public class ResultIterator implements Iterator<ResultLine>
{
	private Iterator<HashMap<String,RDFNode>> resultSet;
	private QueryExecution qe;

	public ResultIterator(Iteration<HashMap<String,RDFNode>> resultSet,QueryExecution qe)
	{
		this.resultSet = resultSet.iterator();
		this.qe = qe;
	}

	@Override
	public boolean hasNext()
	{
		if(!resultSet.hasNext())
		{
			if(qe != null)
				qe.close();
			return false;
		}
		return true;
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
