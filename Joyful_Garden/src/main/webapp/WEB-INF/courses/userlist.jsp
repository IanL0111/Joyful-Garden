<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="zh-TW">
<head>
    <meta charset="UTF-8">
    <title>Joyful Garden課程列表</title>

    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="../../css/userlist.css" />
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
   
</head>
<body>

<img class="fixed-image" src="/image/background.png" alt="fixed-image" />
<%@ include file="header.jsp" %>
  
<section class="background-img">
    <div class="filter"></div>
    <h3>
        Courses <br />
    </h3>
</section>

<div class="user-course-container">
    <h1>精選特色主題課程</h1> 
    <c:forEach var="course" items="${userCoursesList}">
        <div class="user-course-item">
            <a href='/courses/details/${course.courseID}'> <!-- 課程超連結 -->
                <img src="data:image/png;base64,${course.image}" class="img-fluid" alt="課程圖片">
            </a>
            <div class="user-course-text">
                <div class="course-name"><a href='/courses/details/${course.courseID}'><c:out value="${course.courseName}" /></a></div><!-- 課程名稱超連結 -->
                <div class="course-startDate"><c:out value="${course.startDate}" /></div>
                <a href='/courses/details/${course.courseID}' class="user-course-description">
                    <c:if test="${fn:length(course.description) gt 100}">
                        <c:out value="${fn:substring(course.description, 0, 100)}" />
                    </c:if>
                    <c:if test="${fn:length(course.description) le 100}">
                        <c:out value="${course.description}" />
                    </c:if>
                </a>
                <div class="course-price">課程定價：NT <c:out value="${course.price}" /></div>
            </div>
        </div>
    </c:forEach>
</div>

<%@ include file="../shop/MenuBar3.html" %>
<%@ include file="../shop/Footer.html" %>
</body>
</html>
