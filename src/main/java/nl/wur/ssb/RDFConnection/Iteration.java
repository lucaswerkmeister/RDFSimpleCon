package nl.wur.ssb.RDFConnection;

import java.util.Iterator;

public class Iteration<T> implements Iterable<T>
{
	private Iterator<T> iterator;
  public Iteration(Iterator<T> iterator)
  {
  	this.iterator = iterator;
  }
	
	@Override
	public Iterator<T> iterator()
	{
		return iterator;
	}

}
