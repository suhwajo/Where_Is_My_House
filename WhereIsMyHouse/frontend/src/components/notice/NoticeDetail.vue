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
          <li class="breadcrumb-item active">상세 조회</li>
        </ol>
      </nav>
    </div>
    <div class="row">
      <div class="col-12">
        <div class="card">
          <div class="card-body">
            <h5 class="card-title row">
              <b class="col-4">Notice 상세 조회</b>
              <div class="col-8 text-end justify-content-end">
                <router-link class="btn btn-dark ms-3" to="/notice/list"
                  >목록</router-link
                >
              </div>
            </h5>
            <div class="bg-common-light pt-4">
              <div
                class="ms-5 mt-4 ps-2 fs-4 text-common-dark"
                id="notice-title"
              >
                {{ notice.title }}
              </div>
              <div class="mx-5"><hr size="3" /></div>
              <div class="row mt-3 fs-6 mb-1 text-common-dark">
                <div class="col-4 pe-5 ms-5" id="notice-author">
                  작성자 : {{ notice.author }}
                </div>
                <div class="col-2"></div>
                <div class="col-2">조회수 : {{ notice.hit }}</div>
                <div class="col-3 ms-2" id="notice-date">
                  {{ notice.date }}
                </div>
              </div>
              <div class="mx-5"><hr size="3" /></div>
              <div class="ps-2 fs-6 m-5">
                <div class="ms-3" id="notice-content">{{ notice.content }}</div>
              </div>
            </div>

            <div class="row">
              <div class="col-12 text-end">
                <div id="list-user-btn" v-show="checkUser">
                  <router-link
                    class="btn btn-outline-success"
                    :to="{ name: 'NoticeModify', params: { no: notice.no } }"
                  >
                    수정
                  </router-link>
                  <button
                    class="btn btn-outline-danger ms-2"
                    @click="notice_delete()"
                  >
                    삭제
                  </button>
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
import { mapGetters } from "vuex";

export default {
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
