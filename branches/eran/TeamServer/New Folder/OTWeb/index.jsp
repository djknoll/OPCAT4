<%@ 
	page language="java" 
	errorPage="/error.jsp"
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
		if(sUserID == null || sPassword == null){
			getServletContext().getRequestDispatcher("/gateway.jsp").forward(request,response);
		} else {
	%>
	<div id="navBar">
		<%@ include file="adminnav.jsp" %>
		<div id="info">
			<p>The lengths specify the distance that separates adjacent cell borders. If one length is specified, it gives both the horizontal and vertical spacing. If two are specified, the first gives the horizontal spacing and the second the vertical spacing. Lengths may not be negative.</p>
		</div>
	</div>
	<div id="content">
	</div>
<%}%>
</body>
</html>
