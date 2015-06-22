package gui.reqProject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collections;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

public class CSVLoad {

	private Vector rows = new Vector();

	public CSVLoad(File file) {
		try {

			//file = new File("req.csv");

			BufferedReader bufRdr = new BufferedReader(new FileReader(file));
			String line = null;

			// read each line of text file
			while ((line = bufRdr.readLine()) != null) {
				Vector row  = new Vector();
				StringTokenizer st = new StringTokenizer(line, ",");
				while (st.hasMoreTokens()) {
					// get next token and store it in the array
					row.add(st.nextToken());
				}
				this.rows.add(row);
			}

			// close the file
			bufRdr.close();
		} catch (Exception e) {
			// Fail msg
		}
	}

	public Iterator getRowsIterator() {
		return Collections.list(this.rows.elements()).iterator() ; 
	}
	
	public Vector getRowAt(int i) {
		return (Vector) this.rows.elementAt(i) ; 
	}
}
