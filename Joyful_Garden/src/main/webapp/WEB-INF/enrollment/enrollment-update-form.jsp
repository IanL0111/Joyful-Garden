<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-TW">
<head>
    <meta charset="UTF-8">
    <title>報名修改表單</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.min.css">
    <style>
        .container {
            max-width: 600px;
            margin-top:20px;
            padding-top: 20px;
        }
        h1 {
            text-align: center;
            margin-top:20px;
            margin-bottom: 10px;
            font-size:30px;
        }
        form {
            background-color: #f9f9f9;
            padding: 10px;
            border-radius: 10px;
            box-shadow: 0px 0px 10px 0px rgba(0,0,0,0.1);
        }
        .form-group {
            margin-bottom: 20px;
        }
        label {
            font-weight: bold;
        }
        button[type="submit"], .return-btn {
            width: 100%;
            margin-top: 10px;
        }
    </style>
</head>
<body>

    <h1>報名修改表單</h1>

    <div class="container">
        <form id="editForm" action="/updateEnrollment/${enrollment.enrollmentID}" method="post">
            <div class="form-group">
                <label for="studentName">學生姓名：</label>
                <input type="text" id="studentName" name="studentName" value="${enrollment.student.studentName}" class="form-control" required readonly>
            </div>
            <div class="form-group">
                <label for="courseName">課程名稱：</label>
                <input type="text" id="courseName" name="courseName" value="${enrollment.course.courseName}" class="form-control" required readonly>
            </div>
            <div class="form-group">
                <label for="enrollmentDate">報名日期：</label>
                <input type="date" id="enrollmentDate" name="enrollmentDate" value="${enrollment.enrollmentDate}" class="form-control" required>        
            </div>
            <div class="form-group">
                <label for="paymentStatus">支付狀態：</label>
                <select id="paymentStatus" name="paymentStatus" class="form-control" required>
                    <option value="是">是</option>
                    <option value="否">否</option>
                </select>
            </div>
            <div class="form-group">
                <label for="paymentAmount">支付金額：</label>
                <input type="text" id="paymentAmount" name="paymentAmount" value="${enrollment.paymentAmount}" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="paymentDate">支付日期：</label>  
                <input type="date" id="paymentDate" name="paymentDate" value="${enrollment.paymentDate}" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="email">電子信箱：</label>
                <input type="email" id="email" name="email" value="${enrollment.student.email}" class="form-control" required readonly>
            </div>
            <div class="form-group">
                <label for="contactNumber">行動電話：</label>
                <input type="text" id="contactNumber" name="contactNumber" value="${enrollment.student.contactNumber}" class="form-control" required readonly>
                <input type="hidden" name="courseID" value="${enrollment.course.courseID}">
                <input type="hidden" name="studentID" value="${enrollment.student.studentID}">
            </div>
            <button type="submit" class="btn btn-primary">儲存修改</button>
            <a  href="<c:url value='/enrollment-management'/>" class="btn btn-secondary return-btn">返回課程報名管理</a>
        </form>
    </div>

    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js"></script>
    <script>
        $(document).ready(function(){
            $('.date').datepicker({
                format: 'yyyy-mm-dd',
                autoclose: true,
                todayHighlight: true
            });
        });
    </script>
</body>
</html>
