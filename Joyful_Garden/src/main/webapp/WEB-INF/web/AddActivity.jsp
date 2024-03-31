<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>新增活動</title>
   
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.css">
    <style>
        body {
        font-family: 'Arial', sans-serif;
        background-color: #f0f2f5;
        margin: 0;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        font-size: 16px; 
    }
    .container {
        background-color: white;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        width: 500px;
    }
    h2 {
        color: #333;
        font-size: 24px; 
    }
    form {
        display: flex;
        flex-direction: column;
        align-items: stretch;
    }
    input[type="text"], input[type="date"], textarea, input[type="file"] {
        padding: 10px;
        margin: 10px 0;
        border-radius: 5px;
        border: 1px solid #ccc;
        width: calc(100% - 20px); 
        font-size: 16px; 
    }
    input[type="submit"] {
        cursor: pointer;
        background-color: #4CAF50;
        color: white;
        border: none;
        padding: 10px;
        border-radius: 5px;
        font-weight: bold;
        margin-top: 20px;
        transition: background-color 0.3s ease;
        font-size: 16px; 
    }
    input[type="submit"]:hover {
        background-color: #45a049;
    }
    textarea {
        resize: vertical;
        font-size: 16px; /* 調整文本區字體大小 */
    }
    </style>
</head>
<body> 
<div class="container">
    <h2>新增活動</h2>
    <form id="addActivityForm" action="<c:url value='/web/activity'/>" method="post" enctype="multipart/form-data">
        活動名稱：<input type="text" name="activityName" required><br>
        日期：<input type="date" name="activityDate" required><br>
        地點：<input type="text" name="activityLocation" required><br>
        內容：<textarea name="activityContent" required></textarea><br>
        圖片：<input type="file" name="activityImage" multiple><br> 
        <input type="submit" value="新增活動">
    </form>
</div>


<script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
<script>
    
    document.getElementById('addActivityForm').addEventListener('submit', function(event) {
        event.preventDefault(); 
        
        
        var form = this;
        var formData = new FormData(form);
        var xhr = new XMLHttpRequest();
        xhr.open('POST', form.getAttribute('action'), true);
        xhr.onload = function() {
            if (xhr.status === 200) {
                
                swal({
                    title: "新增成功",
                    text: "您已成功新增活動！",
                    icon: "success",
                    button: "確定",
                }).then(function() {
                    
                    window.location.href = "/activity"; 
                });
                
                form.reset();
            } else {
                
                swal("新增失敗", "請稍後重試！", "error");
            }
        };
        xhr.send(formData);
    });
</script>
</body>
</html>
