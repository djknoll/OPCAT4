package extensionTools.uml.umlDiagrams;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * The class hides the file writing utilities.
 * @author Eran TOch
 *
 */
public class XmiWriter {

	FileOutputStream file = null;
	
	public XmiWriter(String filename)	{
		try {
			file = new FileOutputStream(filename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void write(byte[] bytes)	{
		try {
			file.write(bytes);
			String str = new String(bytes);
			System.out.println(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close()	{
		try {
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
