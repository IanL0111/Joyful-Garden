<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>註冊會員</title>

    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">

    <!-- SweetAlert CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/11.0.18/sweetalert2.min.css">

    <!-- 引入jQuery庫 -->
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>

    <!-- SweetAlert JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/11.0.18/sweetalert2.min.js"></script>

    <!-- 引入Firebase SDK -->
    <script src="https://www.gstatic.com/firebasejs/10.9.0/firebase-app.js"></script>
    <script src="https://www.gstatic.com/firebasejs/10.9.0/firebase-storage.js"></script>

    <script>
        // Your web app's Firebase configuration
        const firebaseConfig = {
            apiKey: "AIzaSyBQ9cGjYavaE-yDr09cBuVmBA9L9ApZiBM",
            authDomain: "joyfulgarden-fee93.firebaseapp.com",
            databaseURL: "https://joyfulgarden-fee93-default-rtdb.asia-southeast1.firebasedatabase.app",
            projectId: "joyfulgarden-fee93",
            storageBucket: "joyfulgarden-fee93.appspot.com",
            messagingSenderId: "569296245879",
            appId: "1:569296245879:web:ec95db666420e415eae272"
        };

        // Initialize Firebase
        firebase.initializeApp(firebaseConfig);
    </script>

    <style>
        body {
            background: url(../image/backgroundfff.png) no-repeat;
            background-size: cover;
            background-position: center;
            display: flex;
            align-items: center;
            justify-content: center;
            min-height: 100vh;
        }

        .container {
            background: transparent;
            border: 2px solid rgba(255, 255, 255, 0.2);
            backdrop-filter: blur(20px);
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
            color: #7a7281;
            border-radius: 10px;
            padding: 30px 40px;
        }

        h1 {
            font-size: 36px;
            text-align: center;
        }

        .form-group {
            margin-bottom: 20px;
            border-color: #7a7281;
            
        }

        .form-control {
            width: 100%;
            background: transparent;
            border: none;
            outline: none;
            border: 2px solid #7a7281;
            border-radius: 40px;
            font-size: 16px;
            color: #7a7281;
            padding: 15px;
            border-color: #7a7281;

        }

        .error-message {
            color: #6e627a;
            font-size: 12px;
            text-decoration: none;
            font-weight: 600;
            margin-left: 15px;
        }

        .btn-primary {
            width: 100%;
            background-color: #fff;
            border: none;
            outline: none;
            border-radius: 40px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            cursor: pointer;
            font-size: 16px;
            color: #7a7281;
            font-weight: 600;
            padding: 15px;
        }
    </style>


    <script>
        $(document).ready(function () {
            // 在輸入框失去焦點時進行格式驗證
            $('input').blur(function () {
                var inputId = $(this).attr('id');
                var inputValue = $(this).val().trim();
                var errorMessageId = inputId + "-error";
                var errorMessage = "";

                switch (inputId) {
                    case "nickName":
                        if (inputValue === "") {
                            errorMessage = "會員暱稱不能為空";
                        } else if (!/^[a-zA-Z\u4e00-\u9fa5]{1,32}$/.test(inputValue)) {
                            errorMessage = "會員暱稱僅可以包含中英文";
                        }
                        break;
                    case "username":
                        if (inputValue === "") {
                            errorMessage = "帳號(Email)不能為空";
                        } else if (!/\S+@\S+\.\S+/.test(inputValue)) {
                            errorMessage = "帳號(Email)需符合Email格式";
                        }
                        break;
                    case "password":
                        if (inputValue === "") {
                            errorMessage = "密碼不能為空";
                        } else if (!/^[~!@#$%^&*a-zA-Z0-9]{6,20}$/.test(inputValue)) {
                            errorMessage = "密碼可包含特殊字元(~!@#$%^&*)、英文大小寫、數字，介於6到20個字元";
                        }
                        break;
                    case "phoneNumber":
                        if (inputValue === "") {
                            errorMessage = "手機號碼不能為空";
                        } else if (!/^09\d{8}$/.test(inputValue)) {
                            errorMessage = "手機號碼格式為09開頭的十碼數字";
                        }
                        break;
                    case "birthdate":
                        var currentDate = new Date();
                        var selectedDate = new Date(inputValue);
                        if (selectedDate > currentDate) {
                            errorMessage = "生日不得晚於當天日期";
                        }
                        break;
                    default:
                        break;
                }

                $("#" + errorMessageId).text(errorMessage);
            });

            // 阻止輸入框中輸入空格
            $('input').on('input', function () {
                this.value = this.value.replace(/\s/g, '');
            });

            // 上傳大頭貼到Firebase Storage
            $('#memberPicture').on('change', function (e) {
                var file = e.target.files[0];
                var storageRef = firebase.storage().ref('memberPictures/' + file.name);
                var uploadTask = storageRef.put(file);

                uploadTask.on('state_changed', function (snapshot) {
                    var progress = (snapshot.bytesTransferred / snapshot.totalBytes) * 100;
                    console.log('Upload is ' + progress + '% done');
                }, function (error) {
                    console.error('Upload failed:', error);
                }, function () {
                    uploadTask.snapshot.ref.getDownloadURL().then(function (downloadURL) {
                        console.log('File available at', downloadURL);
                        $('#memberPictureURL').val(downloadURL);
                    });
                });
            });

            // 表單提交前檢查是否有錯誤提示，無錯誤則提交表單
            $('form').submit(function (event) {
                var hasError = false;
                $('.error-message').each(function () {
                    if ($(this).text() !== "") {
                        hasError = true;
                        return false; // 終止each迴圈
                    }
                });
                if (hasError) {
                    // 若有錯誤，顯示SweetAlert提示
                    event.preventDefault(); // 阻止表單提交
                    Swal.fire({
                        icon: 'error',
                        title: '資料有誤！',
                        text: '請確認輸入資料是否正確！'
                    });
                } else {
                    // 若無錯誤，顯示SweetAlert提示，然後跳轉頁面
                    event.preventDefault(); // 阻止表單提交
                    Swal.fire({
                        icon: 'success',
                        title: '成功註冊！',
                        text: '感謝您的註冊！'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            setTimeout(function () {
                                $('form').unbind('submit').submit(); // 解除submit事件的綁定並提交表單
                            }, 100); // 等待0.1秒後提交表單
                        }
                    });
                }
            });
        });
    </script>
