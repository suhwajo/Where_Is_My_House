<template>
  <div class="shadow mb-3 py-3 row" :id="'container-area-' + code">
    <div class="col-9 text-center fs-5">
      <div class="text-common-light" :id="'asid-list-simpleInfo-' + code">
        <button tpye="button" class="btn text-common-light" @click="click_area">
          {{ address }}
        </button>
      </div>
      <div
        class="text-common-light"
        :id="'asid-list-detailInfo-' + code"
        style="display: none"
      >
        <button tpye="button" class="btn text-common-light" @click="click_area">
          {{ address }}
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
            @click="search_environments"
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
        class="btn btn-delete"
        @click="deleteArea({ code })"
      >
        삭제
      </button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "InterestList",
  data() {
    return {
      detail_code: -1,
      markers: [],
      clinics: [],
      hospitals: [],
      environments: [],
    };
  },
  props: {
    code: String,
    address: String,
    interests: [],
  },
  mounted() {},
  methods: {
    click_area: function () {
      this.$parent.move(this.address);
      for (var i = 0; i < this.interests.length; i++) {
        document.querySelector(
          "#asid-list-simpleInfo-" + this.interests[i].code
        ).style.display = "block";
        document.querySelector(
          "#asid-list-detailInfo-" + this.interests[i].code
        ).style.display = "none";

        this.detail_code = -1;
      }
      if (this.detail_code != this.code) {
        document.querySelector(
          "#asid-list-simpleInfo-" + this.code
        ).style.display = "none";
        document.querySelector(
          "#asid-list-detailInfo-" + this.code
        ).style.display = "block";

        if (this.detail_code != -1) {
          document.querySelector(
            "#asid-list-simpleInfo-" + this.detail_code
          ).style.display = "block";
          document.querySelector(
            "#asid-list-detailInfo-" + this.detail_code
          ).style.display = "none";
        }

        this.detail_code = this.code;
      } else {
        document.querySelector(
          "#asid-list-simpleInfo-" + this.code
        ).style.display = "block";
        document.querySelector(
          "#asid-list-detailInfo-" + this.code
        ).style.display = "none";

        this.detail_code = -1;
      }
      this.$parent.getBig();
    },
    deleteArea: function () {
      this.$emit("code", this.code);
    },
    search_clinics: function () {
      const url = "http://localhost:9999/rest/area/clinics";
      axios
        .get(url, {
          params: {
            code: this.code,
          },
        })
        .then((response) => response.data)
        .then((data) => this.set_clinics(data));
    },

    set_clinics(data) {
      this.$parent.initMarkers();
      this.clinics = data.list;
      if (this.clinics.length == 0) {
        alert("주변 병원이 없습니다.");
      } else {
        this.clinics.forEach((clinic) => {
          let infoWindowDiv = `
              <div class="bg-common-dark text-common-light text-center">
                  <div class="fs-4">${clinic.clinicName}</div>
                  <div class="fs-5 mt-5">${clinic.address}</div>
                  <div class="fs-5 mt-5">${clinic.mainPhoneNumber}</div>
              </div>
          `;

          this.$parent.setMarker(clinic.address, infoWindowDiv);
        });
      }
    },
    search_hospitals: function () {
      const url = "http://localhost:9999/rest/area/hospitals";
      axios
        .get(url, {
          params: {
            code: this.code,
          },
        })
        .then((response) => response.data)
        .then((data) => this.set_hospitals(data));
    },

    set_hospitals(data) {
      this.$parent.initMarkers();
      this.hospitals = data.list;
      if (this.hospitals.length == 0) {
        alert("주변 병원이 없습니다.");
      } else {
        this.hospitals.forEach((hospital) => {
          let infoWindowDiv = `
              <div class="bg-common-dark text-common-light text-center">
                  <div class="fs-4">${hospital.hospitalName}</div>
                  <div class="fs-5 mt-5">${hospital.address}</div>
                  <div class="fs-5 mt-5">${hospital.phoneNumber}</div>
              </div>
          `;

          this.$parent.setMarker(hospital.address, infoWindowDiv);
        });
      }
    },
    search_environments() {
      const url = "http://localhost:9999/rest/area/environments";
      axios
        .get(url, {
          params: {
            code: this.code,
          },
        })
        .then((response) => response.data)
        .then((data) => this.set_environments(data));
    },

    set_environments(data) {
      this.$parent.initMarkers();
      this.environments = data.list;
      if (this.environments.length == 0) {
        alert("주변 환경시설이 없습니다.");
      } else {
        this.environments.forEach((environment) => {
          let infoWindowDiv = `
              <div class="bg-common-dark text-common-light text-center">
                  <div class="fs-4">${environment.name}</div>
                  <div class="fs-4 mt-3">${environment.industryName}</div>
                  <div class="fs-4 mt-4">${environment.checkList}</div>
                  <div class="fs-5 mt-5">${environment.address}</div>
              </div>
          `;

          this.$parent.setMarker(environment.address, infoWindowDiv);
        });
      }
    },
  },
};
</script>

<style scoped>
.btn-delete {
  background-color: red;
  color: white;
  font-size: 10px;
  width: 15px;
}
</style>
