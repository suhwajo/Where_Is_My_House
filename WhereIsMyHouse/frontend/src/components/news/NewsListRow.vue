<template>
  <tr @click="goDetail()">
    <th scope="row">{{ no + 1 }}</th>
    <td>{{ title }}</td>
    <td>{{ date }}</td>
  </tr>
</template>

<script>
export default {
  props: {
    no: Number,
    title: String,
    link: String,
    date: String,
  },
  methods: {
    goDetail() {
      window.open(this.link);
    },
  },
};
</script>

<style></style>
