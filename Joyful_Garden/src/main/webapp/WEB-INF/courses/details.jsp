<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-TW">
<head>
    <meta charset="UTF-8">
    <title>課程詳細資訊</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="../../css/details.css" />
</head>
<body>
<img class="fixed-image" src="/image/static04.png" alt="fixed-image" />
<%@ include file="header.jsp" %>

<section class="background-image">
    <div class="filter"></div>
    <h3>
        <br />
    </h3>
</section>

<div class="course-details-container">
    <c:forEach var="selectedCourse" items="${details}">
        <div class="course-details-item">
            <img src="data:image/png;base64,${selectedCourse.image}" class="img-fluid" alt="課程圖片">
            <div class="course-name">${selectedCourse.courseName}</div>
            <div class="user-course-description">${selectedCourse.description}</div>
            <div class="course-startDate">開課日期 : ${selectedCourse.startDate}</div>
            <div class="course-price">課程定價：NT ${selectedCourse.price}</div>
            <button class="btn btn-primary" onclick="redirectToEnrollmentForm(${selectedCourse.courseID}, ${selectedCourse.price})">報名去</button>
        </div>
    </c:forEach>
</div>

    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.bundle.min.js"></script>
    <script type="text/javascript">
        function redirectToEnrollmentForm(courseID, coursePrice) {
            window.location.href = '/enrollment-form/' + courseID + '?coursePrice=' + coursePrice;
        }
    </script>

<%@ include file="Footer.html" %>
</body>
</html>
