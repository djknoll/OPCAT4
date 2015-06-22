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
		String errorMessage = null;
		errorMessage = (String)session.getAttribute("errorMessage");
	%>
	<div id="navBar">
		<%@ include file="adminnav.jsp" %>
		<div id="info">
			<h1>Error</h1>
			<p>You have encountered an error. This page provides the error's code and details about its cause. If you are not sure why this happened, please feel free to contact your system administrator.</p>
		</div>
	</div>
   <div id="content">
	   <div id="error">
		   <h1>error</h1>
		   <p><%= errorMessage %></p>
	   </div>
	</div>
</body>
</html>
