package org.objectprocess.Client;

import gui.Opcat2;

import java.io.Serializable;
import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.objectprocess.team.ExceptionHandler;


/**
 * @author Dizza Beimel
 * @
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ChatMessageHandler {
	InitialContext ctx = null;
	TopicConnectionFactory topicConnectionFactory = null;
	Topic chatTopic = null;
	TopicConnection topicConnection = null;
	TopicSession topicSession = null;
	TopicSubscriber  topicSubscriber = null;
        TopicPublisher topicPublisher = null;
        Opcat2 myOpcat2;

  public ChatMessageHandler(Opcat2 opcat2) {
    myOpcat2 = opcat2;
  }

  public void closeConnection()
  {
    if (topicConnection != null) {
      try {
        topicConnection.close();
      } catch (Exception e) {
        ExceptionHandler exceptionHandler = new ExceptionHandler(e);
      }
    }
  }

  public void openConnection(Integer sessionID)
  {
    String messageSelector = "SessionID = " + sessionID;

    //	Create a JNDI API InitialContext object.
    try {
      Properties env = new Properties();
      env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
      env.put(Context.URL_PKG_PREFIXES,"org.jboss.naming:org.jnp.interfaces");
      env.put(Context.PROVIDER_URL, myOpcat2.getTeamMember().getServerAddr()+":1099");
//      env.put(Context.PROVIDER_URL, "localhost:1099");

      try{ ctx = new InitialContext(env);
      } catch (NamingException e) {
        ExceptionHandler exceptionHandler = new ExceptionHandler(e);
      }
    } catch (Exception e) {
      ExceptionHandler exceptionHandler = new ExceptionHandler(e);
    }

    //	Look up connection factories and topic.
    try {
      topicConnectionFactory = (TopicConnectionFactory) ctx.lookup("ConnectionFactory");
      chatTopic = (Topic) ctx.lookup("topic/chatTopic");
    } catch (NamingException e) {
      ExceptionHandler exceptionHandler = new ExceptionHandler(e);
    }

    try {
      topicConnection = topicConnectionFactory.createTopicConnection();
      topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
      topicSubscriber = topicSession.createSubscriber(chatTopic/*, messageSelector, true*/);
      topicSubscriber.setMessageListener(new ChatListener(myOpcat2));
      topicPublisher = topicSession.createPublisher(chatTopic);

      topicConnection.start(); }

    catch (Exception e) {
      ExceptionHandler exceptionHandler = new ExceptionHandler(e);
    }
  }


  public void publishMessageToTopic(
      Integer userID,
      Integer sessionID,
      int systemMessageCode,
      Serializable chatMessageObject) throws Exception {

    ObjectMessage message = null;

    try {

      message = topicSession.createObjectMessage();
      message.setIntProperty("ChatMessageType", systemMessageCode);
      message.setIntProperty("UserID", userID.intValue());
      message.setIntProperty("SessionID",sessionID.intValue());
      if (chatMessageObject != null)
        message.setObject(chatMessageObject);

      topicPublisher.publish(message);
    }catch (JMSException jmsE) {
      ExceptionHandler exceptionHandler = new ExceptionHandler(jmsE);
    }
  }
}



class ChatListener implements MessageListener {

  /* (non-Javadoc)
   * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
   */
  Opcat2 myOpcat2;

  public ChatListener(Opcat2 opcat2) {
    myOpcat2 = opcat2;
  }

  public void onMessage(Message message) {

    ObjectMessage objectMessage = (ObjectMessage)message;

    try{

      int messageType = objectMessage.getIntProperty("ChatMessageType");
      int senderID = objectMessage.getIntProperty("UserID");
      int sessionID = objectMessage.getIntProperty("SessionID");

      switch (messageType) {
        case ChatMessages.USER_CHAT_MESSAGE:
          //fetch the file object from the message
          ChatMessageObject chatMessageObject =
              (ChatMessageObject)objectMessage.getObject();
          //put the new message into the chat room area
          myOpcat2.getChatRoom().addNewLineToWriteArea(chatMessageObject);
          break;

        case ChatMessages.SYSTEM_CHAT_SESSION:
          break;
      }

//      System.out.println("the message was: " + messageType);
    } catch (Exception e) {e.printStackTrace();}
  }
}
