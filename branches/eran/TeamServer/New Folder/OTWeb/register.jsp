<%@
	page contentType="text/html; charset=iso-8859-1" 
	language="java" 
	errorPage="/error.jsp"
%>
<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<title></title>
<link href="style.css" rel="stylesheet" type="text/css">
</head>
<body bgcolor="#FFFFFF">
<table width="950" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="30">&nbsp;</td>
    <td><table width="920" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td><img src="images/opcatlogo.gif" width="333" height="67"></td>
        </tr>
        <tr>
          <td height="4"></td>
        </tr>
        <tr>
          <td height="1"><img src="images/line900.gif" width="900" height="1"></td>
        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td height="18" align="left" valign="bottom"><span class="texthome">Use the Navigator to access the Users, Workgroups, OPM Models and Sessions currently available in the system.</span></td>
        </tr>
        <tr>
          <td height="18" align="left" valign="bottom"><span class="texthome">Use the Edit button to update the selected element. </span></td>
        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td><table width="920" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="140" align="left" valign="top">&nbsp;</td>
                <td width="30">&nbsp;</td>
                <td align="left" valign="top"><table width="750" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="400" align="left" valign="top"><form name="register" method="post" action="admin">
                      <table width="400" border="1" cellpadding="0" cellspacing="0" bordercolor="345487">
                        <tr>
                          <td><table width="100%" border="0" cellspacing="1" cellpadding="0">
                              <tr >
                                <td colspan="2" class="tableHeader" >Register a new user</td>
                              </tr>
                              <tr >
                                <td class="tableRowLeft">Login Name : </td>
                                <td class="tableRowRight"><input name="loginName" type="text" class="TextBoxControl" >                                </td>
                              </tr>
                              <tr >
                                <td width="150" class="tableRowLeft">Password : </td>
                                <td width="250" class="tableRowRight"><input name="password" type="text" class="TextBoxControl" >                                </td>
                              </tr>
                              <tr >
                                <td width="150" class="tableRowLeft">First Name : </td>
                                <td width="250" class="tableRowRight">
                                  <input name="firstName" type="text" class="TextBoxControl" >                                </td>
                              </tr>
                              <tr >
                                <td width="150" class="tableRowLeft">Last Name : </td>
                                <td width="250" class="tableRowRight">
                                  <input name="lastName" type="text" class="TextBoxControl" >                                </td>
                              </tr>
                              <tr>
                                <td width="150" class="tableRowLeft">Email : </td>
                                <td width="250" class="tableRowRight">
                                  <input name="email" type="text" class="TextBoxControl" >                                </td>
                              </tr>
                              <tr>
                                <td class="tableRowLeft">Description : </td>
                                <td class="tableRowRight"><input name="description" type="text" class="TextBoxControl" ></td>
                              </tr>
                              <tr>
                                <td class="tableRowLeft"><input type="hidden" name="action" value="register"></td>
                                <td class="tableRowRight"><input name="register" type="submit" class="ButtonControl" id="register" value="register"></td>
                              </tr>
                          </table></td>
                        </tr>
                      </table>
                    </form></td>
                    <td width="20">&nbsp;</td>
                    <td>&nbsp;</td>
                  </tr>
                </table></td>
              </tr>
          </table></td>
        </tr>
        <tr>
          <td class="texthome">&nbsp;</td>
        </tr>
        <tr>
          <td class="texthome">&copy; Site developed by Dizza Beimel and Lior Galanti, 2004 at the Technion Institute of Technology.</td>
        </tr>
    </table></td>
  </tr>
</table>
</body>
</html>