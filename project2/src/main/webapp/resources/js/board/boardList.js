
// 썸네일 클릭 시 Modal창으로 출력하기

// 즉시 실행 함수

( () => {
    const thumbnailList = document.getElementsByClassName("list-thumbnail");

    if(thumbnailList.length > 0) { // 썸네일이 존재하는 경우
        
        // Modal 관련 요소 얻어오기
        const modal = document.querySelector(".modal");
        const modalClose = document.getElementById("modal-close");
        const modalImage = document.getElementById("modal-image");

        for (let th of thumbnailList) {
            th.addEventListener("click", () => {
                // modal창에 show클래스가 없으면 추가, 있으면 삭제
                modal.classList.toggle("show");
                
                // 클릭한 썸네일의 src 속성값을 얻어와 모달창의 src속성값에 저장
                modalImage.setAttribute("src", th.getAttribute("src")); 
            })
    }

    // x버튼 동작
    modalClose.addEventListener("click", () => {
        // hide 클래스를 추가해서 0.5초동안 투명해지는 애니메이션 작동
        modal.classList.toggle("hide");

        // 0.5초 후 : setTimeOut
        setTimeout(() => {
            modal.classList.remove("show", "hide");
        }, 500);
    });

    }

}) ();


// 글쓰기 버튼


( () => {
    const insertBtn = document.getElementById("insertBtn");

    if(insertBtn != null) {
        insertBtn.addEventListener("click", () => {
            location.href = "/write/" + boardCode; // Get방식으로 요청
        })
    }


})();