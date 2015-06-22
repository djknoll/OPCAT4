/*
 * Created on 17/11/2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package org.objectprocess.Client;

/**
 * @author moonwatcher
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class PermissionFlags {
	public static final Integer NULL_PERMISSIONS = new Integer(0);
	public static final Integer VIEWER = new Integer(4);
	public static final Integer EDITOR = new Integer(20);
	public static final Integer COMMITTER = new Integer(84);
	public static final Integer MODERATOR = new Integer(340);
	public static final Integer ADMINISTRATOR = new Integer(1364);
	public static final Integer CREATOR = new Integer(5460);
	
	public static final Integer LOGGEDIN = new Integer(536870912);
	
	public static final Integer SERVER_USERID = new Integer(1);

	public static final Integer MAIN_ADMINISTRATOR_USERID = new Integer(1);
}
