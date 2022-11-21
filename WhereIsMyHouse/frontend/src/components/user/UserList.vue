<template>
  <div class="body">
    <div style="height: 160px"></div>

    <!-- Main Contents -->
    <main class="mt-4 container">
      <!-- contents start -->
      <div class="bg-common-light pt-4">
        <div style="height: 30px"></div>
        <div class="row">
          <div class="col-9 ms-4 ps-4 fs-3 text-common-dark">
            <i class="fa-solid fa-table-list"></i>&nbsp;&nbsp;사용자 목록
          </div>
          <div class="col-2 text-end">
            <router-link
              to="/user/add"
              type="button"
              class="btn btn-dark bg-common-dark"
            >
              회원 등록
            </router-link>
          </div>
        </div>
        <div
          class="row ms-5 mt-4 pb-3 text-common-dark text-center fw-bold fs-5 border-dark border-3 border-bottom"
          style="width: 92%"
        >
          <div class="col-2 text-common-dark">아이디</div>
          <div class="col-2 text-common-dark">이름</div>
          <div class="col-2 text-common-dark">이메일</div>
          <div class="col-2 text-common-dark">전화번호</div>
          <div class="col-2 text-common-dark">관리자</div>
          <div class="col-2 text-common-dark"></div>
        </div>
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

        <div class="mt-5 d-flex justify-content-center">
          <div class="d-flex justify-content-center fs-4" id="list-page"></div>
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
                  placeholder="검색어..."
                />
                <button
                  class="btn btn-dark bg-common-dark"
                  type="button"
                  onclick="search()"
                >
                  검색
                </button>
              </div>
            </div>
          </div>
        </div>

        <div style="height: 70px"></div>
      </div>
    </main>

    <div style="height: 296px"></div>
  </div>
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
        console.log(this.users);
        // this.pages = response.data.page;
      });
      // console.log(this.users);
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

<style scoped>
.body {
  background: #928490;
}
</style>
