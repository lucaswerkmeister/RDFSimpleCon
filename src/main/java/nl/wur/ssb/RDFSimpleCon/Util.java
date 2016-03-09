package nl.wur.ssb.RDFSimpleCon;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.VFS;

public class Util
{
	public static InputStream getResourceFile(String file) throws IOException
	{
		return Util.class.getResourceAsStream("/" + file);
	}
	
	public static String readFile(String file) throws IOException
	{
		return IOUtils.toString(Util.getResourceFile(file));
	}

}
