<%@ 
	page language="java" 
	errorPage="/error.jsp"
	import ="org.objectprocess.session.CollaborativeSessionAccess"
	import ="org.objectprocess.session.CollaborativeSessionAccessHome" 
	import ="org.objectprocess.session.OpmModelAccess"
	import ="org.objectprocess.session.OpmModelAccessHome" 
	import ="org.objectprocess.cmp.CollaborativeSessionValue" 
	import ="java.util.ArrayList"
	import ="java.util.Iterator"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Untitled Document</title>
<link rel="stylesheet" href="cssstyle.css" type="text/css"></link>
</head>

<body>
	<%@ include file="header.jsp"%>
	<%	
		String sUserID = (String)session.getAttribute("sUserID");
		String sPassword = (String)session.getAttribute("sPassword");
		if(sUserID == null ||sPassword == null){
			getServletContext().getRequestDispatcher("/gateway.jsp").forward(request,response);
		} else {
			String display = new String(request.getParameter("display"));
			ArrayList collaborativeSessions = null;
			if (0 == display.compareTo("all")) {
				CollaborativeSessionAccessHome collaborativeSessionAccessHome = (CollaborativeSessionAccessHome)session.getAttribute("collaborativeSessionAccessHome");
				CollaborativeSessionAccess collaborativeSessionAccess = collaborativeSessionAccessHome.create();
				collaborativeSessions = collaborativeSessionAccess.getAllCollaborativeSessions(sUserID,sPassword);
			} else {
				String opmModelID = new String(request.getParameter("opmmodelid"));
				OpmModelAccessHome opmModelAccessHome = (OpmModelAccessHome)session.getAttribute("opmModelAccessHome");
				OpmModelAccess opmModelAccess = opmModelAccessHome.create();
				collaborativeSessions = opmModelAccess.getCollaborativeSessionsForOpmModel(sUserID,sPassword,opmModelID);
			}
			Iterator iterate = collaborativeSessions.iterator();
			CollaborativeSessionValue id = null;
	%>
	<div id="navBar">
		<%@ include file="adminnav.jsp" %>
		<div id="info">
			<h1>Collaborative Sessions</h1>
			<p>This is a list of all collaborative sessions registered with OPCATeam. You can view and edit session profiles by clicking the appropriate name. Collaborative sessions are based on an OPM model revision, and result in a new revision when committed.</p>
		</div>
	</div>
	<div id="content">
		<table>
			<tr class="columnHeader">
			  <td>Name</td>
			  <td>Creation Time</td>
			  <td>Last Update</td>
			  <td>Enabled</td>
			  <td>Terminated</td>
			  <td>Users</td>
			</tr>
			<%
				while(iterate.hasNext()){
				id = (CollaborativeSessionValue)iterate.next();
				pageContext.setAttribute("collaborativeSessionValue",id);
				String enabled = null;
				String terminated = null;
				if (id.getEnabled().booleanValue()== true) { enabled = "<span class=\"greenTrue\">Enabled</span>"; }
				else { enabled = "<span class=\"redFalse\">Disabled</span>"; }
				if (id.getTerminated().booleanValue()== true) { terminated = "<span class=\"redFalse\">Terminated</span>"; }
				else { terminated = "<span class=\"greenTrue\">Active</span>"; }							
			%>
			<jsp:useBean id="collaborativeSessionValue" scope="page" class="org.objectprocess.cmp.CollaborativeSessionValue" />
			<tr>
				<td>
					<a href="session_profile.jsp?showsessionid=<jsp:getProperty name = "collaborativeSessionValue" property = "collaborativeSessionID"/>">
						<jsp:getProperty name = "collaborativeSessionValue" property = "collaborativeSessionName"/>
						</a>
				</td>
				<td>
					<jsp:getProperty name = "collaborativeSessionValue" property = "creationTime"/>
				</td>
				<td>
					<jsp:getProperty name = "collaborativeSessionValue" property = "lastUpdate"/>
				</td>
				<td>
					<%=enabled%>
				</td>
				<td>
					<%=terminated%>
				</td>
				<td>
					<a href="permissions.jsp?display=session&name=<jsp:getProperty name = "collaborativeSessionValue" property = "collaborativeSessionName" />&id=<jsp:getProperty name = "collaborativeSessionValue" property = "collaborativeSessionID" />">
						permissions
					</a>
				</td>			
			</tr>
		<%}%>
		</table>
	</div>
<%}%>
</body>
</html>
