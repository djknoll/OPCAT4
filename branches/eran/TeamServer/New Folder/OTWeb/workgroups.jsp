<%@ 
	page language="java" 
	errorPage="/error.jsp"
	import ="org.objectprocess.session.WorkgroupAccess"
	import ="org.objectprocess.session.WorkgroupAccessHome" 
	import ="org.objectprocess.cmp.WorkgroupValue" 
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
		Iterator iterate = null;
		WorkgroupValue id = null;
		if(sUserID == null || sPassword == null){
			getServletContext().getRequestDispatcher("/gateway.jsp").forward(request,response);
		}else{
			WorkgroupAccessHome workgroupAccessHome = (WorkgroupAccessHome)session.getAttribute("workgroupAccessHome");
			WorkgroupAccess workgroupAccess = workgroupAccessHome.create();
			ArrayList workgroups = workgroupAccess.getAllWorkgroups(sUserID,sPassword);
			iterate = workgroups.iterator();
	%>
	<div id="navBar">
		<%@ include file="adminnav.jsp" %>
		<div id="info">
			<h1>Workgroups</h1>
			<p>This is a list of all workgroups registered with OPCATeam. You can view and edit workgroup profiles by clicking the appropriate name.</p>
		</div>
	</div>
	<div id="content">
		<table>
			<tr class="columnHeader">
				<td>Name </td>
				<td>Creation Time</td>
				<td>Enabled</td>
				<td>OPM models</td>
				<td>Users</td>
			</tr>
			<%
				while(iterate.hasNext()){
				id = (WorkgroupValue)iterate.next();
				pageContext.setAttribute("workgroupValue",id);
				String enabled = null;
				if (id.getEnabled().booleanValue()== true){ enabled = "<span class=\"greenTrue\">Enabled</span>"; }
				else { enabled = "<span class=\"redFalse\">Disabled</span>"; }
			%>
			<jsp:useBean id="workgroupValue" scope="page" class="org.objectprocess.cmp.WorkgroupValue" />
			<tr>
				<td>
					<a href="workgroup_profile.jsp?showworkgroupid=<jsp:getProperty name = "workgroupValue" property = "workgroupID"/>">
								<jsp:getProperty name = "workgroupValue" property = "workgroupName"/>
					</a> 
				</td>
				<td>
					<jsp:getProperty name = "workgroupValue" property = "creationTime"/> 
				</td>
				<td>
					<%=enabled%>
				</td>
				<td>
					<a href="opmmodels.jsp?display=workgroup&workgroupid=<jsp:getProperty name = "workgroupValue" property = "workgroupID" />">
						OPM model
					</a>
				</td>
				<td>
					<a href="permissions.jsp?display=workgroup&name=<jsp:getProperty name = "workgroupValue" property = "workgroupName" />&id=<jsp:getProperty name = "workgroupValue" property = "workgroupID" />">
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
