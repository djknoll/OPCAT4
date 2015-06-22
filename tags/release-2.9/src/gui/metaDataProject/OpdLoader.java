package gui.metaDataProject;

import gui.opdProject.Opd;
import gui.opdProject.OpdProject;
import gui.projectStructure.Entry;
import gui.util.OpcatLogger;

import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

public class OpdLoader implements MetaLoader {
	
	
	OpdProject project;
	
	long opdID ; 
	
	Opd opd ; 

	private Vector rows = new Vector();

	private Vector headers = new Vector();

	private int nameIndex = 2;

	private int idIndex = 0;

	private MetaStatus status = new MetaStatus();;
	

	OpdLoader(OpdProject project, long opdID) {

		try {
			this.project = project;
			this.opd = project.getSystemStructure().getOpd(opdID) ;
			this.opdID = opdID ; 

			headers.add("ID");
			headers.add("Type");
			headers.add("Name");
			headers.add("Description");

			Iterator iter = Collections.list(project.getSystemStructure().getEntriesInOpd(opd.getOpdId())).iterator() ; 
			while (iter.hasNext()) {
				Object obj = iter.next();
				//if (obj instanceof ThingEntry) {
					Entry entry = (Entry) obj;
					Vector row = new Vector();
					row = entry.getLogicalEntity().getAllData() ; 
					rows.add(row);
				//}
			}
			status.setLoadFail(false);
		} catch (Exception ex) {
			status.setLoadFail(true);
			status.setFailReason(ex.getMessage());
			OpcatLogger.logError(ex) ; 
		}
	}

	public int getColoringData(Vector row) {
		// TODO Auto-generated method stub
		return -1;
	}

	public int getColoringIndex() {
		// TODO Auto-generated method stub
		return -1;
	}

	public String getExtID(Vector row) {
		// TODO Auto-generated method stub
		return ((Long) row.elementAt(idIndex)).toString() ;
	}

	public String getGlobalID() {
		// TODO Auto-generated method stub
		return project.getGlobalID();
	}

	public Vector getHeaders() {
		// TODO Auto-generated method stub
		return headers;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return opd.getName();
	}

	public String getName(Vector row) {
		// TODO Auto-generated method stub
		return (String) row.elementAt(nameIndex);
	}

	public Vector getRowAt(int i) {
		// TODO Auto-generated method stub
		return (Vector) rows.elementAt(i);
	}

	public Iterator getRowsIterator() {
		// TODO Auto-generated method stub
		return rows.iterator();
	}

	public int getSize() {
		// TODO Auto-generated method stub
		return rows.size();
	}

	public boolean hasColoringData() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean hasIDData() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean hasNameData() {
		// TODO Auto-generated method stub
		return true;
	}

	public MetaStatus getStatus() {
		return status;
	}	
	
	public String getPath() {
		return opd.getName() + "@" + project.getPath() ; 
	}

}
