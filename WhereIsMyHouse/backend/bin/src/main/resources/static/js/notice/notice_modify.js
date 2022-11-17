// 수정할 게시글의 번호
let no;

// 게시글 수정
function notice_modify() {
  let title = document.querySelector("#title").value;
  let content = document.querySelector("#content").value;

  if (!title) {
    alert("제목을 입력하세요.");
    return;
  }
  if (!content) {
    alert("내용을 입력하세요.");
    return;
  }

  let confirm_write = confirm("공지사항을 작성하시겠습니까?");

  if (confirm_write) {
    const url = "/rest/notice/modify";

    fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        no: no,
        title: title,
        content: content,
      }),
    })
      .then((response) => response.json())
      .then((data) => {
        if (data.check) {
          location.href = "/notice/notice_detail.html?no=" + no;
        } else {
          alert("게시글 수정에 실패 했습니다. 다시 시도해주세요.");
        }
      });
  }
}

// 수정 취소
function notice_cancel() {
  let confirm_cancel = confirm("정말 취소하시겠습니까?");

  if (confirm_cancel) location.href = "/notice/notice_detail.html?no=" + no;
}

function init() {
  const nowUrl = new URL(location.href);
  const params = nowUrl.searchParams;

  no = params.get("no");

  const url = "/rest/notice/detail?no=" + no;

  fetch(url)
    .then((response) => response.json())
    .then((data) => {
      let notice = data.notice;

      document.querySelector("#title").value = notice.title;
      document.querySelector("#content").value = notice.content;
    });
}

init();
