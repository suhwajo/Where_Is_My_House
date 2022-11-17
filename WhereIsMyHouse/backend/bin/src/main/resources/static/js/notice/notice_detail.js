// 목록으로 돌아갔을 때 검색어와 페이지를 유지해주기 위함
let page_no;
let keyword;

// 현재 공지사항의 번호
let no;

// 공지사항 목록 페이지로 이동
function notice_list() {
    const url = "/notice/notice_list.html";
    let params = `page=${page_no}&keyword=${keyword}`;

    location.href = url + "?" + params;
}

// 공지사항 수정 페이지로 이동
function notice_modify() {
    const url = "/notice/notice_modify.html";
    let params = `no=${no}`;

    location.href = url + "?" + params;
}

// 공지사항 삭제 기능
function notice_delete() {
    let confirm_delete = confirm("정말 삭제하시겠습니까?");

    if (confirm_delete) {
        const url = "/rest/notice/delete";

        fetch(url, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                no: no,
            }),
        })
            .then((response) => response.json())
            .then((data) => {
                if (data.check) {
                    alert("삭제되었습니다.");
                    const url = "/notice/notice_list.html";
                    let params = `page=${page_no}&keyword=${keyword}`;

                    location.href = url + "?" + params;
                } else {
                    alert("삭제가 실패 했습니다. 다시 시도해주세요.");
                }
            });
    }
}

/*
    쿼리스트링에 no에 해당하는 공지사항 정보를 불러옴
    불러온 정보를 바탕으로 내용을 표시해준다.
    또한, 공지사항의 작성자가 현재 로그인한 사용자라면 수정, 삭제 버튼을 보이게 한다.
*/
function init() {
    const nowUrl = new URL(location.href);
    const params = nowUrl.searchParams;

    no = params.get("no");
    page_no = params.get("page");
    keyword = params.get("keyword");

    const url = "/rest/notice/detail?no=" + no;

    fetch(url)
        .then((response) => response.json())
        .then((data) => {
            let notice = data.notice;

            document.querySelector("#notice-title").innerText = notice.title;
            document.querySelector("#notice-author").innerText = notice.author;
            document.querySelector("#notice-date").innerText = notice.date;
            document.querySelector("#notice-content").innerText = notice.content;

            if (data.checkUser) {
                document.querySelector("#list-user-btn").style.display = 'block';
            }
        });
}

init();
