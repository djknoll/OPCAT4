package org.objectprocess.config;

/**
 * Class containing numerical codes for exceptions.
 * 
 * @author Lior Galanti
 */
public class FaultCode {
    public static final String delimiter = 									"#";
    public static final String AuthenticationFailedCode = 	"01000" + delimiter;
    public static final String NoSuchUserCode = 			"01101" + delimiter;
    public static final String UserAlreadyLoggedInCode = 	"01201" + delimiter;
    public static final String UserDisabledCode = 			"01301" + delimiter;
    public static final String UserNotLoggedInCode = 		"01401" + delimiter;
    public static final String WrongPasswordCode = 			"01501" + delimiter;

    public static final String LookupCode = 				"02000" + delimiter;	
    public static final String NoSuchOpmModelCode = 		"02003" + delimiter;
    public static final String NoSuchWorkgroupCode = 		"02002" + delimiter;
    public static final String NoSuchSessionCode = 			"02004" + delimiter;
    public static final String NoUsersFoundCode = 			"02101" + delimiter;
    public static final String NoWorkgroupsFoundCode = 		"02102" + delimiter;	
    public static final String NoOpmModelsFoundCode = 		"02103" + delimiter;
    public static final String NoSessionsFoundCode = 		"02104" + delimiter;
    public static final String NoRevisionsFoundCode = 		"02105" + delimiter;
    public static final String UserLookupCode	=			"02201" + delimiter;
    public static final String WorkgroupLookupCode =		"02202" + delimiter;
    public static final String OpmModelLookupCode =			"02203" + delimiter;
    public static final String RevisionLookupCode =			"02205" + delimiter;
    public static final String PermissionLookupCode =		"02206" + delimiter;

    public static final String CreationCode =  				"03000" + delimiter;
    public static final String UserAlreadyExistCode = 		"03001" + delimiter;
    public static final String WorkgroupAlreadyExistCode = 	"03002" + delimiter;
    public static final String OpmModelAlreadyExistCode =	"03003" + delimiter;
    public static final String SessionAlreadyExistCode = 	"03004" + delimiter;

    public static final String RecursiveDisableCode = 		"04000" + delimiter;
    public static final String UsersStillLoggedInCode =		"04001" + delimiter;
    public static final String ActiveOpmModelFoundCode	=	"04003" + delimiter;
    public static final String ActiveSessionFoundCode =		"04004" + delimiter;

    public static final String TokenCode = 					"05000" + delimiter;
    public static final String NotTheTokenHolderCode =		"05100" + delimiter;
    public static final String TokenNotAvailableCode = 		"05200" + delimiter;
}
