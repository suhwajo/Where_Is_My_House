<template>
  <b-list-group-item class="flex-column align-items-start">
    <div class="d-flex w-100 justify-content-between">
      <h5 class="mb-1">{{ comment.userName }}</h5>
      <small class="text-muted">{{ comment.commentTime }}</small>
    </div>
    <p :inner-html.prop="comment.comment | filterEnterToBr"></p>
    <div class="text-right">
      <b-button-group>
        <b-button variant="secondary" @click="showModalModify()">수정</b-button>
        <b-button variant="outline-secondary" @click="showModalDelete()">
          삭제
        </b-button>
      </b-button-group>
    </div>

    <!-- 수정 모달 창 작성 -->
    <b-modal
      :ref="`comment-${comment.commentNo}`"
      title="댓글 수정"
      header-bg-variant="dark"
      header-text-variant="light"
      centered
      hide-footer
    >
      <!--수정 모달 창 Body 작성 -->
      <div>
        <b-input-group style="width: 240px" prepend="작성자">
          <b-form-input
            v-model="input.userName"
            placeholder="작성자 입력..."
            readonly
          ></b-form-input>
        </b-input-group>
        <b-form-textarea
          placeholder="댓글 입력..."
          v-model="input.comment"
          rows="3"
          max-rows="6"
        ></b-form-textarea>
      </div>

      <!-- 수정 모달 창 Footer 작성 -->
      <div class="text-right">
        <b-button-group>
          <b-button variant="outline-secondary">취소</b-button>
          <b-button variant="secondary" @click="modify">수정</b-button>
        </b-button-group>
      </div>
    </b-modal>
  </b-list-group-item>
</template>

<script>
export default {
  props: {
    isbn: String,
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
