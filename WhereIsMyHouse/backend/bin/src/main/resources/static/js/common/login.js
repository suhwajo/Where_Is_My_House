window.onload = function () {
    loginCheck();
};

function loginCheck() {
    console.log("loginCheck");
    const url = "/rest/user/isLogin";
    fetch(url)
        .then((response) => response.json())
        .then((data) => loginHandling(data))
        .catch(loginHandling(null));
}

function loginHandling(data) {
	
	console.log(data);
	
    if (data) {
        let userMenus = document.querySelectorAll(".user-menu");

        userMenus.forEach((item) => {
            item.style.display = "";
        });

        document.querySelector("#login").style.display = "none";
        if(data.adminAccount) {
            document.querySelector("#nav-allUsers").style.display = "";
        }
        else {
            document.querySelector("#nav-allUsers").style.display = "none";
        }
    } else {
        let userMenus = document.querySelectorAll(".user-menu");

        userMenus.forEach((item) => {
            item.style.display = "none";
        });

        document.querySelector("#login").style.display = "";
    }
}

function logout() {
    const url = "/rest/user/logout";

    fetch(url)
        .then((response) => response.json())
        .then((data) => {
            if (data.check) {
                console.log("logout합니다 ");
                console.log(data);
                loginCheck();
            } else {
                console.log("로그아웃에 실패했습니다. 다시 시도해주세요.")
            }
        })
}
