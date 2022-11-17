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
            <button
              type="button"
              class="btn btn-dark bg-common-dark"
              onclick="add_user()"
            >
              회원 등록
            </button>
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
        <div id="list-user"></div>

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
export default {
  name: "UserList",
};
</script>

<style scoped>
.body {
  background: #928490;
}
</style>
