package nl.wur.ssb.RDFSimpleCon;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;

import org.apache.commons.io.IOUtils;

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

  
  public static String prepareFileFromJar(String file) throws IOException
  {
    return Util.prepareFileIntFromJar(file,false,true);
  }

  public static String prepareBinaryFromJar(String file) throws IOException
  {
    return Util.prepareFileIntFromJar(file,true,true);
  }
  
  public static String getTempFile(String file) throws IOException
  {
    return Util.prepareFileIntFromJar(file,false,false);
  }
  
  public static void cleanTemp() throws IOException
  {
    String tempPath = Util.getTempPath();
    (new File(tempPath)).delete();    
  }
  
  private static String prepareFileIntFromJar(String file, boolean isBinary,boolean doCreate) throws IOException
  {
    // Copies to location next to JAR file
    String tempPath = Util.getTempPath();
    (new File(tempPath)).mkdir();
    File outBinFileName = new File(tempPath + file.substring(Math.max(0,file.lastIndexOf("/"))));
    if(!outBinFileName.exists() && doCreate)
    {
      InputStream stream = Util.getResourceFile(file.replaceAll("\\{OS\\}",getOs()));
      IOUtils.copy(stream,new FileOutputStream(outBinFileName));
      if(isBinary)
        outBinFileName.setExecutable(true);
      System.out.println("Creating file: " + outBinFileName.toString());
    }
    return outBinFileName.toString();
  }
  
  private static String getTempPath() throws IOException
  {
    String path = Util.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    String jarPath = URLDecoder.decode(path,"UTF-8").toString();
    return jarPath.substring(0,jarPath.lastIndexOf("/")) + "/temp/";  
  }
  
  public static String getOs()
  {
    String os = System.getProperty("os.name").toLowerCase();
    if (os.indexOf("win") >= 0)
    {
      return ("windows");
    }
    else if (os.indexOf("mac") >= 0)
    {
      return ("mac");
    }
    else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0 || os.indexOf("aix") > 0)
    {
      return ("linux");
    }
    else
    {
      throw new RuntimeException("Could not determine operating system");
    }
  }
  
}
