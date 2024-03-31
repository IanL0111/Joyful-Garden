<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>編輯活動</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
            font-size: 16px; 
        }

        .container {
            box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
            border-radius: 10px;
            overflow: hidden;
            background-color: white;
            padding: 20px;
            width: 80%;
            margin: 20px auto;
        }

        h2 {
            color: #333;
            text-align: center;
            margin-bottom: 20px;
            font-size: 24px; 
        }

        form {
            display: flex;
            flex-direction: column;
        }

        input[type=text], input[type=date], input[type=file], textarea {
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ddd;
            border-radius: 5px;
            width: calc(100% - 22px); 
            font-size: 16px; 
        }

        textarea {
            resize: vertical;
            font-size: 16px; 
        }

        button {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            margin-top: 10px;
        }

        button:hover {
            background-color: #45a049;
        }

        img {
            max-width: 100px;
            margin-top: 5px;
            border-radius: 5px;
            
        }
        
    </style>
<script src="https://cdn.jsdelivr.net/npm/axios@1.6.7/dist/axios.min.js"></script> 
</head>
<body>
<div class="container">
    <h2>編輯活動</h2>
    <form enctype="multipart/form-data">
        活動編號：<input type="text" name="activityName" value="${act.activityId}" required readonly><br>
        活動名稱：<input type="text" id="activityName" name="activityName" value="${act.activityName}" required><br>
        日期：<input type="date" id="activityDate" name="activityDate" value="${act.activityDate}" required><br>
        地點：<input type="text" id="activityLocation" name="activityLocation" value="${act.activityLocation}" required><br>
        內容：<textarea id="activityContent" name="activityContent" required>${act.activityContent}</textarea><br>
        圖片：<input type="file" id="inputImage" name="activityImage" multiple/><br>
        
        <div style="display: flex; flex-wrap: wrap;">
   			 <c:forEach items="${images}" var="image">
      			  <img src="${image.imagesPath}" style="max-width: 100px; margin-right: 10px; margin-bottom: 10px; border-radius: 5px;">
   			 </c:forEach>
		</div>
		
        <button type="button" onclick="update(${act.activityId})">更新活動</button>
    </form>
</div>

<script>
 function update(activityId){
	var formData = new FormData();
    var activityName = document.getElementById('activityName').value;
    var activityDate = document.getElementById('activityDate').value;
    var activityLocation = document.getElementById('activityLocation').value;
    var activityContent = document.getElementById('activityContent').value;
    
    formData.append('activityName', activityName);
    formData.append('activityDate', activityDate);
    formData.append('activityLocation', activityLocation);
    formData.append('activityContent', activityContent);
    
    var fileInput = document.getElementById('inputImage');
    var file = fileInput.files;

    if(file.length > 0){
        for (let i = 0; i < file.length; i++) {
            formData.append('activityImageList', file[i]);
        }

        axios.post('/activity/'+activityId, formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        }).then((response)=>{
            console.log(response);
            window.location.href = '/activity'; 
        })
        .catch((error)=>{
            console.log('There was a problem with your Axios request:', error);
        });
    } else {
        axios.put('/activity/'+activityId, formData)
        .then((response)=>{
            console.log(response);
            window.location.href = '/activity'; 
        })
        .catch((error) =>{
            console.log('There was a problem with your Axios request:', error);
        });
    }
} 
</script>

</body>
</html>
