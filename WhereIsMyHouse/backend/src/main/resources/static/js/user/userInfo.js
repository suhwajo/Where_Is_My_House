// 현재 보고있는 정보의 유저 아이디
let now_userId;

/*
    정보 수정을 누르면 인풋들이 나오도록 
    정보 수정에서 취소를 누르면 다시 정보들이 나오도록
*/
function editOpen() {
    document.querySelectorAll(".info-show").forEach((info) => {
        info.style.display = "none";
    });
    document.querySelectorAll(".info-input").forEach((info) => {
        info.style.display = "block";
    });
}

function editCancel() {
    document.querySelectorAll(".info-show").forEach((info) => {
        info.style.display = "block";
    });
    document.querySelectorAll(".info-input").forEach((info) => {
        info.style.display = "none";
    });
}

/*
    회원 수정 기능과 회원 탈퇴 기능
*/

function editConfirm() {
    let pwd = document.querySelector("#input-pwd").value;
    let name = document.querySelector("#input-name").value;
    let email = document.querySelector("#input-email").value;
    let phone = document.querySelector("#input-phone").value;

    if (!pwd || !name || !email || !phone) {
        alert("모든 정보를 입력해 주세요.");
        return;
    }

    const url = "/rest/user/update";

    fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            id: now_userId,
            pwd: pwd,
            name: name,
            email: email,
            phone: phone,
        }),
    })
        .then((response) => response.json())
        .then((data) => {
            if (data.check) {
                alert("회원 정보 변경에 성공했습니다.");
                location.reload();
            } else {
                alert("회원 정보 변경에 실패 했습니다. 다시 시도해주세요.");
            }
        });
}

function userOut() {
    let outConfirm = confirm("정말 탈퇴하시겠습니까??");

    if (outConfirm) {
        const url = "/rest/user/delete/";

        fetch(url+now_userId,{
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
            }
        })
            .then((response) => response.json())
            .then((data) => {
                if (data.check) {
                    logout();
                    alert("탈퇴성공!!!!!!!!!!!!");
                    location.href = "/";
                }
                else
                    alert("탈퇴에 실패했습니다. 다시 시도해주세요.");
            });
    }
}

/*
    초기 설정
    url 에 쿼리스트링으로 userId를 받아오고
    해당 userId를 이용해 user 객체를 받아와서 정보를 뿌린다.
*/

function init() {
    const nowUrl = new URL(location.href);
    const params = nowUrl.searchParams;

    let userId = params.get("userId");

    if (!userId) {
        const url = "/rest/user/isLogin";

        fetch(url)
            .then((response) => response.json())
            .then((data) => {
                console.log(data);
                if (data.id)
                    setInfo(data.id);
            });
    } else {
        setInfo(userId);
    }
}

function setInfo(userId) {
    now_userId = userId;

    const url = "/rest/user/getUser/";

    fetch(url + userId)
        .then((response) => response.json())
        .then((user) => {

            document.querySelector("#show-id").innerText = user.id;
            document.querySelector("#show-pwd").innerText = user.pwd;
            document.querySelector("#show-name").innerText = user.name;
            document.querySelector("#show-email").innerText = user.email;
            document.querySelector("#show-phone").innerText = user.phoneNum;

            document.querySelector("#input-pwd").value = user.pwd;
            document.querySelector("#input-name").value = user.name;
            document.querySelector("#input-email").value = user.email;
            document.querySelector("#input-phone").value = user.phoneNum;
        });
}

init();
