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
                      Create an Account
                    </h5>
                    <p class="text-center small">
                      Enter your personal details to create account
                    </p>
                  </div>

                  <div class="row g-3 needs-validation" novalidate>
                    <div class="col-12">
                      <label for="yourId" class="form-label">ID</label>
                      <input
                        type="id"
                        name="id"
                        class="form-control"
                        id="yourId"
                        v-model="id"
                        placeholder="id"
                        required
                      />
                      <div class="invalid-feedback">
                        Please enter a valid Id!
                      </div>
                    </div>

                    <div class="col-12">
                      <label for="pwd" class="form-label">Password</label>
                      <input
                        type="password"
                        name="pwd"
                        class="form-control"
                        id="pwd"
                        v-model="pwd"
                        placeholder="??????, ?????? ?????? 6?????? ??????"
                        required
                      />
                      <div class="invalid-feedback">
                        Please enter your password!
                      </div>
                    </div>

                    <div class="col-12">
                      <label for="pwd_check" class="form-label">
                        Check Password
                      </label>
                      <input
                        type="password"
                        name="pwd_check"
                        class="form-control"
                        id="pwd_check"
                        v-model="pwd_check"
                        placeholder="???????????? ??????"
                        required
                      />
                      <div class="invalid-feedback">
                        Please enter your password!
                      </div>
                    </div>

                    <div class="col-12">
                      <label for="name" class="form-label">Name</label>
                      <input
                        type="text"
                        name="name"
                        class="form-control"
                        id="name"
                        placeholder="?????? ??????"
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
                        type="email"
                        name="name"
                        class="form-control"
                        placeholder="Email ??????"
                        id="email"
                        v-model="email"
                        required
                      />
                    </div>

                    <div class="col-12">
                      <label for="phone" class="form-label">Phone Number</label>
                      <input
                        type="tel"
                        name="phone"
                        class="form-control"
                        placeholder="010-xxxx-xxxx"
                        pattern="[0-9]{3}-[0-9]{4}-[0-9]{4}"
                        id="phone"
                        v-model="phone"
                        required
                      />
                      <div class="invalid-feedback">
                        Please enter your name!
                      </div>
                    </div>

                    <div class="col-12">
                      <button class="btn btn-primary w-100" @click="join">
                        Create Account
                      </button>
                    </div>
                    <div class="col-12">
                      <p class="small mb-0">
                        Already have an account?
                        <router-link to="/user/login">Log in</router-link>
                      </p>
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
  computed: {
    ...mapGetters(["isLogin"]),
  },
  created() {
    if (this.isLogin) {
      alert("????????? ???????????????.");
      this.$router.push({ name: "HomeView" });
    }
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
        alert("?????? ????????? ????????? ?????????.");
        return;
      }

      if (this.pwd != this.pwd_check) {
        alert("??????????????? ???????????? ????????? ????????? ????????????.");
        return;
      }

      // if (!isUseId) {
      //   alert("????????? ??? ?????? ??????????????????.");
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
            alert("??????????????? ??????????????????. ???????????????.");
            location.href = "/user/login";
          } else {
            alert("??????????????? ?????? ????????????. ?????? ??????????????????.");
          }
        });
    },
  },
};
</script>

<style scoped></style>
