<%@ tag language="java" pageEncoding="UTF-8"%>
<%@attribute name="title" fragment="true"%>
<html>
	<head>
		<title><jsp:invoke fragment="title" /></title>
	</head>
	<body>
		<jsp:doBody />
	</body>
</html>
