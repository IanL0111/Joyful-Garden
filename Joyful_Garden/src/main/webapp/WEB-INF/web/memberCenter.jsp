<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>首頁</title>
    <style>
        body {
            display: flex;
            align-items: center;
            justify-content: center;
            min-height: 100vh;
            background: url(../image/blue1.jpg) no-repeat;
            background-size: cover;
            background-position: center;
        }

        .container {
            width: 550px;
            background: transparent;
            border: 2px solid rgba(255, 255, 255, 0.2);
            backdrop-filter: blur(20px);
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
            color: white;
            border-radius: 10px;
            padding: 30px 40px;
            font-size:20px;
        }

        .container h1 {
            font-size: 36px;
            text-align: center;
        }

        .container p {
            font-size: 18px;
            text-align: center;
        }

        .container ul {
            list-style: none;
            padding: 0;
        }

        .container ul li {
            margin-bottom: 10px;
        }

        .container ul li strong {
            font-weight: bold;
            margin-right: 5px;
        }

        .button-container {
            display: flex;
            justify-content: space-between;
            margin-top: 20px;
        }

        .button {
            width: 48%;
            height: 45px;
            background-color: #fff;
            border: 2px solid #7a7281;
            border-radius: 40px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            cursor: pointer;
            font-size: 16px;
            color: #7a7281;
            font-weight: 600;
            display: flex;
            align-items: center;
            justify-content: center;
            text-decoration: none;
            transition: all 0.3s ease;
        }

        .button:hover {
            background-color: #7a7281;
            color: #fff;
        }
    </style>
</head>

<body>
    <div class="container">
        <h1>會員中心</h1>
        <p>您好，${member.nickName}</p>
        <form action="/user/web/memberCenter/${member.memberId}" method="post">
        <ul>
            <li><strong>帳號(Email)：</strong>${member.username}</li>
            <li><strong>暱稱：</strong><input type="text" name="nickName" value="${member.nickName}"></li>
            <li><strong>手機號碼：</strong><input type="text" name="phoneNumber" value="${member.phoneNumber}"></li>
            <li><strong>生日：</strong>${member.birthdate}</li>
            <li><strong>地址：</strong><input type="text" name="address" value="${member.address}"></li>
            <!-- 其他用户信息，根据需要添加 -->
        </ul>
        <!-- 登出按鈕 -->
        <div class="button-container">
            <a href="/logout" class="button">登出</a>
            
           
            <button type="submit" class="button">更新資料</button>
        </div>
        </form>
    </div>
    <!-- JavaScript -->
    <%@ include file="../shop/MenuBar2.html" %>
    <script>
        
    </script>
</body>

</html>
