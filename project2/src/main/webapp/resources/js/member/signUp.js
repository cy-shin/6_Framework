console.log("signUp.js");

// let emailCheck; // 변수 플래그를 이용하면 쉬우나 비효율적일 수 있음 -> 객체를 활용하자
// JS 객체를 이용한 유효성 검사 결과 저장 객체
// JS 객체 = { "K":V, "K":V, ...} (Map형식) (*** Key는 무조건 String)
const checkObj = {
    "memberEmail"       : false, 
    "memberPw"          : false, 
    "memberPwConfirm"   : false,
    "memberNickName"    : false,
    "memberTel"         : false 
};

// Map 형식에서 value값 불러오기 -> 변수명.key 또는 변수명["key"]를 이용하면 객체의 속성에 접근 가능
// checkObj['memberEmail']
// checkObj.memberEmail
//for(let k in checkObj) console.log(checkObj[k]);

// 회원 가입 양식이 제출 되었을 때
const signUpForm = document.getElementById("signUp-frm");
signUpForm.addEventListener("submit", function(event){
    
    // checkObj의 속성 중 하나라도 false가 있다면 제출 이벤트를 제거함
    // 객체용 for문 for in 구문 : 객체의 key값을 순서대로 접근하는 반복문
    // [작성법]
    // for(let key in 객체명)
            // == key
    // -> 객체에서 순서대로 key를 하나씩 꺼내 왼쪽 변수에 저장

    for(let key in checkObj) {

        let str;

        if(!checkObj[key]) { // checkObj의 속성 하나를 꺼내 값을 검사했는데 false인 경우( = 유효하지 않은 key가 있는 경우 )
            
            switch(key){
            case "memberEmail" : str = "이메일이 유효하지 않습니다."; break;
            case "memberPw" : str = "비밀번호가 유효하지 않습니다."; break;
            case "memberPwConfirm" : str = "비밀번호 확인이 유효하지 않습니다."; break;
            case "memberNickName" : str = "닉네임이 유효하지 않습니다."; break;
            case "memberTel" : str = "전화번호가 유효하지 않습니다."; break;
            }
            
            alert(str); // 대화상자를 출력
            document.getElementById(key).focus(); // 유효하지 않은 입력으로 focus를 보냄
            event.preventDefault(); // 제출 이벤트 제거
            return; // 함수 종료
        }
    }

})

// 이메일 유효성 검사
const memberEmail = document.getElementById("memberEmail");
const emailMessage = document.getElementById("emailMessage");
const receiveCheck = emailMessage.nextElementSibling;
memberEmail.addEventListener("input", function(){
    // 문자가 입력되지 않은 경우
    if(memberEmail.value.trim().length == 0) {
        emailMessage.innerText = "메일을 받을 수 있는 이메일을 입력해주세요.";
        memberEmail.value = "";

        // confirm, error 클래스 전부 제거 -> 검정 글씨로 만들기
        emailMessage.classList.remove("confirm", "error");

        // 유효성 검사 확인 객체에 현재 상태를 저장
        checkObj.memberEmail = false;

        return; // 글자가 없는 경우, 이 조건식을 수행한 후 함수를 종료
    }
    
    // ^[A-Za-z\d\-\_]{4,}@[가-힣\w\-\_]+(\.\w+){1,3}$
    // 영어 대문자, 영어 소문자, 숫자(\d), -(하이픈), _(언더바)로 구성된 4글자 이상의 단어
    // ..로 시작해서
    // @
    // 아무 단어 및 -, _, 한글을 포함해 한 글자 이상
    // (.한글자 이상 단어) 조합(예 : .com)이 1번에서 3번 반복
    // ..으로 끝나는 구조만 허용함
    const regEx = /^[A-Za-z\d\-\_]{4,}@[가-힣\w\-\_]+(\.\w+){1,3}$/;
    
    if(regEx.test(memberEmail.value)) { // 유효한 경우
        // emailMessage.innerText = "유효한 이메일 형식입니다.";
        // emailMessage.classList.add("confirm");
        // emailMessage.classList.remove("error");
        // // 유효성 검사 확인 객체에 현재 상태를 저장        
        // checkObj.memberEmail = true;

        // 이메일이 유효한 형식이라면, 중복되는 이메일이 있는지 검사
        // -> AJAX

        // jQuery를 이용한 ajax 코드
        // -> $.ajax( JS 객체 )
        // $ : jQuery 기호
        // $.ajax() : jQuery에서 제공하는 ajax라는 이름의 함수
        // JS 객체 : {K:V, K:V, K:V, K:V, K:V, ....}

        // $.ajax() 함수의 매개변수로 전달되는 객체에는
        // 반드시 "url"이라는 key가 포함되어야 하며,
        // 선택적으로 data. type, dataType, success, error, complete, async 등을 포함 가능

        $.ajax({
            url : "/emailDupCheck", // 비동기 통신을 진행할 서버의 요청 주소
            data : {"memberEmail" : memberEmail.value}, // JS-> 서버로 전달할 값
            type : "GET", //데이터 전달 방식(GET POST) (ajax에서는 주로 GET)
            success : (result) => { // 비동기 통신 성공, 응답을 받았을 때
                // result : 서버로부터 받은 응답 데이터(매개변수 이름은 자유)
                console.log(result);

                if(result == 0) { // 중복 아님
                    emailMessage.innerText = "사용 가능한 이메일 입니다.";
                    emailMessage.classList.add("confirm");
                    emailMessage.classList.remove("error");
                    checkObj.memberEmail = true;
                } else {
                    emailMessage.innerText = "이미 사용중인 이메일입니다.";
                    emailMessage.classList.add("error");
                    emailMessage.classList.remove("confirm");
                    checkObj.memberEmail = false;
                }
            },
            error : () => { // 비동기 통신이 실패했을 때 수행
                console.log("ajax 통신 실패");
            },
            complete : () => { // success, error 수행 여부 관계 없이 무조건 수행
                console.log("중복 검사 수행 완료");

            }
        });

    } else { // 유효하지 않은 경우
        emailMessage.innerText = "이메일 형식이 유효하지 않습니다.";
        emailMessage.classList.add("error")
        emailMessage.classList.remove("confirm");

        // 유효성 검사 확인 객체에 현재 상태를 저장
        checkObj.memberEmail = false;

    }

})

