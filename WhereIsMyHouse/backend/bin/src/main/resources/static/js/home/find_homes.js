/*
    리스트에서 아파트 클릭 시
    지도의 중심 좌표를 아파트로 이동시키고
    클릭한 아파트의 디테일 정보를 표시한다.
    detail_no : 현재 상세정보를 표시중인 아파트의 번호, 없을 경우 -1
*/

let detail_no = -1;

function click_home(address, no, name) {
    move(address);

    change_detail(no);

    document.querySelector("#map-homeName").innerText = name;
}

function click_deal(no) {
    change_detail(no);
}

function change_detail(no) {
    if (detail_no != no) {
        document.querySelector("#asid-list-simpleInfo-" + no).style.display = "none";
        document.querySelector("#asid-list-detailInfo-" + no).style.display = "block";

        if (detail_no != -1) {
            document.querySelector("#asid-list-simpleInfo-" + detail_no).style.display = "block";
            document.querySelector("#asid-list-detailInfo-" + detail_no).style.display = "none";
        }

        detail_no = no;
    } else {
        document.querySelector("#asid-list-simpleInfo-" + no).style.display = "block";
        document.querySelector("#asid-list-detailInfo-" + no).style.display = "none";

        detail_no = -1;
    }
}

/*
    검색한 결과에 대한 모든 마커를 표시
    markers : 현재 표시된 모든 마커들을 저장하고 있는 배열
*/

// 특정 아파트에 대한 마커를 설정하고 배열에 추가
function setHomeMarker(address, aptName, aptCode, dongCode) {
    let geocoder = new kakao.maps.services.Geocoder();
    let marker;

    geocoder.addressSearch(address, function (result, status) {
        // 정상적으로 검색이 완료됐으면
        if (status === kakao.maps.services.Status.OK) {
            let coords = new kakao.maps.LatLng(result[0].y, result[0].x);

            // 결과값으로 받은 위치를 마커로 표시합니다
            marker = new kakao.maps.Marker({
                map: map,
                position: coords,
            });

            if (dongCode) {
                kakao.maps.event.addListener(
                    marker,
                    "click",
                    marker_click(aptCode, aptName, dongCode, address)
                );
            }

            marker.setMap(map);
            markers.push(marker);
        }
    });
}

function marker_click(aptCode, aptName, dongCode, address) {
    return function () {
        getDeals(aptCode, aptName, dongCode, address);
    };
}

/*
    모달에 입력받은 위치의 homeInfo들을 가져와서 
    목록에 표시하고
    지도에 마커 표시
*/

function getHomes() {
    // 모달에 입력받은 시도, 구군, 동 정보를 가져오기 위함
    let sidoSel = document.querySelector("#sido");
    let gugunSel = document.querySelector("#gugun");
    let dongSel = document.querySelector("#dong");
    let code = dongSel[dongSel.selectedIndex].value;

    // 지역 정보가 선택되지 않았으면 경고창을 띄워줌
    if (code == "") {
        window.alert("지역을 선택해주세요.");
    } else {
        let sido = sidoSel[sidoSel.selectedIndex].text;
        let gugun = gugunSel[gugunSel.selectedIndex].text;
        let dong = dongSel[dongSel.selectedIndex].text;
        let address = sido + " " + gugun + " " + dong;

        // 보여줄 주소 설정
        document.querySelector("#aside-list-info").innerHTML = address;
        // 지도 중앙 해당 위치로 이동
        move(address);

        initMarkers();
        detail_no = -1;

        const url = "/rest/home/getInfos?code=" + code;

        fetch(url)
            .then((response) => response.json())
            .then((data) => setHomes(data));
    }
}

// 백엔드에서 받은 homeInfo들을 이용해 리스트와 맵에 정보, 마커 표시
function setHomes(data) {
    const aside_list = document.querySelector("#aside-list");
    aside_list.innerHTML = "";

    data.forEach((home) => {
        let home_div = `
        <div class="shadow mb-3 py-3" onclick="click_home('${home.address}', ${home.aptCode}, '${home.apartmentName}')">
            <div class="text-center text-common-light" id="asid-list-simpleInfo-${home.aptCode}">
                <div>${home.apartmentName}</div>
                <div> ${home.address}</div>
            </div>
            <div class="text-center text-common-light" id="asid-list-detailInfo-${home.aptCode}" style="display: none;">
                <div>${home.apartmentName}</div>
                <div>${home.address}</div>
                <div>건설년도 : ${home.buildYear}</div>
                <a class="text-common-light text-decoration-none link-dark" href="javascript:getDeals('${home.aptCode}', '${home.apartmentName}', '${home.dongCode}', '${home.address}')">
                거래 내역 보기
                </a>
            </div>
      </div>`;

        aside_list.innerHTML += home_div;

        setHomeMarker(home.address, home.apartmentName, home.aptCode, home.dongCode);
    });

    document.querySelector("#deal-back-btn").style.display = "none";
}

/*
    선택한 info에 대한 deal을 가져옴
    목록에 deal 들을 뿌려줌
*/

function getDeals(aptCode, aptName, dongCode, address) {
    const url = "/rest/home/getDeals?code=" + aptCode;

    initMarkers();
    detail_no = -1;

    setHomeMarker(address, "", "", "");

    // 보여줄 아파트 이름 설정
    document.querySelector("#aside-list-info").innerText = aptName;

    fetch(url)
        .then((response) => response.json())
        .then((data) => setDeals(data, dongCode));
}

function setDeals(data) {
    const aside_list = document.querySelector("#aside-list");
    aside_list.innerHTML = "";

    data.forEach((deal) => {
        let deal_div = `
        <div class="shadow mb-3 py-3" onclick="click_deal(${deal.no})">
            <div class="text-center text-common-light" id="asid-list-simpleInfo-${deal.no}">
                <div>${deal.tradeType}</div>
                <div>${deal.dealAmount}</div>
                <div>${deal.dealYear}년 ${deal.dealMonth}월 ${deal.dealDay}일</div>
            </div>
            <div class="text-center text-common-light" id="asid-list-detailInfo-${deal.no}" style="display: none;">
                <div>${deal.tradeType}</div>
                <div>${deal.dealAmount}</div>
                <div>${deal.dealYear}년 ${deal.dealMonth}월 ${deal.dealDay}일</div>
                <div>면적 : ${deal.area}</div>
                <div>${deal.floor} 층</div>
            </div>
      </div>`;

        aside_list.innerHTML += deal_div;
    });

    document.querySelector("#deal-back-btn").style.display = "block";
}
