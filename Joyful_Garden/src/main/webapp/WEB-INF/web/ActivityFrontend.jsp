<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <title>JoyfulGarden 手作美學</title>
    
    <link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
    
    <link href="css/styles.css" rel="stylesheet" />

    <style>
        .img-container {
            height: 200px;
           
            overflow: hidden;
        }

        .img-container img {
            height: 100%;
            width: 100%;
            object-fit: cover;
        }

        .btn-custom {
            background-image: linear-gradient(to right, #f45c43, #f9d423);
            
            color: white;
            
            border-radius: 30px;
           
            border: 1px solid #f9d423;
           
            padding: 10px 20px;
           
            font-size: 16px;
           
            box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.15);
            
            transition: all 0.3s ease;
            
        }

        .btn-custom:hover,
        .btn-custom:focus {
            background-image: linear-gradient(to right, #f58634, #f9d624);
            
            color: #ffffff;
            
            text-decoration: none;
            
            box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
            
            transform: translateY(-2px);
           
        }
    </style>

</head>

<body>
    <!-- Responsive navbar-->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container px-5">
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"><span
                    class="navbar-toggler-icon"></span></button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
            </div>
        </div>
    </nav>
    
    <div class="container px-4 px-lg-5">
        
        <div class="row gx-4 gx-lg-5 align-items-center my-5">
            <div class="col-lg-7 d-flex justify-content-center">
               
                <img class="img-fluid rounded mb-4 mb-lg-0 mx-auto"
                    src="./image/logo.jpg"
                    alt="..." style="width: 300px" />
            </div>
            <div class="col-lg-5">
                <h1 class="font-weight-light">Joyful Garden 手作美學</h1>
                <p>客製禮品：花禮、酒墨畫、精油蠟燭、寶石皂、晶漾花、錶花蛋糕、永生花禮服拼貼｜培訓課程 :
                    Aube永生花證書、酒墨畫師資證書、氨基酸寶石皂證書、烘培蠟燭證書、UDS晶漾花證書及各項單品體驗課｜婚禮佈置</p>
                <a class="btn btn-primary" href="#!">聯繫我們</a>
            </div>
        </div>
        <!-- Call to Action-->
        <div class="text-white">
            <div class="card-body">
                <hr class="my-4" style="border-top: 5px solid black;">
            </div>
        </div>
        <div class="row gx-4 gx-lg-5 ">
            <c:forEach items="${activity}" var="act">
                <div class="col-md-4 mb-5">
                    <div class="card h-100">
                        <div class="card-body">
                            <div class="row">
                               
                                <div class="col-md-6 img-container">
                                   
                                    <c:if test="${not empty act.images}">
                                        <c:forEach items="${act.images}" var="image" varStatus="loop">
                                            <c:if test="${loop.index == 0}">
                                                <img src="${image.imagesPath}" alt="活動圖片" class="img-fluid">
                                            </c:if>
                                        </c:forEach>
                                    </c:if>
                                </div>
                               
                                <div class="col-md-6 d-flex flex-column justify-content-start">
                                    <div>${act.activityDate}</div> 
                                    <div><strong>${act.activityName}</strong></div>
                                    <div>${act.activityContent}</div> 
                                </div>
                            </div>
                        </div>
                       <div class="card-footer d-flex justify-content-center">
   						 <a class="btn btn-primary btn-sm btn-custom" href="/act/${act.activityId}">More</a>
					   </div>

                    </div>
                </div>
            </c:forEach>
        </div>
        
       
   
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    
    <script src="js/scripts.js"></script>
    <%@ include file="../shop/MenuBar3.html" %>
    <%@ include file="../shop/Footer.html" %>
</body>
</html>
