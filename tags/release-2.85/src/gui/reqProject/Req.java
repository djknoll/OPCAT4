package gui.reqProject;

import java.util.Calendar;
import java.util.Random;


public class Req {

	private String name;
	
	/**
	 * this holds only the indentation of the req like 
	 * 1 or 1.3 or 3.5.6
	 */
	private String reqIndentNumber ; 

	/**
	 * internal req id
	 */
	private long id ;
	
	/**
	 * holdes the req ID from the extrnal req file
	 */
	private String extId ;	
	
	/**
	 * req description 
	 */
	private String description;

	/**
	 * req risk.
	 */
	private String risk;
	
	
	public Req() {
		
		this.extId =  this.generateGlobalID();
		this.id = this.GetIDFromExtId(this.extId) ; 
		
	}

	private long GetIDFromExtId(String extId) {

		char[] chId = extId.toCharArray() ;
		String tempID =""  ; 
		for (int i = 0 ; i < chId.length; i++) {
			tempID = tempID + (int) chId[i] ; 
		}
		return   Long.valueOf(tempID).longValue();
	}
	
	
	public Req(String extId) {
		
		this.extId = extId ;
		this.id =  this.GetIDFromExtId(extId);
	}
	
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getId() {
		return this.id;
	}


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRisk() {
		return this.risk;
	}

	public void setRisk(String risk) {
		this.risk = risk;
	}

	public String getReqIndentNumber() {
		return this.reqIndentNumber;
	}

	public void setReqIndentNumber(String reqIndentNumber) {
		this.reqIndentNumber = reqIndentNumber;
	}

	public String getExtId() {
		return this.extId;
	}

	public void setExtId(String extId) {
		this.extId = extId;
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

}
