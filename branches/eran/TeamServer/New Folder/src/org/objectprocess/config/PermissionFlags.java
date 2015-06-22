/*
 * Created on 17/11/2003
 */
package org.objectprocess.config;

/**
 * Class containing numerical values for permissions.
 * Values represent bit masks for permissions flags. 
 * Each permission also sets the lower permission flag:
 * 
 * NNNN NNNN NNNN NNNN NNNC NANM NCNE NVNN
 * 0000 0000 0000 0000 0000 0000 0000 0000	0
 * 0000 0000 0000 0000 0000 0000 0000 0100	4
 * 0000 0000 0000 0000 0000 0000 0001 0100	20		= 4 + 16
 * 0000 0000 0000 0000 0000 0000 0101 0100	84		= 4 + 16 + 64
 * 0000 0000 0000 0000 0000 0001 0101 0100	340		= 4 + 16 + 64 + 256
 * 0000 0000 0000 0000 0000 0101 0101 0100	1364	= 4 + 16 + 64 + 256 + 1024
 * 0000 0000 0000 0000 0001 0101 0101 0100	5460	= 4 + 16 + 64 + 256 + 1024 + 4096 
 * 
 * @author moonwatcher
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
	
	public static final String SERVER_USERID = "null_user";
	public static final String MAIN_ADMINISTRATOR_USERID = "Administrator";
}
