// 현재 리스트 번호
let page_no;
// 현재 검색어 정보
let keyword;

// 공지사항 작성 페이지로 이동
function notice_write() {
	location.href = "/notice/notice_write.html";
}

// 공지사항 상세 페이지로 이동
function notice_detail(no) {
	const url = "/notice/notice_detail.html";
	let params = `no=${no}&page=${page_no}&keyword=${keyword}`;

	console.log(url + "?" + params);
	location.href = url + "?" + params;
}

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
	const list_notice = document.querySelector("#list-notice");
	list_notice.innerHTML = "";

	data.list.forEach((notice) => {
		let date = notice.date.split(" ")[0];
		let notice_div = `
        <div class="row ms-5 pt-4 text-dark text-center fs-6" style="width: 92%">
            <a
            class="col-8 text-common-dark link-dark text-decoration-none"
            href="javascript:notice_detail('${notice.no}');"
            >${notice.title}</a
            >
            <div class="col-2 align-self-center">${notice.author}</div>
            <div class="col-2 align-self-center">${date}</div>
        </div>
        `;

		list_notice.innerHTML += notice_div;
	});

	// 페이지네이션 처리
	const list_page = document.querySelector("#list-page");
	list_page.innerHTML = "";
	console.log(data.page);
	data.page.forEach((page) => {
		let page_div;
		if (page == page_no) {
			page_div = `<a class="text-common-dark mx-2 align-self-center link-dark" href="#">${page}</a>`;
		} else {
			page_div = `<a class="text-common-dark mx-2 align-self-center link-dark text-decoration-none" href="javascript:changePage(${page})">
                    ${page}
                  </a>`;
		}
		list_page.innerHTML += page_div;
	});
}

// 페이지 선택 시 현재 페이지를 변경하고 새로운 공지사항 정보를 불러오기
function changePage(target_page) {
	page_no = target_page;
	getNoticeList();
}

// 검색 시 현재 검색어를 변경하고 새로운 검색어 정보 불러오기
function search() {
	let search_keyword = document.querySelector("#keyword").value;

	if (!search_keyword) {
		alert("검색어를 입력해주세요.");
		return;
	}

	keyword = search_keyword;
	getNoticeList();
}

/*
    페이지 초기 설정
    쿼리스트링에 페이지, 검색어 정보가 있다면 그 것을 사용한다
    없다면 검색어는 없고, 페이지는 1
*/
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
