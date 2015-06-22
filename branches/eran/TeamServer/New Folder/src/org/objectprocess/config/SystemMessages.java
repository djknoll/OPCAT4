/*
 * Created on 31/12/2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package org.objectprocess.config;

/**
 * Class containing numercial codes for JMS mesages sent on the CollaborativeSessionTopic.
 * 
 * @author moonwatcher
 *
 */
public class SystemMessages {
	
	public static final int USER_LOGGED_INTO_SESSION = 1;
	public static final int USER_LOGGED_OUT_OF_SESSION = 2;
	public static final int USER_COMMITED_SESSION = 3;
	public static final int USER_RETURNED_TOKEN = 4;	
	public static final int USER_IS_TOKENHOLDER = 5;	
	public static final int PUBLISH_UPDATED_FILE = 6;
	public static final int UPDATE_USER_COLLABORATIVE_SESSION_PERMISSIONS = 7;
	public static final int UPDATE_COLLABORATIVE_SESSION_DETAILS = 8;
	
	public static final int UPDATE_USER_DETAILS = 9; //not implemented in current version
	public static final int UPDATE_WORKGROUP_DETAILS = 10; //not implemented in current version
	public static final int UPDATE_OPMMODEL_DETAILS = 11; //not implemented in current version}
}
