/*
    아이디 확인 부분
    아이디의 제한 범위 (5 이상 16 이하) 인지 확인
    사용할 수 있는 아이디인지 확인
    확인한 내용을 표시해준다.
*/

let isUseId = false;
document.querySelector("#id").addEventListener("keyup", function () {
  let userId = this.value;
  let resultDiv = document.querySelector("#idcheck-result");

  if (userId.length < 5 || userId.length > 16) {
    resultDiv.setAttribute("class", "mb-3 text-danger");
    resultDiv.textContent = "아이디는 5자 이상 16자 이하 입니다.";
    resultDiv.setAttribute("style", "");
    isUseId = false;
  } else {
    const url = "/rest/user/idCheck?userId=";
    fetch(url + userId)
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        if (data.cnt == 0) {
          resultDiv.setAttribute("class", "mb-3 text-common-light");
          resultDiv.textContent = userId + "는 사용할 수 있습니다.";
          isUseId = true;
        } else {
          resultDiv.setAttribute("class", "mb-3 text-danger");
          resultDiv.textContent = userId + "는 사용할 수 없습니다.";
          isUseId = false;
        }
      });
  }
});

/*
    회원가입 기능
    모든 내용을 작성했는지 확인하고 회원가입을 진행한다.
*/

function join() {
  let id = document.querySelector("#id").value;
  let pwd = document.querySelector("#pwd").value;
  let pwd_check = document.querySelector("#pwd_check").value;
  let name = document.querySelector("#name").value;
  let email = document.querySelector("#email").value;
  let phoneNum = document.querySelector("#phone").value;

  if (!id || !pwd || !name || !email || !phone) {
    alert("모든 정보를 입력해 주세요.");
    return;
  }

  if (pwd != pwd_check) {
    alert("비밀번호와 비밀번호 체크의 내용이 다릅니다.");
    return;
  }

  if (!isUseId) {
    alert("사용할 수 없는 아이디입니다.");
    return;
  }

  const url = "/rest/user/join";

  fetch(url, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      id: id,
      pwd: pwd,
      name: name,
      email: email,
      phone: phoneNum,
    }),
  })
    .then((response) => response.json())
    .then((data) => {
      if (data.check) {
        alert("회원가입에 성공했습니다  이동합니다.");
        location.href = "/user/login_page.html";
      } else {
        alert("회원가입에 실패 했습니다. 다시 시도해주세요.");
      }
    });
}
