package gui.metaDataProject;

import gui.opdProject.OpdProject;

import java.io.File;
import java.util.Iterator;
import java.util.Vector;

public class MetaProjectLoader implements MetaLoader {

	private MetaLoader loader = null;

	private MetaStatus status = new MetaStatus();


	public MetaProjectLoader(MetaSourceType type, Object source) {

		if (type.getType() == MetaSourceType.TEXT_FILE_CSV) {
			loadCSV((String) source);
		}

		if (type.getType() == MetaSourceType.OPCAT_PROJECT) {
			loadProject((OpdProject) source);
		}
		
		if (type.getType() == MetaSourceType.OPCAT_OPD) {
			loadOpd((OpdProject) ((Object[]) source)[0],   ((Long) ((Object[]) source)[1]).longValue());
		}		
	}
	
	private void loadProject(OpdProject project) {
		loader = new OpdProjectLoader(project) ;
	}
	
	private void loadOpd(OpdProject project,  long opdID) {
		loader = new OpdLoader(project, opdID) ;
	}	

	private void loadCSV(String file) {
		File csvFile = new File(file);
		if (!csvFile.exists()) {
			status.setLoadFail(true);
			status.setFailReason("File does not exist");
			return;
		}

		try {
			loader = new CSVFileLoader(file);
		} catch (Exception ex) {
			status.setLoadFail(false) ; 
			status.setFailReason(ex.getMessage()) ; 
		}

		if (!loader.hasIDData()) {
			// NO ID in FILE so we can not continue
			status.setLoadFail(true);
			status.setFailReason("No ID in File");
			return;
		} else {
			status.setLoadFail(false);
		}

	}

	public String getGlobalID() {
		return loader.getGlobalID();
	}

	public Vector getHeaders() {
		return loader.getHeaders();
	}

	public Vector getRowAt(int i) {
		return loader.getRowAt(i);
	}

	public Iterator getRowsIterator() {
		return loader.getRowsIterator();
	}

	public int getSize() {
		return loader.getSize();
	}

	public String getName(Vector row) {
		return loader.getName(row);
	}

	public int getColoringData(Vector row) {
		return loader.getColoringData(row);
	}

	public String getExtID(Vector row) {
		return loader.getExtID(row);
	}

	public int getColoringIndex() {
		return loader.getColoringIndex();
	}
	
	public String getName() {
		return loader.getName();
	}

	public boolean hasColoringData() {
		return loader.hasColoringData();
	}

	public boolean hasIDData() {
		return loader.hasIDData();
	}

	public boolean hasNameData() {
		return loader.hasNameData();
	}

	public MetaStatus getStatus() {
		return status;
	}
	
	public String getPath() {
		return loader.getPath();
	}

	public boolean isShowColorValueAsPrograssBar() {
	    // TODO Auto-generated method stub
	    return loader.isShowColorValueAsPrograssBar();
	}



}
