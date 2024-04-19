<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Insert title here</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
<div class="container"> <!-- '.container'를 'container'로 수정 -->
    <form action="/login" method="POST">
        <div class="mb-3"> <!-- 부트스트랩의 마진 클래스를 사용하여 레이아웃 조정 -->
            <label for="loginId" class="form-label">아이디</label> <!-- 라벨 추가 -->
            <input type="text" class="form-control" id="loginId" name="loginId" placeholder="아이디">
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">비밀번호</label> <!-- 라벨 추가 -->
            <input type="password" class="form-control" id="password" name="password" placeholder="비밀번호">
        </div>
        <button type="submit" class="btn btn-primary">로그인하기</button> <!-- 버튼 스타일 적용 -->
    </form>
    <div class="mt-3"> <!-- Google 로그인 링크 스타일 적용 -->
        <a href="/oauth2/authorization/google" class="btn btn-danger">구글 로그인</a>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
