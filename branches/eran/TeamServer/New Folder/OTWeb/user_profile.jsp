<%@ 
	page contentType="text/html;charset=iso-8859-1" 
	language="java"   
	errorPage="/error.jsp"
	import ="org.objectprocess.session.UserAccess"
	import ="org.objectprocess.session.UserAccessHome" 
	import ="org.objectprocess.cmp.UserValue" 
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
			String userID = new String(request.getParameter("showuserid"));
			String enabled;
			UserAccessHome userAccessHome = (UserAccessHome)session.getAttribute("userAccessHome");
			UserAccess userAccess = userAccessHome.create();
			UserValue id = userAccess.getUserByPK(sUserID,sPassword,userID);
			String password = "";
			if( id.getEnabled().booleanValue() == true ) { enabled = "checked"; }
			else { enabled = ""; }
			pageContext.setAttribute("enabled",enabled);
			pageContext.setAttribute("",password);
			pageContext.setAttribute("userValue",id);

			String administrator = null;
			if ( id.getAdministrator().booleanValue()== true) { administrator = "<span class=\"greenTrue\">True</span>"; }
			else { administrator = "<span class=\"redFalse\">False</span>"; }
	%>
	<div id="navBar">
		<%@ include file="adminnav.jsp" %>
		<div id="info">
			<h1><jsp:getProperty name = "userValue" property = "firstName" /> <jsp:getProperty name = "userValue" property = "lastName" /></h1>
			<p>Properties page for user. To change the password just type in a new one and hit update. Naturally you can also edit all other attributes this way, but the current password is never displayed.</p>
		</div>
	</div>
	<div id="content">
		<form name="login" method="post" action="admin">
		   <table>
		   <tr>
			   <td><p>User GUID :</p></td>
			   <td><jsp:getProperty name = "userValue" property = "userID" /></td>
		   </tr>
		   		   <tr>
			   <td><p>Administrator :</p></td>
			   <td><%=administrator%></td>
		   </tr>
		   		   <tr>
			   <td><p>Last login on :</p></td>
			   <td><jsp:getProperty name = "userValue" property = "lastLoginTime" /></td>
		   </tr>
		   		   <tr>
			   <td><p>Login Name :</p></td>
			   <td><input name="loginName" type="text" value = "<jsp:getProperty name = "userValue" property = "loginName" />" ></td>
		   </tr>
		   		   <tr>
			   <td><p>First Name :</p></td>
			   <td><input name="firstName" type="text" value ="<jsp:getProperty name = "userValue" property = "firstName" />"> </td>
		   </tr>
		   		   <tr>
			   <td><p>Last Name :</p></td>
			   <td><input name="lastName" type="text" value ="<jsp:getProperty name = "userValue" property = "lastName" />"></td>
		   </tr>
		   	<tr>
			   <td><p>Password :</p></td>
			   <td><input name="password" type="text" value ="<%=password%>"></td>
		   </tr>
		   		   <tr>
			   <td><p>Email :</p></td>
			   <td><input name="email" type="text" value ="<jsp:getProperty name = "userValue" property = "email" />"> </td>
		   </tr>
		   		   <tr>
			   <td><p>Description :</p></td>
			   <td><input name="description" type="text" value="<jsp:getProperty name = "userValue" property = "description" />"></td>
		   </tr>
		   		   <tr>
			   <td><p>Enabled :</p></td>
			   <td><input name="enabled"  class="checkbox"  type="checkbox"  <%=enabled%>></td>
		   </tr>
		   </tr>
		   		   <tr>
			   <td>
				   <input type="hidden" name="updateUserID" value = "<jsp:getProperty name = "userValue" property = "userID" />">
				   <input type="hidden" name="action" value="updateuser">
			   </td>
			   <td><input name="update" class="button" type="submit" value="Update"></td>
		   </tr>
		   </table>
	   </form>
	</div>
<%}%>
</body>
</html>
