<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"
	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"
	prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested"
	prefix="nested"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!-- Styles: Common and global stylesheet  -->
<style type="text/css" media="all">
    @import url("<html:rewrite page='/css/common.css'/>");
</style>
<title>Password Status</title>
</head>
<body>
<logic:present name="userPwdStatus">
	<table class="entityview" width="600">
		<tbody>
			<tr>
				<th colspan="2" style="text-align: left;"><bean:write name="passwordStatus" property="userPwdStatus.cn" /> Password Status</th>
			</tr>
			<tr>
				<th>User ID: </th>
				<td><bean:write name="passwordStatus" property="userPwdStatus.userid" /></td>
			</tr>
      <tr>
        <th>Full name: </th>
        <td><bean:write name="passwordStatus" property="userPwdStatus.cn" /></td>
      </tr>
      <tr>
        <th>Email: </th>
        <td><bean:write name="passwordStatus" property="userPwdStatus.email" /></td>
      </tr>
      <tr>
        <th>Last change password:</th>
        <td><bean:write name="passwordStatus" property="userPwdStatus.lastPasswordChangedTime"  format="yyyy-MM-dd HH:mm:ss"/></td>
      </tr>
      <tr>
        <th>Password max age:</th>
        <td>
        <logic:equal name="passwordStatus" property="userPwdStatus.passwordMaxAgeInSeconds" value="0">Never expire!</logic:equal>
        <logic:greaterThan name="passwordStatus" property="userPwdStatus.passwordMaxAgeInSeconds" value="0"><bean:write name="passwordStatus" property="userPwdStatus.passwordMaxAgeInSeconds" /></logic:greaterThan>
        </td>
      </tr>
      
      <logic:greaterThan name="passwordStatus" property="userPwdStatus.passwordMaxAgeInSeconds" value="0">
      <logic:present name="passwordExpired">
      <tr>
        <th>
          <font color="red"><b>Password had expired at: </b></font>
        </th>
        <td>
          <font color="red"><b><bean:write name="passwordStatus" property="userPwdStatus.passwordExpireTime"  format="yyyy-MM-dd HH:mm:ss"/></b></font>
        </td>
      </tr>
      </logic:present>
      <logic:notPresent name="passwordExpired">
      <tr>
        <th>
          Password will expired at:
        </th>
        <td>
          <bean:write name="passwordStatus" property="userPwdStatus.passwordExpireTime"  format="yyyy-MM-dd HH:mm:ss"/>
        </td>
      </tr>
      <tr>
        <th>
          Time left:
        </th>
        <td>
          <bean:write name="dayLeft"/> Day <bean:write name="hourLeft"/> Hour <bean:write name="minuteLeft"/> minute
        </td>
      </tr>
      </logic:notPresent>
      </logic:greaterThan>
      
      <logic:equal name="passwordStatus" property="needToWebNotify" value="true">
      <tr>
        <td colspan="2" >
          <font color="red" size="+2" ><b><a href="/pkmspasswd.form">"Please click and immediately change your password!</a></b></font>
        </td>
      </tr>
      </logic:equal>
		</tbody>
	</table>
</logic:present>
<logic:notPresent name="userPwdStatus">
<logic:present name="uid">Could not load user:[<b><bean:write name="uid"/></b>] information!</logic:present>
<logic:notPresent name="uid">Could not found uid from Http Request!</logic:notPresent>
</logic:notPresent>
</body>
</html>