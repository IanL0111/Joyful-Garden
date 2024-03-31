<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-TW">
<head>
    <meta charset="UTF-8">
    <title>課程報名管理</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="../../css/enrollmentManagement.css" />
</head>
<body>

<h1>課程報名管理</h1>

<div class="search-container">
    <form id="searchForm" action="/enrollments/search" method="post"> <!-- 修改這裡的 action -->
        <input  name="searchInput" type="text" id="searchInput" class="search-input" placeholder="搜尋課程關鍵字...">
        <button id="searchButton" class="search-button" type="submit">搜尋</button> <!-- 修改這裡的 type -->
    </form>
</div>
<button id="deleteSelected" class="delete-selected">刪除所選報名資料</button>
<button id="returnBtn" onclick="goBack()" class="returnBtn">返回</button>
<button id="exportBtn" class="exportBtn">匯出報名資料</button>
<div class="enrollments-container">
    <table>
        <thead>
        <tr>
            <th><input type="checkbox" id="selectAll"></th>
            <th>報名編號</th>
            <th>學生姓名</th>
            <th>課程名稱</th>
            <th>報名日期</th>
            <th>支付狀態</th>
            <th>支付金額</th>
            <th>支付日期</th>
            <th>電子信箱</th>
            <th>行動電話</th>
            <th>編輯</th>
            <th>刪除</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${empty enrollmentsList}">
                <tr>
                    <td colspan="12">查無此課程</td>
                </tr>
            </c:when>
            <c:otherwise>
                <c:forEach var="enrollment" items="${enrollmentsList}">
                    <tr>
                        <td><input type="checkbox" class="delete-checkbox" name="selectedCourses" value="${enrollment.enrollmentID}"></td>
                        <td><c:out value="${enrollment.enrollmentID}" /></td>
                        <td><c:out value="${enrollment.student.studentName}" /></td>
                        <td><c:out value="${enrollment.course.courseName}" /></td>
                        <td><c:out value="${enrollment.enrollmentDate}" /></td>
                        <td><c:out value="${enrollment.paymentStatus}" /></td>
                        <td><c:out value="${enrollment.paymentAmount}" /></td>
                        <td><c:out value="${enrollment.paymentDate}" /></td>
                        <td><c:out value="${enrollment.student.email}" /></td>
                        <td><c:out value="${enrollment.student.contactNumber}" /></td>
                        <td><a href="/updateEnrollment/${enrollment.enrollmentID}" class="edit-button">編輯</a></td>
                        <td><button class="delete-button" data-enrollment-id="${enrollment.enrollmentID}">刪除</button></td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>
</div>

<%@ include file="../shop/MenuBar3.html" %>

<script>
$(document).ready(function () {
    // 刪除所選報名資料
    $('#deleteSelected').click(function () {
        var selectedIds = [];
        $('.delete-checkbox:checked').each(function () {
            selectedIds.push($(this).val());
        });

        if (selectedIds.length === 0) {
            Swal.fire('請選擇要刪除的報名資料');
        } else {
            Swal.fire({
                title: '確定刪除所選報名資料嗎？',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonText: '確定',
                cancelButtonText: '取消'
            }).then((result) => {
                if (result.isConfirmed) {
                    $.ajax({
                        url: '/deleteSelectedEnrollments',
                        type: 'POST',
                        traditional: true,
                        data: { selectedEnrollments: selectedIds },
                        success: function () {
                            location.reload();
                        },
                        error: function () {
                            Swal.fire('刪除報名資料時出錯');
                        }
                    });
                }
            });
        }
    });

    // 刪除單筆報名資料
    $('.delete-button').click(function () {
        var enrollmentId = $(this).data('enrollment-id');
        Swal.fire({
            title: '確定刪除這筆報名資料嗎？',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: '確定',
            cancelButtonText: '取消'
        }).then((result) => {
            if (result.isConfirmed) {
                $.ajax({
                    url: '/deleteEnrollment',
                    type: 'POST',
                    data: { enrollmentId: enrollmentId },
                    success: function () {
                        location.reload();
                    },
                    error: function () {
                        Swal.fire('刪除報名資料時出錯');
                    }
                });
            }
        });
    });

    // 全選/取消全選功能
    $('#selectAll').click(function () {
        $('.delete-checkbox').prop('checked', $(this).prop('checked'));
    });

    // 匯出報名資料按鈕事件
    $('#exportBtn').click(function() {
        var format = "excel"; // 設置匯出格式
        window.location.href = "/exportEnrollments?format=" + format;
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
        url: "/checkCourseName",
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

function goBack() {
    window.history.back();
}
</script>

</body>
</html>
