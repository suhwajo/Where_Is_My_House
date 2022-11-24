<template>
  <main id="main" class="main">
    <div class="container-fluid">
      <div class="pagetitle row justify-content-center">
        <h1>회원 정보 관리</h1>
        <nav>
          <ol class="breadcrumb">
            <li class="breadcrumb-item">관리자 모드</li>
            <li class="breadcrumb-item active">회원 정보 관리</li>
            <li class="breadcrumb-item active">회원 추가</li>
          </ol>
        </nav>
      </div>
      <!-- End Page Title -->

      <div class="container">
        <section
          class="section register d-flex flex-column align-items-center justify-content-center py-4"
        >
          <div class="container">
            <div class="row justify-content-center">
              <div
                class="col-lg-4 col-md-6 d-flex flex-column align-items-center justify-content-center"
              >
                <div class="card mb-3">
                  <div class="card-body">
                    <div class="pt-4 pb-2">
                      <h5 class="card-title text-center pb-0 fs-4">
                        Create an Account
                      </h5>
                      <p class="text-center small">
                        Enter your personal details to create account
                      </p>
                    </div>

                    <div
                      id="form-add"
                      class="row g-3 needs-validation"
                      novalidate
                    >
                      <input type="hidden" name="act" value="add-member" />

                      <div class="col-12">
                        <label for="yourId" class="form-label">ID</label>
                        <input
                          type="text"
                          name="id"
                          class="form-control"
                          id="id"
                          v-model="id"
                          placeholder="id"
                          required
                        />
                        <div class="invalid-feedback">
                          Please enter a valid Id!
                        </div>
                      </div>
                      <div id="idcheck-result"></div>

                      <div class="col-12">
                        <label for="yourPassword" class="form-label"
                          >Password</label
                        >
                        <input
                          type="password"
                          name="pwd"
                          class="form-control"
                          id="pwd"
                          v-model="pwd"
                          placeholder="password"
                          required
                        />
                        <div class="invalid-feedback">
                          Please enter your password!
                        </div>
                      </div>

                      <div class="col-12">
                        <label for="yourPassword" class="form-label"
                          >Password Check</label
                        >
                        <input
                          type="password"
                          name="pwd_check"
                          class="form-control"
                          id="pwd_check"
                          v-model="pwd_check"
                          placeholder="check password"
                          required
                        />
                        <div class="invalid-feedback">
                          Please enter your password again!
                        </div>
                      </div>

                      <div class="col-12">
                        <label for="yourName" class="form-label">Name</label>
                        <input
                          type="text"
                          name="name"
                          class="form-control"
                          id="name"
                          placeholder="name"
                          v-model="name"
                          required
                        />
                        <div class="invalid-feedback">
                          Please enter your name!
                        </div>
                      </div>

                      <div class="col-12">
                        <label for="email" class="form-label">Email</label>

                        <input
                          type="text"
                          name="email"
                          class="form-control"
                          placeholder="email"
                          id="email"
                          v-model="email"
                          required
                        />
                      </div>

                      <div class="col-12">
                        <label for="yourPhone" class="form-label"
                          >Phone Number</label
                        >
                        <input
                          type="text"
                          name="phone"
                          class="form-control"
                          placeholder="010-xxxx-xxxx"
                          pattern="[0-9]{3}-[0-9]{4}-[0-9]{4}"
                          id="phone"
                          v-model="phone"
                          required
                        />
                        <div class="invalid-feedback">
                          Please enter your phone number!
                        </div>
                      </div>
                      <div class="col-12">
                        <button
                          class="btn btn-primary w-100"
                          @click="join"
                          id="btn-join"
                        >
                          Create Account
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
    </div>
  </main>
</template>

<script>
import axios from "axios";
import { mapGetters } from "vuex";

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
  created() {
    if (!this.adminAccount) {
      alert("접근이 불가합니다.");
      this.$router.push({ name: "HomeView" });
    }
  },
  computed: {
    ...mapGetters(["adminAccount"]),
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
      axios
        .post(url, data)
        .then((response) => response.data)
        .then((data) => {
          if (data.check) {
            alert("회원추가에 성공했습니다. 이동합니다.");
            //this.$router.push({ name: "UserList" });
            location.href = "/user/list";
          } else {
            alert("회원추가에 실패 했습니다. 다시 시도해주세요.");
          }
        });
    },
  },
};
</script>

<style scoped></style>
