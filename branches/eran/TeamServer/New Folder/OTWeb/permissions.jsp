<%@ 
	page language="java" 
	errorPage="/error.jsp"
	import ="org.objectprocess.session.WorkgroupAccess"
	import ="org.objectprocess.session.WorkgroupAccessHome" 
	import ="org.objectprocess.session.OpmModelAccess"
	import ="org.objectprocess.session.OpmModelAccessHome" 
	import ="org.objectprocess.session.CollaborativeSessionAccess"
	import ="org.objectprocess.session.CollaborativeSessionAccessHome" 
	import ="org.objectprocess.cmp.EnhancedWorkgroupValue"
	import ="org.objectprocess.cmp.Enhanced2WorkgroupPermissionsValue"
	import ="org.objectprocess.cmp.EnhancedOpmModelValue"	
	import ="org.objectprocess.cmp.Enhanced2OpmModelPermissionsValue"
	import ="org.objectprocess.cmp.EnhancedCollaborativeSessionValue"
	import ="org.objectprocess.cmp.Enhanced2CollaborativeSessionPermissionsValue"
	import ="org.objectprocess.security.SecurityManager"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Permissions</title>
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
			int length = 0;
			String header = null;
			SecurityManager securityManager = new SecurityManager();
			String display = new String(request.getParameter("display"));
			Enhanced2WorkgroupPermissionsValue uewpvList[] = null;
			Enhanced2OpmModelPermissionsValue ueompvList[] = null;
			Enhanced2CollaborativeSessionPermissionsValue uecspvList[] = null;
			String id = new String(request.getParameter("id"));;
			String name = new String(request.getParameter("name"));;
			if (0 == display.compareTo("workgroup")){
				WorkgroupAccessHome workgroupAccessHome = (WorkgroupAccessHome)session.getAttribute("workgroupAccessHome");
				WorkgroupAccess workgroupAccess = workgroupAccessHome.create();
				EnhancedWorkgroupValue ewv = workgroupAccess.getEnhancedWorkgroupByPK(sUserID,sPassword,id);
				uewpvList = ewv.getWorkgroupsPermissions();
				length = uewpvList.length;
				header = "User permissions for workgroup: " + request.getParameter("name");
			} else { if (0 == display.compareTo("opmmodel")) {
				OpmModelAccessHome opmModelAccessHome = (OpmModelAccessHome)session.getAttribute("opmModelAccessHome");
				OpmModelAccess opmModelAccess = opmModelAccessHome.create();
				EnhancedOpmModelValue eomv = opmModelAccess.getEnhancedOpmModelByPK(sUserID,sPassword,id);
				ueompvList = eomv.getOpmModelsPermissions();
				length = ueompvList.length;
				header = "User permissions for OPM model: " + name;
			} else { if (0 == display.compareTo("session")) {
				CollaborativeSessionAccessHome collaborativeSessionAccessHome = (CollaborativeSessionAccessHome)session.getAttribute("collaborativeSessionAccessHome");
				CollaborativeSessionAccess collaborativeSessionAccess = collaborativeSessionAccessHome.create();
				EnhancedCollaborativeSessionValue ecsv = collaborativeSessionAccess.getEnhancedCollaborativeSessionByPK(sUserID,sPassword,id);
				uecspvList = ecsv.getCollaborativeSessionsPermissions();
				length = uecspvList.length;
				header = "User permissions for collaborative session: " + name;
			}
			}
			}
	%>
	<div id="navBar">
		<%@ include file="adminnav.jsp" %>
		<div id="info">
			<p>The lengths specify the distance that separates adjacent cell borders. If one length is specified, it gives both the horizontal and vertical spacing. If two are specified, the first gives the horizontal spacing and the second the vertical spacing. Lengths may not be negative.</p>
		</div>
	</div>
	<div id="content">
		<table>
			<tr class="columnHeader">
			  <td>Login Name</td>
			  <td>Viewer</td>
			  <td>Editor</td>
			  <td>Commiter</td>
			  <td>Moderator</td>
			  <td>Administrator</td>
			  <td>Creator</td>
			</tr>
				<%
					int iterator = 0;
					Integer accessControl = null;
					String loginname = null;
					String userid = null;
					String viewer = null, editor=null, commiter = null, moderator = null, administrator = null, creator = null;
					
					while(iterator < length){
						if (0 == display.compareTo("workgroup")){
							loginname = uewpvList[iterator].getUser().getLoginName();
							userid = uewpvList[iterator].getUser().getUserID();
							accessControl = uewpvList[iterator].getAccessControl();
						} else { if (0 == display.compareTo("opmmodel")){
							loginname = ueompvList[iterator].getUser().getLoginName();
							userid = ueompvList[iterator].getUser().getUserID();
							accessControl = ueompvList[iterator].getAccessControl();
						} else { if (0 == display.compareTo("session")){
							loginname = uecspvList[iterator].getUser().getLoginName();
							userid = uecspvList[iterator].getUser().getUserID();
							accessControl = uecspvList[iterator].getAccessControl();
						}
						}
						}
						
						if (securityManager.isViewer(accessControl)){
							viewer = "<span class=\"greenTrue\">True</span>"; 
						} else { viewer = "<span class=\"redFalse\">False</span>"; }
						if (securityManager.isEditor(accessControl)){
							editor = "<span class=\"greenTrue\">True</span>"; 
						} else { editor = "<span class=\"redFalse\">False</span>"; }
						if (securityManager.isCommiter(accessControl)){
							commiter = "<span class=\"greenTrue\">True</span>"; 
						} else { commiter = "<span class=\"redFalse\">False</span>"; }
						if (securityManager.isModerator(accessControl)){
							moderator = "<span class=\"greenTrue\">True</span>"; 
						} else { moderator = "<span class=\"redFalse\">False</span>"; }
						if (securityManager.isAdministrator(accessControl)){
							administrator = "<span class=\"greenTrue\">True</span>"; 
						} else { administrator = "<span class=\"redFalse\">False</span>"; }
						if (securityManager.isCreator(accessControl)){
							creator = "<span class=\"greenTrue\">True</span>"; 
						} else { creator = "<span class=\"redFalse\">False</span>"; }
				%>
			<tr>
				<td><a href="/OTWeb/user_profile.jsp?showuserid=<%=userid%>"><%=loginname%></a></td>
				<td><%=viewer%></td>
				<td><%=editor%></td>
				<td><%=commiter%></td>
				<td><%=moderator%></td>
				<td><%=administrator%></td>
				<td><%=creator%></td>
			</tr>
		<%iterator++;}%>
		</table>
	</div>
<%}%>
</body>
</html>
