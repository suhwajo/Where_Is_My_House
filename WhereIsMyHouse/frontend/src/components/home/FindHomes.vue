<template>
  <main id="main" class="main">
    <div class="pagetitle">
      <h1>아파트</h1>
      <nav>
        <ol class="breadcrumb">
          <li class="breadcrumb-item"><a href="index.html">Home</a></li>
          <li class="breadcrumb-item active">아파트</li>
        </ol>
      </nav>

      <section class="secion dashboard">
        <div class="row">
          <!-- 시도 선택 Card -->
          <div class="col-xxl-2 col-md-6">
            <div class="card info-card sales-card">
              <div class="card-body">
                <h5 class="card-title">시도 <span>| Sido</span></h5>
                <div class="d-flex align-items-center">
                  <select
                    class="form-select bg-secondary text-light"
                    id="sido"
                    v-model="selectedSido"
                  >
                    <option value="">시도선택</option>
                    <option
                      v-for="(sido, index) in sidos"
                      :key="index"
                      :value="sido"
                    >
                      {{ sido }}
                    </option>
                  </select>
                </div>
              </div>
            </div>
          </div>
          <!-- End Sales Card -->
          <!-- 시도 선택 Card -->
          <div class="col-xxl-2 col-md-6">
            <div class="card info-card sales-card">
              <div class="card-body">
                <h5 class="card-title">구군 <span>| Gugun</span></h5>

                <div class="d-flex align-items-center">
                  <select
                    class="form-select bg-secondary text-light"
                    id="gugun"
                    v-model="selectedGugun"
                  >
                    <option value="">구군선택</option>
                    <option
                      v-for="(gugun, index) in guguns"
                      :key="index"
                      :value="gugun"
                    >
                      {{ gugun }}
                    </option>
                  </select>
                </div>
              </div>
            </div>
          </div>
          <!-- End Sales Card -->

          <!-- 동 선택 Card -->
          <div class="col-xxl-2 col-md-6">
            <div class="card info-card sales-card">
              <div class="card-body">
                <h5 class="card-title">동 <span>| Dong</span></h5>

                <div class="d-flex align-items-center">
                  <select
                    class="form-select bg-secondary text-light"
                    id="dong"
                    v-model="selectedDong"
                  >
                    <option value="">동선택</option>
                    <option
                      v-for="(dong, index) in dongs"
                      :key="index"
                      :value="{ code: dong.dongCode, name: dong.dongName }"
                    >
                      {{ dong.dongName }}
                    </option>
                  </select>
                </div>
              </div>
            </div>
          </div>
          <div class="col-xxl-2 col-md-6">
            <div class="card sales-card mt-5">
              <button
                type="button"
                id="list-btn"
                class="btn btn-primary"
                @click="getHomes()"
              >
                매매정보검색
              </button>
            </div>
          </div>
        </div>
      </section>
      <div class="row">
        <div class="col-8">
          <div class="card">
            <div class="card-body">
              <h5 class="card-title">
                <b>아파트 실거래가 조회 </b>
                <span v-if="address != ''">| {{ address }}</span>
              </h5>

              <!-- Kakao Map start -->
              <div id="map" style="width: 100%; height: 600px"></div>
              <!-- Kakao Map end -->
            </div>
          </div>
        </div>
        <div class="col-4">
          <div class="card">
            <div class="card-body">
              <h5 class="card-title">
                <b>검색 결과</b>
              </h5>

              <div class="list-group" style="height: 600px">
                <div id="aside-list" class="" v-if="homes.length > 0">
                  <div
                    class="list-group-item list-group-item-action"
                    v-for="(home, index) in homes"
                    :key="index"
                    @click="getDeals(home)"
                    v-show="detail_no == -1 || detail_no == home.aptCode"
                  >
                    <div class="d-flex w-100 justify-content-between">
                      <h5 class="mb-1">{{ home.apartmentName }}</h5>
                      <small
                        >건설년도:
                        <span style="color: #4154f1">{{
                          home.buildYear
                        }}</span></small
                      >
                    </div>
                    <p class="mb-1">{{ home.address }}</p>
                  </div>
                </div>
                <div id="aside-list" class="" v-if="deals.length > 0">
                  <div
                    class="list-group-item list-group-item-action"
                    v-for="(deal, index) in deals"
                    :key="index"
                    @click="click_deal(deal.no)"
                    v-show="detail_no == -1 || detail_no == deal.no"
                  >
                    <div class="d-flex w-100 justify-content-between">
                      <h5 class="mb-1">{{ deal.tradeType }}</h5>
                      <small
                        >{{ deal.dealYear }}년 {{ deal.dealMonth }}월
                        {{ deal.dealDay }}일</small
                      >
                    </div>
                    <div class="text-center text-common-light">
                      <div>거래액 : {{ deal.dealAmount }} 만원</div>
                      <div>면적 : {{ deal.area }}</div>
                      <div>층 : {{ deal.floor }} 층</div>
                      <button
                        class="btn btn-primary"
                        v-show="detail_no == deal.no"
                      >
                        이전으로
                      </button>
                    </div>
                  </div>
                </div>
              </div>
              <div class="mt-3" id="deal-back-btn" v-show="dealBack">
                <button
                  type="button"
                  class="btn btn-primary w-100"
                  @click="getHomes()"
                >
                  돌아가기
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- Main Contents -->
    <main class="mt-4 container">
      <!-- contents start -->
      <div class="row">
        <!-- Left side contents-->
        <aside class="col-4"></aside>
      </div>
    </main>
  </main>
