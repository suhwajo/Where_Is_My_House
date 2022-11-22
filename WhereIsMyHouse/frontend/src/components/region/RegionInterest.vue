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
          <!-- 관심 지역 추가 부분 -->
          <div class="text-center mb-3 w-100">
            <button
              type="button"
              class="btn btn-dark w-100 bg-common-dark"
              data-bs-toggle="modal"
              data-bs-target="#findModal"
            >
              관심 지역 추가
            </button>
          </div>
          <div class="mt-4 overflow-auto" style="height: 1000px">
            <interest-row
              @code="deleteList"
              v-for="(interest, index) in interests"
              :key="index"
              :code="interest.code"
              :address="interest.address"
              :interests="interests"
            />
            <!-- <div id="aside-list"></div> -->
          </div>
        </aside>
        <!-- 중앙 지도 부분 -->
        <section class="col-8">
          <div class="mx-5" id="map" style="height: 630px"></div>
          <div
            class="mt-4 text-center text-common-dark fs-3 fw-bold"
            id="map-areaText"
          ></div>
        </section>
      </div>
    </main>

    <div style="height: 50px"></div>

    <!-- 관심 지역 추가 모달 -->
    <div class="modal fade" id="findModal" tabindex="-1">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">관심 지역 추가</h5>
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
                aria-label="Default select example"
                v-model="selectedSido"
              >
                <option value="0" selected>시도선택</option>
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
                aria-label="Default select example"
                v-model="selectedGugun"
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
                aria-label="Default select example"
                v-model="selectedDong"
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
              @click="addFocusArea"
            >
              추가
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- 주변 상권 검색 모달 -->
    <div class="modal fade" id="storeModal" tabindex="-1">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">검색 상권 업종 선택</h5>
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
                id="big"
                aria-label="Default select example"
                v-model="selectedBigStore"
              >
                <option value="" selected>대분류</option>
                <option
                  v-for="(bigStore, index) in bigStores"
                  :key="index"
                  :value="bigStore"
                >
                  {{ bigStore }}
                </option>
              </select>
            </div>
            <div class="mb-3 mt-3">
              <select
                class="form-select"
                id="middle"
                aria-label="Default select example"
                v-model="selectedMiddleStore"
              >
                <option value="" selected>중분류</option>
                <option
                  v-for="(middleStore, index) in middleStores"
                  :key="index"
                  :value="middleStore"
                >
                  {{ middleStore }}
                </option>
              </select>
            </div>
            <div class="mb-3 mt-3">
              <select
                class="form-select"
                id="small"
                aria-label="Default select example"
                v-model="selectedSmallStore"
              >
                <option value="" selected>소분류</option>
                <option
                  v-for="(smallStore, index) in smallStores"
                  :key="index"
                  :value="smallStore"
                >
                  {{ smallStore }}
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
              @click="search_stores"
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
import { mapGetters } from "vuex";

