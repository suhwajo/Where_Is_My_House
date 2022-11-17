<template>
  <div id="list-user">
    <div class="row ms-5 pt-4 text-dark text-center fs-6" style="width: 92%">
      <div class="col-2" id="userid">
        <router-link
          :to="{
            name: 'UserInfo',
            params: { userId: id },
          }"
          class="text-decoration-none text-common-dark link-dark"
          >{{ id }}</router-link
        >
      </div>
      <div class="col-2 text-common-dark">{{ name }}</div>
      <div class="col-2 text-common-dark">{{ email }}</div>
      <div class="col-2 text-common-dark">{{ phoneNum }}</div>
      <div class="col-2 text-common-dark">{{ isAdmin }}</div>
      <div class="col-2 text-common-dark">
        <a
          class="col-8 text-decoration-none text-common-dark link-danger"
          @click="deleteUser"
        >
          삭제</a
        >
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  props: {
    id: String,
    name: String,
    email: String,
    phoneNum: String,
    isAdmin: String,
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
