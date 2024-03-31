<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Activity Sign</title>
<style>

    body {
        font-family: Arial, sans-serif;
        background-color: #f8f9fa;
        margin: 0;
        padding: 0;
    }
    .container {
        width: 80%;
        margin: 50px auto;
        background-color: #fff;
        border-radius: 8px;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        overflow: hidden;
    }
    h1 {
        background-color: #343a40;
        color: #fff;
        margin: 0;
        padding: 20px;
        text-align: center;
        text-transform: uppercase;
        border-top-left-radius: 8px;
        border-top-right-radius: 8px;
    }
    table {
        width: 100%;
        border-collapse: collapse;
    }
    table th, table td {
        padding: 15px;
        border-bottom: 1px solid #ddd;
        text-align: center; 
    }
    table th {
        background-color: #343a40;
        color: #fff;
        text-transform: uppercase;
    }
    table tbody tr:nth-child(even) {
        background-color: #f8f9fa;
    }
    .export-button-container {
        text-align: center;
        margin-top: 20px;
        margin-bottom: 20px;
    }
    .custom-button {
        padding: 10px 20px;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        font-size: 16px;
        transition: background-color 0.3s ease;
    }
    .excel-button {
        background-color: #28a745;
        color: #fff;
    }
    .custom-button:hover {
        opacity: 0.8;
    }
    .delete-button {
        background-color: #dc3545;
        color: #fff;
        border: none;
        border-radius: 5px;
        padding: 8px 16px;
        cursor: pointer;
        font-size: 14px;
        transition: background-color 0.3s ease;
    }
    .delete-button:hover {
        background-color: #c82333;
    }
</style>
</head>
<body>
    <div class="container">
        <h1>報名學生資料表</h1>
        <table border="1">
            <thead>
                <tr>
                    <th>活動名稱</th>
                    <th>報名學生</th>
                    <th>報名日期</th>
                    <th>報名信箱</th>
                    <th>報名手機</th>
                    <th>操作</th> <!-- 新增一列用于操作按钮 -->
                </tr>
            </thead>
            <tbody>
    <c:forEach items="${asStudents}" var="aaa">
    <tr>           	
        <td>${aaa.activity.activityName}</td>
        <td>${aaa.student.name}</td>
        <td>${aaa.registerDate.substring(0, 10)}</td>       
        <td>${aaa.student.email}</td>  
        <td>${aaa.student.phone}</td>
        <td>
            <form id="deleteForm_${aaa.student.studentId}" action="/students/${aaa.student.studentId}" method="post">
                <input type="hidden" name="_method" value="delete">
                <button type="submit" class="delete-button">刪除</button>
            </form>
        </td>
    </tr>
</c:forEach>
</tbody>
        </table>
        <div class="export-button-container">
            <form id="exportForm" action="/exportASStudents" method="post">
                <button type="submit" class="custom-button excel-button">匯出Excel</button>
                <a href="/activity" class="custom-button excel-button">返回活動頁面</a>          
            </form>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script>
        $(document).ready(function() {
            $('#exportForm').submit(function(event) {
                event.preventDefault();

                $.ajax({
                    type: 'POST',
                    url: '/exportASStudents',
                    xhrFields: {
                        responseType: 'blob'
                    },
                    success: function(response, status, xhr) {
                        var filename = "ASStudentsDetails.xlsx";
                        var blob = new Blob([response], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
                        var link = document.createElement('a');
                        link.href = window.URL.createObjectURL(blob);
                        link.download = filename;
                        document.body.appendChild(link);
                        link.click();
                        document.body.removeChild(link);

                        // 在成功时显示SweetAlert
                        Swal.fire({
                            icon: 'success',
                            title: '導出成功',
                            text: '您已成功導出 Excel！',
                            confirmButtonText: '確定',
                        });
                    },
                    error: function(xhr, status, error) {
                        // 在失败时显示SweetAlert
                        Swal.fire({
                            icon: 'error',
                            title: '導出失敗',
                            text: '請稍後重試！',
                            confirmButtonText: '確定',
                        });
                    }
                });
            });
        });

        // 添加一个确认删除的函数
        function confirmDelete(studentId) {
    if (confirm('確定刪除？')) {
        $.ajax({
            type: 'POST',
            url: '/deleteStudent?id=' + studentId,
            success: function(data) {
                // 刪除成功後進行頁面導向
                window.location.href = '/xxx';
            },
            error: function(xhr, status, error) {
                console.error('刪除學生時發生錯誤:', error);
            }
        });
    }
}
        
        
    </script>
</body>
</html>
