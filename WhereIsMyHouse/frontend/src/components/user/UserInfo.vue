<template>
  <main id="main" class="main">
    <div class="pagetitle">
      <h1>Profile</h1>
      <nav>
        <ol class="breadcrumb">
          <li class="breadcrumb-item"><a href="/">Home</a></li>
          <li class="breadcrumb-item">MyPage</li>
          <li class="breadcrumb-item active">Profile</li>
        </ol>
      </nav>
    </div>
    <!-- End Page Title -->

    <section class="section profile">
      <div class="row">
        <div class="col-xl-4">
          <div class="card">
            <div
              class="card-body profile-card pt-4 d-flex flex-column align-items-center"
            >
              <img
                src="@/assets/profile-img.png"
                alt="Profile"
                class="rounded-circle"
              />
              <h2 class="user-id">{{ user.id }} | {{ userName }}</h2>
            </div>
          </div>
        </div>

        <div class="col-xl-8">
          <div class="card">
            <div class="card-body pt-3">
              <!-- Bordered Tabs -->
              <ul class="nav nav-tabs nav-tabs-bordered">
                <li class="nav-item">
                  <button
                    class="nav-link active"
                    data-bs-toggle="tab"
                    data-bs-target="#profile-overview"
                  >
                    Overview
                  </button>
                </li>

                <li class="nav-item">
                  <button
                    class="nav-link"
                    data-bs-toggle="tab"
                    data-bs-target="#profile-edit"
                  >
                    Edit Profile
                  </button>
                </li>

                <li class="nav-item">
                  <button
                    class="nav-link"
                    data-bs-toggle="tab"
                    data-bs-target="#profile-resign"
                  >
                    Resign
                  </button>
                </li>
              </ul>
              <div class="tab-content pt-2">
                <div
                  class="tab-pane fade show active profile-overview"
                  id="profile-overview"
                >
                  <h5 class="card-title">Profile Details</h5>

                  <div class="row">
                    <div class="col-lg-3 col-md-4 label">ID</div>
                    <div class="col-lg-9 col-md-8 user-id">{{ user.id }}</div>
                  </div>

                  <div class="row">
                    <div class="col-lg-3 col-md-4 label">Password</div>
                    <div class="col-lg-9 col-md-8 user-pw">
                      {{ user.pwd }}
                    </div>
                  </div>

                  <div class="row">
                    <div class="col-lg-3 col-md-4 label">Name</div>
                    <div class="col-lg-9 col-md-8 user-name">
                      {{ user.name }}
                    </div>
                  </div>

                  <div class="row">
                    <div class="col-lg-3 col-md-4 label">Email</div>
                    <div class="col-lg-9 col-md-8 user-addr">
                      {{ user.email }}
                    </div>
                  </div>

                  <div class="row">
                    <div class="col-lg-3 col-md-4 label">Phone Number</div>
                    <div class="col-lg-9 col-md-8 user-phone">
                      {{ user.phoneNum }}
                    </div>
                  </div>
                </div>

                <div class="tab-pane fade profile-edit pt-3" id="profile-edit">
                  <form>
                    <div class="row mb-3">
                      <label class="col-lg-3 col-md-4 col-form-label">ID</label>
                      <div class="col-lg-9 col-md-8 user-id">{{ user.id }}</div>
                    </div>

                    <div class="row mb-3">
                      <label for="pwd" class="col-md-4 col-lg-3 col-form-label"
                        >Password</label
                      >
                      <div class="col-md-8 col-lg-9">
                        <input
                          name="pwd"
                          type="password"
                          class="form-control user-pw"
                          id="user-pw"
                          v-model="user.pwd"
                        />
                      </div>
                    </div>

                    <div class="row mb-3">
                      <label for="name" class="col-md-4 col-lg-3 col-form-label"
                        >Name</label
                      >
                      <div class="col-md-8 col-lg-9">
                        <input
                          name="name"
                          type="text"
                          class="form-control user-name"
                          id="user-name"
                          v-model="user.name"
                        />
                      </div>
                    </div>

                    <div class="row mb-3">
                      <label
                        for="email"
                        class="col-md-4 col-lg-3 col-form-label"
                        >Email</label
                      >
                      <div class="col-md-8 col-lg-9">
                        <input
                          name="email"
                          type="email"
                          class="form-control user-addr"
                          id="user-addr"
                          v-model="user.email"
                        />
                      </div>
                    </div>

                    <div class="row mb-3">
                      <label
                        for="phone"
                        class="col-md-4 col-lg-3 col-form-label"
                        >Phone Number</label
                      >
                      <div class="col-md-8 col-lg-9">
                        <input
                          name="phone"
                          type="text"
                          class="form-control user-phone"
                          id="user-phone"
                          v-model="user.phoneNum"
                        />
                      </div>
                    </div>

                    <div class="text-center">
                      <button @click="editConfirm" class="btn btn-primary">
                        정보 수정
                      </button>
                    </div>
                  </form>
                  <!-- End Profile Edit Form -->
                </div>

                <div class="tab-pane fade pt-3" id="profile-resign">
                  <!-- Resign Form -->
                  <form>
                    <div class="row mb-3">
                      <label
                        for="fullName"
                        class="col-md-4 col-lg-3 col-form-label"
                        >Resign Membership</label
                      >
                      <div class="col-md-8 col-lg-9">
                        <div class="form-check">
                          <label class="form-check-label" for="newProducts">
                            정말로 탈퇴하시겠습니까?
                          </label>
                          <button
                            class="btn btn-primary m-3"
                            @click.prevent="userOut"
                          >
                            Yes
                          </button>
                        </div>
                      </div>
                    </div>
                  </form>
                  <!-- End settings Form -->
                </div>
              </div>
              <!-- End Bordered Tabs -->
            </div>
          </div>
        </div>
      </div>
    </section>
  </main>
