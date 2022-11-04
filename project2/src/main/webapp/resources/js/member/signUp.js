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
            // case "memberPwConfirm" : str = "비밀번호 확인이 유효하지 않습니다."; break;
            // case "memberNickName" : str = "닉네임이 유효하지 않습니다." break;
            // case "memberTel" : str = "전화번호가 유효하지 않습니다." break;
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
memberEmail.addEventListener("keyup", function(){
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
        emailMessage.innerText = "유효한 이메일 형식입니다.";
        emailMessage.classList.add("confirm");
        emailMessage.classList.remove("error");

        // 유효성 검사 확인 객체에 현재 상태를 저장        
        checkObj.memberEmail = true;

    } else { // 유효하지 않은 경우
        emailMessage.innerText = "이메일 형식이 유효하지 않습니다.";
        emailMessage.classList.add("error")
        emailMessage.classList.remove("confirm");

        // 유효성 검사 확인 객체에 현재 상태를 저장
        checkObj.memberEmail = false;

    }

})