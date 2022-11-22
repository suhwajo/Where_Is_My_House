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
          <li class="breadcrumb-item active">상세 조회</li>
        </ol>
      </nav>
    </div>
    <div class="row">
      <div class="col-12">
        <div class="card">
          <div class="card-body">
            <h5 class="card-title row">
              <b class="col-4">Q&amp;A 상세 조회</b>
              <div class="col-8 text-end justify-content-end">
                <router-link class="btn btn-dark ms-3" to="/qna/list"
                  >목록</router-link
                >
              </div>
            </h5>
            <div class="bg-common-light pt-4">
              <div
                class="ms-5 mt-4 ps-2 fs-4 text-common-dark"
                id="notice-title"
              >
                {{ qna.title }}
              </div>
              <div class="mx-5"><hr size="3" /></div>
              <div class="row mt-3 fs-6 mb-1 text-common-dark">
                <div class="col-4 pe-5 ms-5" id="notice-author">
                  작성자 : {{ qna.author }}
                </div>
                <div class="col-2"></div>
                <div class="col-2">조회수 : {{ qna.hit }}</div>
                <div class="col-3 ms-2" id="notice-date">
                  {{ qna.date }}
                </div>
              </div>
              <div class="mx-5"><hr size="3" /></div>
              <div class="ps-2 fs-6 m-5">
                <div class="ms-3" id="notice-content">{{ qna.content }}</div>
              </div>
            </div>

            <div class="row">
              <div class="col-12 text-end">
                <div id="list-user-btn" v-show="checkUser">
                  <router-link
                    class="btn btn-outline-success"
                    :to="{ name: 'QnaModify', params: { no: qna.no } }"
                  >
                    수정
                  </router-link>
                  <button
                    class="btn btn-outline-danger ms-2"
                    @click="qna_delete()"
                  >
                    삭제
                  </button>
                </div>
              </div>
            </div>
            <!-- <div class="row mt-5">
              <div class="col-9 text-end ms-5">
                
              </div>
            </div> -->
          </div>
        </div>
        <comment-write
          :no="qna.no"
          :author="memberId"
          v-show="isLogin"
        ></comment-write>

        <section class="section dashboard">
          <div class="row">
            <comment-row
              v-for="comment in comments"
              :key="comment.id"
              :qna_no="qna.no"
              :comment="comment"
            ></comment-row>
          </div>
        </section>
      </div>
    </div>
  </main>
</template>

<script>
import axios from "axios";
import { mapGetters } from "vuex";

export default {
  components: {
    "comment-write": () => import("@/components/qna/include/CommentWrite.vue"),
    "comment-row": () => import("@/components/qna/include/CommentRow.vue"),
  },
  data() {
    return {
      qna: {},
      checkUser: false,
      comments: [],
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
        this.comments = data.comments;

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
