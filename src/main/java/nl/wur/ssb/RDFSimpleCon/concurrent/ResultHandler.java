package nl.wur.ssb.RDFSimpleCon.concurrent;

import nl.wur.ssb.RDFSimpleCon.ResultLine;

public interface ResultHandler
{
	public void handleResult(ResultLine item,int count) throws Exception;
}
