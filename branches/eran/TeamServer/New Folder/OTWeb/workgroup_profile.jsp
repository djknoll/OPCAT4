<%@ 
	page contentType="text/html; charset=iso-8859-1" 
	language="java"   
	errorPage="/error.jsp"
	import ="org.objectprocess.session.WorkgroupAccess"
	import ="org.objectprocess.session.WorkgroupAccessHome" 
	import ="org.objectprocess.cmp.WorkgroupValue" 
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
			String workgroupID = new String(request.getParameter("showworkgroupid"));
			String enabled;
			WorkgroupAccessHome workgroupAccessHome = (WorkgroupAccessHome)session.getAttribute("workgroupAccessHome");
			WorkgroupAccess workgroupAccess = workgroupAccessHome.create();
			WorkgroupValue id = workgroupAccess.getWorkgroupByPK(sUserID,sPassword,workgroupID);
			if( id.getEnabled().booleanValue() == true) { enabled = "checked"; }
			else { enabled = ""; }
			pageContext.setAttribute("enabled",enabled);
			pageContext.setAttribute("workgroupValue",id);
	%>
	<div id="navBar">
		<%@ include file="adminnav.jsp" %>
		<div id="info">
		<h1><jsp:getProperty name = "workgroupValue" property = "workgroupName" /></h1>
			<p>Properties page for workgroup.</p>
		</div>
	</div>
	<div id="content">
		<form name="login" method="post" action="admin">
		   <table>
				<tr >
				  <td><p>Workgroup GUID :</p></td>
				  <td><jsp:getProperty name = "workgroupValue" property = "workgroupID" /></td>
				</tr>
				<tr>
				  <td><p>Creation Time :</p></td>
				  <td><jsp:getProperty name = "workgroupValue" property = "creationTime" /></td>
				</tr>
				<tr>
				  <td><p>Workgroup Name :</p></td>
				  <td>
					<input name="workgroupName" type="text"  value = "<jsp:getProperty name = "workgroupValue" property = "workgroupName" />" ></td>
				</tr>
				<tr>
				  <td><p>Description :</p></td>
				  <td><input name="description" type="text"  value="<jsp:getProperty name = "workgroupValue" property = "description" />"></td>
				</tr>
				<tr >
				  <td><p>Enabled :</p></pp></td>
				  <td><input name="enabled"  class="checkbox"  type="checkbox" <%=enabled%>></td>
				</tr>
				<tr >
					<td>
						<input type="hidden" name="action" value="updateworkgroup">
						<input type="hidden" name="workgroupID" value="<jsp:getProperty name = "workgroupValue" property = "workgroupID" />">
					</td>
				  <td><input name="Submit" class="button" type="submit" value="Update"></td>
				</tr>
		   </table>
	   </form>
	</div>
<%}%>
</body>
</html>
