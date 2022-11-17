// 현재 리시트 번호, 검색어 정보를 바탕으로 공지사항 목록을 불러옴
function getNoticeList() {
    const url = "/rest/notice/list";
    let params = `page=${page_no}&keyword=${keyword}`;
  
    fetch(url + "?" + params)
      .then((response) => response.json())
      .then((data) => setNoticeList(data));
  }

  // 불러온 공지사항 목록과 페이지를 표시해줌
function setNoticeList(data) {
    // 공지사항들을 목록으로 표시
    const list_notice = document.querySelector("#notice");
    list_notice.innerHTML = "";
    
    let idx=0;
    
    data.list.forEach((notice) => {
      if(idx>4)return false;

      let date = notice.date.split(" ")[0];
      let notice_div = `
          <div class="row ms-5 pt-4 text-center fs-5 mb-4">
            <a  class="col-4 text-common-dark link-dark text-decoration-none" href="javascript:notice_detail('${notice.no}');">${notice.title}</a>
            <div class="col-2 align-self-center">${notice.author}</div>
            <div class="col-2 align-self-center">${date}</div>
          </div>
          `;
  
      list_notice.innerHTML += notice_div;
      idx++;
    });
}

function init() {
    const nowUrl = new URL(location.href);
    const params = nowUrl.searchParams;
  
    let initKeyword = params.get("keyword");
    let initPageNo = params.get("page");
  
    if (initKeyword) keyword = initKeyword;
    else keyword = "";
  
    if (initPageNo) page_no = initPageNo;
    else page_no = 1;
  
    getNoticeList();
  }
  
  init();
  