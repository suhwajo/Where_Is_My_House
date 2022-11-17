<template>
  <div>
    <div style="height: 150px"></div>

    <!-- Main Contents -->
    <main class="mt-4 container">
      <div class="bg-common-light pt-4">
        <div style="height: 30px"></div>
        <div class="ms-5 mt-4 ps-2 fs-4 text-common-dark" id="notice-title">
          {{ qna.title }}
        </div>
        <div class="mx-5"><hr size="3" /></div>
        <div class="row mt-3 fs-5 mb-1 text-common-dark">
          <div class="col-2 text-center pe-5 ms-3" id="notice-author">
            {{ qna.author }}
          </div>
          <div class="col-7"></div>
          <div class="col-2 ms-4 text-end" id="notice-date">
            {{ qna.date }}
          </div>
        </div>
        <div class="mx-5"><hr size="3" /></div>
        <div class="ps-2 fs-6 m-5">
          <div class="ms-3" id="notice-content">{{ qna.content }}</div>
        </div>

        <div class="mx-5"><hr size="3" /></div>

        <div class="row mt-5">
          <div class="col-2 ps-5">
            <router-link class="btn btn-dark ms-3" to="/qna/list"
              >목록</router-link
            >
          </div>
          <div class="col-9 text-end ms-5">
            <div id="list-user-btn" v-show="checkUser">
              <router-link
                class="btn btn-dark"
                :to="{ name: 'QnaModify', params: { no: qna.no } }"
              >
                수정
              </router-link>
              <button class="btn btn-dark ms-2" @click="qna_delete()">
                삭제
              </button>
            </div>
          </div>
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
      qna: {},
      checkUser: false,
    };
  },
  created() {
    const url =
      "http://localhost:9999/rest/board/detail?no=" + this.$route.params.no;

    axios
      .get(url)
      .then((response) => response.data)
      .then((data) => {
        this.qna = data.board;
        this.qna.date = this.qna.date.split(" ")[0];

        if (this.qna.author == this.memberId) {
          this.checkUser = true;
        }
      });
  },
  methods: {
    qna_delete: function () {
      let confirm_delete = confirm("정말 삭제하시겠습니까?");

      if (confirm_delete) {
        const url = "http://localhost:9999/rest/board/delete?no=" + this.qna.no;

        axios
          .delete(url)
          .then((response) => response.data)
          .then((data) => {
            if (data.check) {
              alert("삭제되었습니다.");
              this.$router.push({
                name: "QnaList",
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