</template>

<script>
import axios from "axios";

export default {
  name: "FindHomes",
  data() {
    return {
      map: null,
      geocoder: null,
      selectedSido: "",
      selectedGugun: "",
      selectedDong: "",
      sidos: [],
      guguns: [],
      dongs: [],
      infowindow: null,
      address: "",
      homes: [],
      deals: [],
      markers: [],
      detail_no: -1,
      simple: true,
      aptName: "",
      dealBack: false,
    };
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      kakao.maps.load(this.initMap());
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.addEventListener("load", () => {
        kakao.maps.load(this.initMap);
      });
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=b18df3a5abd802c12c1df25daf571a15&libraries=services";
      document.head.appendChild(script);
    }

    this.getSido();
  },
  methods: {
    initMap() {
      const container = document.getElementById("map");

      const options = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 4,
      };

      //지도 객체를 등록합니다.
      //지도 객체는 반응형 관리 대상이 아니므로 initMap에서 선언합니다.
      this.map = new kakao.maps.Map(container, options);
      this.geocoder = new kakao.maps.services.Geocoder();
    },
    move(address) {
      // 주소-좌표 변환 객체를 생성합니다
      // 주소로 좌표를 검색합니다
      let this1 = this;
      this.geocoder.addressSearch(address, function (result, status) {
        // 정상적으로 검색이 완료됐으면
        if (status === kakao.maps.services.Status.OK) {
          let coords = new kakao.maps.LatLng(result[0].y, result[0].x);

          // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
          this1.map.setCenter(coords);
        }
      });
    },
    //시도 정보 얻어오기
    getSido() {
      const url = "http://localhost:9999/rest/home/getSido";

      axios
        .get(url)
        .then((response) => response.data)
        .then((data) => {
          this.sidos = data;
        });
    },
    //구군 정보 얻어오기
    getGugun() {
      const url = "http://localhost:9999/rest/home/getGugun?sido=";

      axios
        .get(url + this.selectedSido)
        .then((response) => response.data)
        .then((data) => {
          this.guguns = data;
        });
    },
    //동 정보 얻어오기
    getDong() {
      const url = "http://localhost:9999/rest/home/getDong?";

      let param = "sido=" + this.selectedSido + "&gugun=" + this.selectedGugun;
      axios
        .get(url + param)
        .then((response) => response.data)
        .then((data) => {
          this.dongs = data;
        });
    },
    //매매정보 읽어오기
    getHomes() {
      // 모달에 입력받은 시도, 구군, 동 정보를 가져오기 위함
      this.deals = [];
      let code = this.selectedDong.code;

      // 지역 정보가 선택되지 않았으면 경고창을 띄워줌
      if (code == "") {
        window.alert("지역을 선택해주세요.");
      } else {
        let address =
          this.selectedSido +
          " " +
          this.selectedGugun +
          " " +
          this.selectedDong.name;

        // 보여줄 주소 설정
        this.address = address;
        //지도 중앙 해당 위치로 이동
        this.move(address);

        this.initMarkers();
        this.detail_no = -1;

        const url = "http://localhost:9999/rest/home/getInfos?code=" + code;

        this.dealBack = false;
        axios
          .get(url)
          .then((response) => response.data)
          .then((data) => {
            this.homes = data;
            this.homes.forEach((home) => this.setHomeMarker(home));
          });
      }
    },
    initMarkers() {
      this.markers.forEach((marker) => {
        marker.setMap(null);
      });

      this.markers = [];
    },
    setHomeMarker(home) {
      let marker;
      let this1 = this;

      this.geocoder.addressSearch(home.address, function (result, status) {
        // 정상적으로 검색이 완료됐으면
        if (status === kakao.maps.services.Status.OK) {
          let coords = new kakao.maps.LatLng(result[0].y, result[0].x);

          // 결과값으로 받은 위치를 마커로 표시합니다
          marker = new kakao.maps.Marker({
            map: this1.map,
            position: coords,
          });

          if (home.dongCode) {
            kakao.maps.event.addListener(marker, "click", function () {
              this1.getDeals(home);
            });
          }

          marker.setMap(this1.map);
          this1.markers.push(marker);
        }
      });
    },
    getDeals(home) {
      //aptCode, aptName, dongCode, address
      this.move(home.address);
      this.aptName = home.apartmentName;
      const url =
        "http://localhost:9999/rest/home/getDeals?code=" + home.aptCode;

      this.initMarkers();
      this.detail_no = -1;

      this.setHomeMarker(home);

      // 보여줄 아파트 이름 설정
      this.address = home.apartmentName;

      axios
        .get(url)
        .then((response) => response.data)
        .then((data) => this.setDeals(data, home.dongCode));
    },
    setDeals(data) {
      this.deals = data;
      this.homes = [];
      this.dealBack = true;
    },
    click_deal(no) {
      this.change_detail(no);
    },
    change_detail(no) {
      if (this.detail_no != no) {
        this.detail_no = no;
      } else {
        this.detail_no = -1;
      }
    },
  },
  watch: {
    selectedSido: function (newVal) {
      if (newVal != "") this.getGugun();
    },
    selectedGugun: function (newVal) {
      if (newVal != "") this.getDong();
    },
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.button-group {
  margin: 10px 0px;
}

button {
  margin: 0 3px;
}
</style>
