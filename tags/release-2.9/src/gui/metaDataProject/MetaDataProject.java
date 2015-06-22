package gui.metaDataProject;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

public class MetaDataProject implements MetaLoader {

	private MetaSourceType sourceType = new MetaSourceType(
			MetaSourceType.UNDEFINED);

	InputStream fileStream;

	HashMap hm = null;

	MetaStatus status = new MetaStatus();

	MetaLoader loader;
	
	

	public MetaDataProject(Object source, MetaSourceType type) {
		sourceType = type;
		// globalID = generateGlobalID() ;
		loader = new MetaProjectLoader(sourceType, source);

		this.hm = new HashMap();
		if (!loader.getStatus().isLoadFail() && loader.hasIDData()) {
			Iterator rows = loader.getRowsIterator();
			while (rows.hasNext()) {
				Vector row = (Vector) rows.next();
				MetaDataItem req = new MetaDataItem( loader
						.getExtID(row), loader.getGlobalID());
				req.setAllData(row);
				req.setName(loader.getName(row));
				req.setColoringLevel(loader.getColoringData(row));
				this.hm.put(new Long(req.getId()), req);
			}
			status.setLoadFail(false) ; 
		} else {
			status.setLoadFail(true);
			status.setFailReason(loader.getStatus().getFailReason());
		}
	}

	public String getName() {
		return loader.getName();
	}

	public String getGlobalID() {
		return loader.getGlobalID();
	}

	public Collection values() {
		return this.hm.values();
	}
	
	public String getPath() {
		return loader.getPath() ; 
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

	public Vector getHeaders() {
		return loader.getHeaders();
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
	public int getMaxColoringLevelValue() {

		int max = -1;
		Iterator iter = hm.values().iterator();

		while (iter.hasNext()) {
			MetaDataItem item = (MetaDataItem) iter.next();
			int level = item.getColoringLevel();
			if (max < level)
				max = level;
		}

		return max;
	}

	public boolean isColoring() {
		return loader.hasColoringData();
	}

	public int getColoringIndex() {
		return loader.getColoringIndex();
	}

	public String getType() {
		return loader.getName();
	}

	public MetaSourceType getSourceType() {
		return sourceType;
	}

	public int getColoringData(Vector row) {
		// TODO Auto-generated method stub
		return loader.getColoringData(row);
	}

	public String getExtID(Vector row) {
		// TODO Auto-generated method stub
		return loader.getExtID(row);
	}

	public String getName(Vector row) {
		// TODO Auto-generated method stub
		return loader.getName();
	}

	public Vector getRowAt(int i) {
		// TODO Auto-generated method stub
		return loader.getRowAt(i);
	}

	public Iterator getRowsIterator() {
		// TODO Auto-generated method stub
		return loader.getRowsIterator();
	}

	public int getSize() {
		// TODO Auto-generated method stub
		return loader.getSize();
	}

	public boolean hasColoringData() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean hasIDData() {
		// TODO Auto-generated method stub
		return loader.hasIDData();
	}

	public boolean hasNameData() {
		// TODO Auto-generated method stub
		return loader.hasNameData();
	}

}
