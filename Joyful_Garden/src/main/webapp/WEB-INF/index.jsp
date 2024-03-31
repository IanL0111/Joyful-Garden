<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>花藝手作工作室</title>
    
      <link rel="stylesheet" href="../../css/index.css" />
    
</head>
<body>
<img class="fixed-image" src="/image/static01.png" alt="fixed-image" />
<%@ include file="courses/header.jsp" %>
 

   <main>
		<section class="background-imge-static">
		    <div class="filter"></div>
		    <h3>
		        JOYFUL TO THE WORLD <br />
		    </h3>
		</section>
		
	<a href="/shop" style="text-decoration: none; color: inherit;">
      <section class="info-circles">
        <h3>SHOP</h3>
        <div class="circles">
          <div class="circle">
            <img
              title="藝術花藝版畫"
              src="/image/product002.jpg"
              alt="藝術花藝版畫"
            />
            <p>藝術花藝版畫</p>
          </div>
          <div class="circle">
            <img
              title="酒墨畫永生花相框"
              src="/image/LINE_ALBUM_酒墨畫永生花相框擺飾_240201_1.jpg"
              alt="酒墨畫永生花相框"
            />
            <p>酒墨畫永生花相框</p>
          </div>
          <div class="circle">
            <img
              title="金箔浮雕"
              src="/image/product001.jpg"
              alt="金箔浮雕"
            />
            <p>金箔浮雕</p>
          </div>
          <div class="circle">
            <img
              title="永生花束"
              src="/image/永生花束-閃亮.jpg"
              alt="永生花束"
            />
            <p>永生花束</p>
          </div>
        </div>
      </section>
      </a>
      
      
      <section class="background-img">
        <div class="filter"></div>
       <h3>在花藝課堂上，我們用手做出美麗的作品，<br />享受著創作的喜悅和美感</h3>
      </section>
      
      
    <a href="/courses/userlist" style="text-decoration: none; color: inherit;">
<h1>COURSE</h1>
   <Section class="infoCon">
     <!-- 使用 :target 來達成點擊查看的效果 -->
    <section id="guide" class="info-section" data-target="guide">
      <div class="info-content">
        <h2>石墨暈染</h2>
        <p>石墨暈染是一種藝術技法，通過運用石墨、墨水和水等材料，將色彩或圖案輕染於紙張上。</p>
        <p>這種技法不僅可以用於繪畫，還能應用於裝飾、手工藝品和書法等領域。</p>
        <p>石墨暈染課程旨在學習如何運用石墨和墨水的特性，掌握暈染的技巧與方法。</p>
        <img src="/image/course01.JPG" alt="">
        <img src="/image/course02.jpg" alt="">
      </div>
    </section>
    
        <section id="guide" class="info-section" data-target="guide">
      <div class="info-content">
        <h2>永生花祝年繩</h2>
        <p>永生花祝年繩課程是一門專注於永生花創作的課程，旨在學習如何製作出美麗耐久的作品。</p>
        <p>永生花是特殊保存的真花，能夠長期保持外觀色彩，成為了近年受歡迎裝飾品和禮品。</p>
        <p>學員將學習到如何選擇合適的永生花材料，並培養出如何製作出各種風格款式的祝年繩。</p>
        <img src="/image/course03.jpg" alt="">
        <img src="/image/course04.jpg" alt="">
      </div>
    </section>
  </Section>
  </a>
     
      <section class="empty-container">
        <h3>與親朋好友一起舉辦花園派對，我們在花的環繞中共享著歡笑和喜悅</h3>
      </section>
      
      <a href="/activity2" style="text-decoration: none; color: inherit;">
       <section class="info-circles">
        <h3>EVENT</h3>
        <div class="circles">
          <div class="event">
            <img
              title="青年活動營手作體驗營"
              src="/image/event01.jpg"
              alt="青年活動營手作體驗營"
            />
            <p>青年活動營手作體驗營</p>
          </div>
          <div class="event">
            <img
              title="親子手做工作坊"
              src="/image/event02.jpg"
              alt="親子手做工作坊"
            />
            <p>親子手做工作坊</p>
          </div>
          <div class="event">
            <img
              title="校園手作藝術教育"
              src="/image/event03.jpg"
              alt="校園手作藝術教育"
            />
            <p>校園手作藝術教育</p>
          </div>
          <div class="event">
            <img
              title="企業包團手作體驗"
              src="/image/event04.jpg"
              alt="企業包團手作體驗"
            />
            <p>企業包團手作體驗</p>
          </div>
        </div>
      </section>
      </a>
      
      <section class="background-img">
        <div class="filter"></div>
        <h3>將手裡的花送給摯愛的人，那份喜悅和感動溫暖了我們的心靈</h3>
      </section>
      
      <a href="/allPosts-google.html" style="text-decoration: none; color: inherit;">
        <section class="forum-section">
        <h3>論壇</h3>
        <p>在我們的論壇上，您可以參與各種討論，與其他花藝愛好者分享您的心得和作品。</p>
    </section>
    </a>
      
   
    </main>
    
    <script>
      document.addEventListener("DOMContentLoaded", function () {
        var topBackground = document.querySelector("section.background-img");
        var imagePaths = [
          "./image/static05.png",
          "./image/static01.png",
          "./image/static04.png"
        ];
        var currentIndex = 0;
      
        function changeBackground() {
          // 設定淡出效果
          topBackground.style.transition = "opacity 1s";
          topBackground.style.opacity = 0;
      
          // 使用 setTimeout 設定延遲，確保淡出效果生效
          setTimeout(function () {
            // 切換背景圖片
            topBackground.style.backgroundImage = 'url("' + imagePaths[currentIndex] + '")';
      
            // 設定淡入效果
            topBackground.style.transition = "opacity 1s";
            topBackground.style.opacity = 1;
          }, 1000);
      
          currentIndex = (currentIndex + 1) % imagePaths.length;
        }
      
        // 設定初始背景圖片
        changeBackground();
      
        // 使用 setInterval 定期更換背景圖片
        setInterval(changeBackground, 3000);
      });
      
    </script>
<%@ include file="shop/MenuBar3.html" %>
<%@ include file="shop/Footer.html" %>

</body>
</html>
