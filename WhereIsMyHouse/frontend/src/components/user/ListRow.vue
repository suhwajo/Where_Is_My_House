<template>
  <tr>
    <th scope="row">{{ id }}</th>
    <td>{{ name }}</td>
    <td>{{ phoneNum }}</td>
    <td>{{ email }}</td>
    <td>{{ isAdmin }}</td>
    <td>
      <router-link
        :to="{
          name: 'UserInfo',
          params: { userId: id },
        }"
        class="btn btn-warning rounded-pill btn-block"
      >
        <b>수정</b>
      </router-link>
      <!-- <button
        type="button"
        class="btn btn-warning rounded-pill btn-block"
        onclick="location.href='${root}/user?act=m-modify&userId=${ member.userId }'"
      >
        수정
      </button> -->
      &nbsp;
      <button
        type="button"
        class="btn btn-danger rounded-pill btn-block"
        @click="deleteUser"
      >
        <b>삭제</b>
      </button>
    </td>
  </tr>
</template>

<script>
import axios from "axios";

export default {
  props: {
    id: String,
    name: String,
    email: String,
    phoneNum: String,
    isAdmin: Boolean,
  },
  methods: {
    deleteUser: function () {
      if (this.isAdmin == true) {
        alert("관리자는 임의로 탈퇴시킬 수 없습니다.");
        return;
      }

      let chk = confirm("정말 탈퇴시키겠습니까?");

      if (!chk) return;

      const url = "http://localhost:9999/rest/user/delete/" + this.id;

      axios
        .delete(url)
        .then((response) => response.data)
        .then((data) => {
          if (data.check) {
            alert("삭제되었습니다.");
            this.$router.push({ name: "UserList" });
          } else {
            alert("삭제에 실패했습니다. 다시 시도해주세요.");
          }
        });
      this.$router.go();
    },
  },
};
</script>

<style></style>
