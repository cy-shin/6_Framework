console.log("main.js loaded")

// 아이디 저장 체크박스가 체크 되었을 때에 대한 동작
const saveId = document.getElementById("saveId");

// radio, checkbox 값이 변할 때 발생하는 이벤트 : change
saveId.addEventListener("change", function(event){
    // change는 체크가 되거나, 해제될 때 이벤트 발생
    // -> 체크 유무 검사가 필요
    
    console.log(event);

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


// 이메일로 회원 정보 조회(AJAX)
const inputEmail = document.getElementById("inputEmail");
const selectEmail = document.getElementById("selectEmail");

selectEmail.addEventListener("click", e => {
    
    $.ajax({
        url : "/selectEmail",
        data : {"email": inputEmail.value},
        type : "POST",
        dataType : "JSON", // 응답데이터의 형식이 JSON이다. -> 자동으로 JS 객체로 변환
                              // 만약 Jackson-databind를 사용하면 JS객체 형태로 넘어오기 때문에, 이 작업이 필요하지 않음
        success : (member) => {
            console.log(member);

            // 1) JSON 형태의 문자열로 반환된 경우 (JSON -> JS 객체)
            // 방법 1) JSON.parse(문자열)
            // console.log(JSON.parse(member));

            // 방법 2) dataType : "JSON" 추가 *data"T"ype
            // 

            // 2) Jackson 라이브러리를 이용

            // ------------------------------------ //
            // 현재 상황
            // 이메일로 회원 정보 조회
            // 값이 있으면 객체로, 없으면 null로 응답하고 있음

            if ( member == null ) {

                // h4 요소 생성
                const h4 = document.createElement("h4");

                // 내용 추가
                if(inputEmail.value.trim().length==0) {
                    h4.innerText = "이메일을 입력해주세요.";
                } else {
                    const regEx = /^[A-Za-z\d\-\_]{4,}@[가-힣\w\-\_]+(\.\w+){1,3}$/;
                    if(!regEx.test(inputEmail.value)) {
                        h4.innerText = "이메일 형식이 올바르지 않습니다."
                        h4.style.color="red";
                    } else {
                        h4.innerText = inputEmail.value + "를 찾을 수 없습니다.";
                    }
                }

                // selectEmail의 다음 요소로 추가하겠다(.after(요소))
                // append(요소) : 마지막 자식으로 추가
                // prepend(요소) : 첫 번째 자식으로 추가
                // after(요소) : 다음(이후)에 추가
                // before(요소) : 이전에 추가

                // selectEmail의 다음 요소가 존재하면, 삭제
                if(selectEmail.nextElementSibling != null) {
                    selectEmail.nextElementSibling.remove();
                }
                selectEmail.after(h4);
                
            } else { // 일치 O

                const ul = document.createElement("ul");

                const li1 = document.createElement("li");
                li1.innerText = "회원번호 : " + member.memberNo;

                const li2 = document.createElement("li");
                li2.innerText = "이메일 : " + member.memberEmail;

                const li3 = document.createElement("li");
                li3.innerText = "닉네임 : " + member.memberNickName;

                const li4 = document.createElement("li");
                li4.innerText = "주소 : " + member.memberAddress;

                const li5 = document.createElement("li");
                li5.innerText = "가입일 : " + member.enrollDate;

                const li6 = document.createElement("li");
                li6.innerText = "탈퇴여부 : " + member.memberDeleteFlag;

                ul.append(li1, li2, li3, li4, li5, li6);

                // selectEmail의 다음 요소가 존재하면, 삭제
                if(selectEmail.nextElementSibling != null) {
                    selectEmail.nextElementSibling.remove();
                }
                selectEmail.after(ul);
            }
        },
        error : () => {
            console.log("이메일로 조회하기 실패");
        }
    });
});

