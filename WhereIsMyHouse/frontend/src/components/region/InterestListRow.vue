<template>
  <div class="shadow mb-3 py-3 row" id="container-area-${area.code}">
    <div class="col-9 text-center fs-5">
      <div class="text-common-light" id="asid-list-simpleInfo-${area.code}">
        <button
          tpye="button"
          class="btn text-common-light"
          @click="click_area({ code }, { address })"
        >
          {{ area.address }}
        </button>
      </div>
      <div
        class="text-common-light"
        id="asid-list-detailInfo-${area.code}"
        style="display: none"
      >
        <button
          tpye="button"
          class="btn text-common-light"
          @click="click_area({ code }, { address })"
        >
          {{ area.address }}
        </button>
        <div class="">
          <button
            type="button"
            class="btn text-common-basic link-dark"
            data-bs-toggle="modal"
            data-bs-target="#storeModal"
          >
            주변 상권 검색
          </button>
        </div>
        <div class="">
          <button
            type="button"
            class="btn text-common-basic link-dark"
            @click="search_environments({ code })"
          >
            주변 환경 시설 검색
          </button>
        </div>
        <div class="">
          <button
            type="button"
            class="btn text-common-basic link-dark"
            @click="search_hospitals({ code })"
          >
            주변 병원 검색
          </button>
        </div>
        <div class="">
          <button
            type="button"
            class="btn text-common-basic link-dark"
            @click="search_clinics({ code })"
          >
            주변 코로나 선별 검사소 검색
          </button>
        </div>
      </div>
    </div>
    <div class="col-2 align-self-center">
      <button
        type="button"
        class="btn text-light"
        @click="deleteArea({ code })"
      >
        <i class="fa-solid fa-trash"></i>
      </button>
    </div>
  </div>
</template>

<script>
export default {
  name: "InterestList",
  data() {
    return {
      detail_code: -1,
    };
  },
  props: {
    code: String,
    address: String,
  },
  mounted() {},
  methods: {
    click_area(code, address) {
      this.move(address);

      if (this.detail_code != code) {
        document.querySelector("#asid-list-simpleInfo-" + code).style.display =
          "none";
        document.querySelector("#asid-list-detailInfo-" + code).style.display =
          "block";

        if (this.detail_code != -1) {
          document.querySelector(
            "#asid-list-simpleInfo-" + this.detail_code
          ).style.display = "block";
          document.querySelector(
            "#asid-list-detailInfo-" + this.detail_code
          ).style.display = "none";
        }

        this.detail_code = code;
      } else {
        document.querySelector("#asid-list-simpleInfo-" + code).style.display =
          "block";
        document.querySelector("#asid-list-detailInfo-" + code).style.display =
          "none";

        this.detail_code = -1;
      }
    },
    //move(address) {
    // 주소-좌표 변환 객체를 생성합니다
    // 주소로 좌표를 검색합니다
    //let this1 = this;
    //this.geocoder.addressSearch(address, function (result, status) {
    // 정상적으로 검색이 완료됐으면
    // if (status === kakao.maps.services.Status.OK) {
    //   let coords = new kakao.maps.LatLng(result[0].y, result[0].x);
    //   // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
    //   this1.map.setCenter(coords);
    // }
    //  });
    //},
  },
};
</script>

<style></style>
