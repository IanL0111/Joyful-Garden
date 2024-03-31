<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>活動列表</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
        }

        .container {
            box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
            border-radius: 10px;
            overflow: hidden;
            background-color: white;
            width: 80%;
            margin: auto;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            border-bottom: 1px solid #ddd;
            padding: 12px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
        }

        tr:hover {
            background-color: #f5f5f5;
        }

        img {
            border-radius: 5px;
            margin-top: 5px;
            max-width: 100px;
            max-height: 100px;
        }
    </style>
</head>
<body>
<div class="container">
    <h1 style="text-align: center;">活動列表</h1>
    <table border="1">
        <tr>
        	<th>圖片</th>
            <th>活動名稱</th>
            <th>日期</th>
            <th>地點</th>
            <th>内容</th>
           
        </tr>
        <c:forEach items="${activity}" var="act">
            <tr>
           	    <td>
                    <img src="${act.images.imagesPath}" alt="活動圖片">
                </td>
                <td>${act.activityName}</td>
                <td>${act.activityDate}</td>
                <td>${act.activityLocation}</td>
                <td>${act.activityContent}</td>
                
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
