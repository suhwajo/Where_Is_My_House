<template>
  <div>
    <!-- 네비게이션 바 -->
    <nav class="navbar navbar-expand-lg fixed-top">
      <div class="container">
        <!-- 로고 -->
        <router-link to="/" class="navbar-brand" id="nav-index">
          <img src="@/assets/logo.png" alt="" width="70px" />
        </router-link>
        <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#mynavbar"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="mynavbar">
          <!-- 네비게이션 메뉴들 -->
          <ul class="navbar-nav ms-auto">
            <li class="nav-item">
              <router-link to="/home" class="nav-link" id="nav-findHome"
                >매매검색</router-link
              >
            </li>
            <li class="nav-item ms-3">
              <router-link to="/notice" class="nav-link" id="nav-notice"
                >공지사항</router-link
              >
            </li>
            <li class="nav-item ms-3">
              <router-link to="/news" class="nav-link" id="nav-todayNews"
                >오늘의뉴스</router-link
              >
            </li>
            <li class="nav-item ms-3">
              <router-link to="/qna" class="nav-link" id="nav-board"
                >QnA</router-link
              >
            </li>
            <li class="nav-item ms-3 user-menu" v-show="isLogin">
              <router-link to="/user/region" class="nav-link" id="nav-focusArea"
                >관심지역</router-link
              >
            </li>
            <li class="nav-item ms-3" id="login" v-show="!isLogin">
              <router-link to="/user/login" class="nav-link" id="nav-login"
                >로그인</router-link
              >
            </li>
            <li class="nav-item ms-3 dropdown user-menu" v-show="isLogin">
              <a
                class="btn dropdown-toggle nav-link"
                href="#"
                role="button"
                id="dropdownMenuLink"
                data-bs-toggle="dropdown"
                aria-expanded="false"
              >
                <i class="fa-solid fa-user"></i>
              </a>
              <ul class="dropdown-menu dropdown-menu-dark">
                <li>
                  <a class="dropdown-item" @click="logout" id="nav-logout"
                    >로그아웃</a
                  >
                </li>
                <!--수정요망 : 로딩시 바로 userId를 읽어와야하는데 빈칸이므로 warning 발생한다.-->
                <li>
                  <router-link
                    :to="{
                      name: 'UserInfo',
                      params: { userId: memberId },
                    }"
                    class="dropdown-item"
                    id="nav-userInfo"
                    >{{ memberId }}회원정보</router-link
                  >
                </li>
                <li>
                  <router-link
                    to="/user/list"
                    class="dropdown-item"
                    id="nav-allUsers"
                    v-show="adminAccount"
                    >전체회원보기</router-link
                  >
                </li>
              </ul>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
export default {
  name: "NaviBar",
  data: function () {
    return {
      login: "",
    };
  },
  created() {
    if (this.$session.get("userInfo"))
      this.login = this.$session.get("userInfo").userId;
  },
  computed: {
    ...mapGetters(["memberId", "isLogin", "adminAccount"]),
  },
  methods: {
    ...mapActions(["doLogout"]),
    logout: function () {
      this.login = "";
      this.$store.dispatch("doLogout").then(() => {
        alert("로그아웃합니다.");
        this.$session.destroy();
        console.log(this.$router);
        if (this.$router.currentRoute.name != "HomeView")
          this.$router.push("/");
      });
      //this.$router.push("/"));
      // this.$router.go();
    },
  },
};
</script>

<style scoped>
.nav-link {
  color: #f3e8eb;
}
</style>
