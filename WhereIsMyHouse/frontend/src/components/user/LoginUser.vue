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
                      Login to Your Account
                    </h5>
                    <p class="text-center small">
                      Enter your username &amp; password to login
                    </p>
                  </div>

                  <form class="row g-3 needs-validation" novalidate>
                    <div class="col-12">
                      <label for="yourId" class="form-label">ID</label>
                      <input
                        type="id"
                        name="id"
                        class="form-control"
                        id="yourId"
                        required
                        placeholder="id"
                        v-model="id"
                      />
                      <div class="invalid-feedback">
                        Please enter a valid Id!
                      </div>
                    </div>

                    <div class="col-12">
                      <label for="yourPassword" class="form-label"
                        >Password</label
                      >
                      <input
                        type="password"
                        name="password"
                        class="form-control"
                        id="yourPassword"
                        required
                        placeholder="password"
                        v-model="pwd"
                      />
                      <div class="invalid-feedback">
                        Please enter your password!
                      </div>
                    </div>

                    <div class="col-12">
                      <button
                        class="btn btn-primary w-100"
                        type="button"
                        @click="login"
                      >
                        Login
                      </button>
                    </div>
                    <div class="col-12">
                      <p class="small mb-0">
                        Don't have account?
                        <router-link to="/user/join"
                          >Create an account</router-link
                        >
                      </p>
                    </div>
                    <div class="col-12">
                      <p class="small mb-0">
                        Forget your password?
                        <router-link
                          :to="{
                            name: 'FindPw',
                            params: { userId: id },
                          }"
                          >Find password</router-link
                        >
                      </p>
                    </div>
                  </form>
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
import { mapGetters } from "vuex";
export default {
  name: "UserLogin",
  data() {
    return {
      id: "",
      pwd: "",
      saveId: false,
    };
  },
  computed: {
    ...mapGetters(["isLogin"]),
  },
  created() {
    if (this.isLogin) {
      alert("접근이 불가합니다.");
      this.$router.push({ name: "HomeView" });
    }
  },
  methods: {
    login() {
      let isValid = true;
      let errMsg = "";

      !this.id
        ? ((isValid = false),
          (errMsg = "아이디를 입력해주세요."),
          this.$refs.id.focus())
        : !this.pwd
        ? ((isValid = false),
          (errMsg = "비밀번호를 입력해주세요."),
          this.$refs.pwd.focus())
        : (isValid = true);

      if (!isValid) {
        alert(errMsg);
      }

      var data = {
        userId: this.id,
        userPwd: this.pwd,
        saveId: this.saveId,
      };
      this.$store
        .dispatch("doLogin", data)
        .then(() => {
          this.$router.push({ name: "HomeView" });
          this.$session.set("userInfo", data);
          this.$session.set("userSet", data);
        })
        .catch(() => {
          alert("아이디 혹은 패스워드가 올바르지 않습니다.");
        });
    },
  },
};
</script>

<style scoped></style>
