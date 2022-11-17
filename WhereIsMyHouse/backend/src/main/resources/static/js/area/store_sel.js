function getBig() {

    const url = "/rest/area/getBig"

    fetch(url)
        .then((response) => response.json())
        .then((data) => {
            let sel = document.querySelector("#big");
            let opt = `<option value="">큰 카테고리 선택</option>`;
            data.forEach((big) => {
                opt += `<option value="big">${big}</option>`;
            });
            sel.innerHTML = opt;
        })
}

function getMiddle() {
    const url = "/rest/area/getMiddle?big="

    let bigSel = document.querySelector("#big");
    let big = bigSel[bigSel.selectedIndex].innerText;

    fetch(url + big)
        .then((response) => response.json())
        .then((data) => {
            let sel = document.querySelector("#middle");
            let opt = `<option value="">중간 카테고리 선택</option>`;
            data.forEach((middle) => {
                opt += `<option value="middle">${middle}</option>`;
            });
            sel.innerHTML = opt;
        })
}

function getSmall() {
    const url = "/rest/area/getSmall?middle="

    let middleSel = document.querySelector("#middle");
    let middle = middleSel[middleSel.selectedIndex].innerText;

    fetch(url + middle)
        .then((response) => response.json())
        .then((data) => {
            let sel = document.querySelector("#small");
            let opt = `<option value="">작은 카테고리 선택</option>`;

            data.forEach((small) => {
                opt += `<option value="${small.code}">${small.name}</option>`;
            });
            sel.innerHTML = opt;
        })
}

// 시도가 바뀌면 구군정보 얻기.
document.querySelector("#big").addEventListener("change", function () {
    if (this[this.selectedIndex].value) {
        getMiddle();
        document.querySelector("#small").innerHTML = '';
    }
});

// 구군이 바뀌면 동정보 얻기.
document.querySelector("#middle").addEventListener("change", function () {
    if (this[this.selectedIndex].value) {
        getSmall();
    }
});

getBig();