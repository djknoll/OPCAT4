<%@ 
	page language="java" 
	errorPage="/error.jsp"
	import ="org.objectprocess.session.OpmModelAccess"
	import ="org.objectprocess.session.OpmModelAccessHome" 
	import ="org.objectprocess.cmp.OpmModelRevisionsValue" 
	import ="org.objectprocess.cmp.MetaRevisionValue" 
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
			String opmModelID = new String(request.getParameter("opmmodelid"));
			OpmModelAccessHome opmModelAccessHome = (OpmModelAccessHome)session.getAttribute("opmModelAccessHome");
			OpmModelAccess opmModelAccess = opmModelAccessHome.create();
			OpmModelRevisionsValue omrv = opmModelAccess.getOpmModelRevisionsByPK(sUserID,sPassword,opmModelID);
			MetaRevisionValue revisions[] = omrv.getOpmModelRevisions();
			MetaRevisionValue id = null;
	%>
	<div id="navBar">
		<%@ include file="adminnav.jsp" %>
		<div id="info">
			<h1>Revisions</h1>
			<p>Or to be exact, OPM model revisions, represent a milestone in model development. A revision is created when a collaborative sessions is committed. A Revision code has a major revision number, a minor revision number and a build number. The build number increments with every new revision and can tell you how many revisions are stored for this particular OPM model.</p>
		</div>
	</div>
	<div id="content">
		<table>
			<tr class="columnHeader">
				<td>OPM model </td>
				<td>Major </td>
				<td>Minor</td>
				<td>Build </td>
				<td>Enabled</td>
				<td>Creation time</td>
			</tr>
			<%
				int iterator = 0;
				while(iterator < revisions.length){
				id = revisions[iterator];
				pageContext.setAttribute("MetaRevisionValue",id);
				String enabled = null;
				if (id.getEnabled().booleanValue()== true){ enabled = "<span class=\"greenTrue\">Enabled</span>"; }
				else { enabled = "<span class=\"redFalse\">Disabled</span>"; }
			%>
			<jsp:useBean id="MetaRevisionValue" scope="page" class="org.objectprocess.cmp.MetaRevisionValue" />
			<tr>
				<td >
					<a href="opmmodel_profile.jsp?showopmmodelid=<jsp:getProperty name = "MetaRevisionValue" property = "opmModelID" />" class="tableRowDark">
						OPM model
					</a>
				</td>
				<td><jsp:getProperty name = "MetaRevisionValue" property = "majorRevision"/></td>
				<td><jsp:getProperty name = "MetaRevisionValue" property = "minorRevision"/></td>
				<td><jsp:getProperty name = "MetaRevisionValue" property = "build"/></td>
				<td><%=enabled%></td>
				<td><jsp:getProperty name = "MetaRevisionValue" property = "creationTime"/></td>
			</tr>
		 <%iterator++; }%>
		</table>
	</div>
<%}%>
</body>
</html>
