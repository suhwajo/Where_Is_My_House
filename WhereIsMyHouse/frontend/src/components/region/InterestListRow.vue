<template>
  <div
    class="list-group-item list-group-item-action"
    :id="'container-area-' + code"
  >
    <div class="row">
      <div class="col-9 text-center fs-5">
        <div class="text-common-light" :id="'asid-list-simpleInfo-' + code">
          <button
            type="button"
            class="btn text-common-light"
            @click="click_area"
          >
            {{ address }}
          </button>
        </div>
        <div
          class="text-common-light"
          :id="'asid-list-detailInfo-' + code"
          style="display: none"
        >
          <button
            type="button"
            class="btn text-common-light"
            @click="click_area"
          >
            {{ address }}
          </button>
          <br />
          <button
            type="button"
            class="btn text-common-basic link-dark"
            data-bs-toggle="modal"
            data-bs-target="#storeModal"
          >
            주변 상권 검색
          </button>
          <select
            name=""
            class="form-select"
            id="option"
            v-model="selectOption"
          >
            <option value="">시설 검색</option>
            <option value="env">주변 환경 시설 검색</option>
            <option value="hosp">주변 병원 검색</option>
            <option value="corona">주변 코로나 선별 검사소 검색</option>
          </select>
        </div>
      </div>
      <div class="col-3 align-self-center">
        <button type="button" class="btn btn-warning" @click="deleteArea">
          삭제
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { mapActions } from "vuex";
export default {
  name: "InterestList",
  data() {
    return {
      detail_code: -1,
      markers: [],
      clinics: [],
      hospitals: [],
      environments: [],
      selectOption: "",
    };
  },
  props: {
    code: String,
    address: String,
    interests: [],
  },
  watch: {
    selectOption: function (newVal) {
      if (newVal == "env") {
        this.search_environments();
      } else if (newVal == "hosp") {
        this.search_hospitals();
      } else if (newVal == "corona") {
        this.search_hospitals();
      }
    },
  },
  mounted() {},
  methods: {
    ...mapActions(["doSetDongCode"]),
    click_area: function () {
      this.$parent.move(this.address);
      if (this.detail_code != this.code) {
        for (var i = 0; i < this.interests.length; i++) {
          document.querySelector(
            "#asid-list-simpleInfo-" + this.interests[i].code
          ).style.display = "block";
          document.querySelector(
            "#asid-list-detailInfo-" + this.interests[i].code
          ).style.display = "none";

          this.detail_code = -1;
        }
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
        this.$store.dispatch("doSetDongCode", this.code);
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
              <div class="alert alert-info alert-dismissible fade show" role="alert">
                <h5 class="alert-heading">${clinic.clinicName}</h5>
                <hr>
                <p class="mb-0">${clinic.address}</p>
                <p class="mb-0">${clinic.mainPhoneNumber}</p>
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
              <div class="alert alert-info alert-dismissible fade show" role="alert">
                <h5 class="alert-heading">${hospital.hospitalName}</h5>
                <hr>
                <p class="mb-0">${hospital.address}</p>
                <p class="mb-0">${hospital.phoneNumber}</p>
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
              <div class="alert alert-info alert-dismissible fade show" role="alert">
                <h5 class="alert-heading">${environment.name}</h5>
                <p>${environment.industryName}</p>
                <hr>
                <p class="mb-0">${environment.checkList}</p>
                <p class="mb-0">${environment.address}</p>
              </div>
          `;

          this.$parent.setMarker(environment.address, infoWindowDiv);
        });
      }
    },
  },
};
</script>

<style scoped></style>
