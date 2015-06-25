package nl.wur.ssb.RDFSimpleCon;

import java.io.IOException;
import java.net.URL;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.VFS;

public class Util
{
	public static FileObject getResourceFile(String file) throws IOException
	{
	  String uri = null;
		URL url = Util.class.getClassLoader().getResource(".");
		if(url == null)
		{
   		uri = Util.class.getClassLoader().getResource("nl/wur/ssb/util/Util.class").toString();
			uri = uri.substring(0,uri.length() - "nl/wur/ssb/util/Util.class".length());
		}
		else
			uri = url.toString();
		//uri = uri.substring(0,uri.length() - "nl/wur/ssb/util/Util.class".length());
		if(uri.endsWith("bin/"))
			uri = uri.substring(0,uri.length() - "bin/".length()) + "resource/";
		uri = uri + file;
		/*
		FileObject[] children = jarFile.getChildren();
		System.out.println( "Children of " + jarFile.getName().getURI() );
		for ( int i = 0; i < children.length; i++ )
		{
		  System.out.println( children[ i ].getName().getBaseName() );
		}*/
		return VFS.getManager().resolveFile(uri);
	}

}
