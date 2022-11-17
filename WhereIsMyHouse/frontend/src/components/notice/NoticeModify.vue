<template>
  <div>
    <div style="height: 150px"></div>

    <!-- Main Contents -->
    <main class="mt-4 container">
      <div class="bg-common-light pt-4">
        <div style="height: 30px"></div>
        <div class="ps-2 fs-3 ms-5">공지사항&nbsp;수정</div>
        <div class="mx-5"><hr size="3" /></div>

        <div class="row">
          <div class="col-1"></div>
          <div class="my-5 col-10">
            <div class="mb-3">
              <input
                class="form-control"
                type="text"
                id="title"
                placeholder="제목을 입력하세요"
                v-model="notice.title"
              />
            </div>
            <textarea
              class="form-control"
              id="content"
              rows="10"
              v-model="notice.content"
            ></textarea>
          </div>
        </div>

        <div class="text-center m-4">
          <button class="btn btn-dark" @click="notice_modify()">작성</button>
          <button class="btn btn-dark ms-2" @click="notice_cancel()">
            취소
          </button>
        </div>

        <div style="height: 50px"></div>
      </div>
    </main>
  </div>
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

      let confirm_write = confirm("공지사항을 작성하시겠습니까?");

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
