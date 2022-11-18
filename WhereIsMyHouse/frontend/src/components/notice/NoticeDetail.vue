<template>
  <div>
    <div style="height: 150px"></div>

    <!-- Main Contents -->
    <main class="mt-4 container">
      <div class="bg-common-light pt-4">
        <div style="height: 30px"></div>
        <div class="ms-5 mt-4 ps-2 fs-4 text-common-dark" id="notice-title">
          {{ notice.title }}
        </div>
        <div class="mx-5"><hr size="3" /></div>
        <div class="row mt-3 fs-5 mb-1 text-common-dark">
          <div class="col-2 text-center pe-5 ms-3" id="notice-author">
            {{ notice.author }}
          </div>
          <div class="col-7"></div>
          <div class="col-2 ms-4 text-end" id="notice-date">
            {{ notice.date }}
          </div>
        </div>
        <div class="mx-5"><hr size="3" /></div>
        <div class="ps-2 fs-6 m-5">
          <div class="ms-3" id="notice-content">{{ notice.content }}</div>
        </div>

        <div class="mx-5"><hr size="3" /></div>

        <div class="row mt-5">
          <div class="col-2 ps-5">
            <router-link class="btn btn-dark ms-3" to="/notice/list"
              >목록</router-link
            >
          </div>
          <div class="col-9 text-end ms-5">
            <div id="list-user-btn" v-show="checkUser">
              <router-link
                class="btn btn-dark"
                :to="{ name: 'NoticeModify', params: { no: notice.no } }"
              >
                수정
              </router-link>
              <button class="btn btn-dark ms-2" @click="notice_delete()">
                삭제
              </button>
            </div>
          </div>
        </div>

        <comment-write
          :no="notice.no"
          :author="memberId"
          v-show="isLogin"
        ></comment-write>
        <div style="height: 50px"></div>
      </div>
    </main>
  </div>
</template>

<script>
import axios from "axios";
import { mapGetters } from "vuex";

export default {
  components: {
    "comment-write": () =>
      import("@/components/notice/include/CommentWrite.vue"),
  },
  data() {
    return {
      notice: {},
      checkUser: false,
    };
  },
  created() {
    const url =
      "http://localhost:9999/rest/notice/detail?no=" + this.$route.params.no;

    axios
      .get(url)
      .then((response) => response.data)
      .then((data) => {
        this.notice = data.notice;
        this.notice.date = this.notice.date.split(" ")[0];

        if (this.notice.author == this.memberId) {
          this.checkUser = true;
        }
      });
  },
  methods: {
    notice_delete: function () {
      let confirm_delete = confirm("정말 삭제하시겠습니까?");

      if (confirm_delete) {
        const url =
          "http://localhost:9999/rest/notice/delete?no=" + this.notice.no;

        axios
          .delete(url)
          .then((response) => response.data)
          .then((data) => {
            if (data.check) {
              alert("삭제되었습니다.");
              this.$router.push({
                name: "NoticeList",
              });
            } else {
              alert("삭제가 실패 했습니다. 다시 시도해주세요.");
            }
          });
      }
    },
  },
  computed: {
    ...mapGetters(["memberId", "isLogin"]),
  },
};
</script>

<style></style>
