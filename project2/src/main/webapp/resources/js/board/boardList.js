
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


// 검색을 한 경우 검색창에 검색 key, query 남겨놓기
(() => {
    const select = document.getElementById("seacrh-key");
    const input = document.getElementById("search-query");
    const option = document.querySelectorAll("#search-key > option");

    if(select != null) { // 검색창이 존재할 때
        const params = new URL(location.href).searchParams;
        // 주소에서 쿼리스트링만 분리한 객체

        const key = params.get("key");
        const query = params.get("query");

        // input에 이전 검색어를 값으로 추가
        input.value = query;

        // select에서 이전 검색한 key의 값과 일치하는 option 태그에
        // selected 속성 추가
        for(let op of option) {
            // option의 value와 key가 일치할 때
            if(op.value == key) {
                // op.setAttribute("selected", true)
                op.selected = true;
            }
        }
    }

})();