<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>文件上传</title>
</head>
<body>
	<h1>文件上传</h1>
	<form action="http://localhost:8092/file" method="post" enctype="multipart/form-data">
		文件： <input name="fileName" type="file" /> <br /> <input type="submit"
			value="提交">
	</form>
</body>
</html>