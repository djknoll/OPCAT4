package gui.metaDataProject;

import java.util.Calendar;
import java.util.Random;
import java.util.Vector;

public class MetaDataItem {

	/**
	 * internal req id
	 */
	private long id;

	private Vector data = new Vector();

	private int nameIndex = -1;

	private int levelIndex = -1;
	
	private String extID = " ";  

	private long GetIDFromExtId(String extId) {

		char[] chId = extId.toCharArray();
		String tempID = "";
		for (int i = 0; i < chId.length; i++) {
			tempID = tempID + (int) chId[i];
		}
		if (tempID.length() > 19) {
			tempID = tempID.substring(tempID.length() - 19, tempID.length());
		}
		return Long.valueOf(tempID.trim()).longValue();
	}

	public MetaDataItem(String type, String extId) {
		this.id = GetIDFromExtId(type + extId);
		extID = extId ; 
	}

	public long getId() {
		return this.id;
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

	public Vector getAllData() {
		return data;
	}

	public void setAllData(Vector data) {
		this.data = data;
	}

	public String getStringField(int index) {

		String str = (String) data.get(index);

		if (str != null) {
			return (String) data.get(index);
		} else {
			return " ";
		}
	}

	public Object getField(int index) {
		Object obj = data.get(index);
		if (obj != null) {
			return data.get(index);
		} else {
			return " ";
		}
	}

	public void setDataField(int index, Object data) {
		this.data.set(index, data);
	}

	public String getName() {
		String str = " ";

		if (nameIndex != -1) {
			str = (String) data.get(nameIndex);
		}
		return str;
	}

	public int getLevel() {
		Integer level = new Integer(-1) ; 

		if (levelIndex != -1) {
			level = new Integer((String) data.get(levelIndex));
		}
		return level.intValue();
	}

	public void setNameIndex(int nameIndex) {
		this.nameIndex = nameIndex;
	}

	public void setLevelIndex(int levelIndex) {
		this.levelIndex = levelIndex;
	}
	
	public int getLevelIndex() {
		return levelIndex; 
	}

	public String getExtID() {
		return extID;
	}	

}