export default {
  name: "UserRegion",
  components: {
    "interest-row": () => import("@/components/region/InterestListRow.vue"),
  },

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
      interests: [],
      selectedBigStore: "",
      selectedMiddleStore: "",
      selectedSmallStore: "",
      bigStores: [],
      middleStores: [],
      smallStores: [],
      smallCodeStores: [],
      center: false,
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
    this.getRegionList();
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
    selectedBigStore: function (newVal) {
      console.log("bigStore " + newVal);
      if (newVal != "") this.getMiddle();
    },
    selectedMiddleStore: function (newVal) {
      console.log("middleStore " + newVal);
      if (newVal != "") this.getSmall();
    },
  },
  computed: {
    ...mapGetters(["dongCode"]),
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
      console.log(this.map);
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
      this.getBig();
    },
    getBig() {
      const url = "http://localhost:9999/rest/area/getBig";
      axios
        .get(url)
        .then((response) => response.data)
        .then((data) => {
          this.bigStores = data.list;
        });
    },
    getMiddle() {
      const url = "http://localhost:9999/rest/area/getMiddle?big=";

      axios
        .get(url + this.selectedBigStore)
        .then((response) => response.data)
        .then((data) => {
          this.middleStores = data.list;
        });
    },
    getSmall() {
      const url = "http://localhost:9999/rest/area/getSmall?middle=";
      axios
        .get(url + this.selectedMiddleStore)
        .then((response) => response.data)
        .then((data) => {
          console.log(data.smallName);
          for (var i = 0; i < data.length; i++) {
            this.smallStores.push(data[i].smallName);
          }
          this.smallCodeStores = data;
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

        this.setMarkerers();
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
      this.center = false;
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
    addFocusArea() {
      // 모달에 입력받은 시도, 구군, 동 정보를 가져오기 위함
      let sido = this.selectedSido;
      let gugun = this.selectedGugun;
      let dong = this.selectedDong.name;
      let code = this.selectedDong.code;
      console.log(sido, " ", gugun, " ", dong, " ", code);
      // let el = document.getElementById("sido");
      // let len = el.options.length;
      // for (var i = 0; i < len; i++) {
      //   el.options[i].selected = false;
      // }
      // document.getElementById("sido").options[0].selected = true;
      // 지역 정보가 선택되지 않았으면 경고창을 띄워줌
      if (code == "") {
        window.alert("지역을 선택해주세요.");
      } else {
        const url = "http://localhost:9999/rest/area/insert";
        var data = {
          sido: sido,
          gugun: gugun,
          dong: dong,
          code: code,
          userId: this.$session.get("userInfo").userId,
        };
        axios
          .post(url, data)
          .then((response) => response.data)
          .then((data) => this.interests.push(data));
      }
      // var newData = {
      //   address: sido + " " + gugun + " " + dong,
      //   code: code,
      //   userId: data.userId,
      // };
      // this.interests.push(newData);
    },
    //관심지역 정보 얻어오기
    getRegionList() {
      const url = "http://localhost:9999/rest/area/list";

      axios
        .get(url, {
          params: {
            userId: this.$session.get("userInfo").userId,
          },
        })
        .then((response) => response.data)
        .then((data) => {
          this.interests = data.areas;
        });
      console.log(this.interests);
    },
    deleteList: function (deleteCode) {
      console.log(this.interests);
      console.log(deleteCode);
      const url = "http://localhost:9999/rest/area/delete";
      axios
        .delete(url, {
          params: {
            code: deleteCode,
            userId: this.$session.get("userInfo").userId,
          },
        })
        .then((response) => console.log(response.data));

      for (var i = 0, size = this.interests.length; i < size; i++) {
        if (this.interests[i].code == deleteCode) {
          this.interests.splice(i, 1);
          break;
        }
      }
    },
    setMarker(address, infoWindowDiv) {
      let this1 = this;
      let geocoder = this.geocoder;
      let marker;
      geocoder.addressSearch(address, function (result, status) {
        // console.log(result);
        // 정상적으로 검색이 완료됐으면
        if (status === kakao.maps.services.Status.OK) {
          let coords = new kakao.maps.LatLng(result[0].y, result[0].x);
          if (!this1.center) {
            this1.map.setCenter(coords);
            this1.center = true;
          }
          // 결과값으로 받은 위치를 마커로 표시합니다
          marker = new kakao.maps.Marker({
            map: this1.map,
            position: coords,
          });
          marker.setMap(this1.map);
          this1.markers.push(marker);
          // 등록할 인포 윈도우 정보가 있으면 표시한다.
          if (infoWindowDiv) {
            let infoWindow = new kakao.maps.InfoWindow({
              map: this1.map,
              position: coords,
              content: infoWindowDiv,
              removable: true,
            });
            // 마커에 클릭이벤트를 등록합니다
            kakao.maps.event.addListener(marker, "click", function () {
              // 마커 위에 인포윈도우를 표시합니다
              infoWindow.open(this1.map, marker);
            });
            infoWindow.close();
          }
        }
      });
    },
    search_stores() {
      const url = "http://localhost:9999/rest/area/stores";

      let smallCode = "";
      for (var i = 0; i < this.smallCodeStores.length; i++) {
        if (this.smallCodeStores[i].smallName == this.selectedSmallStore) {
          smallCode = this.smallCodeStores[i].smallCode;
          break;
        }
      }
      console.log(this.selectedSmallStore, " ", smallCode, " ", this.dongCode);
      axios
        .get(url, {
          params: {
            smallCode: smallCode,
            dongCode: this.dongCode,
          },
        })
        .then((response) => response.data)
        .then((data) => this.set_stores(data));
    },

    set_stores(data) {
      this.initMarkers();

      data.list.forEach((store) => {
        let infoWindowDiv = `
            <div class="bg-common-dark text-common-light text-center">
                <div class="fs-4">${store.storeName}</div>
                <div class="fs-5 mt-4">${store.address}</div>
            </div>
        `;

        this.setMarker(store.address, infoWindowDiv);
      });
    },
  },
};
</script>

<style scoped>
.region-backgraound {
  background: black;
}
</style>
