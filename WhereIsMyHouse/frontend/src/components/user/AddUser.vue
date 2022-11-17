<template>
  <div class="body">
    <div style="height: 50px"></div>

    <!-- Main Contents -->
    <main class="mt-4 container">
      <div style="height: 100px"></div>
      <!-- contents -->
      <section class="pt-5 rounded-2 mb-5 text-light">
        <div style="height: 30px"></div>
        <div class="row">
          <div class="d-flex justify-content-center m-5 col">
            <img
              class="img-fluid"
              src="@/assets/logo.png"
              alt=""
              width="400px"
            />
          </div>
          <div class="col">
            <div class="fs-3 fw-bold text-center w-75">회원 추가 등록</div>
            <div class="mb-4 mt-4 w-75">
              <div class="mb-3 mt-3 row">
                <label for="id" class="col-sm-4 col-form-label">아이디</label>
                <div class="col-sm-8">
                  <input
                    type="text"
                    class="form-control"
                    id="id"
                    placeholder="id"
                    name="id"
                    required
                    v-model="id"
                  />
                </div>
              </div>
              <div id="idcheck-result"></div>
              <div class="mb-3 row">
                <label for="pwd" class="col-sm-4 col-form-label"
                  >비밀번호</label
                >
                <div class="col-sm-8">
                  <input
                    type="password"
                    class="form-control"
                    id="pwd"
                    placeholder="password"
                    name="pwd"
                    required
                    v-model="pwd"
                  />
                </div>
              </div>
              <div class="mb-3 row">
                <label
                  class="col-sm-4 col-form-label form-label"
                  for="pwd_check"
                  >비밀번호 확인</label
                >
                <div class="col-sm-8">
                  <input
                    type="password"
                    class="form-control"
                    id="pwd_check"
                    name="pwd_check"
                    placeholder="check password"
                    v-model="pwd_check"
                  />
                </div>
              </div>
              <div class="mb-3 row">
                <label for="name" class="col-sm-4 col-form-label">이름</label>
                <div class="col-sm-8">
                  <input
                    type="text"
                    class="form-control"
                    id="name"
                    placeholder="name"
                    name="name"
                    required
                    v-model="name"
                  />
                </div>
              </div>
              <div class="mb-3 row">
                <label for="email" class="col-sm-4 col-form-label"
                  >이메일</label
                >
                <div class="col-sm-8">
                  <input
                    type="email"
                    class="form-control"
                    id="email"
                    placeholder="email"
                    name="email"
                    required
                    v-model="email"
                  />
                </div>
              </div>
              <div class="mb-3 row">
                <label for="phone" class="col-sm-4 col-form-label"
                  >전화번호</label
                >
                <div class="col-sm-8">
                  <input
                    type="tel"
                    class="form-control"
                    id="phone"
                    name="phone"
                    pattern="[0-9]{3}-[0-9]{4}-[0-9]{4}"
                    placeholder="Phone : 010-xxxx-xxxx"
                    required
                    v-model="phone"
                  />
                </div>
              </div>
              <div class="mt-5">
                <button
                  type="button"
                  class="btn btn-dark bg-common-dark w-100"
                  @click="join"
                >
                  추가 회원 등록
                </button>
              </div>
            </div>
          </div>
        </div>
      </section>
    </main>

    <div style="height: 84px"></div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "UserJoin",
  data() {
    return {
      id: "",
      pwd: "",
      pwd_check: "",
      name: "",
      email: "",
      phone: "",
    };
  },
  methods: {
    join: function () {
      if (
        !this.id ||
        !this.pwd ||
        !this.pwd_check ||
        !this.name ||
        !this.email ||
        !this.phone
      ) {
        alert("모든 정보를 입력해 주세요.");
        return;
      }

      if (this.pwd != this.pwd_check) {
        alert("비밀번호와 비밀번호 체크의 내용이 다릅니다.");
        return;
      }

      // if (!isUseId) {
      //   alert("사용할 수 없는 아이디입니다.");
      //   return;
      // }

      const url = "http://localhost:9999/rest/user/join";
      var data = {
        id: this.id,
        pwd: this.pwd,
        name: this.name,
        email: this.email,
        phone: this.phone,
      };
      console.log(data);
      axios
        .post(url, data)
        .then((response) => response.data)
        .then((data) => {
          if (data.check) {
            alert("회원추가에 성공했습니다  이동합니다.");
            location.href = "/user/list";
          } else {
            alert("회원추가에 실패 했습니다. 다시 시도해주세요.");
          }
        });
    },
  },
};
</script>

<style scoped>
.body {
  background: #928490;
}
</style>
