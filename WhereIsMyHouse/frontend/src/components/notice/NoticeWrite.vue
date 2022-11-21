<template>
  <div>
    <!-- Main Contents -->
    <main class="mt-4 container">
      <div class="bg-common-light pt-4">
        <div style="height: 30px"></div>
        <div class="ps-2 fs-3 ms-5">공지사항&nbsp;작성</div>
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
                v-model="title"
              />
            </div>
            <textarea
              class="form-control"
              id="content"
              rows="10"
              v-model="content"
            ></textarea>
          </div>
        </div>

        <div class="text-center m-4">
          <button class="btn btn-dark" @click="notice_write()">작성</button>
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
      title: "",
      content: "",
    };
  },
  methods: {
    notice_write: function () {
      if (!this.title) {
        alert("제목을 입력하세요.");
        return;
      }
      if (!this.content) {
        alert("내용을 입력하세요.");
        return;
      }

      let confirm_write = confirm("공지사항을 작성하시겠습니까?");

      if (confirm_write) {
        const url = "http://localhost:9999/rest/notice/write";

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
                name: "NoticeList",
              });
            } else {
              alert("게시글 작성에 실패 했습니다. 다시 시도해주세요.");
            }
          });
      }
    },
    notice_cancel: function () {
      let confirm_cancel = confirm("정말 취소하시겠습니까?");

      if (confirm_cancel)
        this.$router.push({
          name: "NoticeList",
        });
    },
  },
  computed: {
    ...mapGetters(["memberId", "isLogin"]),
  },
};
</script>

<style></style>
