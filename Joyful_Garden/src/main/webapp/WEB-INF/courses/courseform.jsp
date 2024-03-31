<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="zh-TW">
<head>
    <meta charset="UTF-8">
    <title>課程表單</title>
    <link rel="stylesheet" href="../../css/courseForm.css" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.0.18/dist/sweetalert2.min.css">
    <style>
    /* 將預設圖片置中 */
    #preview-image {
        display: block;
        margin: 0 auto;
        max-width: 100%;
        height: auto;
        margin-bottom: 10px;
    }
    
    #preview-image {
    cursor: pointer; 
    }
</style>
    
</head>
<body>

    <h2>課程表單</h2>

    <form id="courseForm" action="<c:url value='/courses/saveCourse' />" method="post" enctype="multipart/form-data">
        <label for="courseName">課程名稱：</label>
        <div style="display: none">
            <label>courseID: </label><input type="text" name="courseID" value="${course.courseID}">
        </div>
        <input type="text" id="courseName" name="courseName" value="${course.courseName}" required><br>
        <label for="description">課程描述：</label>
        <textarea id="description" name="description">${course.description}</textarea><br>
        <label for="price">價格：</label>
        <input type="number" id="price" name="price" value="${course.price}" required><br>
        <label for="startDate">開課日期：</label>
        <input type="date" id="startDate" name="startDate" value="${course.startDate}" required><br>
        <input type="file" id="image" name="file" accept="image/*">
        <label class="file-label" for="image">選擇圖片</label><br>
	      <c:choose>
	            <c:when test="${not empty course.image}">
	                <img id="preview-image" src="data:image/png;base64,${course.image}" alt="商品圖片" style="max-width: 100%; height: auto;">
	            </c:when>
	            <c:otherwise>
	                <img id="preview-image" src="/image/no_image.png" alt="沒有可用圖片" style="max-width: 100%; height: auto;">
	            </c:otherwise>
	        </c:choose>
        <!-- 新增 Base64 字串的 input 欄位 -->
        <input type="hidden" id="base64Image" name="base64Image" value="${course.image}" />
        <!-- 其他課程相關資訊的輸入欄位 -->
        <input type="hidden" name="courseID" value="${course.courseID}">
        <input type="submit" value="儲存">
    </form>
    <a href="<c:url value='/courses/list' />" class="return-btn">返回課程列表</a>
    
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.0.18/dist/sweetalert2.min.js"></script>
      <script>
    document.addEventListener("DOMContentLoaded", function() {
        // 顯示選擇的檔案名稱或預設文字
        function updateFileLabel() {
            var filename = document.getElementById('image').value.split('\\').pop();
            if (filename) {
                document.querySelector('label.file-label').textContent = filename;
            } else {
                document.querySelector('label.file-label').textContent = "選擇圖片";
            }
        }

        // 顯示選擇的圖片
        function readURL(input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();

                reader.onload = function(e) {
                    document.getElementById('preview-image').src = e.target.result;
                };

                reader.readAsDataURL(input.files[0]);
            }
        }

        // 初始化時執行一次
        updateFileLabel();

        // 監聽檔案選擇事件
        document.getElementById('image').addEventListener('change', function() {
            var maxSize = 1024 * 1024; // 1MB
            var fileSize = this.files[0].size;

            if (fileSize > maxSize) {
                Swal.fire({
                    icon: 'error',
                    title: '錯誤',
                    text: '圖片檔案大小超過限制（最大1MB）。',
                }).then(function () {
                    document.getElementById('image').value = ''; // 清空輸入，避免上傳過大的檔案
                    updateFileLabel(); // 重新顯示檔案名稱或預設文字
                });
            } else {
                updateFileLabel();
                readURL(this);
            }
        });

        // 在編輯時顯示預覽圖片
        readURL(document.getElementById('image'));

        // 監聽表單提交事件
     document.getElementById('courseForm').addEventListener('submit', function(e) {
    	 
            e.preventDefault(); // 阻止表單預設提交行為

            // 取得表單元素
            var form = e.target;

            // 使用 FormData 包裝表單資料
            var formData = new FormData(form);

            // 使用 Axios 非同步提交表單資料
            axios.post(document.getElementById('courseForm').action, formData)
                .then(function(response) {
                    // 處理成功的回應，這裡可以更新頁面或執行其他操作
                    console.log(response.data);
                    
                 // 添加頁面轉導的程式碼
                    window.location.href = "<c:url value='/courses/list' />";
                })
                .catch(function(error) {
                    // 處理錯誤的回應，這裡可以顯示錯誤訊息或執行其他操作
                    console.error("儲存失敗：" + error);
                });
        });
        
        // 監聽"no image"圖片的點擊事件
        document.getElementById('preview-image').addEventListener('click', function() {
            // 觸發檔案選擇事件
            document.getElementById('image').click();
        });
    });
    </script>
</body>
</html>
