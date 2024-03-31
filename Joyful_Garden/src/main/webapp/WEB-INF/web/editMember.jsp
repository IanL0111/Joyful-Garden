<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>編輯會員</title>

            <!-- Bootstrap CSS -->
            <link rel="stylesheet"
                href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css">
        </head>

        <body>
            <div class="container mt-5">
                <h2>編輯會員</h2>
                <form method="post" modelAttribute="member" action="/edit/${member.memberId}">
                    <div class="mb-3">
                        <label for="username" class="form-label">帳號(Email)</label>
                        <input type="text" class="form-control" id="username" name="username"
                            value="${member.username}">
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">密碼</label>
                        <input type="password" class="form-control" id="password" name="password"
                            value="${member.password}">
                    </div>
                    <div class="mb-3">
                        <label for="nickName" class="form-label">會員暱稱</label>
                        <input type="text" class="form-control" id="nickName" name="nickName"
                            value="${member.nickName}">
                    </div>
                    <div class="mb-3">
                        <label for="memberPicture" class="form-label">大頭貼圖片</label>
                        <input type="file" class="form-control-file" id="memberPicture" name="memberPicture"
                            value="${member.memberPicture}">
                    </div>
                    <div class="mb-3">
                        <label for="phoneNumber" class="form-label">手機號碼</label>
                        <input type="text" class="form-control" id="phoneNumber" name="phoneNumber"
                            value="${member.phoneNumber}">
                    </div>
                    <div class="mb-3">
                        <label for="birthdate" class="form-label">生日</label>
                        <input type="date" class="form-control" id="birthdate" name="birthdate"
                            value="${member.birthdate}">
                    </div>
                    <div class="mb-3">
                        <label for="address" class="form-label">送貨地址</label>
                        <input type="text" class="form-control" id="address" name="address" value="${member.address}">
                    </div>
                    <div class="mb-3">
                        <label for="verificationCode" class="form-label">驗證碼</label>
                        <input type="text" class="form-control" id="verificationCode" name="verificationCode"
                            value="${member.verificationCode}">
                    </div>
                    <div class="mb-3">
                        <label class="form-label">已驗證?</label>
                        <p>(已驗證:true / 未驗證:false)</p>
                        <div class="d-flex">
                            <p class="form-check me-3">
                                <input class="form-check-input" type="radio" name="verified" id="verifiedTrue"
                                    value="true" ${member.verified ? 'checked' : '' }>
                                <label class="form-check-label" for="verifiedTrue">已驗證</label>
                            </p>
                            <p class="form-check">
                                <input class="form-check-input" type="radio" name="verified" id="verifiedFalse"
                                    value="false" ${!member.verified ? 'checked' : '' }>
                                <label class="form-check-label" for="verifiedFalse">未驗證</label>
                            </p>
                        </div>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">停權中?</label>
                        <p>(正常:false / 停權中:true)</p>
                        <div class="d-flex">
                            <p class="form-check me-3">
                                <input class="form-check-input" type="radio" name="deleted" id="deletedFalse"
                                    value="false" ${!member.deleted ? 'checked' : '' }>
                                <label class="form-check-label" for="deletedFalse">正常</label>
                            </p>
                            <p class="form-check">
                                <input class="form-check-input" type="radio" name="deleted" id="deletedTrue"
                                    value="true" ${member.deleted ? 'checked' : '' }>
                                <label class="form-check-label" for="deletedTrue">停權中</label>
                            </p>
                        </div>
                    </div>

                    <div class="mb-3">
                        <label for="memberLevel" class="form-label">會員等級</label>
                        <input type="text" class="form-control" id="memberLevel" name="memberLevel"
                            value="${member.memberLevel}">
                    </div>
                    <button type="submit" class="btn btn-primary">提交</button>
                </form>
            </div>
        </body>

        </html>