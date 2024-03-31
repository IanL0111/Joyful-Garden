<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <!DOCTYPE html>
    <html>

    <head>
      <meta charset="UTF-8" />
      <title>會員管理系統</title>
      <link rel="shortcut icon" type="image/x-icon" href="#">

      <!-- Bootstrap CSS -->
      <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" />

      <!-- Bootstrap Icons CSS -->
      <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css" rel="stylesheet">

      <!-- SweetAlert CSS -->
      <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css" />

      <!-- 引入Firebase SDK -->
      <script src="https://www.gstatic.com/firebasejs/8.6.8/firebase-app.js"></script>
      <script src="https://www.gstatic.com/firebasejs/8.6.8/firebase-storage.js"></script>

      <script>
        // 初始化Firebase
        var firebaseConfig = {
          // ... (填入您的Firebase配置信息) ...
        };
        firebase.initializeApp(firebaseConfig);
      </script>
    </head>

    <body>
      <div class="container mt-5">
        <h1 class="text-center mb-4">會員管理系統</h1>

        <!-- 註冊會員按鈕 -->
        <form action="registerPage" method="get">
          <button type="submit">註冊頁面</button>
        </form>

        <!-- 登入會員按鈕 -->
        <form action="loginPage" method="get">
          <button type="submit">登入頁面</button>
        </form>

        <!-- 查詢會員功能 -->
        <div class="row">
          <div class="col-md-6 offset-md-3">
            <form id="searchForm" action="" method="GET" class="mb-3">
              <div class="input-group">
                <input type="text" id="searchInput" class="form-control" placeholder="請輸入會員編號">
                <button type="button" id="clearSearchButton" class="btn btn-outline-secondary" aria-label="清除搜尋">
                  <i class="bi bi-x"></i>
                </button>
                <button type="submit" id="searchButton" name="search" class="btn btn-primary">查詢</button>
              </div>
            </form>
          </div>
        </div>
      </div>

      <!-- 會員列表 -->
      <div id="memberList" class="table-responsive">
        <table class="table table-bordered">
          <thead>
            <tr>
              <th scope="col">會員編號</th>
              <th scope="col">帳號(Email)</th>
              <th scope="col">密碼</th>
              <th scope="col">暱稱</th>
              <!-- <th scope="col">大頭貼</th> -->
              <th scope="col">手機號碼</th>
              <th scope="col">生日</th>
              <th scope="col">地址</th>
              <th scope="col">驗證碼</th>
              <th scope="col">驗證狀態</th>
              <th scope="col">停權中</th>
              <th scope="col">會員等級</th>
              <th scope="col">操作</th>
            </tr>
          </thead>
          <tbody>
            <!-- 使用 JSP 迴圈動態生成會員列表 -->
            <c:forEach var="m" items="${members}">
              <tr>
                <td>${m.memberId}</td>
                <td>${m.username}</td>
                <td>${m.password}</td>
                <td>${m.nickName}</td>
                <!-- <td> -->
                <!-- 使用 JSP 語法插入 Firebase Storage 中的圖片 URL -->
                <!-- <img src="https://firebasestorage.googleapis.com/v0/b/joyfulgarden-fee93.appspot.com/o/images%2Fcute.png?alt=media&token=6d2c4a4a-5031-4a81-a280-c573f05144ad"  alt="大頭貼" style="max-width: 100px; max-height:
                  100px;"></img> -->

                <!-- </td> -->
                <!-- <td>
                  <!-- 使用 JSP 語法插入 Firebase Storage 中的圖片 URL
                  <img src="${m.memberPicture}" alt="大頭貼" style="max-width: 100px; max-height: 100px;">
              </td> -->
                <td>${m.phoneNumber}</td>
                <td>${m.birthdate}</td>
                <td>${m.address}</td>
                <td>${m.verificationCode}</td>
                <td>${m.verified}</td>
                <td>${m.deleted}</td>
                <td>${m.memberLevel}</td>
                <td>
                  <a href="admin/members/${m.memberId}" class="btn btn-primary">編輯</a>
                  <a href="#" method="delete" onclick="admin/deleteMember(${m.memberId})" class="btn btn-danger"
                    data-confirm="確定刪除？">刪除</a>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>

      <!--  jQuery -->
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

      <!-- Bootstrap JS -->
      <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
      <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js"></script>
      <!-- SweetAlert JS -->
      <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>

      <script>
        // 定義函數，用於動態插入會員資料
        function insertMemberRow(member) {
          var row = '<tr>';
          row += '<td>' + member.memberId + '</td>';
          row += '<td>' + member.username + '</td>';
          row += '<td>' + member.password + '</td>';
          row += '<td>' + member.nickName + '</td>';
          row += '<td id="memberPicture_' + member.memberId + '"><img src="#" alt="大頭貼" style="max-width: 100px; max-height: 100px;"></td>';
          row += '<td>' + member.phoneNumber + '</td>';
          row += '<td>' + member.birthdate + '</td>';
          row += '<td>' + member.address + '</td>';
          row += '<td>' + member.verificationCode + '</td>';
          row += '<td>' + member.verified + '</td>';
          row += '<td>' + member.deleted + '</td>';
          row += '<td>' + member.memberLevel + '</td>';
          row += '<td>';
          row += '<a href="members/' + member.memberId + '" class="btn btn-primary">編輯</a>';
          row += '<a href="#" onclick="deleteMember(' + member.memberId + ')" class="btn btn-danger" data-confirm="確定刪除？">刪除</a>';
          row += '</td>';
          row += '</tr>';

          // 插入新行到會員列表中
          $('#memberList tbody').append(row);

          // 從 Firebase Storage 加載會員大頭貼圖片的 URL
          var storageRef = firebase.storage().ref('memberPictures/' + member.memberPicture);
          storageRef.getDownloadURL().then(function (url) {
            // 將 URL 設置為對應會員的大頭貼
            $('#memberPicture_' + member.memberId).find('img').attr('src', url);
          }).catch(function (error) {
            console.error("無法從 Firebase Storage 加載會員大頭貼圖片的 URL：" + error);
            $('#memberPicture_' + member.memberId).html('無法加載大頭貼');
          });
        }

        // 查詢會員功能
        $('#searchForm').submit(function (event) {
          event.preventDefault(); // 防止表單提交到預設的動作
          const searchValue = $('#searchInput').val(); // 獲取查詢值
          // 發送 AJAX GET 請求到後端
          $.get('/searchbymemberid/' + searchValue).done(function (data) {
            console.log("成功收到資料:", data);

            // 清空現有的會員列表
            $('#memberList tbody').empty();
            console.log("已清空會員列表");

            if (data !== 'no result') {
              // 將返回的字串資料按照空格分隔成陣列
              var memberData = data.split(' ');

              // 動態插入查詢到的會員資料到會員列表
              var member = {
                memberId: memberData[0],
                username: memberData[1],
                password: memberData[2],
                nickName: memberData[3],
                memberPicture: memberData[4],
                phoneNumber: memberData[5],
                birthdate: memberData[6],
                address: memberData[7],
                verificationCode: memberData[8],
                verified: memberData[9],
                deleted: memberData[10],
                memberLevel: memberData[11]
              };

              insertMemberRow(member);
              console.log("會員列表成功插入會員資料");
            } else {
              $('#memberList tbody').append('<tr><td colspan="15">找不到符合條件的會員。</td></tr>');
              console.log("找不到符合條件的會員");
            }
          }).fail(function (xhr, status, error) {
            console.error("發生錯誤:", error); // 输出错误信息
          });
        });

        // 清空搜尋列
        $(document).ready(function () {
          $('#clearSearchButton').click(function () {
            $('#searchInput').val('');
          });
        });

        // 刪除會員功能
        function deleteMember(memberId) {
          Swal.fire({
            title: "確定刪除？",
            text: "刪除後將無法恢復！",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "確定",
            cancelButtonText: "取消",
          }).then((result) => {
            if (result.isConfirmed) {
              $.ajax({
                url: "/members/" + memberId,
                type: "DELETE",
                success: function (response) {
                  Swal.fire(
                    "刪除成功！",
                    "您的會員已經成功刪除。",
                    "success"
                  ).then(() => {
                    location.reload();
                  });
                },
                error: function (xhr, status, error) {
                  console.error("刪除失敗：" + error);
                  Swal.fire(
                    "刪除失敗！",
                    "發生了一些錯誤，無法刪除會員。",
                    "error"
                  );
                },
              });
            }
          });
        }
      </script>
    </body>

    </html>