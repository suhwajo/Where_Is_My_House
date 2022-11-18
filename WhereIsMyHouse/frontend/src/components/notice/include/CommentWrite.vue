<template>
  <div class="box p-3">
    <b-input-group style="width: 240px" prepend="작성자">
      <div>{{ author }}</div>
    </b-input-group>
    <b-input-group>
      <b-form-textarea
        placeholder="댓글 입력..."
        v-model="comment"
        rows="3"
        max-rows="6"
      >
      </b-form-textarea>
      <b-input-group-append>
        <b-button variant="secondary" @click="write()">등록</b-button>
      </b-input-group-append>
    </b-input-group>
  </div>
</template>

<script>
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
    write() {},
  },
};
</script>

<style></style>
