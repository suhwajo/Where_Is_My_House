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
              <router-link
                to="/"
                class="nav-link"
                id="nav-focusArea"
                style="display: none"
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
                  <a class="dropdown-item" id="nav-logout">로그아웃</a>
                </li>
                <li>
                  <router-link
                    :to="{
                      name: 'UserInfo',
                      params: { userId: login },
                    }"
                    class="dropdown-item"
                    id="nav-userInfo"
                    >회원정보</router-link
                  >
                </li>
                <li>
                  <a class="dropdown-item" id="nav-allUsers">전체회원보기</a>
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
import { mapGetters } from "vuex";
export default {
  name: "NaviBar",
  data: function () {
    return {
      login: "",
    };
  },
  created() {
    this.login = this.$session.get("userInfo").userId;
    console.log(this.login);
  },
  computed: {
    ...mapGetters(["memberId", "isLogin"]),
  },
};
</script>

<style scoped>
.nav-link {
  color: #f3e8eb;
}
</style>
