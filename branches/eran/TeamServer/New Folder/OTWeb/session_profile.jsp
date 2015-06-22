<%@ 
	page contentType="text/html; charset=iso-8859-1" 
	language="java"   
	errorPage="/error.jsp"
	import ="org.objectprocess.session.CollaborativeSessionAccess"
	import ="org.objectprocess.session.CollaborativeSessionAccessHome" 
	import ="org.objectprocess.cmp.CollaborativeSessionValue" 
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
			getServletContext().getRequestDispatcher("/index.jsp").forward(request,response);
		} else {
			String collaborativeSessionID = new String(request.getParameter("showsessionid"));
			String enabled;
			CollaborativeSessionAccessHome collaborativeSessionAccessHome = (CollaborativeSessionAccessHome)session.getAttribute("collaborativeSessionAccessHome");
			CollaborativeSessionAccess collaborativeSessionAccess = collaborativeSessionAccessHome.create();
			CollaborativeSessionValue id = collaborativeSessionAccess.getCollaborativeSessionByPK(sUserID,sPassword,collaborativeSessionID);
			if( id.getEnabled().booleanValue() == true)
				enabled = "checked";
			else
				enabled = "";	
			pageContext.setAttribute("enabled",enabled);
			pageContext.setAttribute("terminated",enabled);
			pageContext.setAttribute("collaborativeSessionValue",id);	
	
			String terminated = null;
			if (id.getTerminated().booleanValue()== true) { terminated = "<span class=\"redFalse\">Terminated</span>"; }
			else { terminated = "<span class=\"greenTrue\">Active</span>"; }												
	%>
	<div id="navBar">
		<%@ include file="adminnav.jsp" %>
		<div id="info">
			<h1><jsp:getProperty name = "collaborativeSessionValue" property = "collaborativeSessionName" /></h1>
			<p>Properties page for collaborative session.</p>		
		</div>
	</div>
	<div id="content">
		<form name="login" method="post" action="admin">
		   <table>
				  <tr>
					<td ><p>Last Updated :</p></td>
					<td><jsp:getProperty name = "collaborativeSessionValue" property = "lastUpdate" /></td>
				  </tr>
				  <tr>
					<td ><p>Creation Time :</p></td>
					<td><jsp:getProperty name = "collaborativeSessionValue" property = "creationTime" /></td>
				  </tr>
				  <tr>
					<td ><p>Token Holder ID :</p></td>
					<td><jsp:getProperty name = "collaborativeSessionValue" property = "tokenHolderID" /></td>
				  </tr>
				  <tr>
					<td ><p>Commited :</p></td>
					<td><%=terminated%></td>
				  </tr>
				  <tr>
					<td ><p>OPM Model :</p></td>
					<td><jsp:getProperty name = "collaborativeSessionValue" property = "opmModelID" /></td>
				  </tr>
				  <tr>
					<td><p>Session Name :</p></td>
					<td>
					  <input name="collaborativeSessionName" type="text"  value = "<jsp:getProperty name = "collaborativeSessionValue" property = "collaborativeSessionName" />" >                                </td>
				  </tr>
				  <tr>
					<td ><p>Description :</p></td>
					<td><input name="description" type="text"  value="<jsp:getProperty name = "collaborativeSessionValue" property = "description" />"></td>
				  </tr>
				  <tr>
					<td ><p>Token Timeout :</p></td>
					<td><input name="tokenTimeout" type="text"  value="<jsp:getProperty name = "collaborativeSessionValue" property = "tokenTimeout" />"></td>
				  </tr>
				  <tr>
					<td ><p>User Timeout :</p></td>
					<td><input name="userTimeout" type="text"  value="<jsp:getProperty name = "collaborativeSessionValue" property = "userTimeout" />"></td>
				  </tr>
				  <tr>
					<td ><p>Enabled :</p></td>
					<td><input name="enabled"  class="checkbox"  type="checkbox" <%=enabled%>></td>
				  </tr>
				  <tr>
					<td>
						<input type="hidden" name="action" value="updatesession">
						<input type="hidden" name="sessionID" value = "<jsp:getProperty name = "collaborativeSessionValue" property = "collaborativeSessionID" />" >
				   </td>
				   <td><input name="Submit" type="submit" class="button" value="Update"></td>
				  </tr>
		   </table>
	   </form>
	</div>
<%}%>
</body>
</html>