</template>

<script>
import axios from "axios";
import { mapActions, mapGetters } from "vuex";

export default {
  name: "UserInfo",
  data() {
    return {
      user: {},
      userName: "",
    };
  },
  computed: {
    ...mapGetters(["memberId", "isLogin"]),
  },
  created() {
    if (!this.isLogin) {
      alert("로그인 후 접근이 가능합니다.");
      this.$router.push({ name: "UserLogin" });
    }
    let userId = this.$route.params.userId;
    if (userId == null) {
      userId = this.$session.get("userInfo").userId;
    } else if (this.memberId != this.$session.get("userInfo").userId) {
      alert("접근이 불가합니다.");
      this.$router.push({ name: "HomeView" });
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
    ...mapActions(["doLogout"]),
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
            this.$router.go();
            // location.href(window.location.href + "?userId=" + this.user.id);
          } else {
            alert("회원 정보 변경에 실패 했습니다. 다시 시도해주세요.");
          }
        });
    },
    userOut() {
      let outConfirm = confirm("정말 탈퇴하시겠습니까??");
      alert(outConfirm);
      if (outConfirm) {
        const url = "http://localhost:9999/rest/user/delete";
        if (this.user.adminAccount) alert("관리자계정은 탈퇴가 불가합니다.");
        else {
          axios
            .delete(url, {
              params: {
                userId: this.user.id,
              },
            })
            .then((response) => response.data)
            .then((data) => {
              if (
                data.check &&
                this.user.id == this.$session.get("userInfo").id
              ) {
                this.doLogout();
                alert("탈퇴하였습니다.");
                location.href = "/";
              } else if (data.check) {
                alert("회원을 삭제하였습니다.");
                location.href = "/user/list";
              } else alert("탈퇴에 실패했습니다. 다시 시도해주세요.");
            });
        }
      }
    },
  },
};
</script>

<style scoped></style>
