<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="kr">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>1수업용 프로젝트</title>

    <link rel="stylesheet" href="/resources/css/main-style.css">

    <script src="https://kit.fontawesome.com/f7459b8054.js" crossorigin="anonymous"></script>
</head>
<body>
    <main>
       <%-- header.jsp 추가{포함} --%>
		<%--
			jsp 액션 태그 중 include?
			- 해당 위치에 page 속성으로 지정된 jsp 파일의 내용이 포함됨
			- jsp파일의 경로는 /webapp 폴더를 기준으로 작성
		 --%>
        <%-- WEBAPP폴더를 기준으로 경로 작성하기 --%>
       <jsp:include page="/WEB-INF/views/common/header.jsp" />

        <section class="content">
            <section class="content-1">
                ${loginMember}
            </section>

            <section class="content-2">

            <%-- 로그인 여부에 따라 출력 화면 변경 --%>
            <c:choose>

            <%-- 로그인 x인 경우 --%>
                <c:when test = "${empty sessionScope.loginMember}">
                       <%-- 절대 경로 방식 (현재 /가 프로젝트 최상위) --%>
                <form action="/member/login" name="login-frm" method="POST">
                    <!-- frm form -->
                    <!-- 아이디, 비밀번호, 로그인 버튼 -->
                    <fieldset id="id-pw-area"> 
                        <!-- 아이디/비밀번호 -->
                        <section>
                            <input type="text" name="inputEmail" placeholder="이메일" autocomplete="off" value="${cookie.saveId.value}">
                                                                                                    <%-- 쿠키 중 saveId에 저장된 값 --%>
                            <input type="password" name="inputPw" placeholder="비밀번호">
                        </section>
        
                        <!-- 로그인 -->
                        <section>
                            <!-- 버튼 태그에 type 안쓰면 기본값 submit -->
                            <button>로그인</button>
                        </section>
                    </fieldset>
        
                    <%-- 쿠키에 saveId가 있을 경우 --%>
                    <c:if test="${!empty cookie.saveId}">
                        <%-- temp 변수 선언 --%>
                        <c:set var="temp" value="checked" />
                        <%-- c:set은 pageScope로 page 어디서든 사용 가능하며, if문 밖에서도 유효함 --%>
                    </c:if>
                    <!-- 아이디 저장 체크박스 -->
                    <!-- label 태그 내부에 input 태그를 작성하면 label for="id"를 작성한 것처럼 만들어짐 -->
                    <label>
                        <input type="checkbox" name="saveId" ${temp}> 아이디 저장
                        <%-- 쿠키가 없을경우 temp=null, el은 null을 빈칸으로 처리함 --%>
                    </label>
        
                    <!-- 회원가입, ID/PW찾기 -->
                    <article id="signUp-find-area">
                        <a href="/member/signUp">회원가입</a>
                        <span>|</span>
                        <a href="#">ID/PW찾기</a>
                    </article>
                </form>
                </c:when>

                <%-- 로그인 ㅇ인 경우 --%>
                <c:otherwise>
                    <article class="login-area">
                    <a href="#">
                        <img id = "member-profile" src="/resources/images/dog.jpg">
                    </a>

                    <!--회원 정보 + 로그아웃 버튼 -->
                    <div class="my-info">
                        <div>
                        	<%--myPage-info? --%>
                            <a href="/member/myPage/info" id="nickname">${loginMember.memberNickName}</a>
                            <a href="/member/logout" id="logout-btn">로그아웃</a>
                        </div>
                        <p>${loginMember.memberEmail}</p>
                    </div>
                </c:otherwise>
            </c:choose>




            </section>
        </section>
    </main>

    <%-- footer.jsp 포함 --%>
    <jsp:include page="/WEB-INF/views/common/footer.jsp"/>

</body>
</html>