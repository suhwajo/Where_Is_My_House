/*
    관심지역 추가 기능 부분
    모달의 정보를 불러와서 관심지역을 추가한다.
*/

function addFocusArea() {
    // 모달에 입력받은 시도, 구군, 동 정보를 가져오기 위함
    let sidoSel = document.querySelector("#sido");
    let gugunSel = document.querySelector("#gugun");
    let dongSel = document.querySelector("#dong");
    let sido = sidoSel[sidoSel.selectedIndex].text;
    let gugun = gugunSel[gugunSel.selectedIndex].text;
    let dong = dongSel[dongSel.selectedIndex].text;
    let code = dongSel[dongSel.selectedIndex].value;

    // 지역 정보가 선택되지 않았으면 경고창을 띄워줌
    if (code == "") {
        window.alert("지역을 선택해주세요.");
    } else {
        const url = "/rest/area/insert";

        fetch(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                sido: sido,
                gugun: gugun,
                dong: dong,
                code: code,
            }),
        })
            .then((response) => response.json())
            .then((data) => addArea(data));
    }
}

function addArea(area) {
    const aside_list = document.querySelector("#aside-list");

    let area_div = `
    <div class="shadow mb-3 py-3 row" id="container-area-${area.code}">
        <div class="col-9 text-center fs-5">
            <div class="text-common-light" id="asid-list-simpleInfo-${area.code}">
                <button tpye="button" class="btn text-common-light" onclick="click_area('${area.code}', '${area.address}')">${area.address}</button>
            </div>
            <div class="text-common-light" id="asid-list-detailInfo-${area.code}" style="display: none">
                <button tpye="button" class="btn text-common-light" onclick="click_area('${area.code}', '${area.address}')">${area.address}</button>
                <div class="">
                    <button type="button" class="btn text-common-basic link-dark" data-bs-toggle="modal"
                        data-bs-target="#storeModal" onclick="set_area(${area.code})">
                        주변 상권 검색
                    </button>
                </div>
                <div class="">
                    <button type="button" class="btn text-common-basic link-dark" onclick="search_environments(${area.code})">
                        주변 환경 시설 검색
                    </button>
                </div>
                <div class="">
                    <button type="button" class="btn text-common-basic link-dark" onclick="search_hospitals(${area.code})">
                        주변 병원 검색
                    </button>
                </div>
                 <div class="">
                    <button type="button" class="btn text-common-basic link-dark" onclick="search_clinics(${area.code})">
                        주변 코로나 선별 검사소 검색
                    </button>
                </div>
            </div>
        </div>
        <div class="col-2 align-self-center">
            <button type="button" class="btn text-light" onclick="deleteArea('${area.code}')">
                <i class="fa-solid fa-trash"></i>
            </button>
        </div>
    </div>
    `;

    aside_list.innerHTML += area_div;
}

/*
    관심지역 삭제 부분
 */
function deleteArea(code) {
    const url = "/rest/area/delete?code="

    fetch(url + code)
        .then((response) => response.json())
        .then((data) => {
            if (data.check) {
                document.querySelector(`#container-area-${code}`).style.display = 'none';
            }
        })
}

/*
    관심지역 목록에서 하나를 클릭 했을 때
    지도에서 해당 위치로 이동
    해당 관심지역에서 주변 시설 검색을 할 수 있게 표시
*/

let detail_code = -1;

function click_area(code, address) {
    move(address);

    if (detail_code != code) {
        document.querySelector("#asid-list-simpleInfo-" + code).style.display = "none";
        document.querySelector("#asid-list-detailInfo-" + code).style.display = "block";

        if (detail_code != -1) {
            document.querySelector("#asid-list-simpleInfo-" + detail_code).style.display = "block";
            document.querySelector("#asid-list-detailInfo-" + detail_code).style.display = "none";
        }

        detail_code = code;
    } else {
        document.querySelector("#asid-list-simpleInfo-" + code).style.display = "block";
        document.querySelector("#asid-list-detailInfo-" + code).style.display = "none";

        detail_code = -1;
    }
}

/*
    초기 설정
    해당 유저의 관심지역 목록을 불러온다.
*/

function init() {
    const url = "/rest/area/list";

    fetch(url)
        .then((response) => response.json())
        .then((data) => {
            data.forEach((area) => {
                addArea(area);
            });
        });
}

init();
