// 현재 리스트 번호
let page_no;
// 현재 검색어 정보
let keyword;

// 사용자 상세 정보 페이지로 이동
function infoUser(id) {
    const url = "/user/userInfo.html";
    let params = `userId=${id}`;

    console.log(url + "?" + params);
    location.href = url + "?" + params;
}

// 사용자 삭제
function deleteUser(id, isAdmin) {

    if (isAdmin) {
        alert("관리자는 임의로 탈퇴시킬 수 없습니다.");
        return;
    }

    let chk = confirm("정말 탈퇴시키겠습니까?");

    if (!chk)
        return;

    const url = `/rest/user/delete/${id}`;

    fetch(url,{
        method: "DELETE",
        headers: {
            "Content-Type": "application/json",
        }
        })
        .then((response) => response.json())
        .then((data) => {
            if (data.check) {
                getUserList();
            }
            else {
                alert("삭제에 실패했습니다. 다시 시도해주세요.");
            }
        })
}

// 현재 리시트 번호, 검색어 정보를 바탕으로 공지사항 목록을 불러옴
function getUserList() {
    const url = `/rest/user/list`;

    fetch(url)
        .then((response) => response.json())
        .then((data) => setUserList(data));
}

// 불러온 공지사항 목록과 페이지를 표시해줌
function setUserList(data) {
    // 공지사항들을 목록으로 표시
    const list_user = document.querySelector("#list-user");
    list_user.innerHTML = "";

    console.log(data);

    data.list.forEach((user) => {
        let isAdmin = 'X';
        if (user.adminAccount)
            isAdmin = 'O';

        let user_div = `
        <div class="row ms-5 pt-4 text-dark text-center fs-6" style="width: 92%">
            <div class="col-2" id="userid">
                <a class="text-decoration-none text-common-dark link-dark"
                   href="javascript:infoUser('${user.id}')">${user.id}</a>
            </div>
            <div class="col-2 text-common-dark">${user.name}</div>
            <div class="col-2 text-common-dark">${user.email}</div>
            <div class="col-2 text-common-dark">${user.phoneNum}</div>
            <div class="col-2 text-common-dark">${isAdmin}</div>
            <div class="col-2 text-common-dark">
                <a class="col-8 text-decoration-none text-common-dark link-danger"
                   href="javascript:deleteUser('${user.id}', ${user.adminAccount})">
                    삭제</a>
            </div>
        </div>
        `;

        list_user.innerHTML += user_div;
    });

    // 페이지네이션 처리
    // const list_page = document.querySelector("#list-page");
    // list_page.innerHTML = "";

    // data.pages.forEach((page) => {
    //     let page_div;
    //     if (page == page_no) {
    //         page_div = `<a class="text-common-dark mx-2 align-self-center link-dark" href="#">${page}</a>`;
    //     } else {
    //         page_div = `<a class="text-common-dark mx-2 align-self-center link-dark text-decoration-none" href="javascript:changePage(${page})">
    //                 ${page}
    //               </a>`;
    //     }
    //     list_page.innerHTML += page_div;
    // });
}

// 페이지 선택 시 현재 페이지를 변경하고 새로운 공지사항 정보를 불러오기
function changePage(target_page) {
    page_no = target_page;
    getUserList();
}

// 검색 시 현재 검색어를 변경하고 새로운 검색어 정보 불러오기
function search() {
    let search_keyword = document.querySelector("#keyword").value;

    if (!search_keyword) {
        alert("검색어를 입력해주세요.");
        return;
    }

    keyword = search_keyword;
    getUserList();
}

function add_user(){
    location.href="/user/join.html";
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

    getUserList();
}

init();