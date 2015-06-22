<%@ 
	page language="java" 
	errorPage="/error.jsp"
	import ="org.objectprocess.session.UserAccess"
	import ="org.objectprocess.session.UserAccessHome" 
	import ="org.objectprocess.cmp.UserValue" 
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
			ArrayList users = null;
			UserAccessHome userAccessHome = (UserAccessHome)session.getAttribute("userAccessHome");
			UserAccess userAccess = userAccessHome.create();
			users = userAccess.getAllUsers(sUserID,sPassword);
			
			Iterator iterate = users.iterator();
			UserValue id = null;
	%>
	<div id="navBar">
		<%@ include file="adminnav.jsp" %>
		<div id="info">
			<h1>Users</h1>
			<p>This is a list of all users registered with OPCATeam. You can view and edit user profiles by clicking the appropriate login name.</p>
		</div>
	</div>
	<div id="content">
		<table>
			<tr class="columnHeader">
				<td >Login Name</td>
				<td >First Name</td>
				<td >Last Name</td>
				<td >Enabled</td>
				<td >Logged In</td>
				<td >Workgroups</td>
			</tr>
			<%
				while(iterate.hasNext()){
				id = (UserValue)iterate.next();
				pageContext.setAttribute("userValue",id);
				String enabled = null;
				String loggedIn = null;
				if (id.getEnabled().booleanValue()== true){ enabled = "<span class=\"greenTrue\">Enabled</span>"; }
				else { enabled = "<span class=\"redFalse\">Disabled</span>"; }
				if (id.getLoggedIn().booleanValue()== true) { loggedIn = "<span class=\"greenTrue\">Logged In</span>"; }
				else { loggedIn = "<span class=\"redFalse\">Logged Out</span>"; }
			%>
			<jsp:useBean id="userValue" scope="page" class="org.objectprocess.cmp.UserValue" />
			<tr>
				<td>
					<a href="user_profile.jsp?showuserid=<jsp:getProperty name = "userValue" property = "userID"/>">
						<jsp:getProperty name = "userValue" property = "loginName"/>
					</a>
				</td>
				<td>
					<jsp:getProperty name = "userValue" property = "firstName"/>
				</td>
				<td>
					<jsp:getProperty name = "userValue" property = "lastName"/>
				</td>
				<td>
					<%=enabled%>
				</td>
				<td>
					<%=loggedIn%>
				</td>
				<td>
					<a href="workgroups.jsp?display=user&userid=<jsp:getProperty name = "userValue" property = "userID" />">
						workgroup
					</a>
				</td>
			</tr>
		<%}%>
		</table>
	</div>
<%}%>
</body>
</html>
