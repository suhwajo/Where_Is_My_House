<template>
  <div class="col-lg-12">
    <div class="card">
      <div class="card-body">
        <h5 class="card-title">댓글 입력</h5>

        <!-- Vertical Form -->
        <form class="row g-3">
          <div class="col-12">
            <div class="form-floating">
              <textarea
                class="form-control"
                placeholder="Address"
                id="floatingTextarea"
                style="height: 100px"
                v-model="comment"
              ></textarea>
              <label for="floatingTextarea">Address</label>
            </div>
          </div>
          <div class="text-center">
            <button @click="write()" class="btn btn-primary">Submit</button>
            &nbsp;
            <button type="reset" class="btn btn-secondary">Reset</button>
          </div>
        </form>
        <!-- Vertical Form -->
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  props: {
    author: String,
    no: String,
  },
  data() {
    return {
      comment: "",
    };
  },
  methods: {
    write() {
      if (!this.author) {
        alert("로그인해야 댓글을 쓸 수 있습니다!");
        return;
      }
      if (!this.comment) {
        alert("댓글을 입력하세요!");
        return;
      }
      const url = "http://localhost:9999/rest/comment/write";
      axios
        .post(url, {
          memberId: this.author,
          no: this.no,
          content: this.comment,
        })
        .then((response) => response.data)
        .then((data) => {
          if (data.check) {
            this.$router.go();
          } else {
            alert("댓글 작성에 실패 했습니다. 다시 시도해주세요.");
          }
        });
    },
  },
};
</script>

<style></style>
