package org.objectprocess.Client;

import java.util.Date;

public class ChatMessageObject extends java.lang.Object implements
		java.io.Serializable {
	 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	 

	private Integer mySenderID;

	private String mySenderLoginName;

	private String myMessageBody;

	private Date myTimeStamp;

	public ChatMessageObject(Integer senderID, String senderLoginName,
			String messageBody, Date timeStamp) {
		this.mySenderID = senderID;
		this.mySenderLoginName = senderLoginName;
		this.myMessageBody = messageBody;
		this.myTimeStamp = timeStamp;

	}

	// getter functions
	public Integer getSenderId() {
		return this.mySenderID;
	}

	public String getSenderLoginName() {
		return this.mySenderLoginName;
	}

	public String getMessageBody() {
		return this.myMessageBody;
	}

	public Date getTimeStamp() {
		return this.myTimeStamp;
	}
}