// 비밀번호, 탈퇴, 내 정보 변경 유효성 검사 및 사진 업로드
console.log("myPage.js loaded");

// 비밀번호 유효성 검사

// 비밀번호 변경 form 요소 선택
const changePwForm = document.getElementById("changePwForm");

if(changePwForm != null) { // changePwForm 요소가 존재할 때에만
    changePwForm.addEventListener("submit", function(event){
        // ** 이벤트 핸들러 매개변수 event || e **
        // -> 현재 발생한 이벤트 정보를 가지고 있는 event 객체가 전달됨.

        console.log(event);

        // 비밀번호 변경에 사용되는 input 요소 모두 얻어오기
        const currentPw = document.getElementById("currentPw");
        const newPw = document.getElementById("newPw");
        const newPwConfirm = document.getElementById("newPwConfirm");

        // innerHTML / innerText 시작태그 종료태그 있을 때!
        // 없을 때는? value 사용

        // 현재 비밀번호가 작성되지 않았을 때
        if(currentPw.value.trim().length == 0){
            alert("현재 비밀번호를 입력해주세요.")
            currentPw.focus();
            currentPw.value = "";
            // return false; 
            // --> 인라인 이벤트 모델의 onsubmit = "return 함수명()"; 에서만 가능

            event.preventDefault();
            // -> 이벤트를 수행하지 못하게 하는 함수!
            // ---> 기본 이벤트를 삭제?
            return; // 값을 반환해서 함수를 종료
        }

        // 새 비밀번호가 작성되지 않았을 때
        if(newPw.value.trim().length == 0) {
            // alert("새 비밀번호를 입력해주세요.")
            // newPw.focus();
            // newPw.value = "";
            alertAndFocus(this,"새 비밀번호를 입력해주세요");
            event.preventDefault();
            return;
        }
        
        // 새 비밀번호 확인이 작성되지 않았을 때
        if(newPwConfirm.value.trim() == 0) {
            alertAndFocus(this,"새 비밀번호 확인을 입력해주세요");
            event.preventDefault();
            return;
        }

        // 비밀번호 정규식 검사(특수문자 여부 확인 등)


        // 새 비밀번호 확인이 일치하지 않을 때?
        if(newPw.value != newPwConfirm.value){
            alert("새 비밀번호가 일치하지 않습니다.")
            newPwConfirm.focus();
            event.preventDefault();
            return;
        }

    })
}

// 함수를 사용해서 중복 구문 줄이기
// 경고창 출력 + 포커스 이동 + 값 삭제
function alertAndFocus(input, str) {
    alert(str);
    input.focus();
    input.value = "";
}



// 표준 이벤트 모델
// const memberDeleteForm = document.getElementById("memberDeleteForm");

// if(memberDeleteForm != null) { // 요소가 존재할 때만
//     memberDeleteForm.addEventListener("submit",function(event){

//         // 회원 비밀번호
//         const memberPw = document.getElementById("memberPw");
    
//         if(memberPw.value.trim().length==0) {
//             alertAndFocus(memberPw, "현재 비밀번호를 입력해주세요.")
//             event.preventDefault();
//             return;
//         }
//         // 동의 체크박스
//         const agreeBox = document.querySelector("input[name='agree']")
    
//         if(!agreeBox.checked) {
//             alert("탈퇴에 동의하시면 약관에 체크해주세요.")
//             agreeBox.focus();
//             event.preventDefault();
//             return;
//         }
    
//         if( !confirm("정말로 탈퇴하시겠습니까?") ) {
//             event.preventDefault();
//             return;
//         }
//     })
// }

// 인라인 이벤트 모델로도 만들어보기
// onsubmit="return 리턴값" 구조를 활용함!
function deleteForm(form) {
    const memberPw = document.querySelector("input[name='memberPw']");
    const agreeBox = document.querySelector("input[name='agree']")
    
    if(memberPw.value.trim().length==0) {
        alertAndFocus(form, "비밀번호를 확인해주세요.")
        return false;
    }

    if(!agreeBox.checked) {
        alertAndFocus(form, "약관에 동의해주세요.")
        return false;
    }

    if(!confirm("정말로 탈퇴하시겠습니까?")) {
        return false;
    }

    
}