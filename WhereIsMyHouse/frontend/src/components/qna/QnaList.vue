<template>
  <div>
    <div style="height: 150px"></div>
    <main class="mt-4 container">
      <div class="bg-common-light pt-4">
        <div style="height: 30px"></div>
        <div class="row">
          <div class="col-9 ms-4 ps-4 fs-3 text-common-dark">
            <i class="fa-solid fa-bullhorn"></i>&nbsp;&nbsp;Q&amp;A
          </div>
          <div class="col-2 text-end">
            <router-link class="btn btn-dark bg-common-dark" to="/qna/write">
              Q&amp;A 작성
            </router-link>
          </div>
        </div>
        <div
          class="row ms-5 mt-4 pb-3 text-common-dark text-center fw-bold fs-5 border-dark border-3 border-bottom"
          style="width: 92%"
        >
          <div class="col-8">제목</div>
          <div class="col-2">작성자</div>
          <div class="col-2">날짜</div>
        </div>
        <list-row
          v-for="(qna, index) in qnas"
          :key="index"
          :no="qna.no"
          :title="qna.title"
          :author="qna.author"
          :date="qna.date"
        >
        </list-row>

        <div class="mt-5 d-flex justify-content-center">
          <div class="d-flex justify-content-center fs-4" id="list-page">
            <span v-for="(paged, index) in pages" :key="index">
              <a
                v-if="paged == page"
                class="text-common-dark mx-2 align-self-center link-dark"
                href="#"
              >
                {{ paged }}
              </a>
              <a
                v-else
                class="text-common-dark mx-2 align-self-center link-dark text-decoration-none"
              >
                {{ paged }}
              </a>
            </span>
          </div>
        </div>

        <div class="col-lg-8 col-md-10 col-sm-12 ms-5 mt-4">
          <div class="row align-self-center mb-2">
            <div class="col-md-2 text-start"></div>
            <div class="col-md-7 offset-3">
              <div class="input-group input-group-sm justify-content-center">
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

        <div style="height: 80px"></div>
      </div>
    </main>
  </div>
</template>

<script>
import axios from "axios";

export default {
  components: {
    "list-row": () => import("@/components/qna/ListRow.vue"),
  },
  data() {
    return {
      keyword: "",
      page: 1,
      qnas: [],
      pages: [],
    };
  },
  methods: {
    getNoticeList: function () {
      // 현재 리시트 번호, 검색어 정보를 바탕으로 공지사항 목록을 불러옴
      const url = "http://localhost:9999/rest/board/list";
      let params = `page=${this.page}&keyword=${this.keyword}`;

      axios.get(url + "?" + params).then((response) => {
        this.qnas = response.data.list;
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
    this.getNoticeList();
  },
};
</script>

<style></style>
