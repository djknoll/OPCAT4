package org.objectprocess.Client;

import java.util.Date;

public class ChatMessageObject extends java.lang.Object
   implements java.io.Serializable
  {
    private Integer mySenderID;
    private String mySenderLoginName;
    private String myMessageBody;
    private Date myTimeStamp;

   public ChatMessageObject(
      Integer senderID,
      String senderLoginName,
      String messageBody,
      Date timeStamp)
   {
     mySenderID = senderID;
     mySenderLoginName = senderLoginName;
     myMessageBody = messageBody;
     myTimeStamp= timeStamp;

   }
//getter functions
   public Integer getSenderId() {
     return mySenderID;
   }
   public String getSenderLoginName() {
     return mySenderLoginName;
   }
   public String getMessageBody() {
     return myMessageBody;
   }
   public Date getTimeStamp() {
     return myTimeStamp;
   }
 }