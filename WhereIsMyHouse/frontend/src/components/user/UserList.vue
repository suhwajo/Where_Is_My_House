<template>
  <main id="main" class="main">
    <div class="container-fluid">
      <div class="pagetitle row justify-content-center">
        <h1>회원 정보 관리</h1>
        <nav>
          <ol class="breadcrumb">
            <li class="breadcrumb-item">관리자 모드</li>
            <li class="breadcrumb-item active">회원 정보 관리</li>
          </ol>
        </nav>
      </div>
      <!-- End Page Title -->

      <div class="row justify-content-center">
        <div class="col">
          <div class="card overflow-auto">
            <div class="card-body">
              <h5 class="card-title row">
                <b class="col">사용자 목록</b>
              </h5>

              <!-- Default Accordion -->
              <table class="table">
                <thead>
                  <tr>
                    <th scope="col">아이디</th>
                    <th scope="col">이름</th>
                    <th scope="col">전화번호</th>
                    <th scope="col">Email</th>
                    <th scope="col">관리자</th>
                    <th scope="col">EDIT</th>
                  </tr>
                </thead>
                <tbody>
                  <list-row
                    v-for="(user, index) in users"
                    :key="index"
                    :id="user.id"
                    :name="user.name"
                    :email="user.email"
                    :phoneNum="user.phoneNum"
                    :isAdmin="user.adminAccount"
                  >
                  </list-row>
                </tbody>
              </table>
              <div class="mt-5 d-flex justify-content-center">
                <div
                  class="d-flex justify-content-center fs-4"
                  id="list-page"
                ></div>
              </div>

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
                        placeholder="검색어..."
                        v-model="keyword"
                      />
                      <button
                        class="btn btn-dark bg-common-dark"
                        type="button"
                        @click="search"
                      >
                        검색
                      </button>
                    </div>
                  </div>
                </div>
              </div>

              <router-link
                to="/user/add"
                class="btn btn-warning rounded-pill btn-block text-white"
              >
                <b>회원 등록</b>
              </router-link>
            </div>
            <!-- End Default Accordion Example -->
          </div>
        </div>
      </div>
    </div>
  </main>
</template>

<script>
import axios from "axios";

export default {
  name: "UserList",

  components: {
    "list-row": () => import("@/components/user/ListRow.vue"),
  },
  data() {
    return {
      keyword: "",
      page: 1,
      users: [],
      pages: [],
    };
  },
  methods: {
    getUserList: function () {
      // 현재 리시트 번호, 검색어 정보를 바탕으로 공지사항 목록을 불러옴
      const url = "http://localhost:9999/rest/user/list";
      // let params = `page=${this.page}&keyword=${this.keyword}`;

      axios.get(url).then((response) => {
        this.users = response.data.list;
        // this.pages = response.data.page;
      });
    },
    search: function () {
      if (!this.keyword) {
        alert("검색어를 입력해주세요.");
        return;
      }
      this.getUserList();
    },
  },
  created() {
    this.getUserList();
  },
};
</script>

<style scoped></style>
