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
              <p class="col-sm-6 text-center info-show" id="show-pwd">
                {{ user.pwd }}
              </p>
              <input
                type="password"
                class="info-input col-sm-4"
                name="pwd"
                id="input-pwd"
                style="display: none"
                v-model="pwd"
              />
            </div>
            <div class="mb-3 row">
              <p class="col-sm-6 text-center">이름</p>
              <p class="col-sm-6 text-center info-show" id="show-name">
                {{ user.name }}
              </p>
              <input
                type="text"
                class="info-input col-sm-4"
                name="name"
                id="input-name"
                style="display: none"
                v-model="name"
              />
            </div>
            <div class="mb-3 row">
              <p class="col-sm-6 text-center">이메일</p>
              <p class="col-sm-6 text-center info-show" id="show-email">
                {{ user.email }}
              </p>
              <input
                type="email"
                class="info-input col-sm-4"
                name="email"
                id="input-email"
                style="display: none"
                v-model="email"
              />
            </div>
            <div class="mb-3 row">
              <p class="col-sm-6 text-center">전화번호</p>
              <p class="col-sm-6 text-center info-show" id="show-phone">
                {{ user.phoneNum }}
              </p>
              <input
                type="tel"
                class="info-input col-sm-4"
                name="phone"
                id="input-phone"
                style="display: none"
                v-model="phone"
              />
            </div>
            <div class="mt-5 text-center info-show">
              <button
                type="button"
                class="btn btn-dark bg-common-dark me-3"
                onclick="editOpen()"
              >
                정보 수정
              </button>
              <button
                type="button"
                class="btn btn-dark bg-common-dark"
                onclick="userOut()"
              >
                회원 탈퇴
              </button>
            </div>
            <div class="mt-5 text-center info-input" style="display: none">
              <button
                type="button"
                class="btn btn-dark bg-common-dark me-3"
                onclick="editConfirm()"
              >
                수정
              </button>
              <button
                type="button"
                class="btn btn-dark bg-common-dark"
                onclick="editCancel()"
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

export default {
  name: "UserInfo",
  data() {
    return {
      user: {},
    };
  },
  created() {
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
      this.setInfo(userId);
    }
  },
};
</script>

<style scoped>
.body {
  background: #928490;
}
</style>
