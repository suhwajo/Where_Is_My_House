<template>
  <div>
    <!-- ======= Header ======= -->
    <header id="header" class="header fixed-top d-flex align-items-center">
      <div class="d-flex align-items-center justify-content-between">
        <router-link to="/" class="logo d-flex align-items-center">
          <img src="@/assets/logo_homessafy.png" alt="" />
          <span class="d-none d-lg-block">Where Is My Home</span>
        </router-link>
        <!-- <i class="bi bi-list toggle-sidebar-btn"></i> -->
      </div>
      <!-- End Logo -->

      <!-- <div class="search-bar">
        <form
          class="search-form d-flex align-items-center"
          method="POST"
          action="#"
        >
          <input
            type="text"
            name="query"
            placeholder="Search"
            title="Enter search keyword"
          />
          <button type="submit" title="Search">
            <i class="bi bi-search"></i>
          </button>
        </form>
      </div> -->
      <!-- End Search Bar -->

      <nav class="header-nav ms-auto">
        <ul class="d-flex align-items-center">
          <li class="nav-item d-block d-lg-none">
            <a class="nav-link nav-icon search-bar-toggle" href="#">
              <i class="bi bi-search"></i>
            </a>
          </li>
          <!-- End Search Icon-->

          <li class="nav-item pe-4" v-show="!isLogin">
            <router-link to="/user/join" class="nav-link collapsed">
              <i class="bi bi-card-list"></i>
              <span>Register</span>
            </router-link>
          </li>
          <!-- End Register Page Nav -->
          <li class="nav-item pe-4" v-show="!isLogin">
            <router-link to="/user/login" class="nav-link collapsed">
              <i class="bi bi-box-arrow-in-right"></i>
              <span>Login</span>
            </router-link>
          </li>

          <!-- End Profile Nav -->

          <li class="nav-item dropdown pe-3" v-show="isLogin">
            <a
              class="nav-link nav-profile d-flex align-items-center pe-0"
              href="#"
              data-bs-toggle="dropdown"
            >
              <span class="d-none d-md-block dropdown-toggle ps-2">
                {{ memberId }}</span
              > </a
            ><!-- End Profile Iamge Icon -->

            <ul
              class="dropdown-menu dropdown-menu-end dropdown-menu-arrow profile"
            >
              <li class="dropdown-header">
                <h6>{{ memberId }}</h6>
              </li>
              <li>
                <hr class="dropdown-divider" />
              </li>

              <li>
                <router-link
                  :to="{
                    name: 'UserMyInfo',
                    params: { userId: memberId },
                  }"
                  class="dropdown-item d-flex align-items-center"
                  id="nav-userInfo"
                >
                  <i class="bi bi-person"></i>
                  <span>My Profile</span>
                </router-link>
              </li>
              <li>
                <hr class="dropdown-divider" />
              </li>

              <li v-show="adminAccount">
                <router-link
                  class="dropdown-item d-flex align-items-center"
                  to="/user/list"
                >
                  <i class="bi bi-gear"></i>
                  <span>View All Members</span>
                </router-link>
              </li>
              <li v-show="adminAccount">
                <hr class="dropdown-divider" />
              </li>

              <!-- <li>
                <a
                  class="dropdown-item d-flex align-items-center"
                  href="pages-faq.html"
                >
                  <i class="bi bi-question-circle"></i>
                  <span>Need Help?</span>
                </a>
              </li>
              <li>
                <hr class="dropdown-divider" />
              </li> -->

              <li>
                <button
                  class="dropdown-item d-flex align-items-center"
                  @click="logout"
                >
                  <i class="bi bi-box-arrow-right"></i>
                  <span>Log Out</span>
                </button>
              </li>
            </ul>
            <!-- End Profile Dropdown Items -->
          </li>
          <!-- End Profile Nav -->
        </ul>
      </nav>
      <!-- End Icons Navigation -->
    </header>
    <!-- End Header -->

    <!-- ======= Sidebar ======= -->
    <aside id="sidebar" class="sidebar">
      <ul class="sidebar-nav" id="sidebar-nav">
        <li class="nav-item">
          <router-link to="/home" class="nav-link collapsed" id="nav-findHome">
            <i class="bi bi-grid"></i>
            <span>매매 검색</span>
          </router-link>
        </li>
        <!-- End Dashboard Nav -->

        <li class="nav-item">
          <router-link class="nav-link collapsed" to="/notice" id="nav-notice">
            <i class="bi bi-person"></i>
            <span>공지사항</span>
          </router-link>
        </li>
        <!-- End Profile Page Nav -->

        <li class="nav-item">
          <router-link class="nav-link collapsed" to="/news" id="nav-todayNews">
            <i class="bi bi-journal-check"></i>
            <span>오늘의 뉴스</span>
          </router-link>
        </li>
        <!-- End F.A.Q Page Nav -->

        <li class="nav-item" v-show="isLogin">
          <router-link
            class="nav-link collapsed"
            to="/user/region"
            id="nav-focusArea"
          >
            <i class="bi bi-journal-bookmark"></i>
            <span>관심 지역</span>
          </router-link>
        </li>
        <!-- End F.A.Q Page Nav -->

        <li class="nav-item">
          <router-link class="nav-link collapsed" to="/qna" id="nav-board">
            <i class="bi bi-clipboard"></i>
            <span>Q&amp;A</span>
          </router-link>
        </li>
        <!-- End Contact Page Nav -->
      </ul>
    </aside>
    <!-- End Sidebar-->
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
/* .nav-link {
  color: #f3e8eb;
} */
</style>
