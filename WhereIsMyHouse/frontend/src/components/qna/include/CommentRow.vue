<template>
  <div class="col-lg-10">
    <div class="row">
      <div class="col-xxl-4 col-xl-12">
        <div class="card info-card customers-card">
          <div class="filter">
            <a class="icon" href="#" data-bs-toggle="dropdown"
              ><i class="bi bi-three-dots"></i
            ></a>
            <ul class="dropdown-menu dropdown-menu-end dropdown-menu-arrow">
              <li class="dropdown-header text-start">
                <h6>Filter</h6>
              </li>

              <li><a class="dropdown-item" href="#">수정</a></li>
              <li>
                <a class="dropdown-item" href="#">삭제</a>
              </li>
            </ul>
          </div>

          <div class="card-body">
            <h5 class="card-title">
              {{ comment.author }} <span>| {{ comment.date }} </span>
            </h5>

            <div class="d-flex align-items-center">
              <div class="ps-3">{{ comment.content }}</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    qna_no: String,
    comment: Object,
  },
  methods: {
    showModalModify() {
      this.$refs[`comment-${this.comment.commentNo}`].show();
    },
    hideModalModify() {
      this.$refs[`comment-${this.comment.commentNo}`].hide();
    },
    modify() {},
    showModalDelete() {
      this.$bvModal
        .msgBoxConfirm("정말로 삭제 하시겠습니까?", {
          centered: true,
        })
        .then((value) => {
          console.log(value);
          if (value) {
            this.delete();
          }
        });
    },
    delete() {},
  },
};
</script>

<style></style>
