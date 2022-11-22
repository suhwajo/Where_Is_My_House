<template>
  <tr @click="goDetail()">
    <th scope="row">{{ no }}</th>
    <td>{{ title }}</td>
    <td>{{ author }}</td>
    <td>{{ hit }}</td>
    <td>{{ date.split(" ")[0] }}</td>
  </tr>
</template>

<script>
export default {
  props: {
    no: String,
    isbn: String,
    title: String,
    hit: String,
    author: String,
    date: String,
  },
  methods: {
    goDetail() {
      const no = this.no;
      this.$router.push({
        name: "NoticeDetail",
        params: { no },
      });
    },
  },
};
</script>

<style></style>
