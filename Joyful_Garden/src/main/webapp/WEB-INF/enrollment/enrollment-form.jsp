<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-TW">
<head>
    <meta charset="UTF-8">
    <title>報名表單</title>

    <!-- 引入 Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <!-- 引入 SweetAlert2 CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">
    <link rel="stylesheet" href="../../css/enrollment.css" />

  <style>
  button.btn-secondary {
    padding: 10px 15px; 
    font-size: 16px;
    margin-left:1%;
}

  button.enrollment-form-submit{
   padding: 10px 15px; 
   font-size: 16px;
 }
  </style>
</head>
<body>

    <h1>報名表單</h1>

    <div class="enrollment-form-container">
        <div class="enrollment-form-item">
            <!-- 修改表單的 action 屬性為空，去除不必要的 action 屬性 -->
            <form id="enrollmentForm" method="post">
                <p class="enrollment-form-text">學生姓名：</p>
                <input type="text" id="studentName" name="studentName" class="enrollment-form-input" required>

                <p class="enrollment-form-text">聯絡電話：</p>
                <input type="text" id="contactNumber" name="contactNumber" class="enrollment-form-input">

                <p class="enrollment-form-text">地址：</p>
                <input type="text" id="address" name="address" class="enrollment-form-input">

                <p class="enrollment-form-text">電子郵件：</p>
                <input type="email" id="email" name="email" class="enrollment-form-input" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$" required>
                <small id="emailError" style="color: red;"></small>

                <!-- 其他報名資訊欄位可以自行添加 -->

                <input type="hidden" id="studentID" name="studentID">
                <input type="hidden" id="courseID" name="courseID" value="${courseID}">
                <input type="hidden" id="coursePrice" name="coursePrice" value="${coursePrice}">
                <input type="hidden" id="enrollmentDate" name="enrollmentDate">

                <button type="button" id="enrollButton" class="btn enrollment-form-submit">我要報名</button>
                <button type="button" class="btn btn-secondary" onclick="window.location.href = '/';">取消</button>
            </form>
        </div>
    </div>

    <!-- 隱藏表單，用於額外的資料傳遞 -->
    <form id="hiddenForm" style="display: none;">
        <input type="hidden" id="hiddenFormCourseID" name="courseID">
        <input type="hidden" id="hiddenFormCoursePrice" name="coursePrice">
    </form>
    
    

    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>

    <script>
    document.addEventListener('DOMContentLoaded', function () {
        // 偵聽按鈕點擊事件
        $("#enrollButton").click(function (event) {
            event.preventDefault(); // 防止默認的表單提交行為

            // 取得用戶輸入的電子郵件
            var email = $('#email').val();

            // 使用正規表示式檢查電子郵件格式
            var emailRegex = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$/;
            if (!emailRegex.test(email)) {
                // 若格式不正確，顯示錯誤消息並停止提交
                $('#emailError');
                Swal.fire({
                    icon: 'warning',
                    title: '警告',
                    text: '請輸入有效的電子郵件地址',
                    confirmButtonText: '確定'
                });
                return;
            } else {
                // 清空錯誤消息
                $('#emailError').text('');
            }

            // 顯示資料傳輸中的 SweetAlert
            showLoadingAlert();

            // 模擬資料傳輸的延遲效果，這裡可以替換為實際的資料傳輸代碼
            setTimeout(function(){
                // 隱藏 SweetAlert
                Swal.close();

                // 檢查是否已存在相同電話和電子郵件的使用者
                checkExistingUser(email);
            }, 3000); // 假設資料傳輸需要3秒完成
        });
    });

    // 顯示資料傳輸中的 SweetAlert 函數
    function showLoadingAlert() {
        let timerInterval;
        Swal.fire({
            title: "請稍候",
            html: "資料傳輸中...<b></b>",
            timer: 3000,
            timerProgressBar: true,
            didOpen: () => {
                Swal.showLoading();
                const timer = Swal.getPopup().querySelector("b");
                timerInterval = setInterval(() => {
                    timer.textContent = `${Swal.getTimerLeft()}`;
                }, 300);
            },
            willClose: () => {
                clearInterval(timerInterval);
            }
        });
    }

    // 檢查是否已存在相同電話和電子郵件的使用者的函數
    function checkExistingUser(email) {
        // 使用 Axios 發送請求檢查是否已經存在相同電話和電子郵件的使用者
        axios.post('/checkStudentExistence?email=' + email)
            .then(function (response) {
                // 如果存在相同電話和電子郵件的使用者，顯示錯誤消息並返回
                if (response.data === 'existing') {
                    Swal.fire({
                        icon: 'warning',
                        title: '警告',
                        text: '您已經報名過此課程！',
                        confirmButtonText: '確定'
                    }).then(() => {
                        window.location.href = '/'; // 返回到 /courses/userlist 頁面
                    });
                } else {
                    // 如果不存在相同電話和電子郵件的使用者，則檢查是否已報名過相同課程
                    checkDuplicateEnrollment();
                }
            })
            .catch(function (error) {
                console.error(error);
                // 在這裡處理請求失敗的情況
                Swal.fire({
                    icon: 'error',
                    title: '錯誤',
                    text: '請求失敗，請稍後再試',
                    confirmButtonText: '確定'
                }).then(() => {
                    window.location.href = '/'; // 返回到 /courses/userlist 頁面
                });
            });
    }

 // 檢查是否已報名過相同課程的函數
    function checkDuplicateEnrollment() {
        var courseID = $('#courseID').val();
        var email = $('#email').val();

        axios.post('/checkDuplicateEnrollment?courseID=' + courseID + '&email=' + email)
            .then(function (response) {
                // 如果已報名過相同課程，顯示警告消息
                if (response.data === 'duplicate') {
                    Swal.fire({
                        icon: 'warning',
                        title: '警告',
                        text: '您已經報名過相同課程！',
                        confirmButtonText: '確定'
                    }).then(() => {
                        window.location.href = '/'; // 返回到 /courses/userlist 頁面
                    });
                } else {
                    // 否則，提交報名表單
                    submitEnrollmentForm();
                }
            })
            .catch(function (error) {
                console.error(error);
                // 在這裡處理請求失敗的情況
                Swal.fire({
                    icon: 'error',
                    title: '錯誤',
                    text: '請求失敗，請稍後再試',
                    confirmButtonText: '確定'
                }).then(() => {
                    window.location.href = '/'; // 返回到 /courses/userlist 頁面
                });
            });
    }

    // 提交報名表單的函數
    function submitEnrollmentForm() {
        // 創建 FormData 對象
        var formData = new FormData();

        // 添加表單資料到 FormData
        formData.append('studentName', $('#studentName').val());
        formData.append('contactNumber', $('#contactNumber').val());
        formData.append('address', $('#address').val());
        formData.append('email', $('#email').val());
        formData.append('courseID', $('#courseID').val());
        formData.append('coursePrice', $('#coursePrice').val());
        formData.append('enrollmentDate', getCurrentDate());

        // 使用 Axios 提交表單
        axios.post('/enroll', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            })
            .then(function (response) {
                console.log(response);

                // 在這裡處理成功提交後的 SweetAlert2 彈出消息
                if (response.data === 'duplicate') {
                    // 如果已報名過相同課程，顯示警告訊息
                    Swal.fire({
                        icon: 'warning',
                        title: '警告',
                        text: '您已經報名過此課程！',
                        confirmButtonText: '確定'
                    });
                } else {
                    // 如果報名成功，顯示成功訊息
                    Swal.fire({
                        icon: 'success',
                        title: '報名成功',
                        text: '您已成功報名！感謝您的參與。',
                        confirmButtonText: '確定'
                    }).then(() => {
                        window.location.href = '/'; // 返回到 /courses/userlist 頁面
                    });
                }
            })
            .catch(function (error) {
                console.error(error);
                // 在這裡處理提交失敗的邏輯
                Swal.fire({
                    icon: 'error',
                    title: '錯誤',
                    text: '提交失敗，請稍後再試',
                    confirmButtonText: '確定'
                }).then(() => {
                    window.location.href = '/'; // 返回到 /courses/userlist 頁面
                });
            });
    }


    // 獲取當前日期的函數
    function getCurrentDate() {
        var currentDate = new Date();
        var year = currentDate.getFullYear();
        var month = ('0' + (currentDate.getMonth() + 1)).slice(-2); // 月份從0開始，所以要加1，並補零
        var day = ('0' + currentDate.getDate()).slice(-2); // 補零
        return year + '-' + month + '-' + day;
    }
    
 // 發送郵件通知的函數
    function sendEnrollmentConfirmationEmail(email) {
        // 使用 Axios 發送郵件通知的請求
        axios.post('/sendEnrollmentEmail?email=' + email)
            .then(function (response) {
                console.log(response);
                // 在這裡處理郵件通知成功的情況
                if (response.data === 'success') {
                    console.log('郵件通知成功');
                }
            })
            .catch(function (error) {
                console.error(error);
                // 在這裡處理郵件通知失敗的情況
                console.error('郵件通知失敗');
            });
    }

    </script>

</body>
</html>
