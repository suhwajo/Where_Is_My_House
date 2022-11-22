<template>
  <div id="app">
    <header-nav />
    <router-view />
    <footer-nav />
  </div>
</template>

<script>
//import Vue from "vue";
import HeaderNav from "@/components/include/HeaderNav.vue";
import FooterNav from "@/components/include/FooterNav.vue";
// import js1 from "@/assets/vendor/apexcharts/apexcharts.min.js";
// import js2 from "@/assets/vendor/bootstrap/js/bootstrap.bundle.min.js";
// import js3 from "@/assets/vendor/chart.js/chart.min.js";
// import js4 from "@/assets/vendor/echarts/echarts.min.js";
// import js5 from "@/assets/vendor/quill/quill.min.js";
// import js6 from "@/assets/vendor/simple-datatables/simple-datatables.js";
// import js7 from "@/assets/vendor/tinymce/tinymce.min.js";
// import js8 from "@/assets/vendor/php-email-form/validate.js";

// Vue.use(js1);
// Vue.use(js2);
// Vue.use(js3);
// Vue.use(js4);
// Vue.use(js5);
// Vue.use(js6);
// Vue.use(js7);
// Vue.use(js8);

export default {
  components: {
    HeaderNav,
    FooterNav,
  },
  metaInfo: {
    title: "구해줘 홈즈!",
    meta: [
      { charset: "utf-8" },
      // {http-equiv="X-UA-Compatible" content="IE=edge"}
      { name: "viewport" },
      { content: "width=device-width, initial-scale=1.0" },
    ],
    link: [
      // {
      //   href: "https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css",
      //   rel: "stylesheet",
      //   integrity:
      //     "sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3",
      //   crossorigin: "anonymous",
      // },
      {
        href: "https://fonts.gstatic.com",
        rel: "preconnect",
      },
      {
        href: "https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i",
        rel: "stylesheet",
      },
    ],
    script: [
      // {
      //   src: "https://kit.fontawesome.com/c452641c94.js",
      //   crossorigin: "anonymous",
      // },
      {
        type: "text/javascript",
        src: "//dapi.kakao.com/v2/maps/sdk.js?appkey=59289473baf7e9e908c84c45d32fcda2&libraries=services",
      },
      {
        src: "https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js",
        integrity:
          "sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p",
        crossorigin: "anonymous",
      },
      // {
      //   type: "text/javascript",
      //   src: "../assets/js/main.js",
      // },
    ],
  },
  methods: {
    loginCheck() {
      console.log("loginCheck");
      const url = "/rest/user/isLogin";
      fetch(url)
        .then((response) => response.json())
        .then((data) => this.loginHandling(data))
        .catch(this.loginHandling(null));
    },
    loginHandling(data) {
      console.log(data);

      if (data) {
        let userMenus = document.querySelectorAll(".user-menu");

        userMenus.forEach((item) => {
          item.style.display = "";
        });

        document.querySelector("#login").style.display = "none";
        if (data.adminAccount) {
          document.querySelector("#nav-allUsers").style.display = "";
        } else {
          document.querySelector("#nav-allUsers").style.display = "none";
        }
      } else {
        let userMenus = document.querySelectorAll(".user-menu");

        userMenus.forEach((item) => {
          item.style.display = "none";
        });

        document.querySelector("#login").style.display = "";
      }
    },
    logout() {
      const url = "/rest/user/logout";

      fetch(url)
        .then((response) => response.json())
        .then((data) => {
          if (data.check) {
            console.log("logout합니다 ");
            console.log(data);
            this.loginCheck();
          } else {
            console.log("로그아웃에 실패했습니다. 다시 시도해주세요.");
          }
        });
    },
  },
};
</script>

<style>
@import "../src/assets/vendor/bootstrap/css/bootstrap.min.css";
@import "../src/assets/vendor/bootstrap-icons/bootstrap-icons.css";
@import "../src/assets/vendor/boxicons/css/boxicons.min.css";
@import "../src/assets/vendor/quill/quill.snow.css";
@import "../src/assets/vendor/quill/quill.bubble.css";
@import "../src/assets/vendor/remixicon/remixicon.css";
@import "../src/assets/vendor/simple-datatables/style.css";
@import "../src/assets/css/style.css";
</style>
