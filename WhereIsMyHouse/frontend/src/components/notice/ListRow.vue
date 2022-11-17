<template>
  <div id="list-notice">
    <div class="row ms-5 pt-4 text-dark text-center fs-6" style="width: 92%">
      <router-link
        class="col-8 text-common-dark link-dark text-decoration-none"
        :to="{
          name: 'NoticeDetail',
          params: { no },
        }"
      >
        {{ title }}
      </router-link>
      <div class="col-2 align-self-center">{{ author }}</div>
      <div class="col-2 align-self-center">
        {{ date.split(" ")[0] }}
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    no: String,
    isbn: String,
    title: String,
    author: String,
    date: String,
  },
};
</script>

<style></style>
