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

// -------------------------------------------------------------------------------------------------------------------------------------
// 프로필 수정

const profileImage = document.getElementById("profile-image");
const deleteImage = document.getElementById("delete-image");
const imageInput = document.getElementById("image-input");


let initCheck; // 초기 프로필 이미지 상태를 저장하는 변수
// true : 업로드 된 이미지가 있음, false = 기본 이미지(이미지 없음)

// 이미지가 업로드되었거나 삭제되었음을 나타내는 변수
// (초기값(취소) : -1 
// 프로필 삭제됨(x버튼 눌렀음) : 0
// 새 이미지 업로드 : 1)
let deleteCheck = -1;

// 프로필 수정 페이지에 처음 들어왔을 때의 이미지 경로
const originalImage = profileImage.getAttribute("src");


// 프로필 수정 화면인 경우
if(imageInput != null) {

    // 해당 화면 진입 시 프로필 이미지 상태를 저장(initCheck)
    if(profileImage.getAttribute("src") == "/resources/images/user.png") { 
        initCheck = false;
    } // 기본 이미지인 경우
    else {
        initCheck = true;
    } 



    // 이미지가 선택되었을 때 미리보기

    // * input type="file" 요소는 값이 없을 때 ''(빈칸)
    // * input type="file" 요소는 이전에 선택한 파일이 있어도 취소하면 다시 ''(빈칸)
    // * input type="file" 요소로 파일을 선택하면 change 이벤트가 발생한다.

    imageInput.addEventListener("change", e => {
        // e.target : event가 발생한 요소(imageInput)
        // ****** 화살표 함수에서는 this 사용 불가 잊지 말기!

        // 선택된 파일의 목록
        console.log(e.target.files);
        console.log(e.target.files[0]);

        // 선택된 파일이 있을 경우
        if(e.target.files[0] != undefined) { // 선택 안되면 undifined
            // 데이터 url 방식
            const reader = new FileReader();
            // FileReader (파일을 읽어들이는 객체)
            // - 웹 애플리케이션이 비동기적으로 데이터를 읽기 위해서
            //   읽을 파일을 가리키는 File 객체

            // - 읽어들인 파일을 사용자 컴퓨터에 저장할 수 있다

            reader.readAsDataURL(e.target.files[0]);
            // FileReader.readAsDataURL("파일정보")
            // -> 지정된 파일을 읽기 시작함

            // FileReader.onload : 파일 읽기가 완료되었을 때 동작을 지정
            reader.onload = event => {
                // console.log(event.target);
                // event.target.result : 읽어진 파일 결과(실제 이미지 파일)의 경로
                event.target.result;

                // img 태그의 src 속성으로 읽은 이미지 파일 경로 추가
                // == 이미지 미리보기
                profileImage.setAttribute("src", event.target.result);

                deleteCheck = 1;
            };
        } else { // 취소가 눌러진 경우

            // 초기 이미지로 다시 변경
            profileImage.setAttribute("src", originalImage);
        }

        // 클라우드 이용, 프로젝트 내 경로 이용, 데이터 url 방식 이용


        // 데이터 url 방식
        const reader = new FileReader();
        // FileReader (파일을 읽어들이는 객체)
        // - 웹 애플리케이션이 비동기적으로 데이터를 읽기 위해서
        //   읽을 파일을 가리키는 File 객체

        // - 읽어들인 파일을 사용자 컴퓨터에 저장할 수 있다

        reader.readAsDataURL(e.target.files[0]);
        // FileReader.readAsDataURL("파일정보")
        // -> 지정된 파일을 읽기 시작함

        // FileReader.onload : 파일 읽기가 완료되었을 때 동작을 지정
        reader.onload = event => {
            // console.log(event.target);
            // event.target.result : 읽어진 파일 결과(실제 이미지 파일)의 경로
            event.target.result;

            // img 태그의 src 속성으로 읽은 이미지 파일 경로 추가
            // == 이미지 미리보기
            profileImage.setAttribute("src", event.target.result);
        };


    });

    // x버튼이 클릭됐을 경우 -> 기본 이미지로 변경
    deleteImage.addEventListener("click", () => {
        profileImage.setAttribute("src", "/resources/images/user.png")
        imageInput.value = "";
        deleteCheck = 0;
    })
}


function profileValidate(){

    // 이미지가 없음 -> 있음
    if (!initCheck && deleteCheck == 1) {
        return true;
    }
    
    // 이미지가 있었는데 -> 없음(x버튼)
    if (initCheck && deleteCheck == 0) {
        return true;
    }
    
    // 이미지가 있었는데 -> 있음(새로운 이미지 업로드)
    if (initCheck && deleteCheck == 1) {
        return true;
    }

    alert("이미지가 등록되지 않았습니다.");
    return false;
}