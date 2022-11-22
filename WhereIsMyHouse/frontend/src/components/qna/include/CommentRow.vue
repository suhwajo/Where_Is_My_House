<template>
  <div class="col-lg-12">
    <div class="card info-card customers-card">
      <div class="filter" v-if="comment.author == this.memberId">
        <a class="icon" href="#" data-bs-toggle="dropdown"
          ><i class="bi bi-three-dots"></i
        ></a>
        <ul class="dropdown-menu dropdown-menu-end dropdown-menu-arrow">
          <li class="dropdown-header text-start">
            <h6>댓글 관리</h6>
          </li>

          <li>
            <button
              class="dropdown-item"
              data-bs-toggle="modal"
              data-bs-target="#CommentUpdate"
              @click="updateReset"
            >
              수정
            </button>
          </li>
          <li>
            <button class="dropdown-item" @click="showModalDelete">삭제</button>
          </li>
        </ul>
      </div>
      <div class="modal fade" id="CommentUpdate" tabindex="-1">
        <div class="modal-dialog modal-dialog-centered">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title">Comment Update</h5>
              <button
                type="button"
                class="btn-close"
                data-bs-dismiss="modal"
                aria-label="Close"
              ></button>
            </div>
            <div class="modal-body">
              <form class="row g-3">
                <div class="col-12">
                  <label for="comment1" class="form-label">Comment</label>
                  <textarea
                    class="form-control"
                    id="comment1"
                    v-model="content"
                  >
                  </textarea>
                </div>
              </form>
            </div>
            <div class="modal-footer">
              <button
                type="button"
                class="btn btn-secondary"
                data-bs-dismiss="modal"
              >
                Close
              </button>
              <button type="button" class="btn btn-primary" @click="modify()">
                Save changes
              </button>
            </div>
          </div>
        </div>
      </div>
      <!-- End Vertically centered Modal-->

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
</template>

<script>
import axios from "axios";
import { mapGetters } from "vuex";
export default {
  props: {
    qna_no: String,
    comment: Object,
  },
  data() {
    return {
      content: "",
    };
  },
  created() {
    this.content = this.comment.content;
  },
  methods: {
    // showModalModify() {
    //   this.$refs[`comment-${this.comment.commentNo}`].show();
    // },
    // hideModalModify() {
    //   this.$refs[`comment-${this.comment.commentNo}`].hide();
    // },
    updateReset() {
      this.content = this.comment.content;
    },
    modify() {
      const url = "http://localhost:9999/rest/comment/modify";
      axios
        .post(url, {
          memberId: this.comment.author,
          id: this.comment.id,
          content: this.content,
        })
        .then((response) => response.data)
        .then((data) => {
          if (data.check) {
            this.$router.go();
          } else {
            alert("댓글 수정에 실패 했습니다. 다시 시도해주세요.");
          }
        });
    },
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
    delete() {
      const url = "http://localhost:9999/rest/comment/delete";
      axios
        .post(url, {
          memberId: this.comment.author,
          id: this.comment.id,
        })
        .then((response) => response.data)
        .then((data) => {
          if (data.check) {
            this.$router.go();
          } else {
            alert("댓글 삭제에 실패 했습니다. 다시 시도해주세요.");
          }
        });
    },
  },
  computed: {
    ...mapGetters(["memberId"]),
  },
};
</script>

<style></style>
