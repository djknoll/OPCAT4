<%@ 
	page contentType="text/html; charset=iso-8859-1" 
	language="java" 
	errorPage="/exterror.jsp"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>OPCATeam login gateway</title>
<link rel="stylesheet" href="cssstyle.css" type="text/css"></link>
</head>

<body>
	<%@ include file="header.jsp"%>
	<div id="navBar">
		<%@ include file="adminnav.jsp" %>
		<div id="info">
			<h1>Login gateway</h1>
			<p>This is the login gateway. You need to put your login name and your password in the appropriate slots and hit "Login". It won't help pushing the buttons on the left before you log in either. </p>
		</div>
	</div>
   <div id="content">
	   <form name="login" method="post" action="admin">
		   <table>
		   <tr>
			   <td><p>Login Name :</p></td>
			   <td><input name="gatewayLoginName" type="text" ></td>
		   </tr>
		   <tr>
			   <td><p>Password :</p></td>
			   <td><input name="gatewayPassword" type="password" ></td>
		   </tr>
		   <tr>
			   <td><input type="hidden" name="action" value="login"></td>
			   <td><input name="Submit" class="button" type="submit" value="Login"></td>
		   </tr>
		   </table>
	   </form>
	</div>
</body>
</html>
