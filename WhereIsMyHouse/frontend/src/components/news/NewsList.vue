<template>
  <main id="main" class="main">
    <div class="pagetitle">
      <h1>오늘의 뉴스</h1>
      <nav>
        <ol class="breadcrumb">
          <li class="breadcrumb-item">
            <router-link to="/">Home</router-link>
          </li>
          <li class="breadcrumb-item active">오늘의 뉴스</li>
        </ol>
      </nav>
    </div>

    <div class="row">
      <div class="col-12">
        <div class="card">
          <div class="card-body">
            <h5 class="card-title row">
              <b class="col-4">오늘의 부동산 뉴스</b>
            </h5>
            <table class="table table-hover">
              <thead>
                <tr>
                  <th scope="col">no</th>
                  <th scope="col">제목</th>
                  <th scope="col">날짜</th>
                </tr>
              </thead>
              <tbody>
                <list-row
                  v-for="(article, index) in articles"
                  :key="index"
                  :no="index"
                  :title="article.newsTitle"
                  :link="article.newsLink"
                  :date="article.newsDate"
                >
                </list-row>
              </tbody>
            </table>
            <div style="height: 80px"></div>
            <nav aria-label="Page navigation example">
              <ul class="pagination justify-content-center" id="list-page">
                <span v-for="(paged, index) in pages" :key="index">
                  <li v-if="paged == page" class="page-item active">
                    <a class="page-link" :href="'/notice/list?page=' + paged">{{
                      paged
                    }}</a>
                  </li>
                  <li v-else class="page-item">
                    <a class="page-link" :href="'/notice/list?page=' + paged">{{
                      paged
                    }}</a>
                  </li>
                </span>
              </ul>
            </nav>
            <div class="col-lg-8 col-md-10 col-sm-12 ms-5 mt-4">
              <div class="row align-self-center mb-2">
                <div class="col-md-2 text-start"></div>
                <div class="col-md-7 offset-3">
                  <div
                    class="input-group input-group-sm justify-content-center"
                  >
                    뉴스는 한시간마다 업데이트됩니다.
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </main>
</template>

<script>
import axios from "axios";

export default {
  components: {
    "list-row": () => import("@/components/news/NewsListRow.vue"),
  },
  data() {
    return {
      keyword: "",
      page: 1,
      articles: [],
      pages: [],
    };
  },
  methods: {
    getNewsList: function () {
      const url = "http://localhost:9999/rest/news/list";

      axios
        .get(url)
        .then((response) => response.data.news)
        .then((data) => {
          this.articles = data;
          for (var i = 0; i < this.articles.length; i++) {
            var text = this.articles[i].newsTitle;
            text = text.replace(
              /<(\/)?([a-zA-Z]*)(\s[a-zA-Z]*=[^>]*)?(\s)*(\/)?>/gi,
              ""
            );
            this.articles[i].newsTitle = text;
          }
        });
    },
  },
  created() {
    // if (this.$route.query.page != null) this.page = this.$route.query.page;
    this.getNewsList();
  },
};
</script>

<style></style>
