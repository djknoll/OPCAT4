/*
 * Created on 25/12/2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package org.objectprocess.mdb;

import java.util.Timer;
import java.util.TimerTask;

import javax.ejb.MessageDrivenBean;
import javax.jms.Message;
import javax.jms.MessageListener;


/**
 * MDB for the collaborative session topic, messages of all collaborative sessions are trasmited on this domain.
 * Message driven bean implements a JMS client and allows the server to participate in the topic.
 * xDcolet tags for this bean also generate the topic itself.
 * 
 * @ejb.bean 
 * 	name="CollaborativeSessionTopic" 
 *	acknowledge-mode="Auto-acknowledge" 
 *	destination-type="javax.jms.Topic" 
 *	subscription-durability="NonDurable" 
 *	transaction-type="Bean"
 * 
 * @jboss.destination-jndi-name 
 *		name="topic/collaborativeSessionTopic" 
 **/

public class CollaborativeSessionTopicBean
	implements MessageDrivenBean, MessageListener {

	private Timer timer;
	/** Required method for container to set context. */
	public void setMessageDrivenContext(
		javax.ejb.MessageDrivenContext messageContext)
		throws javax.ejb.EJBException {
		this.messageContext = messageContext;
	}
	/** 
	* Required creation method for message-driven beans. 
	* 
	* @ejb.create-method 
	*/
	public void ejbCreate() {
		// no specific action required for message-driven beans 
		System.out.println("Entering MDB constractor");
	}

	/** Required removal method for message-driven beans. */
	public void ejbRemove() {
		messageContext = null;
	}

	/** 
	* This method implements the business logic for the EJB. 
	* 
	* <p>Make sure that the business logic accounts for asynchronous message processing. 
	* For example, it cannot be assumed that the EJB receives messages in the order they were 
	* sent by the client. Instance pooling within the container means that messages are not 
	* received or processed in a sequential order, although individual onMessage() calls to 
	* a given message-driven bean instance are serialized. 
	* 
	* <p>The <code>onMessage()</code> method is required, and must take a single parameter 
	* of type javax.jms.Message. The throws clause (if used) must not include an application 
	* exception. Must not be declared as final or static. 
	*/
	public void onMessage(Message message) {
		try {
			//System.out.println("Message Driven Bean got message: " + message.getIntProperty("SystemMessageType"));
		    //timer = new Timer();
		    //timer.scheduleAtFixedRate(new RemindTask(),10000,10000);
		    
		} catch (Exception e){e.printStackTrace();}
		// do business logic here 
	}
	/** The context for the message-driven bean, set by the EJB container. */
	private javax.ejb.MessageDrivenContext messageContext = null;
	
	class RemindTask extends TimerTask {
	    public void run() {
	    	System.out.println("another 10 sec");
	      }
	}

}
