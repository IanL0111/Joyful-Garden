<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <!DOCTYPE html>
  <html lang="zh-TW">

  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>登入</title>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@10/dist/sweetalert2.min.css">
    <link rel="stylesheet" href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>

    <style type="text/css">
      body {
        display: flex;
        align-items: center;
        justify-content: center;
        min-height: 100vh;
        /* 用來設置元素的最小高度，並以視窗的高度（vh 表示視窗高度的百分比）為單位。 */
        /* background: rgb(185, 165, 176); */
        background: url(../image/backgroundfff.png) no-repeat;
        /* no-repeat：這是 background 屬性的一部分，表示不要重複背景圖片。換句話說，這將使得背景圖片僅顯示一次，而不會在整個元素上重複。 */
        background-size: cover;
        background-position: center;
      }

      .wrapper {
        width: 420px;
        /* background: rgb(197, 153, 177); */
        background: transparent;
        border: 2px solid rgba(255, 255, 255, 0.2);
        backdrop-filter: blur(20px);
        /* 設置背景過濾效果。blur(20px)：這是 backdrop-filter 屬性的值，表示要應用的模糊效果。在這個例子中，使用 blur 函數，參數是 20 像素，這將使元素的背景以高斯模糊的方式呈現，使背景看起來模糊。 */
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
        color: #7a7281;
        ;
        border-radius: 10px;
        padding: 30px 40px;
      }

      .wrapper h1 {
        font-size: 36px;
        text-align: center;
      }
      
      .wrapper h2 {
      color:white;
      }

      .wrapper .input-box {
        position: relative;
        width: 100%;
        height: 50px;
        margin: 30px 0px;
        /* background: rgb(131, 185, 199); */
      }

      .input-box input {
        width: 85%;
        height: 40%;
        background: transparent;
        /* background: transparent; 是 CSS 中用來設置背景色為透明的屬性。當你將一個元素的背景色設置為透明時，該元素的背景將變為完全透明，即不可見。 */
        border: none;
        outline: none;
        /* outline: none; 是 CSS 中的一個屬性，用來移除元素的輪廓線（outline）。輪廓線通常在元素獲得焦點時顯示，例如當用戶在表單元素上進行鍵盤輸入時。輪廓線是瀏覽器默認的樣式，它有助於顯示元素的焦點狀態。使用 outline: none; 可以將這個輪廓線移除。 */
        border: 2px solid rgba(255, 255, 255, 0.2);
        /* 最後一個參數是alpha，表示顏色的透明度的分量，它是介於 0（完全透明）和 1（完全不透明）之間的數字。如果省略不寫，則默認為 1 (完全不透明)。 */
        border-radius: 40px;
        font-size: 16px;
        color: #7a7281;
        ;
        border-color: #7a7281;
        ;
        padding: 20px 45px 20px 20px;
      }

      .input-box input::placeholder {
        color: #7a7281;
        border-color: #7a7281;
      }

      .input-box i {
        position: absolute;
        /* 定位方式: 絕對位置 */
        right: 20px;
        top: 50%;
        transform: translateY(-50%);
        /* 當你使用 transform: translateY(-50%); 時，這個 CSS 屬性和值的作用是將元素相對於其自身的高度的一半向上平移。transform 是一個 CSS 屬性，它允許你對元素進行變形（轉換）。 translateY() 是一種轉換函數，用於將元素在垂直方向上平移。如果給定的參數是正值，元素將向下移動；如果是負值，元素則向上移動。 */
        /* 這一整段代碼的作用是將 .input-box 類別下的 <i> 元素以絕對定位放置在其包含塊的右上角，同時使其垂直居中。這種佈局通常用於將圖示放置在輸入框或其他容器元素的右上角。 */
        font-size: 20px;
      }

      .wrapper .remember-forgot {
        display: flex;
        justify-content: space-between;
        /* 用於 Flexbox 布局的 CSS 屬性，它指定了如何分配主軸上的空間，以及如何對齊彈性容器（flex container）中的彈性項目（flex items）。這會使得容器中的項目沿著主軸（通常是橫向的）均勻分布，並在它們之間留下最大的空間。 */
        font-size: 14.5px;
        margin: -15px 0 15px;

      }

      .remember-forgot label input {
        color: #fff;
        margin-right: 3px;
        cursor: pointer;
        /* 設定鼠標指針在應用於元素上時的樣式。當你將這個樣式應用於一個元素時，鼠標指針會變成小手，表示該元素是一個可點擊的連結或按鈕。 */
      }

      .remember-forgot a {
        color: #fff;
        text-decoration: none;
      }

      .remember-forgot a:hover {
        text-decoration: underline;
      }

      .wrapper .btn {
        width: 100%;
        height: 45px;
        background-color: #7a7281;
        border: none;
        outline: none;
        border-radius: 40px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        /* 設置區塊陰影的位置、模糊程度(值越大越模糊)、顏色 */
        cursor: pointer;
        font-size: 16px;
        color: #ece6ee;
        font-weight: 600;
        margin-bottom: 5px;
      }

      .btn {
        margin-top: 35px;
        margin-bottom: 30px;
      }

      .wrapper .register-link {
        font-size: 14.5px;
        text-align: center;
        margin: 20px 0 15px;
      }

      .register-link p a {
        color: #6e627a;
        text-decoration: none;
        font-weight: 600;
      }

      .register-link p a:hover {
        text-decoration: underline;
      }
    </style>
	<%@ include file="../shop/MenuBar2.html" %>
  </head>

  <body>

    <div class="wrapper">
      <form class="login-form">
        <h2>會員登入</h2>
        <div class="input-box">
          <input type="text" placeholder="請輸入帳號" id="username" name="username" required />
          <i class="bx bxs-user"></i><!-- 加上小人圖標 -->
        </div>

        <div class="input-box">
          <input type="password" placeholder="請輸入密碼" id="password" name="password" required />
          <i class="bx bxs-lock-alt"></i><!-- 加上lock圖標 -->
        </div>

        <button type="button" class="btn" onclick="login()">登入</button>

        <div class="register-link">
          <p>還沒有帳號嗎？<a href="/user/registerPage">點這裡註冊會員</a>
        </div>
      </form>
    </div>

    <script type="text/javascript">

      function login() {
        var username = document.getElementById("username").value;
        var password = document.getElementById("password").value;

        var formData = new FormData();
        formData.append("username", username);
        formData.append("password", password);

          fetch('/user/login', { // 修改這裡的URL為登入的路徑
            method: 'POST',
            body: formData,
          })
          .then(response => {
              Swal.fire({
                icon: 'success',
                title: "登入成功",
                confirmButtonText: '確定'
              }).then(() => {
                window.location.href = '/';
              })
            })
            .catch(error => {
              Swal.fire({
                icon: 'error',
                title:"登入失敗",
                confirmButtonText: '確定'
              });
            });
        }
      

    </script>
    
  </body>

  </html>