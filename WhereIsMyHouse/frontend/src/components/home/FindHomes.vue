<template>
  <div>
    <div style="height: 50px"></div>

    <!-- Main Contents -->
    <main class="mt-4 container">
      <div style="height: 100px"></div>
      <!-- contents start -->
      <div class="row">
        <!-- Left side contents-->
        <aside class="col-4">
          <!-- 조건 검색 부분 -->
          <div class="text-center mb-3 w-100">
            <button
              type="button"
              class="btn btn-dark w-100 bg-common-dark"
              data-bs-toggle="modal"
              data-bs-target="#findModal"
            >
              지역 검색
            </button>
          </div>
          <div class="mt-4 overflow-auto" style="height: 650px">
            <div
              class="text-center text-light fs-3 mb-3"
              id="aside-list-info"
              v-text="address"
            ></div>
            <div id="aside-list" class="" v-if="homes.length > 0">
              <div
                class="shadow mb-3 py-3"
                v-for="(home, index) in homes"
                :key="index"
                @click="click_home(home)"
                v-show="simple || (detail && detail_no == home.aptCode)"
              >
                <div
                  class="text-center text-common-light"
                  v-show="simple"
                  :id="home.aptCode"
                >
                  <div>{{ home.apartmentName }}</div>
                  <div>{{ home.address }}</div>
                </div>
                <div
                  class="text-center text-common-light"
                  v-show="detail && detail_no == home.aptCode"
                >
                  <div>{{ home.apartmentName }}</div>
                  <div>{{ home.address }}</div>
                  <div>건설년도 : {{ home.buildYear }}</div>
                  <button
                    class="text-common-light text-decoration-none link-dark"
                    @click="getDeals(home)"
                  >
                    거래 내역 보기
                  </button>
                </div>
              </div>
            </div>
            <div id="aside-list" class="" v-if="deals.length > 0">
              <div
                class="shadow mb-3 py-3"
                v-for="(deal, index) in deals"
                :key="index"
                @click="click_deal(deal.no)"
                v-show="simple || (detail && detail_no == deal.no)"
              >
                <div
                  class="text-center text-common-light"
                  v-show="simple"
                  :id="deal.no"
                >
                  <div>{{ deal.tradeType }}</div>
                  <div>{{ deal.dealAmount }}</div>
                  <div>
                    {{ deal.dealYear }}년 {{ deal.dealMonth }}월
                    {{ deal.dealDay }}일
                  </div>
                </div>
                <div
                  class="text-center text-common-light"
                  v-show="detail && detail_no == deal.no"
                >
                  <div>{{ deal.tradeType }}</div>
                  <div>{{ deal.dealAmount }}</div>
                  <div>
                    {{ deal.dealYear }}년 {{ deal.dealMonth }}월
                    {{ deal.dealDay }}일
                  </div>
                  <div>면적 : {{ deal.area }}</div>
                  <div>{{ deal.floor }} 층</div>
                </div>
              </div>
            </div>
          </div>
          <div class="mt-3" id="deal-back-btn" v-show="dealBack">
            <button
              type="button"
              class="btn btn-dark bg-common-dark w-100"
              @click="getHomes()"
            >
              돌아가기
            </button>
          </div>
        </aside>
        <!-- 중앙 지도 부분 -->
        <section class="col-8">
          <div class="mx-5" id="map" style="height: 630px"></div>
          <div
            class="mt-4 text-center text-common-dark fs-3 fw-bold"
            id="map-homeName"
          >
            {{ aptName }}
          </div>
        </section>
      </div>
    </main>

    <div style="height: 50px"></div>

    <!-- 지역 검색 모달 -->
    <div class="modal fade" id="findModal" tabindex="-1">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">검색 설정</h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div class="modal-body">
            <div class="mb-3 mt-3">
              <select
                class="form-select"
                id="sido"
                v-model="selectedSido"
                aria-label="Default select example"
              >
                <option value="" selected>시도선택</option>
                <option
                  v-for="(sido, index) in sidos"
                  :key="index"
                  :value="sido"
                >
                  {{ sido }}
                </option>
              </select>
            </div>
            <div class="mb-3 mt-3">
              <select
                class="form-select"
                id="gugun"
                v-model="selectedGugun"
                aria-label="Default select example"
              >
                <option value="" selected>구군선택</option>
                <option
                  v-for="(gugun, index) in guguns"
                  :key="index"
                  :value="gugun"
                >
                  {{ gugun }}
                </option>
              </select>
            </div>
            <div class="mb-3 mt-3">
              <select
                class="form-select"
                id="dong"
                v-model="selectedDong"
                aria-label="Default select example"
              >
                <option value="" selected>동선택</option>
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
          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-secondary"
              data-bs-dismiss="modal"
            >
              취소
            </button>
            <button
              type="button"
              class="btn btn-primary"
              data-bs-dismiss="modal"
              @click="getHomes()"
            >
              검색
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
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
      detail: false,
      simple: true,
      aptName: "",
      dealBack: false,
    };
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
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
      this.simple = true;
      this.detail = false;
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

        console.log(address);

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
      const url =
        "http://localhost:9999/rest/home/getDeals?code=" + home.aptCode;

      this.initMarkers();
      this.detail_no = -1;

      this.setHomeMarker(home);

      // 보여줄 아파트 이름 설정
      this.address = home.aptName;

      axios
        .get(url)
        .then((response) => response.data)
        .then((data) => this.setDeals(data, home.dongCode));
    },
    setDeals(data) {
      this.deals = data;
      this.homes = [];
      this.simple = true;
      this.detail = false;
      this.dealBack = true;
    },
    click_deal(no) {
      this.detail = true;
      this.simple = false;
      this.change_detail(no);
    },
    click_home(home) {
      this.detail = true;
      this.simple = false;
      this.move(home.address);
      this.change_detail(home.aptCode);

      this.aptName = home.aptName;
    },
    change_detail(no) {
      if (this.detail_no != no) {
        this.detail = true;
        this.simple = false;
        this.detail_no = no;
      } else {
        this.simple = true;
        this.detail = false;

        this.detail_no = -1;
      }
    },
  },
  watch: {
    selectedSido: function (newVal) {
      console.log("sido " + newVal);
      if (newVal != "") this.getGugun();
    },
    selectedGugun: function (newVal) {
      console.log("gugun " + newVal);
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
