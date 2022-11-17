<template>
  <div class="body">
    <div style="height: 50px"></div>

    <!-- Main Contents -->
    <main class="mt-4 container">
      <div style="height: 100px"></div>
      <!-- contents start -->
      <!-- contents -->
      <section class="pt-5 rounded-2 mb-5">
        <div style="height: 30px"></div>
        <div class="row">
          <div class="d-flex justify-content-center m-5 col">
            <img
              class="img-fluid"
              src="@/assets/logo.png"
              alt=""
              width="300px"
            />
          </div>
          <div class="col">
            <div class="fs-3 fw-bold my-4 text-common-light w-75 text-center">
              로그인
            </div>
            <div class="mb-4 w-75">
              <div class="form-check mb-3 float-end text-common-light">
                <input
                  class="form-check-input"
                  type="checkbox"
                  value="ok"
                  id="saveId"
                  name="saveId"
                  v-model="saveId"
                />
                <label class="form-check-label" for="saveId">
                  아이디저장
                </label>
              </div>

              <div class="mb-3 mt-3">
                <input
                  type="text"
                  class="form-control"
                  id="id"
                  placeholder="id"
                  name="id"
                  v-model="id"
                  ref="id"
                />
              </div>
              <div class="mb-3">
                <input
                  type="password"
                  class="form-control"
                  id="pwd"
                  placeholder="password"
                  name="pwd"
                  v-model="pwd"
                  ref="pwd"
                />
              </div>
              <div class="mt-4">
                <button
                  type="button"
                  class="btn btn-light bg-common-light w-100"
                  @click="login"
                >
                  로그인
                </button>
              </div>
              <div class="mt-3">
                <button
                  type="button"
                  id="btn-mv-join"
                  class="btn btn-dark bg-common-dark w-100"
                >
                  회원가입
                </button>
              </div>
              <div class="mt-3 text-center">
                <a class="text-light" id="btn-mv-findPw"
                  >비밀번호를 잊어버리셨습니까?</a
                >
              </div>
            </div>
          </div>
        </div>
        <div style="height: 60px"></div>
      </section>
    </main>

    <div style="height: 117px"></div>
  </div>
</template>

<script>
export default {
  name: "UserLogin",
  data() {
    return {
      id: "",
      pwd: "",
      saveId: false,
    };
  },
  methods: {
    login() {
      // let userId = document.querySelector("#id").value;
      // let userPwd = document.querySelector("#pwd").value;
      // let saveId = document.querySelector("#saveId").checked;

      let isValid = true;
      let errMsg = "";

      !this.id
        ? ((isValid = false),
          (errMsg = "아이디를 입력해주세요."),
          this.$refs.id.focus())
        : !this.pwd
        ? ((isValid = false),
          (errMsg = "비밀번호를 입력해주세요."),
          this.$refs.pwd.focus())
        : (isValid = true);

      if (!isValid) {
        alert(errMsg);
      }

      var data = {
        userId: this.id,
        userPwd: this.pwd,
        saveId: this.saveId,
      };
      this.$store
        .dispatch("doLogin", data)
        .then(() => {
          this.$router.push({ name: "HomeView" });
          this.$session.set("userInfo", data);
        })
        .catch(() => {
          alert("아이디 혹은 패스워드가 올바르지 않습니다.");
        });
    },
  },
};
</script>

<style scoped>
.body {
  background: #928490;
}
</style>
