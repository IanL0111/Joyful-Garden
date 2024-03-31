<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>活動列表</title>
    <style>
    	th {
 		   text-align: center;
		}

		.custom-button {
   		 padding: 10px 20px;
   		 border: none;
   		 border-radius: 5px;
   		 cursor: pointer;	
    	 font-size: 16px;		
   		 transition: background-color 0.3s ease;
		}

		.primary-button {
  		  background-color: #007bff;
  		  color: #fff;
		}

		.secondary-button {
   		 background-color: #6c757d;
   		 color: #fff;
		}

		.excel-button {
   		 background-color: #28a745;
   		 color: #fff;
		}		

		.custom-button:hover {
   		 opacity: 0.8;
		}

        body {
            font-family: 'Arial', sans-serif;
            background-color: #f7f7f7;
            margin: 0;
            padding: 20px;
        }

        .container {
            box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
            border-radius: 10px;
            overflow: hidden;
            background-color: white;
            width: 80%;
            margin: auto;
            padding: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            border-bottom: 1px solid #ddd;
            padding: 12px;
        }

        th {
            background-color: #f2f2f2;
        }

        tr:hover {
            background-color: #f5f5f5;
        }

        img {
            border-radius: 5px;
            margin-top: 5px;
            max-width: 100px;
            max-height: 100px;
        }

        .add-activity-button-container {
            text-align: center;
            margin-top: 20px;
        }
    </style>
</head>
<body>

<div class="container">
    <h1 style="text-align: center; color: #333;">活動列表</h1>
    <table border="1">
        <tr>
            <th>活動名稱</th>
            <th>日期</th>
            <th>地點</th>
            <th style="width:350px">内容</th>
            <th>圖片</th>
            <th>操作</th>
        </tr>
        <c:forEach items="${activity}" var="act">
            <tr>
                <td>${act.activityName}</td>
                <td>${act.activityDate}</td>
                <td>${act.activityLocation}</td>
                <td>${act.activityContent}</td>
                <td style="white-space: nowrap;">
    				<c:forEach items="${act.images}" var="image">
      					  <img src="${image.imagesPath}" alt="活動圖片" style="width: 100px; height: 100px; display: inline-block; margin-right: 10px;">
   				    </c:forEach>
			   </td>
                <td style="white-space: nowrap;">
                    <a href="/activity/activityCompiler/${act.activityId}" style="color: #fff; text-decoration: none; margin-right: 10px;" class="custom-button primary-button">編輯</a>
                    <a href="<c:url value='/activity/${act.activityId}'/>" data-method="delete" data-confirm="確定刪除？" style="color: #fff; text-decoration: none;" class="custom-button secondary-button">刪除</a>
                </td>
            </tr>
        </c:forEach>
    </table>

    <div class="add-activity-button-container">
        <a href="<c:url value='/web/activity'/>" style="text-decoration: none; display: inline-block; margin-right: 10px;">
            <button class="custom-button primary-button">新增活動</button>
        </a>
        <form id="exportForm" action="/exportActivities" method="post" style="display: inline-block; margin-right: 10px;">
            <button type="submit" class="custom-button excel-button">匯出Excel</button>
        </form>
        <a href="<c:url value='/xxx'/>" style="text-decoration: none; display: inline-block; margin-right: 10px;">
            <button class="custom-button secondary-button">學生報名表</button>
        </a>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<%@ include file="../shop/MenuBar3.html" %>

<script>
$(document).on('click', 'a[data-method="delete"]', function (e) {
    e.preventDefault();
    var url = $(this).attr('href');
    if (confirm($(this).data('confirm'))) {
        axios.delete(url)
            .then(function (response) {
                location.reload();
            })
            .catch(function (error) {
                console.error('Error deleting activity:', error);
            });
    }
});

$(document).ready(function() {
    $('#exportForm').submit(function(event) {
        event.preventDefault(); 

        $.ajax({
            type: 'POST',
            url: $(this).attr('action'),
            data: new FormData(this),
            contentType: false, 
            processData: false, 
            xhrFields: {
                responseType: 'blob'
            },
            success: function(response, status, xhr) {
                var filename = '';
                var disposition = xhr.getResponseHeader('Content-Disposition');
                if (disposition && disposition.indexOf('attachment') !== -1) {
                    var filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
                    var matches = filenameRegex.exec(disposition);
                    if (matches != null && matches[1]) {
                        filename = matches[1].replace(/['"]/g, '');
                    }
                }
                var blob = new Blob([response], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
                var link = document.createElement('a');
                link.href = window.URL.createObjectURL(blob);
                link.download = filename;
                document.body.appendChild(link);
                link.click();
                document.body.removeChild(link);

                
                Swal.fire({
                    icon: 'success',
                    title: '導出成功',
                    text: '您已成功導出 Excel！',
                    confirmButtonText: '確定',
                });
            },
            error: function(xhr, status, error) {
                console.error('導出失敗:', error);
               
                Swal.fire({
                    icon: 'error',
                    title: '導出失敗',
                    text: '請稍後重試！',
                    confirmButtonText: '確定',
                });
            }
        });
    });
});
</script>


</body>
</html>
