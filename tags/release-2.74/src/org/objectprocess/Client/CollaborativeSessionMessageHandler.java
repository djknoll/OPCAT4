/*
 * Created on 31/12/2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package org.objectprocess.Client;

import gui.Opcat2;

import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import java.util.Properties;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JOptionPane;

import org.objectprocess.SoapClient.CollaborativeSessionValue;
import org.objectprocess.cmp.CollaborativeSessionFileValue;
import org.objectprocess.team.ExceptionHandler;
/**
 * @author moonwatcher
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CollaborativeSessionMessageHandler {
  InitialContext ctx = null;
  TopicConnectionFactory topicConnectionFactory = null;
  Topic collaborativeSessionTopic = null;
  TopicConnection topicConnection = null;
  TopicSession topicSession = null;
  TopicSubscriber  topicSubscriber = null;
  protected Opcat2 myOpcat2;

  public CollaborativeSessionMessageHandler(Opcat2 opcat2) {
  myOpcat2 = opcat2;
  }

  public void closeConnection(){
		if (topicConnection != null) {
			try {
				topicConnection.close();
			} catch (Exception e) {
                        ExceptionHandler exceptionHandler = new ExceptionHandler(e);
                        }
		}
	}
  public void openConnection(Integer sessionID) {

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
      collaborativeSessionTopic = (Topic) ctx.lookup("topic/collaborativeSessionTopic");
    } catch (NamingException e) {
      ExceptionHandler exceptionHandler = new ExceptionHandler(e);
    }

    try {
      topicConnection = topicConnectionFactory.createTopicConnection();
      topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
      topicSubscriber = topicSession.createSubscriber(collaborativeSessionTopic, messageSelector, true);
      topicSubscriber.setMessageListener(new CollaborativeSessionListener(myOpcat2));
      topicConnection.start(); }
    catch (Exception e) {
      ExceptionHandler exceptionHandler = new ExceptionHandler(e);
    }
  }
}

class CollaborativeSessionListener implements MessageListener {
  /* (non-Javadoc)
   * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
   */
  private final static String fileSeparator = System.getProperty("file.separator");
  Opcat2 myOpcat2;

  public CollaborativeSessionListener(Opcat2 opcat2) {
  myOpcat2 = opcat2;
  }

  public void onMessage(Message message) {

    ObjectMessage objectMessage = (ObjectMessage)message;

    try{

      int messageType = objectMessage.getIntProperty("SystemMessageType");
      int senderID = objectMessage.getIntProperty("UserID");
      int sessionID = objectMessage.getIntProperty("SessionID");
      String LoggedInMemberName = null;
      ChatMessageObject chatMessageObject = null;


      switch (messageType) {
        case SystemMessages.USER_LOGGED_INTO_SESSION:
          //if the recieved message was sent by me -ignore it.
          if (myOpcat2.getTeamMember().getLocalEnhancedUser().getUserID().intValue() == senderID)
            return;

          myOpcat2.getTeamMember().updateLocalMemberLoggedInStatus(senderID);
          myOpcat2.getRepository().getAdmin().updateMemberIcon(senderID);
          LoggedInMemberName = myOpcat2.getTeamMember().getLocalMemberName(senderID);

          //add a system message to the chat room area regarding the event
          chatMessageObject = new ChatMessageObject(
              new Integer(senderID),
              "System",
              LoggedInMemberName + " Logged into the session",
              new Date());
          myOpcat2.getChatRoom().addNewLineToWriteArea(chatMessageObject);
          break;

        case SystemMessages.USER_LOGGED_OUT_OF_SESSION:
          //if the recieved message was sent by me -ignore it.
          if (myOpcat2.getTeamMember().getLocalEnhancedUser().getUserID().intValue() == senderID)
            return;

          myOpcat2.getTeamMember().updateLocalMemberLoggedOutStatus(senderID);
          myOpcat2.getRepository().getAdmin().updateMemberIcon(senderID);
          LoggedInMemberName = myOpcat2.getTeamMember().getLocalMemberName(senderID);

          //add a system message to the chat room area regarding the event
          chatMessageObject = new ChatMessageObject(
              new Integer(senderID),
              "System",
              LoggedInMemberName + " Logged out of session",
              new Date());
          myOpcat2.getChatRoom().addNewLineToWriteArea(chatMessageObject);
          break;

        case SystemMessages.USER_COMMITED_SESSION:
          //if the recieved message was sent by me -ignore it.
          if (myOpcat2.getTeamMember().getLocalEnhancedUser().getUserID().intValue() == senderID)
            return;

         //other active members, presents a message on the screen,
         // then loguot from session, and refresh the control panel
          JOptionPane.showMessageDialog(Opcat2.getFrame(),
                              "This session was succesfuly commited by an authorized user." + "\n" +
                              "A new revision based on this session was created on the server." + "\n" +
                              "The session will be closed now.",
                              "Session committed and closed",
                              JOptionPane.INFORMATION_MESSAGE);

          myOpcat2.getActiveCollaborativeSession().logoutFromSession();
          myOpcat2.getRepository().refreshOPCATeamControlPanel();
          myOpcat2.setActiveCollaborativeSession(null);
          break;

        case SystemMessages.USER_RETURNED_TOKEN:
          myOpcat2.getTeamMember().updateLocalCollaborativeSessionToken(sessionID,PermissionFlags.SERVER_USERID.intValue());
          myOpcat2.getActiveCollaborativeSession().setTokenHolderID(PermissionFlags.SERVER_USERID);
          myOpcat2.getRepository().getAdmin().updateTokenHolderOnPanel(PermissionFlags.SERVER_USERID.intValue());

          //add a system message to the chat room area regarding the event
          chatMessageObject = new ChatMessageObject(
              new Integer(senderID),
              "System",
              "Token was returned to server",
              new Date());
          myOpcat2.getChatRoom().addNewLineToWriteArea(chatMessageObject);
          break;

        case SystemMessages.USER_IS_TOKENHOLDER:
          myOpcat2.getTeamMember().updateLocalCollaborativeSessionToken(sessionID,senderID);
          myOpcat2.getActiveCollaborativeSession().setTokenHolderID(new Integer(senderID));
          myOpcat2.getRepository().getAdmin().updateTokenHolderOnPanel(senderID);
          LoggedInMemberName = myOpcat2.getTeamMember().getLocalMemberName(senderID);

          //add a system message to the chat room area regarding the event
          chatMessageObject = new ChatMessageObject(
              new Integer(senderID),
              "System",
              LoggedInMemberName + " is the new Token Holder",
              new Date());
          myOpcat2.getChatRoom().addNewLineToWriteArea(chatMessageObject);
          break;

        case SystemMessages.UPDATE_USER_COLLABORATIVE_SESSION_PERMISSIONS:
        /*The posibilities are:
          1. update permissioned for existing member.
          -> find the user and update its accessControl data member.
          2. taking permissions from existing member.
          -> delete this user from the members tree
          3. add a new user to this session.
          -> add this user to the members tree.
          SINCE it might be complicated - the most simple thing to do is
          re-create the members tree again....*/

          myOpcat2.getRepository().getAdmin().createMembersTree(new Integer(sessionID));
          //add a system message to the chat room area regarding the event
          chatMessageObject = new ChatMessageObject(
              new Integer(senderID),
              "System",
              "Members tree is updated.",
              new Date());
          myOpcat2.getChatRoom().addNewLineToWriteArea(chatMessageObject);
          break;

        case SystemMessages.PUBLISH_UPDATED_FILE:
          //if the recieved message was sent by me -ignore it.
          if (myOpcat2.getTeamMember().getLocalEnhancedUser().getUserID().intValue() == senderID)
            return;

          //fetch the file object from the message
          CollaborativeSessionFileValue csfv =
              (CollaborativeSessionFileValue)objectMessage.getObject();
          //close the current file
          myOpcat2.closeFileForOPCATeam();
          //load the updated file that came with this message
          String fileName = "OPCATeam" + fileSeparator + "Client.opx";
          File outputFile = new File(fileName);
          FileWriter out = new FileWriter(outputFile);
          out.write(csfv.getOpmModelFile());
          out.close();
          //load the file to OPCAT

          myOpcat2.loadFileForOPCATeam(fileName,null,true);
          break;

        case SystemMessages.UPDATE_COLLABORATIVE_SESSION_DETAILS:
          //if the recieved message was sent by me -ignore it.
          if (myOpcat2.getTeamMember().getLocalEnhancedUser().getUserID().intValue() == senderID)
            return;

          CollaborativeSessionValue csv = null;
          //fetch the class from the server.
          csv= myOpcat2.getTeamMember().fatchCollaborativeSession(sessionID);
          //update the sessions details
          if (csv != null)
            {myOpcat2.getTeamMember().updateLocalCollaborativeSession(csv);
              //add a system message to the chat room area regarding the event
              chatMessageObject = new ChatMessageObject(
                new Integer(senderID),
                "System",
                "Collaborative Session's details were updated",
                new Date());
              myOpcat2.getChatRoom().addNewLineToWriteArea(chatMessageObject);
            }
          break;

      }

//      System.out.println("the message was: " + messageType);
    } catch (Exception e) {
      ExceptionHandler exceptionHandler = new ExceptionHandler(e);
    }
  }
}
