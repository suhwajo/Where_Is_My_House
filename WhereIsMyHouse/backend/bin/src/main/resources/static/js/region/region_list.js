/*
    가져올 list 기본설정을 가지는 변수들
 */
// 1: 시도 2: 시군구
let regionType = 1;
// 0: x 1: 아파트 개수 2: 매매 횟수 3: 총 매매 금액 4: 평균 매매 금액
let sortType = 0;
// true : 오름차순 false : 내림차순
let sortMethod = 'ASC';
let keyword = '';
let page = 1;

function get_region_list() {

    const url = "/rest/region/list";
    let params = `regionType=${regionType}&sortType=${sortType}&sortMethod=${sortMethod}&keyword=${keyword}&page=${page}`;

    console.log(params);

    fetch(url + "?" + params)
        .then((response) => response.json())
        .then((data) => set_region_list(data));
}

function set_region_list(data) {
    const list_region = document.querySelector("#list-region");

    list_region.innerHTML = '';

    if(data.list) {
        data.list.forEach((region) => {

            let homeCount = region.homeCount.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
            let dealCount = region.dealCount.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
            let amountSum = region.amountSum.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
            let amountAvg = region.amountAvg.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');

            let region_div = `
            <div class="row ms-5 mt-3 pb-3 text-center fs-6" style="width: 95%">
                <div class="col-3"><a class="text-common-dark text-decoration-none link-dark" href="javascript:region_detail(${region})">${region.region_name}</a>
                </div>
                <div class="col-2 text-common-dark">${homeCount}</div>
                <div class="col-2 text-common-dark">${dealCount}</div>
                <div class="col-2 text-common-dark">${amountAvg}(만원)</div>
                <div class="col-2 text-common-dark">${amountSum}(만원)</div>
            </div>
            `

            list_region.innerHTML += region_div;
        })
    }
}

function change_sort(type) {
    if(type == sortType) {
        if (sortMethod == 'ASC') {
            sortMethod = 'DESC';
            document.querySelector("#caret"+type).setAttribute("class", "fa-solid fa-caret-up text-common-dark text-decoration-none link-dark");
        }
        else {
            sortMethod = 'ASC';
            document.querySelector("#caret"+type).setAttribute("class", "fa-solid fa-caret-down text-common-dark text-decoration-none link-dark")
        }
    }
    else {
        sortType = type;
        sortMethod = 'ASC';
        init_caret();
        document.querySelector("#caret"+type).setAttribute("class", "fa-solid fa-caret-down text-common-dark text-decoration-none link-dark")
    }

    get_region_list();
}

function region_search() {
    keyword = document.querySelector("#keyword").value;

    get_region_list();
}

function change_regionType(type) {
    if(type != regionType) {
        regionType = type;
        keyword = '';
        get_region_list();
    }
}

function init_caret() {
    for(let i = 1; i <= 4; i++) {
        document.querySelector("#caret"+i).setAttribute("class", "fa-solid fa-caret-up text-common-dark text-decoration-none link-dark");
    }
}


function init() {
    get_region_list();
}

init();