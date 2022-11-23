<template>
  <main id="main" class="main">
    <div class="pagetitle">
      <h1>Notice</h1>
      <nav>
        <ol class="breadcrumb">
          <li class="breadcrumb-item">
            <router-link to="/">Home</router-link>
          </li>
          <li class="breadcrumb-item active">
            <router-link to="/notice/list"> Notice </router-link>
          </li>
          <li class="breadcrumb-item active">공지글 수정</li>
        </ol>
      </nav>
    </div>
    <div class="row">
      <div class="col-12">
        <div class="card">
          <div class="card-body">
            <h5 class="card-title"><b>공지글 수정</b></h5>

            <!-- Vertical Form -->
            <div class="row g-3">
              <div class="col-12">
                <label for="title" class="form-label">Title</label>
                <input
                  class="form-control"
                  type="text"
                  id="title"
                  placeholder="제목을 입력하세요"
                  v-model="notice.title"
                />
              </div>
              <div class="col-12">
                <label for="content" class="form-label">Content</label>
                <textarea
                  class="form-control"
                  id="content"
                  rows="10"
                  v-model="notice.content"
                ></textarea>
              </div>

              <div class="text-center">
                <button @click="notice_modify()" class="btn btn-primary">
                  Submit
                </button>
                &nbsp;
                <button @click="notice_cancel()" class="btn btn-secondary">
                  Cancel
                </button>
              </div>
            </div>
            <!-- Vertical Form -->
          </div>
        </div>
      </div>
    </div>
  </main>
</template>

<script>
import axios from "axios";
import { mapGetters } from "vuex";

export default {
  data() {
    return {
      notice: {},
    };
  },
  created() {
    if (!this.isLogin) {
      this.$router.push({ name: "QnaList" });
    }
    const url =
      "http://localhost:9999/rest/notice/detail?no=" + this.$route.params.no;

    axios
      .get(url)
      .then((response) => response.data)
      .then((data) => (this.notice = data.notice));
  },
  methods: {
    notice_modify: function () {
      if (!this.notice.title) {
        alert("제목을 입력하세요. ");
      }
      if (!this.notice.content) {
        alert("내용을 입력하세요.");
      }

      let confirm_write = confirm("공지사항을 수정하시겠습니까?");

      if (confirm_write) {
        const url = "http://localhost:9999/rest/notice/modify";

        axios
          .post(url, {
            no: this.notice.no,
            title: this.notice.title,
            content: this.notice.content,
            memberId: this.memberId,
          })
          .then((response) => response.data)
          .then((data) => {
            console.log(data);
            if (data.check) {
              this.$router.push({
                name: "NoticeDetail",
                params: {
                  no: this.notice.no,
                },
              });
            } else {
              alert("게시글 수정에 실패 했습니다. 다시 시도해주세요.");
            }
          });
      }
    },
    notice_cancel: function () {
      let confirm_cancel = confirm("정말 취소하시겠습니까?");

      if (confirm_cancel)
        this.$router.push({
          name: "NoticeDetail",
          params: {
            no: this.notice.no,
          },
        });
    },
  },
  computed: {
    ...mapGetters(["memberId", "isLogin"]),
  },
};
</script>

<style></style>
