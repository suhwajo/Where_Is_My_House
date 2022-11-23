<template>
  <main id="main" class="main">
    <div class="container">
      <section
        class="section register d-flex flex-column align-items-center justify-content-center py-4"
      >
        <div class="container">
          <div class="row justify-content-center">
            <div
              class="col-lg-4 col-md-6 d-flex flex-column align-items-center justify-content-center"
            >
              <div class="d-flex justify-content-center py-4">
                <router-link
                  to="/"
                  class="logo d-flex align-items-center w-auto"
                >
                  <img src="@/assets/logo.png" alt="" />
                  <span class="d-none d-lg-block">Where Is My Home</span>
                </router-link>
              </div>
              <!-- End Logo -->

              <div class="card mb-3">
                <div class="card-body">
                  <div class="pt-4 pb-2">
                    <h5 class="card-title text-center pb-0 fs-4">
                      Find Password
                    </h5>
                  </div>

                  <div class="row g-3 needs-validation" novalidate>
                    <div class="col-12">
                      <label for="id" class="form-label">ID</label>
                      <input
                        type="text"
                        name="id"
                        class="form-control"
                        id="id"
                        required
                        placeholder="id"
                        v-model="id"
                      />
                      <div class="invalid-feedback">
                        Please enter a valid Id!
                      </div>
                    </div>

                    <div class="col-12">
                      <label for="name" class="form-label">Name</label>
                      <input
                        type="text"
                        name="name"
                        class="form-control"
                        id="name"
                        required
                        placeholder="이름"
                        v-model="name"
                      />
                      <div class="invalid-feedback">
                        Please enter a valid Name!
                      </div>
                    </div>

                    <div class="col-12">
                      <label for="phone" class="form-label">Password</label>
                      <input
                        type="tel"
                        name="phone"
                        class="form-control"
                        id="phone"
                        required
                        pattern="[0-9]{3}-[0-9]{4}-[0-9]{4}"
                        placeholder="010-xxxx-xxxx"
                        v-model="phone"
                      />
                      <div class="invalid-feedback">
                        Please enter your password!
                      </div>
                    </div>

                    <div class="col-12">
                      <button
                        class="btn btn-primary w-100"
                        type="button"
                        @click="findPw"
                      >
                        Find Pwd
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
    </div>
  </main>
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
