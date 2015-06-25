package nl.wur.ssb.RDFConnection.concurrent;

public interface Task
{
	public void run(Object ... args) throws Exception;
}
