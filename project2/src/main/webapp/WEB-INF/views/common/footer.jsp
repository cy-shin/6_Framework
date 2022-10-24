<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

 <footer>
        <p>
            Copyright &copy; Kh Information Educational Institute A-Class
        </p>
        <!-- 단락, 글자만 넣은 후 영역을 나누고 싶을 때 활용 -->
        <!-- &copy : copyright -->

        <article>
            <a href="#">프로젝트 소개</a>
            <span>|</span>
            <a href="#">이용약관</a>
            <span>|</span>
            <a href="#">개인정보 처리방침</a>
            <span>|</span>
            <a href="#">고객센터</a>
        </article>
</footer>

<%-- session scope에 message 속성이 존재하는 경우
    alert창을 이용해서 내용을 출력
--%>

<%-- 
1. 어디서든 재활용하려고
2. header에 작성하는 것 보다 footer에 작성해두는 것이 나중에 찾기 쉬워서
3. header 작업에 영향을 주지 않으려고(header에 작성할 경우 다른 코드의 진행이 멈출 수 있음)

 --%>
<c:if test="${!empty sessionScope.message}">
    <script>
        alert("${sessionScope.message}")
    </script>

    <%-- message 1회 출력 후 session scope에서 삭제 --%>
    <c:remove var="message" scope="session" />
</c:if>