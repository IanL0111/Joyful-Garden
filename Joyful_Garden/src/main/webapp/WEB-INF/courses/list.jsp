<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="zh-TW">
<head>
    <meta charset="UTF-8">
    <title>課程列表</title>

    <link rel="stylesheet" href="../../css/list.css" />

</head>
<body>

    <h1>課程管理列表</h1>
 <div class="container">

  <div  class="search-container">
    <form id="searchForm" action="/courses/search" method="post">
        <input name="searchInput" type="text" id="searchInput" class="search-input" placeholder="搜尋課程關鍵字">
        <button type="submit" class="search-btn">搜尋</button>
    </form>
 </div>

    <div class="add-course-btn-container">
        <a href="/courses/showFormForAdd" class="add-course-btn">新增課程</a>
        <form id="deleteSelectedForm" method="post" action="/courses/deleteSelectedCourses">
            <input type="hidden" name="selectedCourseIds" value="${selectedCourses}">
            <button type="submit" id="deleteSelected" class="delete-btn">刪除所選課程</button>
            <button id="returnBtn" onclick="goBack()" class="returnBtn">返回</button>
        </form>
    </div>

    <div class="course-container">
    <c:choose>
        <c:when test="${empty coursesList}">
            <p>查無此課程</p>
        </c:when>
        <c:otherwise>
            <div class="course-item">
                <form method="post" action="/courses/deleteCourse">
                    <table>
                        <thead>
                        <tr>
                            <th>選擇</th>
                            <th>照片</th>
                            <th>課程編號</th>
                            <th>課程名稱</th>
                            <th>課程描述</th>
                            <th>價格</th>
                            <th>開課日期</th>
                            <th>編輯</th>
                            <th>刪除</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="course" items="${coursesList}">
                            <tr>
                                <td><input type="checkbox" class="delete-checkbox" name="selectedCourses" value="${course.courseID}"></td>
                                <td><img src="data:image/png;base64,${course.image}" alt="商品圖片"></td>
                                <td><c:out value="${course.courseID}" /></td>
                                <td><c:out value="${course.courseName}" /></td>
                                <td><c:out value="${fn:length(course.description) > 32 ? course.description.substring(0, 32) : course.description}" /></td>
                                <td><c:out value="${course.price}" /></td>
                                <td><c:out value="${course.startDate}" /></td>
                                <td><button type="button" onclick="location.href='/courses/showFormForUpdate?courseId=${course.courseID}'" class="edit-btn">編輯</button></td>
                                <td><button type="button" class="delete-btn deletesingleOne" name="courseId" value="${course.courseID}">刪除</button></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </form>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</div>
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <%@ include file="../shop/MenuBar3.html" %>
  
  <script>
    $(document).ready(function() {
        $(".deletesingleOne").click(function(e) {
            e.preventDefault();
            var courseId = $(this).val();

            Swal.fire({
                title: '確定刪除？',
                text: '確定要刪除課程嗎？',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#d33',
                cancelButtonColor: '#3085d6',
                confirmButtonText: '確定',
                cancelButtonText: '取消'
            }).then((result) => {
                if (result.isConfirmed) {
                    $.ajax({
                        type: "POST",
                        url: "/courses/deleteCourse",
                        data: { courseId: courseId },
                        success: function(response) {
                            if (response.error) {
                                Swal.fire({
                                    title: '警告',
                                    text: response.error,
                                    icon: 'warning',
                                    confirmButtonColor: '#d33',
                                    confirmButtonText: '確定'
                                });
                            } else {
                                location.reload();
                            }
                        },
                        error: function(xhr, status, error) {
                            var errorMessage = xhr.responseJSON.error;
                            Swal.fire({
                                title: '刪除失敗',
                                text: '此課程仍有報名資訊，請先刪除報名資料',
                                icon: 'error',
                                confirmButtonColor: '#d33',
                                confirmButtonText: '確定'
                            });
                        }
                    });
                }
            });
        });

        $("#deleteSelectedForm").submit(function(e) {
            e.preventDefault();
          
            var selectedCourses = $("input[name='selectedCourses']:checked");

            if (selectedCourses.length === 0) {
                Swal.fire({
                    title: '請選擇所選課程',
                    icon: 'warning',
                    confirmButtonColor: '#d33',
                    confirmButtonText: '確定'
                });
                return;
            }

            var selectedCourseIds = selectedCourses.map(function () {
                return this.value;
            }).get().join(',');

            Swal.fire({
                title: '確定刪除？',
                text: '確定要刪除所選課程嗎？',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#d33',
                cancelButtonColor: '#3085d6',
                confirmButtonText: '確定',
                cancelButtonText: '取消'
            }).then((result) => {
                if (result.isConfirmed) {
                    $.ajax({
                        type: "POST",
                        url: "/courses/deleteSelectedCourses",
                        data: { selectedCourses: selectedCourseIds },
                        success: function(response) {
                            location.reload();
                        },
                        error: function(error) {
                            console.log("刪除失敗：" + error);
                        }
                    });
                }
            });
        });

        function isValidChineseName(courseName) {
            // 檢查輸入字串是否為中文且長度大於0
            if (/^[\u4e00-\u9fa5]+$/.test(courseName.trim()) || courseName.trim().length === 0) {
                return true;
            } else {
                Swal.fire({
                    title: '請輸入有效的中文課程名稱',
                    icon: 'warning',
                    confirmButtonColor: '#d33',
                    confirmButtonText: '確定'
                });
                return false;
            }
        }


        $("#searchForm").submit(function(e) {
            e.preventDefault();

            var searchInput = $("#searchInput").val();

            if (!isValidChineseName(searchInput)) {
                return;
            }

            $.ajax({
                type: "POST",
                url: "/courses/checkCourseName",
                data: { courseName: searchInput },
                success: function(response) {
                    if (response.valid) {
                        // 中文課程名稱有效，提交表單
                        $("#searchForm").unbind('submit').submit();
                    } else {
                        // 顯示 SweetAlert 查無此課程
                        Swal.fire({
                            title: '查無此課程',
                            icon: 'error',
                            confirmButtonColor: '#d33',
                            confirmButtonText: '確定'
                        });
                    }
                },
                error: function(error) {
                    console.log("Ajax錯誤：" + error);
                }
            });
        });
    }); 

    function goBack() {
        window.history.back();
    }

    </script>
</body>
</html>