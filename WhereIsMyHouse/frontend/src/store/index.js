import Vue from "vue";
import Vuex from "vuex";
import axios from "axios";
import persistedState from "vuex-persistedstate"; //state 유지하도록 도와주는 라이브러리

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    memberId: "",
    isLogin: false,
  },
  getters: {
    //로그인 여부 체크
    isLogin(state) {
      return state.isLogin;
    },
    memberId(state) {
      return state.memberId;
    },
  },
  mutations: {
    //memberId 설정
    setMemberId(state, memberId) {
      state.memberId = memberId;
      state.isLogin = true;
    },
    //초기화
    reset(state) {
      state.memberId = "";
      state.isLogin = false;
      this.$session.reset();
    },
  },
  actions: {
    doLogin({ commit }, user) {
      let resultErr = null;

      return new Promise((resolve, reject) => {
        try {
          axios
            .post("http://localhost:9999/rest/user/login", user)
            .then((response) => response.data)
            .then((data) => {
              if (data.userInfo != null) {
                console.log(data.userInfo);
                resolve(data);
                commit("setMemberId", user.userId);
              } else {
                let err = new Error("Request failed with status code 401");
                err.response = {
                  data: {
                    success: false,
                    errormessage: "로그인되지 않았습니다.",
                  },
                };
                resultErr = err;
                reject(resultErr);
              }
            });
        } catch (err) {
          console.log(err.response);
          if (!err.response) {
            err.response = {
              data: { success: false, errormessage: err.message },
            };
          }
          resultErr = err;
          reject(resultErr);
        }
      });
    },
    // 로그아웃합니다.
    doLogout({ commit }) {
      commit("reset");
    },
  },
  modules: {},
  plugins: [
    persistedState({
      //기본값 localStorage
      storage: sessionStorage,
    }),
  ],
});
