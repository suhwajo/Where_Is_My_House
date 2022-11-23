<template>
  <main id="main" class="main">
    <div class="pagetitle">
      <h1>Q&amp;A</h1>
      <nav>
        <ol class="breadcrumb">
          <li class="breadcrumb-item">
            <router-link to="/">Home</router-link>
          </li>
          <li class="breadcrumb-item active">공지사항</li>
        </ol>
      </nav>
    </div>

    <div class="row">
      <div class="col-12">
        <div class="card">
          <div class="card-body">
            <h5 class="card-title row">
              <b class="col-4">공지사항</b>
              <div class="col-8 text-end justify-content-end">
                <router-link
                  class="btn btn-dark bg-common-dark"
                  to="/notice/write"
                >
                  공지 작성
                </router-link>
              </div>
            </h5>
            <table class="table table-hover">
              <thead>
                <tr>
                  <th scope="col"></th>
                  <th scope="col">제목</th>
                  <th scope="col">작성자</th>
                  <th scope="col">조회수</th>
                  <th scope="col">날짜</th>
                </tr>
              </thead>
              <tbody>
                <list-row
                  v-for="(notice, index) in notices"
                  :key="index"
                  :no="notice.no"
                  :title="notice.title"
                  :hit="notice.hit"
                  :author="notice.author"
                  :date="notice.date"
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
                    <input
                      type="text"
                      class="form-control-sm"
                      id="keyword"
                      v-model="keyword"
                      placeholder="검색어..."
                    />
                    <button
                      class="btn btn-dark bg-common-dark"
                      type="button"
                      @click="search()"
                    >
                      검색
                    </button>
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
    "list-row": () => import("@/components/notice/ListRow.vue"),
  },
  data() {
    return {
      keyword: "",
      page: 1,
      notices: [],
      pages: [],
    };
  },
  methods: {
    getNoticeList: function () {
      // 현재 리시트 번호, 검색어 정보를 바탕으로 공지사항 목록을 불러옴
      const url = "http://localhost:9999/rest/notice/list";
      let params = `page=${this.page}&keyword=${this.keyword}`;

      axios.get(url + "?" + params).then((response) => {
        this.notices = response.data.list;
        this.pages = response.data.page;
      });
    },
    search: function () {
      if (!this.keyword) {
        alert("검색어를 입력해주세요.");
        return;
      }
      this.getNoticeList();
    },
  },
  created() {
    if (this.$route.query.page != null) this.page = this.$route.query.page;
    this.getNoticeList();
  },
};
</script>

<style></style>
