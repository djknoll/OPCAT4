package gui.metaDataProject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

import com.csvreader.CsvReader;

public class CSVLoad {

	private String type = " ";

	private Vector rows = new Vector();

	private Vector headers = new Vector();

	private int size = 0;

	public CSVLoad(File file) {
		try {

			// file = new File("req.csv");
			

			BufferedReader bufRdr = new BufferedReader(new FileReader(file));
			String line = null;
			boolean skip = false ; 

			// First line is Type and Tab name
			if (file.getName().endsWith(".req")) {
				line = "Requirements";
			} else {
				line = bufRdr.readLine();
				skip = true ; 
			}
			type = line;
			bufRdr.close();
						
			
			CsvReader reader = new CsvReader(file.getPath(), ',', Charset.defaultCharset()) ;
			reader.setDelimiter(',') ;  
			reader.setTextQualifier('"') ; 
			reader.setUseTextQualifier(true) ; 
			
			if(skip) reader.skipLine() ; //skip the name of tab line
			reader.readHeaders() ; 	
			String [] hed = reader.getHeaders() ; 
			
			for(int i = 0 ; i < hed.length ; i++) {
				headers.add(hed[i]);
			}
			
			while (reader.readRecord())
			{
				Vector row = new Vector();
				
				for(int i = 0 ; i < hed.length; i++) {
					Object obj = reader.get(hed[i]) ; 
					row.add(obj); 
				}
				
				this.rows.add(row); 

			}		
			
			reader.close() ; 

//			
//			// seconed line has the headears.
//			line = bufRdr.readLine();
//			StringTokenizer st = new StringTokenizer(line, ",");
//			size = st.countTokens();
//			while (st.hasMoreTokens()) {
//				headers.add(st.nextToken());
//			}
//
//			// read each line of text file
//			while ((line = bufRdr.readLine()) != null) {
//				Vector row = new Vector();
//				st = new StringTokenizer(line, ",");
//				while (st.hasMoreTokens()) {
//					// get next token and store it in the array
//					row.add(st.nextToken());
//				}
//				this.rows.add(row);
//			}
//
//			// close the file
//			bufRdr.close();
		} catch (Exception e) {
			// Fail msg
		}
	}

	public Iterator getRowsIterator() {
		return Collections.list(this.rows.elements()).iterator();
	}

	public Vector getRowAt(int i) {
		return (Vector) this.rows.elementAt(i);
	}

	public Vector getHeaders() {
		return headers;
	}

	public int getSize() {
		return size;
	}

	public String getType() {
		return type;
	}
}
