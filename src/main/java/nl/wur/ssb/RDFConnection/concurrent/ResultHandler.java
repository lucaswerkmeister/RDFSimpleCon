package nl.wur.ssb.RDFConnection.concurrent;

import nl.wur.ssb.RDFConnection.ResultLine;

public interface ResultHandler
{
	public void handleResult(ResultLine item,int count) throws Exception;
}
