<template>
  <div>
    <div style="height: 50px"></div>

    <!-- Main Contents -->
    <main class="mt-4 container">
      <div style="height: 100px"></div>
      <!-- contents start -->
      <div class="row">
        <!-- Left side contents-->
        <aside class="col-4">
          <!-- 조건 검색 부분 -->
          <div class="text-center mb-3 w-100">
            <button
              type="button"
              class="btn btn-dark w-100 bg-common-dark"
              data-bs-toggle="modal"
              data-bs-target="#findModal"
            >
              지역 검색
            </button>
          </div>
          <div class="mt-4 overflow-auto" style="height: 650px">
            <div
              class="text-center text-light fs-3 mb-3"
              id="aside-list-info"
            ></div>
            <div id="aside-list" class=""></div>
          </div>
          <div class="mt-3" id="deal-back-btn" style="display: none">
            <button
              type="button"
              class="btn btn-dark bg-common-dark w-100"
              onclick="getHomes('${dongCode}')"
            >
              돌아가기
            </button>
          </div>
        </aside>
        <!-- 중앙 지도 부분 -->
        <section class="col-8">
          <div class="mx-5" id="map" style="height: 630px"></div>
          <div
            class="mt-4 text-center text-common-dark fs-3 fw-bold"
            id="map-homeName"
          ></div>
        </section>
      </div>
    </main>

    <div style="height: 50px"></div>

    <!-- 지역 검색 모달 -->
    <div class="modal fade" id="findModal" tabindex="-1">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">검색 설정</h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div class="modal-body">
            <div class="mb-3 mt-3">
              <select
                class="form-select"
                id="sido"
                aria-label="Default select example"
              ></select>
            </div>
            <div class="mb-3 mt-3">
              <select
                class="form-select"
                id="gugun"
                aria-label="Default select example"
              ></select>
            </div>
            <div class="mb-3 mt-3">
              <select
                class="form-select"
                id="dong"
                aria-label="Default select example"
              ></select>
            </div>
          </div>
          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-secondary"
              data-bs-dismiss="modal"
            >
              취소
            </button>
            <button
              type="button"
              class="btn btn-primary"
              data-bs-dismiss="modal"
              onclick="getHomes()"
            >
              검색
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style></style>