// 비밀번호 유효성 검사
const memberPw = document.getElementById("memberPw");
const memberPwConfirm = document.getElementById("memberPwConfirm");
const pwMessage = document.getElementById("pwMessage");

// input 이벤트 : input 태그에 입력이 된 모든 경우를 인식
memberPw.addEventListener("input", function(){

    // 비밀번호가 입력되지 않은 경우
    if(memberPw.value.trim().length == 0){
        pwMessage.innerText = "영어, 숫자, 특수문자( !, @, #, -, _ )를 포함해 6~20글자 사이로 입력해주세요.";
        memberPw.value = "";
        pwMessage.classList.remove("confirm", "error"); // 검정 글씨로 변환
        checkObj.memberPw = false;
        return;
    }

    // 비밀번호 정규표현식 검사
    const regEx = /^[a-zA-Z\d!@#-_]{6,20}$/;
    if(regEx.test(memberPw.value)){ // 유효한 비밀번호
        checkObj.memberPw = true;

        if(memberPwConfirm.value.trim().length == 0) { // 입력 안된 경우
            pwMessage.innerText = "유효한 비밀번호 형식입니다.";
            pwMessage.classList.add("confirm");
            pwMessage.classList.remove("error");
        } else { // 유효한 비밀번호이면서 확인도 작성된 경우 -> 같은지 비교
            // 비밀번호가 입력될 때
            // 비밀번호 확인에 작성된 값과 일치하는 경우
            if(memberPw.value == memberPwConfirm.value){
                pwMessage.innerText = "비밀번호가 일치합니다.";
                pwMessage.classList.add("confirm");
                pwMessage.classList.remove("error");
                checkObj.memberPwConfirm = true;
            } else { // 일치하지 않는 경우
                pwMessage.innerText = "비밀번호가 일치하지 않습니다.";
                pwMessage.classList.add("error");
                pwMessage.classList.remove("confirm");
                checkObj.memberPwConfirm = false;
            }
        }

    } else { // 유효하지 않은 비밀번호
        pwMessage.innerText = "비밀번호 형식이 유효하지 않습니다.";
        pwMessage.classList.add("error");
        pwMessage.classList.remove("confirm");
        checkObj.memberPw = false;
    }


})

// 비밀번호 확인 유효성 검사
memberPwConfirm.addEventListener("input", function(){

    if(memberPwConfirm.value.trim().length == 0){
        pwMessage.classList.remove("confirm", "error"); // 검정 글씨로 변환
        if(checkObj.memberPw) {
            pwMessage.innerText = "유효한 비밀번호 형식입니다.";
            pwMessage.classList.add("confirm");
            pwMessage.classList.remove("error");
        } else {
            pwMessage.innerText = "비밀번호 형식이 유효하지 않습니다.";
            pwMessage.classList.add("error");
            pwMessage.classList.remove("confirm");
            checkObj.memberPw = false;
        }
        return;
    }

    if(checkObj.memberPw) {
        // 비밀번호, 비밀번호 확인이 같은지 검사
        if(memberPwConfirm.value == memberPw.value) {
            pwMessage.innerText = "비밀번호가 일치합니다.";
            pwMessage.classList.add("confirm");
            pwMessage.classList.remove("error");
            checkObj.memberPwConfirm = true;
        } else {
            pwMessage.innerText = "비밀번호가 일치하지 않습니다.";
            pwMessage.classList.add("error");
            pwMessage.classList.remove("confirm");
            checkObj.memberPwConfirm = false;
        }
    } else { // 비밀번호가 유효하지 않은 경ㅇ우
        checkObj.memberPwConfirm = false;
    }
});

// 닉네임 유효성 검사
const memberNickName = document.getElementById("memberNickName");
const nickMessage = document.getElementById("nickMessage");

memberNickName.addEventListener("input", function(){

    // 닉네임에 문자가 입력되지 않은 경우
    if(memberNickName.value.trim().length == 0) {
        nickMessage.innerText = "한글, 영어, 숫자를 포함해 10글자 이내로 작성해주세요.";
        nickMessage.classList.remove("confirm", "error");
        memberNickName.value = "";
        checkObj.memberNickName = false;
        return;
    }

    const regEx = /^[\w가-힣]{2,10}$/;
    // \w = [A-Za-z0-9]

    // 닉네임이 올바르게 입력된 경우
    if(regEx.test(memberNickName.value)) {

        // 닉네임 중복 검사 코드 추가 예정 

        nickMessage.innerText = "사용 가능한 닉네임입니다."
        nickMessage.classList.add("confirm");
        nickMessage.classList.remove("error");
        checkObj.memberNickName = true;
    } else {
        nickMessage.innerText = "닉네임 형식이 유효하지 않습니다."
        nickMessage.classList.add("error");
        nickMessage.classList.remove("confirm");
        checkObj.memberNickName = false;
    }
})

// 전화번호 유효성 검사
const memberTel = document.getElementById("memberTel");

const telMessage = document.getElementById("telMessage");

memberTel.addEventListener("input", function(){
    // 1. 전화번호가 입력되지 않은 경우
    
    if(memberTel.value.trim().length == 0) {
        telMessage.innerText = "전화번호를 입력해 주세요. (- 제외)";
        memberTel.value = "";
        telMessage.classList.remove("confirm", "error");
        checkObj.memberTel = false;
        return;
    }

    // 전화번호 정규표현식 검사
    const regEx = /^0(1[01679]|2|[3-6][1-5]|70)[1-9]\d{2,3}\d{4}$/;
    // /^0(1[01679]|2|[3-6][1-5]|70)[1-9]\d{2,3}\d{4}$/;
    // ^0 : 0으로 시작

    // (1[01679]|2|[3-6][1-5]|70) : 다음 조합중 하나에 해당
    //  - 1[01679] : 1이 반드시 오고, 그 다음 숫자는 0,1,6,7,9 중 하나
    //  - 2 : 2만 옴
    //  - [3-6][1-5] : 3~6, 1~5로 이루어짐
    //  - 70 :  70만 옴

    // [1-9] : 1~9중 하나
    // \d{2,3} : 숫자 형식 2개 이상 3개 이하
    // \d{4} : 숫자 형식 4개
    // $/ : 로 끝

    // 2. 전화번호가 입력된 경우
    if(regEx.test(memberTel.value)) {
        // 올바른 경우
        telMessage.innerText = "유효한 전화번호입니다.";
        telMessage.classList.add("confirm");
        telMessage.classList.remove("error");
        checkObj.memberTel = true;
    } else {
        // 올바르지 않은 경우
        telMessage.innerText = "전화번호 형식이 유효하지 않습니다.";
        telMessage.classList.add("error");
        telMessage.classList.remove("confirm");
        checkObj.memberTel = false;
    }

})

