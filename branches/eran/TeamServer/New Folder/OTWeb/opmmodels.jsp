<%@ 
	page language="java" 
	errorPage="/error.jsp"
	import ="org.objectprocess.session.OpmModelAccess"
	import ="org.objectprocess.session.OpmModelAccessHome" 
	import ="org.objectprocess.session.WorkgroupAccess"
	import ="org.objectprocess.session.WorkgroupAccessHome" 
	import ="org.objectprocess.cmp.OpmModelValue" 
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
			ArrayList opmModels = null;
			if (0 == display.compareTo("all")) {
				OpmModelAccessHome opmModelAccessHome = (OpmModelAccessHome)session.getAttribute("opmModelAccessHome");
				OpmModelAccess opmModelAccess = opmModelAccessHome.create();
				opmModels = opmModelAccess.getAllOpmModels(sUserID,sPassword);
				
			} else {
				String workgroupid = new String(request.getParameter("workgroupid"));
				WorkgroupAccessHome workgroupAccessHome = (WorkgroupAccessHome)session.getAttribute("workgroupAccessHome");
				WorkgroupAccess workgroupAccess = workgroupAccessHome.create();
				opmModels = workgroupAccess.getOpmModelsForWorkgroup(sUserID,sPassword,workgroupid);
			}
			Iterator iterate = opmModels.iterator();
			OpmModelValue id = null;
	%>
	<div id="navBar">
		<%@ include file="adminnav.jsp" %>
		<div id="info">
			<h1>OPM models</h1>
			<p>This is a list of all OPM model registered with OPCATeam. You can view and edit OPM model profiles by clicking the appropriate name.</p>
		</div>
	</div>
	<div id="content">
		<table>
			<tr class="columnHeader">
			  <td>Name </td>
			  <td>Creation Time</td>
			  <td >Enabled </td>
			  <td>Revisions</td>
			  <td>Sessions</td>
			  <td>Users</td>
			</tr>
			<%
				while(iterate.hasNext()){
				id = (OpmModelValue)iterate.next();
				pageContext.setAttribute("OpmModelValue",id);
				String enabled = null;
				if (id.getEnabled().booleanValue()== true){ enabled = "<span class=\"greenTrue\">Enabled</span>"; }
				else { enabled = "<span class=\"redFalse\">Disabled</span>"; }
			%>
			<jsp:useBean id="OpmModelValue" scope="page" class="org.objectprocess.cmp.OpmModelValue" />
			<tr>
				<td>
					<a href="opmmodel_profile.jsp?showopmmodelid=<jsp:getProperty name = "OpmModelValue" property = "opmModelID"/>" class="tableRowDark">
						<jsp:getProperty name = "OpmModelValue" property = "opmModelName"/>
					</a>
				</td>
				<td>
					<jsp:getProperty name = "OpmModelValue" property = "creationTime"/>
				</td>
				<td>
					<%=enabled%>
				</td>
				<td>
					<a href="revisions.jsp?opmmodelid=<jsp:getProperty name = "OpmModelValue" property = "opmModelID" />">
						revisions
					</a>
				</td>
				<td>
					<a href="sessions.jsp?display=opmmodel&opmmodelid=<jsp:getProperty name = "OpmModelValue" property = "opmModelID" />">
						sessions
					</a>
				</td>
				<td>
					<a href="permissions.jsp?display=opmmodel&name=<jsp:getProperty name = "OpmModelValue" property = "opmModelName" />&id=<jsp:getProperty name = "OpmModelValue" property = "opmModelID" />">
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
