package nl.wur.ssb.RDFSimpleCon.concurrent;

public interface Task
{
	public void run(Object ... args) throws Exception;
}
