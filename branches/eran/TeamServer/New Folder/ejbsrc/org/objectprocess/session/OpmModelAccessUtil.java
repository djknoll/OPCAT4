/*
 * Generated file - Do not edit!
 */
package org.objectprocess.session;

/**
 * Utility class for OpmModelAccess.
 * @lomboz generated
 */
public class OpmModelAccessUtil
{
   /** Cached remote home (EJBHome). Uses lazy loading to obtain its value (loaded by getHome() methods). */
   private static org.objectprocess.session.OpmModelAccessHome cachedRemoteHome = null;

   /** Cached local home (EJBLocalHome). Uses lazy loading to obtain its value (loaded by getLocalHome() methods). */
   private static org.objectprocess.session.OpmModelAccessLocalHome cachedLocalHome = null;

   private static Object lookupHome(java.util.Hashtable environment, String jndiName, Class narrowTo) throws javax.naming.NamingException {
      // Obtain initial context
      javax.naming.InitialContext initialContext = new javax.naming.InitialContext(environment);
      try {
         Object objRef = initialContext.lookup(jndiName);
         // only narrow if necessary
         if (narrowTo.isInstance(java.rmi.Remote.class))
            return javax.rmi.PortableRemoteObject.narrow(objRef, narrowTo);
         else
            return objRef;
      } finally {
         initialContext.close();
      }
   }

   // Home interface lookup methods

   /**
    * Obtain remote home interface from default initial context
    * @return Home interface for OpmModelAccess. Lookup using JNDI_NAME
    */
   public static org.objectprocess.session.OpmModelAccessHome getHome() throws javax.naming.NamingException
   {
      if (cachedRemoteHome == null) {
            cachedRemoteHome = (org.objectprocess.session.OpmModelAccessHome) lookupHome(null, org.objectprocess.session.OpmModelAccessHome.JNDI_NAME, org.objectprocess.session.OpmModelAccessHome.class);
      }
      return cachedRemoteHome;
   }

   /**
    * Obtain remote home interface from parameterised initial context
    * @param environment Parameters to use for creating initial context
    * @return Home interface for OpmModelAccess. Lookup using JNDI_NAME
    */
   public static org.objectprocess.session.OpmModelAccessHome getHome( java.util.Hashtable environment ) throws javax.naming.NamingException
   {
       return (org.objectprocess.session.OpmModelAccessHome) lookupHome(environment, org.objectprocess.session.OpmModelAccessHome.JNDI_NAME, org.objectprocess.session.OpmModelAccessHome.class);
   }

   /**
    * Obtain local home interface from default initial context
    * @return Local home interface for OpmModelAccess. Lookup using JNDI_NAME
    */
   public static org.objectprocess.session.OpmModelAccessLocalHome getLocalHome() throws javax.naming.NamingException
   {
      if (cachedLocalHome == null) {
            cachedLocalHome = (org.objectprocess.session.OpmModelAccessLocalHome) lookupHome(null, org.objectprocess.session.OpmModelAccessLocalHome.JNDI_NAME, org.objectprocess.session.OpmModelAccessLocalHome.class);
      }
      return cachedLocalHome;
   }

   /** Cached per JVM server IP. */
   private static String hexServerIP = null;

   // initialise the secure random instance
   private static final java.security.SecureRandom seeder = new java.security.SecureRandom();

   /**
    * A 32 byte GUID generator (Globally Unique ID). These artificial keys SHOULD <strong>NOT </strong> be seen by the user,
    * not even touched by the DBA but with very rare exceptions, just manipulated by the database and the programs.
    *
    * Usage: Add an id field (type java.lang.String) to your EJB, and add setId(XXXUtil.generateGUID(this)); to the ejbCreate method.
    */
   public static final String generateGUID(Object o) {
       StringBuffer tmpBuffer = new StringBuffer(16);
       if (hexServerIP == null) {
           java.net.InetAddress localInetAddress = null;
           try {
               // get the inet address
               localInetAddress = java.net.InetAddress.getLocalHost();
           }
           catch (java.net.UnknownHostException uhe) {
               System.err.println("OpmModelAccessUtil: Could not get the local IP address using InetAddress.getLocalHost()!");
               // todo: find better way to get around this...
               uhe.printStackTrace();
               return null;
           }
           byte serverIP[] = localInetAddress.getAddress();
           hexServerIP = hexFormat(getInt(serverIP), 8);
       }
       String hashcode = hexFormat(System.identityHashCode(o), 8);
       tmpBuffer.append(hexServerIP);
       tmpBuffer.append(hashcode);

       long timeNow      = System.currentTimeMillis();
       int timeLow       = (int)timeNow & 0xFFFFFFFF;
       int node          = seeder.nextInt();

       StringBuffer guid = new StringBuffer(32);
       guid.append(hexFormat(timeLow, 8));
       guid.append(tmpBuffer.toString());
       guid.append(hexFormat(node, 8));
       return guid.toString();
   }

   private static int getInt(byte bytes[]) {
       int i = 0;
       int j = 24;
       for (int k = 0; j >= 0; k++) {
           int l = bytes[k] & 0xff;
           i += l << j;
           j -= 8;
       }
       return i;
   }

   private static String hexFormat(int i, int j) {
       String s = Integer.toHexString(i);
       return padHex(s, j) + s;
   }

   private static String padHex(String s, int i) {
       StringBuffer tmpBuffer = new StringBuffer();
       if (s.length() < i) {
           for (int j = 0; j < i - s.length(); j++) {
               tmpBuffer.append('0');
           }
       }
       return tmpBuffer.toString();
   }

}