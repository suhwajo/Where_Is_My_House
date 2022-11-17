function notice_write() {
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
    const url = "/rest/notice/write";

    fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        title: title,
        content: content,
      }),
    })
      .then((response) => response.json())
      .then((data) => {
        if (data.check) {
          location.href = "/notice/notice_list.html";
        } else {
          alert("게시글 작성에 실패 했습니다. 다시 시도해주세요.");
        }
      });
  }
}

function notice_cancel() {
  let confirm_cancel = confirm("정말 취소하시겠습니까?");

  if (confirm_cancel) location.href = "/notice/notice_list.html";
}
