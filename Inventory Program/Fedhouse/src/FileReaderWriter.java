import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

class FileReaderWriter implements Serializable
{
	private FileOutputStream fileOut;
	private FileInputStream fileIn;
	private ObjectOutputStream objectOut;
	private ObjectInputStream objectIn;
		
	public FileReaderWriter()
	{
	}
	
	public void writeToFile(String file, Object obj)
	{
	      try
	      {
	         fileOut = new FileOutputStream(file);
	         objectOut = new ObjectOutputStream(fileOut);
	         objectOut.writeObject(obj);
	         objectOut.close();
	         fileOut.close();
	      }
	      catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	}
	
	public Object readFromFile(String file)
	{
		Object obj;
		
        try
        {
           fileIn = new FileInputStream(new File(file));
           objectIn = new ObjectInputStream(fileIn);
           obj = objectIn.readObject();
           objectIn.close();
           fileIn.close();
        }
           catch(IOException i)
        {
           i.printStackTrace();
           return null;
        }
           catch(ClassNotFoundException c)
        {
           c.printStackTrace();
           return null;
        }
        
        return obj;
	}
}