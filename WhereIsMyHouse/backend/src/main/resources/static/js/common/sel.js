function getSido() {

    const url = "/rest/home/getSido"

    fetch(url)
        .then((response) => response.json())
        .then((data) => {
            let sel = document.querySelector("#sido");
            let opt = `<option value="">시도선택</option>`;

            data.forEach((sido) => {
                opt += `<option value="sido">${sido}</option>`;
            });
            sel.innerHTML = opt;
        })
}

function getGugun() {
    const url = "/rest/home/getGugun?sido="

    let sidoSel = document.querySelector("#sido");
    let sido = sidoSel[sidoSel.selectedIndex].innerText;

    fetch(url + sido)
        .then((response) => response.json())
        .then((data) => {
            let sel = document.querySelector("#gugun");
            let opt = `<option value="">구군선택</option>`;
            data.forEach((gugun) => {
                opt += `<option value="gugun">${gugun}</option>`;
            });
            sel.innerHTML = opt;
        })
}

function getDong() {
    const url = "/rest/home/getDong?"
    let gugunSel = document.querySelector("#gugun");
    let sidoSel = document.querySelector("#sido");
    let sido = sidoSel[sidoSel.selectedIndex].innerText;
    let gugun = gugunSel[gugunSel.selectedIndex].innerText;
    
    let param ="sido="+sido+"&gugun="+gugun;
    fetch(url + param)
        .then((response) => response.json())
        .then((data) => {
            let sel = document.querySelector("#dong");
            sel.innerHTML="";
            let opt = `<option value="">동선택</option>`;

            data.forEach((dong) => {
                opt += `<option value="${dong.dongCode}">${dong.dongName}</option>`;
            });
            sel.innerHTML = opt;
        })
}

// 시도가 바뀌면 구군정보 얻기.
document.querySelector("#sido").addEventListener("change", function () {
    if (this[this.selectedIndex].value) {
        getGugun();
        document.querySelector("#dong").innerHTML = '';
    }
});

// 구군이 바뀌면 동정보 얻기.
document.querySelector("#gugun").addEventListener("change", function () {
    if (this[this.selectedIndex].value) {
        getDong();
    }
});

getSido();