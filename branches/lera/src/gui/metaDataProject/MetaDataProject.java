package gui.metaDataProject;

import gui.util.OpcatLogger;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

public class MetaDataProject {

	private String path;

	private String type;

	private String globalID = "";

	private Vector headers = new Vector();

	InputStream fileStream;

	HashMap hm = null;

	MetaStatus status = new MetaStatus();

	public MetaDataProject(InputStream is, String file) {
		// globalID = generateGlobalID() ;
		this.globalID = file;
		this.fileStream = is;
		this.Init(file, this.globalID);
	}

	public MetaDataProject(String file, String globalID) {
		try {
			this.fileStream = new BufferedInputStream(new FileInputStream(
					new File(file)), 4096);
			this.Init(file, globalID);
		} catch (Exception e) {
			OpcatLogger.logError(e);
		}
	}

	private void Init(String file, String globalID) {
		this.path = file;
		this.hm = new HashMap();
		this.globalID = globalID;

		// load the file here.
		/**
		 * TODO change this to a generic loader.
		 */
		File csvFile = new File(file);
		if (!csvFile.exists()) {
			status.setLoadFail(true);
			status.setFailReason("File does not exist");
			return;
		}

		// ***************************************
		CSVLoad loader = new CSVLoad(csvFile);
		/** ************************************ */

		this.type = loader.getType();

		headers = loader.getHeaders();
		int nameIndex = -1;
		for (int i = 0; i < headers.size(); i++) {
			if (((String) headers.get(i)).equalsIgnoreCase("name")) {
				nameIndex = i;
				break;
			}
		}

		int levelIndex = -1;
		for (int i = 0; i < headers.size(); i++) {
			if (((String) headers.get(i)).toLowerCase().contains("level")) {
				levelIndex = i;
				break;
			}
		}

		int idIndex = -1;
		for (int i = 0; i < headers.size(); i++) {
			String hed = (String) headers.get(i);
			if (hed.equalsIgnoreCase("id")
					|| hed.equalsIgnoreCase("Object Identifier")
					|| hed.equalsIgnoreCase("Absolute Number")) {
				idIndex = i;
				break;
			}
		}

		if (idIndex == -1) {
			// NO ID in FILE so we can not continue
			status.setLoadFail(true);
			status.setFailReason("No ID in File");
			return;
		}

		Iterator rows = loader.getRowsIterator();
		while (rows.hasNext()) {
			Vector row = (Vector) rows.next();
			MetaDataItem req = new MetaDataItem(this.getType(), (String) row
					.elementAt(idIndex));
			req.setAllData(row);
			req.setNameIndex(nameIndex);
			req.setLevelIndex(levelIndex);
			this.hm.put(new Long(req.getId()), req);
		}

	}

	public String getType() {
		return this.type;
	}

	public String getGlobalID() {
		return this.globalID;
	}

	public Collection values() {
		return this.hm.values();
	}

	protected String generateGlobalID() {
		Calendar rightNow = Calendar.getInstance();
		long nowMillis = rightNow.getTimeInMillis();
		Random generator = new Random(nowMillis);
		long randomLong = generator.nextLong();
		if (randomLong < 0) {
			randomLong *= -1;
		}
		String globalID = String.valueOf(randomLong) + "."
				+ String.valueOf(nowMillis);
		return globalID;
	}

	public MetaDataItem getMetaDataItem(long reqID) {
		return (MetaDataItem) this.hm.get(new Long(reqID));
	}

	public String getPath() {
		return this.path;
	}

	public Vector getHeaders() {
		return headers;
	}

	public MetaStatus getStatus() {
		return status;
	}

	public void setStatus(MetaStatus status) {
		this.status = status;
	}

	/**
	 * return the maximum Level value of items in this library
	 * 
	 * @return
	 */
	public int getMaxLevelValue() {

		int max = -1;
		Iterator iter = hm.values().iterator();

		while (iter.hasNext()) {
			MetaDataItem item = (MetaDataItem) iter.next();
			int level = item.getLevel();
			if (max < level)
				max = level;
		}

		return max;
	}

	public int getLevelIndex() {
		int index = -1;
		Iterator iter = hm.values().iterator();

		while (iter.hasNext()) {
			MetaDataItem item = (MetaDataItem) iter.next();
			index = item.getLevelIndex();
			if (index >= 0)
				break;
		}

		return index;

	}

}
