/* 
    로그인 버튼을 누르면 id와 pwd를 가져와서
    컨트롤러로 보내준다.
    받은 내용에 유저 객체가 있다면 로그인 성공
    없다면 로그인에 실패한다.
    이 때 아이디저장 여부도 같이 보낸다.
*/

function login() {
  let userId = document.querySelector("#id").value;
  let userPwd = document.querySelector("#pwd").value;
  let saveId = document.querySelector("#saveId").checked;

  if (!userId) {
    window.alert("아이디를 입력하세요.");
    return;
  }

  if (!userPwd) {
    window.alert("비밀번호를 입력하세요. ");
    return;
  }

  const url = "/rest/user/login";

  fetch(url, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      userId: userId,
      userPwd: userPwd,
      saveId: saveId,
    }),
  })
    .then((response) => response.json())
    .then((data) => {
      if (data.userId) {
        location.href = "/index.html";
      } else {
        alert("아이디 혹은 패스워드가 올바르지 않습니다.");
      }
    });
}

/*
    회원가입 버튼 클릭 시 이동 처리
    비밀번호 찾기 클릭 시 이동 처리
*/

document.querySelector("#btn-mv-join").addEventListener("click", function () {
  location.href = "/user/join.html";
});

document.querySelector("#btn-mv-findPw").addEventListener("click", function () {
  location.href = "/user/findPw.html";
});

/* 
    초기 설정
    저장된 아이디가 있다면 불러옴
*/

function init() {
  const url = "/rest/user/saveId";

  fetch(url)
    .then((response) => response.json())
    .then((data) => {
    	console.log(data.saveId);
    	if(data.saveId) {
    		document.querySelector("#id").value = data.saveId;
    	}
    });
}


init();
