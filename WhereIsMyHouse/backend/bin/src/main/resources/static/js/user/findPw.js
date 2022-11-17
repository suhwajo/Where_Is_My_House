/*
    입력받은 데이터를 검사
    입력받은 데이터를 통해 비밀번호를 가져와서 보여준다.
*/

function findPw() {
  let name = document.querySelector("#name").value;
  let id = document.querySelector("#id").value;
  let phoneNum = document.querySelector("#phone").value;

  if (!id || !name || !phone) {
    alert("모든 정보를 입력해 주세요.");
    return;
  }

  const url = getContextPath + "/rest/user/findPw";

  fetch(url, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      id: id,
      name: name,
      phone: phoneNum,
    }),
  })
    .then((response) => response.json())
    .then((data) => {
      if (data.pwd) {
        alert(`찾은 비밀번호는 ${data.pwd} 입니다. 로그인 화면으로 이동합니다.`);
        location.href = getContextPath + "/user/login";
      } else {
        alert("등록된 회원이 존재하지 않습니다.");
      }
    });
}
