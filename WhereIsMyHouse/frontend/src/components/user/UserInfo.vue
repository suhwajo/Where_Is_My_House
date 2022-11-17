<template>
  <div class="body">
    <div style="height: 160px"></div>

    <!-- Main Contents -->
    <main class="mt-4 container">
      <!-- contents start -->
      <div class="row">
        <!-- Left side contents-->
        <div class="col-2"></div>
        <!-- Cneter contents-->
        <section class="col-8 pt-5 rounded-2 mb-5 text-common-light">
          <div class="ms-5">
            <div class="fs-3 fw-bold text-center mb-5">회원 정보</div>
            <div class="mb-3 mt-3 row">
              <p class="col-sm-6 text-center">아이디</p>
              <p class="col-sm-6 text-center" id="show-id">{{ user.id }}</p>
            </div>
            <div class="mb-3 row">
              <p class="col-sm-6 text-center">비밀번호</p>
              <p
                class="col-sm-6 text-center info-show"
                v-if="infoShow"
                id="show-pwd"
              >
                {{ user.pwd }}
              </p>
              <input
                type="password"
                class="info-input col-sm-4"
                name="pwd"
                id="input-pwd"
                v-model="user.pwd"
                v-else
              />
            </div>
            <div class="mb-3 row">
              <p class="col-sm-6 text-center">이름</p>
              <p
                class="col-sm-6 text-center info-show"
                v-if="infoShow"
                id="show-name"
              >
                {{ user.name }}
              </p>
              <input
                type="text"
                class="info-input col-sm-4"
                name="name"
                id="input-name"
                v-model="user.name"
                v-else
              />
            </div>
            <div class="mb-3 row">
              <p class="col-sm-6 text-center">이메일</p>
              <p
                class="col-sm-6 text-center info-show"
                v-if="infoShow"
                id="show-email"
              >
                {{ user.email }}
              </p>
              <input
                type="email"
                class="info-input col-sm-4"
                name="email"
                id="input-email"
                v-model="user.email"
                v-else
              />
            </div>
            <div class="mb-3 row">
              <p class="col-sm-6 text-center">전화번호</p>
              <p
                class="col-sm-6 text-center info-show"
                v-if="infoShow"
                id="show-phone"
              >
                {{ user.phoneNum }}
              </p>
              <input
                type="tel"
                class="info-input col-sm-4"
                name="phone"
                id="input-phone"
                v-model="user.phoneNum"
                v-else
              />
            </div>
            <div class="mt-5 text-center info-show" v-if="infoShow">
              <button
                type="button"
                class="btn btn-dark bg-common-dark me-3"
                @click="editUser"
              >
                정보 수정
              </button>
              <button
                type="button"
                class="btn btn-dark bg-common-dark"
                @click="userOut"
              >
                회원 탈퇴
              </button>
            </div>
            <div class="mt-5 text-center info-input" v-else>
              <button
                type="button"
                class="btn btn-dark bg-common-dark me-3"
                @click="editConfirm"
              >
                수정
              </button>
              <button
                type="button"
                class="btn btn-dark bg-common-dark"
                @click="editUser"
              >
                취소
              </button>
            </div>
          </div>
        </section>
      </div>
    </main>

    <div style="height: 161px"></div>
  </div>
</template>

<script>
import axios from "axios";
import { mapActions } from "vuex";

export default {
  name: "UserInfo",
  data() {
    return {
      user: {},
      infoShow: true,
    };
  },
  created() {
    // if (!this.isLogin) {
    //   alert("로그인 후 접근이 가능합니다.");
    //   this.$router.push({ name: "UserLogin" });
    // }
    console.log("현재 이름", this.$route);
    let userId = this.$route.params.userId;
    if (userId) {
      const url = "http://localhost:9999/rest/user/getUser/" + userId;
      axios
        .get(url)
        .then((response) => response.data)
        .then((data) => {
          console.log(data);
          if (data) this.user = data;
        });
    } else {
      console.log(this.$session.getAll());
    }
  },
  methods: {
    ...mapActions(["doLogout"]),
    editUser() {
      this.infoShow = !this.infoShow;
    },
    editConfirm() {
      if (
        !this.user.pwd ||
        !this.user.name ||
        !this.user.email ||
        !this.user.phoneNum
      ) {
        alert("모든 정보를 입력해 주세요.");
        return;
      }

      const url = "http://localhost:9999/rest/user/update";
      var userMod = {
        id: this.user.id,
        pwd: this.user.pwd,
        name: this.user.name,
        email: this.user.email,
        phone: this.user.phoneNum,
      };
      console.log(userMod);
      axios
        .put(url, userMod)
        .then((response) => response.data)
        .then((data) => {
          if (data.check) {
            alert("회원 정보 변경에 성공했습니다.");
            location.reload();
          } else {
            alert("회원 정보 변경에 실패 했습니다. 다시 시도해주세요.");
          }
        });
    },
    userOut() {
      let outConfirm = confirm("정말 탈퇴하시겠습니까??");

      if (outConfirm) {
        console.log(this.user);
        const url = "http://localhost:9999/rest/user/delete/";
        if (this.user.adminAccount) alert("관리자계정은 탈퇴가 불가합니다.");
        else {
          axios
            .delete(url + this.user.id)
            .then((response) => response.data)
            .then((data) => {
              if (data.check) {
                this.doLogout();
                alert("탈퇴성공!!!!!!!!!!!!");
                location.href = "/";
              } else alert("탈퇴에 실패했습니다. 다시 시도해주세요.");
            });
        }
      }
    },
  },
};
</script>

<style scoped>
.body {
  background: #928490;
}
</style>
