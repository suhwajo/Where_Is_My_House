import Vue from "vue";
import VueRouter from "vue-router";
import VueMeta from "vue-meta";
import VueSession from "vue-session";

Vue.use(VueRouter);
Vue.use(VueMeta);
Vue.use(VueSession);

var sessionOptions = {
  persist: true,
};
Vue.use(VueSession, sessionOptions);

const routes = [
  {
    path: "/",
    name: "HomeView",
    component: () => import("@/views/HomeView.vue"),
  },
  {
    path: "/notice",
    name: "notice",
    component: () => import("@/views/NoticeView.vue"),
    redirect: "/notice/list",
    children: [
      {
        path: "list",
        name: "NoticeList",
        component: () => import("@/components/notice/NoticeList.vue"),
      },
      {
        path: "detail/:no",
        name: "NoticeDetail",
        component: () => import("@/components/notice/NoticeDetail.vue"),
      },
      {
        path: "modify/:no",
        name: "NoticeModify",
        component: () => import("@/components/notice/NoticeModify.vue"),
      },
      {
        path: "write",
        name: "NoticeWrite",
        component: () => import("@/components/notice/NoticeWrite.vue"),
      },
    ],
  },
  {
    path: "/user",
    name: "UserView",
    component: () => import("@/views/UserView.vue"),
    redirect: "/user/list",
    children: [
      {
        path: "list",
        name: "UserList",
        component: () => import("@/components/user/UserList.vue"),
      },
      {
        path: "detail/:userId",
        name: "UserInfo",
        component: () => import("@/components/user/UserInfo.vue"),
      },
      {
        path: "findpw/:userId",
        name: "FindPw",
        component: () => import("@/components/user/FindPw.vue"),
      },
      {
        path: "join",
        name: "UserJoin",
        component: () => import("@/components/user/JoinUser.vue"),
      },
      {
        path: "login",
        name: "UserLogin",
        component: () => import("@/components/user/LoginUser.vue"),
      },
    ],
  },
  {
    path: "/region",
    name: "RegionList",
    component: () => import("@/components/region/RegionList.vue"),
  },
  {
    path: "/home",
    name: "FindHome",
    component: () => import("@/components/home/FindHomes.vue"),
  },
  {
    path: "/news",
    name: "NewsList",
    component: () => import("@/components/home/FindHomes.vue"),
  },
  {
    path: "/qna",
    name: "Qna",
    component: () => import("@/views/QnaView.vue"),
    redirect: "/qna/list",
    children: [
      {
        path: "list",
        name: "QnaList",
        component: () => import("@/components/qna/QnaList.vue"),
      },
      {
        path: "detail/:no",
        name: "QnaDetail",
        component: () => import("@/components/qna/QnaDetail.vue"),
      },
      {
        path: "modify/:no",
        name: "QnaModify",
        component: () => import("@/components/qna/QnaModify.vue"),
      },
      {
        path: "write",
        name: "QnaWrite",
        component: () => import("@/components/qna/QnaWrite.vue"),
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

//라우터 전역 처리
// router.beforeEach((to, from, next) => {
//   if()
// });

export default router;