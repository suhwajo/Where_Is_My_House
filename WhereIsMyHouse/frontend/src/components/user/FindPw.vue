<template>
  <div class="body">
    <div style="height: 250px"></div>

    <!-- Main Contents -->
    <main class="mt-4 container">
      <!-- contents -->
      <section class="pt-5 rounded-2 mb-5 text-light">
        <div class="row">
          <div class="d-flex justify-content-center mx-5 mb-5 col">
            <img
              class="img-fluid"
              src="@/assets/logo.png"
              alt=""
              width="300px"
            />
          </div>
          <div class="col">
            <div class="fs-3 fw-bold text-center w-75">비밀번호찾기</div>
            <div class="mb-4 mt-4 w-75">
              <div class="mb-3 mt-3 row">
                <label for="name" class="col-sm-3 col-form-label">이름</label>
                <div class="col-sm-9">
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
                <label for="id" class="col-sm-3 col-form-label">ID</label>
                <div class="col-sm-9">
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
              <div class="mb-3 row">
                <label for="phone" class="col-sm-3 col-form-label"
                  >전화번호</label
                >
                <div class="col-sm-9">
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
                  @click="findPw"
                >
                  비밀번호 찾기
                </button>
              </div>
            </div>
            <!-- 16이후 수정 -->
          </div>
        </div>
      </section>
    </main>

    <div style="height: 163px"></div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "FindPw",
  data: function () {
    return {
      name: "",
      id: "",
      phone: "",
    };
  },
  methods: {
    findPw() {
      if (!this.id || !this.name || !this.phone) {
        alert("모든 정보를 입력해 주세요.");
        return;
      }

      const url = "http://localhost:9999/rest/user/findPw";
      var userdata = {
        id: this.id,
        name: this.name,
        phone: this.phone,
      };
      console.log(userdata);
      axios
        .get(url, {
          params: {
            id: this.id,
            name: this.name,
            phone: this.phone,
          },
        })
        .then((response) => response.data)
        .then((data) => {
          if (data.pwd) {
            alert(
              `찾은 비밀번호는 ${data.pwd} 입니다. 로그인 화면으로 이동합니다.`
            );
            this.$router.push({ name: "UserLogin" });
          } else {
            alert("등록된 회원이 존재하지 않습니다.");
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