</head>

<body>
    <div class="container mt-5">
        <h1 class="text-center mb-4">會員註冊</h1>

        <div class="row">
            <div class="col-md-6 mx-auto">
                <form action="/user/registerPage" method="post">
                    <div class="form-group">
                        <input type="text" class="form-control" id="nickName" name="nickName" placeholder="會員暱稱"
                            maxlength="32" required>
                        <div id="nickName-error" class="error-message"></div>
                    </div>

                    <!--  <div class="form-group">
                        <label for="memberPicture">大頭貼圖片：</label>
                        <input type="file" class="form-control-file" id="memberPicture" name="memberPicture" accept="image/*">
                        <input type="hidden" id="memberPictureURL" name="memberPictureURL">
                    </div> -->

                    <div class="form-group">
                        <input type="email" class="form-control" id="username" name="username" placeholder="帳號(請輸入Email)"
                            maxlength="32" required>
                        <div id="username-error" class="error-message"></div>
                    </div>

                    <div class="form-group">
                        <input type="password" class="form-control" id="password" name="password" placeholder="密碼"
                            maxlength="20" required>
                        <div id="password-error" class="error-message"></div>
                    </div>

                    <div class="form-group">
                        <input type="tel" class="form-control" id="phoneNumber" name="phoneNumber" placeholder="手機號碼"
                            required>
                        <div id="phoneNumber-error" class="error-message"></div>
                    </div>

                    <div class="form-group">
                        <label for="birthdate">生日：</label>
                        <input type="date" class="form-control" id="birthdate" placeholder="生日" name="birthdate" required>
                        <div id="birthdate-error" class="error-message"></div>
                    </div>

                    <div class="form-group">
                        <input type="text" class="form-control" id="address" placeholder="送貨地址" name="address">
                        <div id="address-error" class="error-message"></div>
                    </div>

                    <button type="submit" class="btn btn-primary">新增會員</button>
                </form>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>

</html>
