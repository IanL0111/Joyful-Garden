<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="utf-8" />
            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
            <meta name="description" content="" />
            <meta name="author" content="" />
            <title>Shop Item - Start Bootstrap Template</title>
            <!-- Favicon-->
            <link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
            <!-- Bootstrap icons-->
            <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
            <!-- Core theme CSS (includes Bootstrap)-->
            <link href="/css/ACstyles.css" rel="stylesheet" />
            
        </head>

        <body>
            <!-- Navigation-->
            <nav class="navbar navbar-expand-lg navbar-light bg-light">
                <div class="container px-4 px-lg-5">

                </div>
            </nav>
            <!-- Product section-->
            <section class="py-5">
                <div class="container px-4 px-lg-5 my-5">
                    <div class="row gx-4 gx-lg-5 align-items-center">
                        <div class="col-md-6"><img class="card-img-top mb-5 mb-md-0">

                            <div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel">
                                <div class="carousel-indicators">
                                    <button type="button" data-bs-target="#carouselExampleIndicators"
                                        data-bs-slide-to="0" class="active" aria-current="true"
                                        aria-label="Slide 1"></button>
                                    <button type="button" data-bs-target="#carouselExampleIndicators"
                                        data-bs-slide-to="1" aria-label="Slide 2"></button>
                                    <button type="button" data-bs-target="#carouselExampleIndicators"
                                        data-bs-slide-to="2" aria-label="Slide 3"></button>
                                </div>
                                <div class="carousel-inner">
                                    <c:forEach items="${Images}" var="i" varStatus="loop">
                                        <c:choose>
                                            <c:when test="${loop.first}">
                                                <div class="carousel-item active">
                                                    <img src="${i.imagesPath}" class="d-block w-100"
                                                        style="width: 100px; height: 500px;">
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="carousel-item">
                                                    <img src="${i.imagesPath}" class="d-block w-100"
                                                        style="width: 100px; height: 500px;">
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>

                                </div>
                                <button class="carousel-control-prev" type="button"
                                    data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
                                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                    <span class="visually-hidden">Previous</span>
                                </button>
                                <button class="carousel-control-next" type="button"
                                    data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
                                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                    <span class="visually-hidden">Next</span>
                                </button>
                            </div>
							
                        </div>
                        <div class="col-md-6 mt-4 ">
                            
                            <p class="display-5 fw-bolder">${activity.activityName}</p>                           
                            <p class="lead">${activity.activityContent}</p>
             				  <a href="/sign/${activity.activityId}" class="btn btn-primary" >我要報名</a>
             				  
                         </div>
                    </div>
                </div>
            </section>
            <!-- Related items section-->
            <section class="py-5 bg-light">
                <div class="container px-4 px-lg-5 mt-5">

                    <div class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center">


                    </div>
                </div>
                <div class="col mb-5">
                    <div class="card h-100">
                        

                    </div>
                </div>

                <section id="contact">
                    <div class="container px-4">
                        <div class="row gx-4 justify-content-center">
                            <div class="col-lg-8">
                                <h2>聯絡我們</h2>
                                <p>地址：高雄市三民區明仁路157號, Kaohsiung, Taiwan</p>
                                <p>信箱：joyciou1212@gmail.com</p>
                                <p>Instagram：<a
                                        href="https://instagram.com/joyful.designing">instagram.com/joyful.designing</a>
                                </p>
                                <p></p>
                            </div>
                        </div>
                    </div>
                </section>

            </section>
            <!-- Footer-->
            <footer class="py-5 bg-dark">
                <div class="container">
                    <p class="m-0 text-center text-white">Copyright &copy; Your Website 2023</p>
                </div>
            </footer>
            <!-- Bootstrap core JS-->
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
            <!-- Core theme JS-->
            <script src="/js/ACscripts.js"></script>
        </body>

        </html>