<template>
  <main id="main" class="main">
    <div class="pagetitle">
      <h1>Q&amp;A</h1>
      <nav>
        <ol class="breadcrumb">
          <li class="breadcrumb-item">
            <router-link to="/">Home</router-link>
          </li>
          <li class="breadcrumb-item active">
            <router-link to="/qna/list"> Q&amp;A </router-link>
          </li>
          <li class="breadcrumb-item active">Q&amp;A&nbsp;작성</li>
        </ol>
      </nav>
    </div>
    <div class="row">
      <div class="col-12">
        <div class="card">
          <div class="card-body">
            <h5 class="card-title"><b>Q&amp;A&nbsp;작성</b></h5>

            <!-- Vertical Form -->
            <div class="row g-3">
              <div class="col-12">
                <label for="title" class="form-label">Title</label>
                <input
                  class="form-control"
                  type="text"
                  id="title"
                  placeholder="제목을 입력하세요"
                  v-model="title"
                />
              </div>
              <div class="col-12">
                <label for="content" class="form-label">Content</label>
                <textarea
                  class="form-control"
                  id="content"
                  rows="10"
                  v-model="content"
                ></textarea>
              </div>

              <div class="text-center">
                <button @click="qna_write()" class="btn btn-primary">
                  Submit
                </button>
                &nbsp;
                <button @click="qna_cancel()" class="btn btn-secondary">
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
      title: "",
      content: "",
    };
  },
  methods: {
    qna_write: function () {
      if (!this.title) {
        alert("제목을 입력하세요.");
        return;
      }
      if (!this.content) {
        alert("내용을 입력하세요.");
        return;
      }

      let confirm_write = confirm("Q&A 을 작성하시겠습니까?");

      if (confirm_write) {
        const url = "http://localhost:9999/rest/board/write";

        axios
          .post(url, {
            title: this.title,
            content: this.content,
            memberId: this.memberId,
          })
          .then((response) => response.data)
          .then((data) => {
            if (data.check) {
              this.$router.push({
                name: "QnaList",
              });
            } else {
              alert("Q&A 작성에 실패 했습니다. 다시 시도해주세요.");
            }
          });
      }
    },
    qna_cancel: function () {
      let confirm_cancel = confirm("정말 취소하시겠습니까?");

      if (confirm_cancel)
        this.$router.push({
          name: "QnaList",
        });
    },
  },
  computed: {
    ...mapGetters(["memberId", "isLogin"]),
  },
};
</script>

<style></style>
