<template>
  <main id="main" class="main">
    <div class="container-fluid">
      <div class="pagetitle row justify-content-center">
        <h1>회원 정보 관리</h1>
        <nav>
          <ol class="breadcrumb">
            <li class="breadcrumb-item">관리자 모드</li>
            <li class="breadcrumb-item active">
              <router-link to="/user/list">회원 정보 관리</router-link>
            </li>
            <li class="breadcrumb-item active">회원 정보 수정</li>
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
                <div class="d-flex justify-content-center py-4">
                  <a
                    href="index.html"
                    class="logo d-flex align-items-center w-auto"
                  >
                    <img src="@/assets/logo.png" alt="" />
                    <span class="d-none d-lg-block">관리자 모드</span>
                  </a>
                </div>
                <!-- End Logo -->

                <div class="card mb-3">
                  <div class="card-body">
                    <div class="pt-4 pb-2">
                      <h5 class="card-title text-center pb-0 fs-4">
                        회원 정보 수정
                      </h5>
                    </div>
                    <div
                      id="form-admin-edit"
                      class="row g-3 needs-validation"
                      novalidate
                    >
                      <input type="hidden" name="act" value="memberEdit" />

                      <div class="col-12">
                        <label for="yourId" class="form-label">ID</label>
                        <input
                          type="text"
                          name="id"
                          class="form-control"
                          id="id"
                          v-model="user.id"
                          disabled
                        />
                        <div class="invalid-feedback">
                          Please enter a valid Id!
                        </div>
                      </div>
                      <div id="idcheck-result"></div>

                      <div class="col-12">
                        <label for="pwd" class="form-label">Password</label>
                        <input
                          type="text"
                          name="pwd"
                          class="form-control"
                          id="pwd"
                          v-model="user.pwd"
                          required
                        />
                        <div class="invalid-feedback">
                          Please enter your password!
                        </div>
                      </div>

                      <div class="col-12">
                        <label for="yourName" class="form-label">Name</label>
                        <input
                          type="text"
                          name="name"
                          class="form-control"
                          id="name"
                          v-model="user.name"
                          required
                        />
                        <div class="invalid-feedback">
                          Please enter your name!
                        </div>
                      </div>

                      <div class="col-12">
                        <label for="email" class="form-label">Address</label>
                        <input
                          type="email"
                          name="email"
                          class="form-control"
                          id="email"
                          v-model="user.email"
                          required
                        />
                      </div>

                      <div class="col-12">
                        <label for="phone" class="form-label"
                          >Phone Number</label
                        >
                        <input
                          type="text"
                          name="phone"
                          class="form-control"
                          v-model="user.phoneNum"
                          pattern="[0-9]{3}-[0-9]{4}-[0-9]{4}"
                          id="phone"
                          required
                        />
                        <div class="invalid-feedback">
                          Please enter your phone number!
                        </div>
                      </div>
                      <div class="col-12">
                        <button
                          class="btn btn-primary w-100"
                          id="btn-edit"
                          @click="editConfirm"
                        >
                          회원정보 수정
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
  name: "ModifyUser",
  data() {
    return {
      user: {},
      userName: "",
    };
  },
  computed: {
    ...mapGetters(["memberId", "isLogin", "adminAccount"]),
  },
  created() {
    if (!this.adminAccount) {
      alert("접근이 불가합니다.");
      this.$router.push({ name: "HomeView" });
    }
    let userId = this.$route.params.userId;

    if (userId == null) {
      userId = this.$session.get("nowUser").id;
    }

    if (userId) {
      const url = "http://localhost:9999/rest/user/getUser";
      axios
        .get(url, {
          params: {
            userId: userId,
          },
        })
        .then((response) => response.data)
        .then((data) => {
          if (data) {
            this.user = data;
            this.$session.set("nowUser", data);
            this.userName = this.user.name;
          }
        });
    }
  },
  methods: {
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
      axios
        .put(url, userMod)
        .then((response) => response.data)
        .then((data) => {
          if (data.check) {
            alert("회원 정보 변경에 성공했습니다.");
            this.$router.push({
              name: "UserList",
            });
          } else {
            alert("회원 정보 변경에 실패 했습니다. 다시 시도해주세요.");
          }
        });
    },
  },
};
</script>

<style></style>
