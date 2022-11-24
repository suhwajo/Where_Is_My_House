<template>
  <div class="container-main">
    <!-- Main Contents -->
    <main id="main" class="main">
      <!-- 메인 배경 부분 -->
      <div>
        <div class="py-4">
          <div class="text-common-light text-center display-3 mb-5">
            <h1 style="size: 50px">
              <span class="d-none d-lg-block">Where Is My Home</span>
            </h1>
          </div>
          <div class="text-common-light text-center display-5">
            <h2>
              <span class="d-none d-lg-block">Suwha &amp; Huijin</span>
            </h2>
          </div>
        </div>
        <!-- 메인 아래 부분 -->
        <div class="row">
          <!-- 공지사항 부분 -->
          <div class="col-6">
            <div class="card">
              <div class="card-body">
                <h5 class="card-title">
                  <b
                    ><router-link to="/notice/list" style="color: #012970"
                      >공지사항</router-link
                    ></b
                  >
                </h5>
                <table class="table table-hover">
                  <thead>
                    <tr>
                      <th scope="col"></th>
                      <th scope="col"></th>
                      <th scope="col"></th>
                      <th scope="col"></th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="(notice, index) in notices" :key="index">
                      <td scope="row"></td>
                      <td>
                        <router-link
                          :to="{
                            name: 'NoticeDetail',
                            params: { no: notice.no },
                          }"
                          >{{ notice.title }}</router-link
                        >
                      </td>
                      <td>{{ notice.author }}</td>
                      <td>{{ notice.date.split(" ")[0] }}</td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
          <!-- 오늘의 뉴스 부분 -->
          <div class="col-6">
            <div class="card">
              <div class="card-body">
                <h5 class="card-title">
                  <b
                    ><router-link to="/news" style="color: #012970"
                      >오늘의 뉴스</router-link
                    ></b
                  >
                </h5>
                <br />

                <table class="table table-hover">
                  <thead>
                    <tr>
                      <th scope="col"></th>
                      <th scope="col"></th>
                      <th scope="col"></th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="(newsone, index) in news" :key="index">
                      <td scope="row"></td>
                      <td>
                        <a :href="newsone.link" target="_blank">{{
                          newsone.newsTitle
                        }}</a>
                      </td>
                      <td>{{ newsone.newsDate }}</td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
          <!-- <div class="col-6 shadow py-5" id="container-todayNews">
            <div class="text-center text-common-dark fw-bold fs-3 mb-5">
              오늘의 뉴스
            </div>
            <div class="text-center fs-5 mb-4">
              <a>제목제목제목</a>
            </div>
          </div> -->
        </div>
      </div>
    </main>
  </div>
</template>

<script>
import axios from "axios";

// @ is an alias to /src
export default {
  name: "HomeView",
  data() {
    return {
      notices: [],
      news: [],
    };
  },
  created() {
    this.getNoticeList();
    this.getNewsList();
  },
  methods: {
    getNoticeList: function () {
      // 현재 리시트 번호, 검색어 정보를 바탕으로 공지사항 목록을 불러옴
      const url = "http://localhost:9999/rest/notice/list";
      let params = `page=1`;

      axios.get(url + "?" + params).then((response) => {
        this.notices = response.data.list.slice(0, 3);
      });
    },
    getNewsList: function () {
      const url = "http://localhost:9999/rest/news/list";

      axios
        .get(url)
        .then((response) => response.data.news)
        .then((data) => {
          this.articles = data.slice(0, 3);
        });
    },
  },
};
</script>

<style scoped>
.container-main {
  width: 100%;
  background-image: url("@/assets/city.jpg");
}
</style>
