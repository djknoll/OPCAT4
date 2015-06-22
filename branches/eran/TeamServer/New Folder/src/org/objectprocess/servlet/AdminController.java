/*
 * Created on 06/03/2004
 */
package org.objectprocess.servlet;

import java.io.IOException;
import java.util.Properties;

import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.objectprocess.cmp.EditableUserValue;
import org.objectprocess.cmp.UpdatableCollaborativeSessionValue;
import org.objectprocess.cmp.UpdatableOpmModelValue;
import org.objectprocess.cmp.UpdatableUserValue;
import org.objectprocess.cmp.UpdatableWorkgroupValue;
import org.objectprocess.cmp.UserValue;
import org.objectprocess.session.CollaborativeSessionAccess;
import org.objectprocess.session.CollaborativeSessionAccessHome;
import org.objectprocess.session.OpmModelAccess;
import org.objectprocess.session.OpmModelAccessHome;
import org.objectprocess.session.UserAccess;
import org.objectprocess.session.UserAccessHome;
import org.objectprocess.session.WorkgroupAccess;
import org.objectprocess.session.WorkgroupAccessHome;

/**
 * @author Lior
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class AdminController extends HttpServlet {
	private InitialContext ctx;
	private UserAccessHome userAccessHome = null;
	private WorkgroupAccessHome workgroupAccessHome = null;
	private OpmModelAccessHome opmModelAccessHome = null;
	private CollaborativeSessionAccessHome collaborativeSessionAccessHome = null;

	private HttpSession session = null;
	private UserAccess userAccess = null;
	private WorkgroupAccess workgroupAccess = null;
	private OpmModelAccess opmModelAccess = null;
	private CollaborativeSessionAccess collaborativeSessionAccess = null;
	
	protected void processRequest (HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		String action = request.getParameter("action");
		try {
			if(action == null){
				getServletContext().getRequestDispatcher("/gateway.jsp").forward(request,response);
				return;
			}
			session = request.getSession(true);
			
			if (action.equals("login")){
				loginUser(request,response);
				getServletContext().getRequestDispatcher("/index.jsp").forward(request,response);
			} else { if (action.equals("newuser")) {
				createNewUser(request,response);
				getServletContext().getRequestDispatcher("/index.jsp").forward(request,response);
			} else { if (action.equals("updateuser")){
				updateUser(request,response);
				getServletContext().getRequestDispatcher("/users.jsp").forward(request,response);
			}else { if (action.equals("updateworkgroup")){
				updateWorkgroup(request,response);
				getServletContext().getRequestDispatcher("/workgroups.jsp").forward(request,response);
			}else { if (action.equals("updatesession")){
				updateSession(request,response);
				getServletContext().getRequestDispatcher("/sessions.jsp?display=all").forward(request,response);
			}else { if (action.equals("updateopmmodel")){
				updateOpmModel(request,response);
				getServletContext().getRequestDispatcher("/opmmodels.jsp?display=all").forward(request,response);
			}else { if (action.equals("logout")){
				logoutUser(request,response);
				getServletContext().getRequestDispatcher("/gateway.jsp").forward(request,response);
			}else { if (action.equals("register")){
				registerNewUser(request,response);
				getServletContext().getRequestDispatcher("/gateway.jsp").forward(request,response);
			}else { if (false){
			}else { if (false){
			}}}}}}}}}}
		} catch (Exception e){
		    String message;
		    Exception cause = (Exception)e.getCause();
		    if (cause instanceof EJBException){	        
		        message = cause.getMessage();
		    } else {
		        message = e.getMessage();
		    }
		    session.setAttribute("errorMessage",message);
			if((String)session.getAttribute("sUserID")== null)
				getServletContext().getRequestDispatcher("/error.jsp").forward(request,response);
			else
				getServletContext().getRequestDispatcher("/error.jsp").forward(request,response);
		}
	}
	
	//Service Functions
	private void logoutUser(HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		session.setAttribute("sUserID",null);
		session.setAttribute("sPassword",null);
		session.setAttribute("currentUser",null);
	}
	
	private void loginUser(HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		String loginName = request.getParameter("gatewayLoginName");
		String password = request.getParameter("gatewayPassword");
		userAccess = userAccessHome.create();
		String userID =  userAccess.webLoginUser(loginName,password);
		UserValue userValue = userAccess.getUserByPK(userID,password,userID);
		if(userID != null){
			//Initialize the session with the userID and the Session Beans References
			session.setAttribute("sUserID",userID);
			session.setAttribute("sPassword",password);
			session.setAttribute("currentUser",userValue);
			session.setAttribute("userAccessHome",userAccessHome);
			session.setAttribute("workgroupAccessHome",workgroupAccessHome);
			session.setAttribute("opmModelAccessHome",opmModelAccessHome);
			session.setAttribute("collaborativeSessionAccessHome",collaborativeSessionAccessHome);
		}
	}
	
	private void createNewUser(HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		Boolean administrator = null;
		String administratorText = request.getParameter("administrator");
		if(administratorText == null) administrator = new Boolean(false);
		else administrator = new Boolean(true);
		
		EditableUserValue editableUserValue = new EditableUserValue(
				null,
				request.getParameter("loginName"),
				request.getParameter("firstName"),
				request.getParameter("lastName"),
				request.getParameter("password"),
				request.getParameter("email"),
				request.getParameter("description"),
				administrator
		);
		userAccess.createUser(editableUserValue);
	}
	
	private void registerNewUser(HttpServletRequest request, HttpServletResponse response)
	throws Exception
{
	Boolean administrator = new Boolean(false);	
	EditableUserValue editableUserValue = new EditableUserValue(
			null,
			request.getParameter("loginName"),
			request.getParameter("firstName"),
			request.getParameter("lastName"),
			request.getParameter("password"),
			request.getParameter("email"),
			request.getParameter("description"),
			administrator
	);
	userAccess.createUser(editableUserValue);
}
	
	private void updateUser (HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		String updateUserID = request.getParameter("updateUserID");
		String textEnabled = request.getParameter("enabled");
		Boolean enabled = null;
		String password = null;
		
		if (0 != request.getParameter("password").compareTo(""))
			password = request.getParameter("password");
		
		if(textEnabled==null) enabled = new Boolean(false);
		else enabled = new Boolean(true);
		
		UpdatableUserValue updatableUserValue = new UpdatableUserValue(
			updateUserID,
			request.getParameter("loginName"),
			request.getParameter("firstName"),
			request.getParameter("lastName"),
			password,
			request.getParameter("email"),
			enabled,
			request.getParameter("description"));
		
		userAccess.updateUser(
				(String)session.getAttribute("sUserID"),
				(String)session.getAttribute("sPassword"),
				updatableUserValue);
		if (updateUserID.equals( (String)session.getAttribute("sUserID"))){
			if (null != password)
				session.setAttribute("sPassword",request.getParameter("password"));
			
			UserValue userValue = userAccess.getUserByPK(
					(String)session.getAttribute("sUserID"),
					(String)session.getAttribute("sPassword"),
					(String)session.getAttribute("sUserID"));
			session.setAttribute("currentUser",userValue);
		}
	}
	
	private void updateWorkgroup (HttpServletRequest request, HttpServletResponse response)
		throws Exception 
	{
		String workgroupID = request.getParameter("workgroupID");
		String enabledText = request.getParameter("enabled");
		Boolean enabled = null;
		if(enabledText == null) enabled = new Boolean(false);
		else enabled = new Boolean(true);
		UpdatableWorkgroupValue updatableWorkgroupValue = new UpdatableWorkgroupValue(				
				workgroupID,
				request.getParameter("workgroupName"),
				enabled,
				request.getParameter("description")
		);
		workgroupAccess = workgroupAccessHome.create();
		workgroupAccess.updateWorkgroup(
				(String)session.getAttribute("sUserID"),
				(String)session.getAttribute("sPassword"),
				updatableWorkgroupValue);
	}
	
	private void updateSession (HttpServletRequest request, HttpServletResponse response)
		throws Exception 
	{
		String collaborativeSessionID = request.getParameter("sessionID");
		Integer tokenTimeout = new Integer(request.getParameter("tokenTimeout"));
		Integer userTimeout = new Integer(request.getParameter("userTimeout"));
		String textEnabled = request.getParameter("enabled");
		Boolean enabled = null;
		if(textEnabled==null) enabled = new Boolean(false);
		else enabled = new Boolean(true);
		
		UpdatableCollaborativeSessionValue updatableCollaborativeSessionValue = new UpdatableCollaborativeSessionValue(					
				collaborativeSessionID,
				request.getParameter("collaborativeSessionName"),
				enabled,
				request.getParameter("description"),
				tokenTimeout,
				userTimeout
		);
		collaborativeSessionAccess = collaborativeSessionAccessHome.create();
		collaborativeSessionAccess.updateCollaborativeSession(
				(String)session.getAttribute("sUserID"),
				(String)session.getAttribute("sPassword"),
				updatableCollaborativeSessionValue);
	}
	
	private void updateOpmModel (HttpServletRequest request, HttpServletResponse response)
		throws Exception 
	{
		String opmModelID = request.getParameter("opmModelID");
		Boolean enabled = null;
		String enabledText = request.getParameter("enabled");
		if(enabledText==null) enabled = new Boolean(false);
		else enabled = new Boolean(true);
		UpdatableOpmModelValue updatableOpmModelValue = new UpdatableOpmModelValue(			
				opmModelID,
				request.getParameter("opmModelName"),
				enabled,
				request.getParameter("description")
		);
		opmModelAccess = opmModelAccessHome.create();
		opmModelAccess.updateOpmModel(
				(String)session.getAttribute("sUserID"),
				(String)session.getAttribute("sPassword"),
				updatableOpmModelValue);
	}
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			ctx = getContext();
			Object oUserAccessHome = ctx.lookup(UserAccessHome.JNDI_NAME);
			userAccessHome = (UserAccessHome)PortableRemoteObject.narrow(oUserAccessHome,UserAccessHome.class);		
			Object oWorkgroupAccessHome = ctx.lookup(WorkgroupAccessHome.JNDI_NAME);
			workgroupAccessHome = (WorkgroupAccessHome)PortableRemoteObject.narrow(oWorkgroupAccessHome,WorkgroupAccessHome.class);
			Object oOpmModelAccessHome = ctx.lookup(OpmModelAccessHome.JNDI_NAME);
			opmModelAccessHome = (OpmModelAccessHome)PortableRemoteObject.narrow(oOpmModelAccessHome,OpmModelAccessHome.class);
			Object oCollaborativeSessionAccessHome = ctx.lookup(CollaborativeSessionAccessHome.JNDI_NAME);
			collaborativeSessionAccessHome = (CollaborativeSessionAccessHome)PortableRemoteObject.narrow(oCollaborativeSessionAccessHome,CollaborativeSessionAccessHome.class);
			
		} catch(Exception e) { e.printStackTrace();}	
	}
	
	public void destroy() {
		super.destroy();
	}
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}
	private InitialContext getContext() throws NamingException {
	    //	Create a JNDI API InitialContext object.
	      Properties env = new Properties();
	      env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
	      env.put(Context.URL_PKG_PREFIXES,"org.jboss.naming:org.jnp.interfaces");
	      env.put(Context.PROVIDER_URL, "localhost:1099");
	    return new InitialContext(env);
	}
	
}
