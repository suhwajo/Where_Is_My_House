// 지도에 주변 정보 마커 추가
function setMarker(address, infoWindowDiv) {
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

            marker.setMap(map);
            markers.push(marker);

            // 등록할 인포 윈도우 정보가 있으면 표시한다.
            if(infoWindowDiv) {
                let infoWindow = new kakao.maps.InfoWindow({
                    content: infoWindowDiv,
                    removable: true,
                });
                // 마커에 클릭이벤트를 등록합니다
                kakao.maps.event.addListener(marker, "click", function () {
                    // 마커 위에 인포윈도우를 표시합니다
                    infoWindow.open(map, marker);
                });
            }
        }
    });
}

// 주변 환경을 보여줄 현재 선택된 지역
let select_area;

// 지역 선택
function set_area(code) {
    select_area = code;
}

// 상권 정보 불러오기
function search_stores() {

    const url = "/rest/area/stores";

    let smallSel = document.querySelector("#small");
    let small = smallSel[smallSel.selectedIndex].value;

    let params = `?smallCode=${small}&dongCode=${select_area}`;

    fetch(url + params)
        .then((response) => response.json())
        .then((data) => set_stores(data))
}

function set_stores(data) {

    initMarkers();
    console.log(data);

    data.forEach((store) => {
        let infoWindowDiv = `
            <div class="bg-common-dark text-common-light text-center">
                <div class="fs-4">${store.storeName}</div>
                <div class="fs-5 mt-4">${store.address}</div>
            </div>
        `

        setMarker(store.address, infoWindowDiv);
    })
}

function search_environments(code) {
    const url = "/rest/area/environments";

    let params = `?code=${code}`;

    fetch(url + params)
        .then((response) => response.json())
        .then((data) => set_environments(data))
}

function set_environments(data) {
    initMarkers();
    console.log(data);

    data.forEach((environment) => {
        let infoWindowDiv = `
            <div class="bg-common-dark text-common-light text-center">
                <div class="fs-4">${environment.name}</div>
                <div class="fs-4 mt-3">${environment.industryName}</div>
                <div class="fs-4 mt-4">${environment.checkList}</div>
                <div class="fs-5 mt-5">${environment.address}</div>
            </div>
        `

        setMarker(environment.address, infoWindowDiv);
    })
}

function search_hospitals(code) {
    const url = "/rest/area/hospitals";

    let params = `?code=${code}`;

    fetch(url + params)
        .then((response) => response.json())
        .then((data) => set_hospitals(data))
}

function set_hospitals(data) {
    initMarkers();
    console.log(data);

    data.forEach((hospital) => {
        let infoWindowDiv = `
            <div class="bg-common-dark text-common-light text-center">
                <div class="fs-4">${hospital.hospital_name}</div>
                <div class="fs-5 mt-5">${hospital.address}</div>
                <div class="fs-5 mt-5">${hospital.phone_number}</div>
            </div>
        `

        setMarker(hospital.address, infoWindowDiv);
    })
}

function search_clinics(code) {
    const url = "/rest/area/clinics";

    let params = `?code=${code}`;

    fetch(url + params)
        .then((response) => response.json())
        .then((data) => set_clinics(data))
}

function set_clinics(data) {
    initMarkers();
    console.log(data);

    data.forEach((clinic) => {
        let infoWindowDiv = `
            <div class="bg-common-dark text-common-light text-center">
                <div class="fs-4">${clinic.clinic_name}</div>
                <div class="fs-5 mt-5">${clinic.address}</div>
                <div class="fs-5 mt-5">${clinic.main_phone_number}</div>
            </div>
        `

        setMarker(clinic.address, infoWindowDiv);
    })
}