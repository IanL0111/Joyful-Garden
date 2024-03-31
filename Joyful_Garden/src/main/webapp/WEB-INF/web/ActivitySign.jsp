<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>活動報名系統</title>

<script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/axios@1.6.7/dist/axios.min.js"></script>
<style>
body, html {
        height: 100%;
        margin: 0;
        display: flex;
        justify-content: center;
        align-items: center;
        font-size: 16px; 
    }
    form {
        width: 400px;
        padding: 20px;
        border: 1px solid #ccc;
        border-radius: 5px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }
    label {
        display: block;
        margin-bottom: 10px;
        font-size: 18px; 
    }
    input[type="text"], textarea {
        width: 100%;
        padding: 8px;
        margin-bottom: 10px;
        box-sizing: border-box;
        font-size: 16px; 
    }
    input[type="button"] {
        padding: 10px 20px;
        background-color: #007bff;
        color: #fff;
        border: none;
        cursor: pointer;
        font-size: 16px; 
    }
    body, html {
        height: 100%;
        margin: 0;
        display: flex;
        justify-content: center;
        align-items: center;
    }
    form {
        width: 400px;
        padding: 20px;
        border: 1px solid #ccc;
        border-radius: 5px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }
    label {
        display: block;
        margin-bottom: 10px;
    }
    input[type="text"], textarea {
        width: 100%;
        padding: 8px;
        margin-bottom: 10px;
        box-sizing: border-box;
    }
    input[type="submit"] {
        padding: 10px 20px;
        background-color: #007bff;
        color: #fff;
        border: none;
        cursor: pointer;
    }
</style>
</head>
<body>
<%--     <form id="signupForm" action="/students/${activityId}" method="post"> --%>
	<form>
        <h2 style="text-align: center;">活動報名系統</h2>
        <label for="name">姓名:</label>
        <input type="text" id="name" name="name">
        <label for="email">電子郵件:</label>
        <input type="text" id="email" name="email">
        <label for="phone">電話:</label>
        <input type="text" id="phone" name="phone">
        <label for="address">地址:</label>
        <textarea id="address" name="address"></textarea>
        <input type="button" value="送出" style="width: 100%;" onclick="submitForm(${ActivityId})">
    </form>

    
    
    
    <script>
    
    function submitForm(ActivityId){
    	
    	var name = document.getElementById('name').value;
    	var email = document.getElementById('email').value;
    	var phone = document.getElementById('phone').value;
    	var address = document.getElementById('address').value;
    	
    	
        if (!name.trim() || !address.trim()) {
            swal("錯誤", "姓名和地址不能為空！", "error");
            return;
        }
    	
    	
        var phonePattern = /^09\d{8}$/; 
        if (!phonePattern.test(phone)) {
            swal("錯誤", "請輸入正確的手機號碼，必須以 09 開頭且共 10 位數字！", "error");
            return;
        }
        
        
        var emailPattern = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
        if (!emailPattern.test(email)) {
            swal("錯誤", "請輸入有效的電子郵件地址！", "error");
            return;
        }
    	
    	var formData = new FormData();
    	formData.append("ActivityId",ActivityId);
    	formData.append("name",name);
    	formData.append("email",email);
    	formData.append("phone",phone);
    	formData.append("address",address);
    	
    	
    	axios.post('/students/'+ActivityId,formData)
    	.then((response)=>{
            swal(response.data, "您已成功報名該活動！", "success")
            .then(function() {
                window.location.href = "/activity2";
            })
    	}).catch((Error)=>{
    		swal("報名失敗", "請稍後重試！", "error");
    		})
  	  }
    
    </script>
</body>
</html>
