console.log("main.js loaded")

// 아이디 저장 체크박스가 체크 되었을 때에 대한 동작
const saveId = document.getElementById("saveId");

// radio, checkbox 값이 변할 때 발생하는 이벤트 : change
saveId.addEventListener("change", function(){

    // change는 체크가 되거나, 해제될 때 이벤트 발생
    // -> 체크 유무 검사가 필요

    // 이벤트 핸들러 내부 this : 이벤트가 발생한 요소(아이디 저장 checkbox)
    console.log(this.checked);


    // 체크박스.checked : 체크 0 == true, 체크 X == false 반환
    // 체크박스.checked = true/false;
    if(this.checked) { // 체크된 경우

        const str = "개인 정보 보호를 위해 개입 PC에서의 사용을 권장합니다."
                    + " 개인 PC가 아닌 경우 취소를 눌러주세요.";
        // window.confirm("내용") : 확인 == true, 취소 == false 반환
        if( !confirm(str) ){ // 취소를 눌렀을 때
            // 체크 해제
            this.checked = false;
        }
    }

});

// 로그인 유효성 검사
// (로그인 form 태그 submit 이벤트 취소하기)
function loginValidate(){
    // Validate : 유효하다

    // document.querySelectot("css선택자")
    // - css 선택자와 일치하는 요소를 얻어옴(선택한다)
    // - 여러 요소가 선택되면 첫 번째 요소만 얻어옴

    const memberEmail = document.querySelector("input[name='memberEmail']");
    const memberPw = document.querySelector("input[name='memberPw']");

    if(memberEmail.value.trim().length<=0) {
        alert("아이디를 입력해주세요.")
        memberEmail.focus(); // 이메일 input 요소에 초점을 맞춤
        memberEmail.value = ""; // 이메일 input에 입력된 내용을 전부 지움(띄어쓰기 없애기 용도)
        return false;
    }
    
    if(memberPw.value.trim().length<=0) {
        alert("비밀번호를 입력해주세요.")
        memberPw.focus(); // 이메일 input 요소에 초점을 맞춤
        memberPw.value = "";
        return false;
    }
}

