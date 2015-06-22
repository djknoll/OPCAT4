<%@ 
	page contentType="text/html; charset=iso-8859-1" 
	language="java"   
	errorPage="/error.jsp"
	import ="org.objectprocess.session.OpmModelAccess"
	import ="org.objectprocess.session.OpmModelAccessHome" 
	import ="org.objectprocess.cmp.OpmModelValue" 
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
			String opmModelID = new String(request.getParameter("showopmmodelid"));
			String enabled = null;
			OpmModelAccessHome opmModelAccessHome = (OpmModelAccessHome)session.getAttribute("opmModelAccessHome");
			OpmModelAccess opmModelAccess = opmModelAccessHome.create();
			OpmModelValue id = opmModelAccess.getOpmModelByPK(sUserID, sPassword, opmModelID);
			if( id.getEnabled().booleanValue() == true)
				enabled = "checked";
			else
				enabled = "";
			pageContext.setAttribute("enabled",enabled);
			pageContext.setAttribute("opmModelValue",id);
	%>
	<div id="navBar">
		<%@ include file="adminnav.jsp" %>
		<div id="info">
			<h1><jsp:getProperty name = "opmModelValue" property = "opmModelName" /></h1>
			<p>Properties page for OPM model.</p>
		</div>
	</div>
	<div id="content">
		<form name="login" method="post" action="admin">
		   <table>
				<tr>
				  <td><p>Creation Time :</p></td>
				  <td><jsp:getProperty name = "opmModelValue" property = "creationTime" /></td>
				</tr>
				<tr>
				  <td><p>Workgroup :</p></td>
				  <td><jsp:getProperty name = "opmModelValue" property = "workgroupID" /></td>
				</tr>
				<tr>
				  <td><p>OPM Model Name :</p></td>
				  <td><input name="opmModelName" type="text" value = "<jsp:getProperty name = "opmModelValue" property = "opmModelName" />" ></td>
				</tr>
				<tr>
				  <td><p>Description :</p></td>
				  <td><input name="description" type="text" value="<jsp:getProperty name = "opmModelValue" property = "description" />"></td>
				</tr>
				<tr>
				  <td><p>Enabled :</p></td>
				  <td><input name="enabled" class="checkbox"  type="checkbox"  <%=enabled%>></td>
				</tr>
				<tr>
				  <td>
					  <input type="hidden" name="action" value="updateopmmodel">
					  <input type="hidden" name="opmModelID" value="<jsp:getProperty name = "opmModelValue" property = "opmModelID" />">
				  </td>
				  <td><input name="Submit" class="button"  type="submit" value="Update"></td>
				</tr>
		   </table>
	   </form>
	</div>
<%}%>
</body>
</html>
