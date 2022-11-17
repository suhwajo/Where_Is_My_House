<template>
  <div>
    <!-- Main Contents -->
    <main class="mt-4">
      <!-- 메인 배경 부분 -->
      <div class="container-main">
        <div style="height: 600px"></div>
        <div class="container-main-text bg-dark py-4">
          <div class="text-common-light text-center display-3 mb-5">
            Find Your Homes
          </div>
          <div class="text-common-light text-center display-5">
            구해줘 홈즈 !
          </div>
        </div>
        <div style="height: 250px"></div>
      </div>

      <!-- 메인 아래 부분 -->
      <div class="row">
        <!-- 공지사항 부분 -->
        <div class="col-6 shadow py-5" id="container-notice">
          <div class="text-center text-common-dark fw-bold fs-3 mb-5">
            공지 사항
          </div>
          <div id="notice"></div>
        </div>
        <!-- 오늘의 뉴스 부분 -->
        <div class="col-6 shadow py-5" id="container-todayNews">
          <div class="text-center text-common-dark fw-bold fs-3 mb-5">
            오늘의 뉴스
          </div>
          <div class="text-center fs-5 mb-4">
            <a>제목제목제목</a>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script>
// @ is an alias to /src

export default {
  name: "HomeView",
};
</script>

<style scoped>
.container-main {
  background-image: url("@/assets/city.jpg");
}

.container-main-text {
  --bs-bg-opacity: 0.2;
}
</style>
