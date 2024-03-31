<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
   <link rel="stylesheet" href="../../css/header.css">
</head>
<body>
<header>
    <nav>
        <ul>
            <li></li>
            <li></li>
            <li></li>
        </ul>
    </nav>

    <section class="logo">
        <!-- <img src="./images/網站Logo.svg" alt="日本旅遊網Logo" /> -->
        <h1><a href="/">Joyful Garden</a></h1>
        
    </section>

    <nav>
        <ul>
            <li></li>
            <li></li>
             <c:choose>
       			 <c:when test="${not empty member.nickName}">
       			 	<li><a href="/user/memberCenter" > Welcome ${member.nickName}</a></li>
       			 </c:when>
       			  <c:otherwise>
            		<li><a href="/user/loginPage" style="text-decoration: none; color: inherit;"> 會員登入</a></li>
        		  </c:otherwise>	
        	</c:choose>
        </ul>
    </nav>
</header>

</body>
</html>