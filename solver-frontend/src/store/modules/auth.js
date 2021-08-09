import axios from "axios";
import router from "@/router";
import API from "@/API.js";

const state = {
  categoryList: [],
  possibleDay: [],
  possibleTime: [],
  accessToken: "",
  isFirst: false,
  userNickname: "",
};

const getters = {
  isLoggedIn: (state) => state.accessToken,
  isFirst: (state) => state.isFirst,
  getAccessToken: (state) => state.accessToken,
};

const mutations = {
  SET_CATEGORY: (state, category) => {
    state.categoryList = category;
  },
  SET_POSSIBLE_DAY: (state, day) => {
    state.possibleDay = day;
  },
  SET_POSSIBLE_TIME: (state, time) => {
    state.possibleTime = time;
  },
  SET_ACCESS_TOKEN: (state, accessToken) => {
    state.accessToken = accessToken;
  },
  SET_IS_FIRST: (state, isFirst) => {
    state.isFirst = isFirst;
  },
  SET_USER_NICKNAME: (state, userNickname) => {
    state.userNickname = userNickname;
  },
};

const actions = {
  signup(context, credentials) {
    axios({
      url: API.URL + API.ROUTES.signup,
      method: "post",
      data: credentials,
      headers: { Authorization: "Bearer " + localStorage.getItem("accessToken") },
    })
      .then((res) => {
        console.log(res);
        router.push({ path: "/" });
      })
      .catch(() => {
        console.log(credentials);
      });
  },
  tokenLogin({ commit }, token) {
    commit("SET_ACCESS_TOKEN", token);
    localStorage.setItem("accessToken", token);
    //토큰으로 닉네임을 찾아오기
    axios({
      url: API.URL + API.ROUTES.getNickname,
      method: "get",
      headers: { Authorization: "Bearer " + token },
    })
      .then((res) => {
        //닉네임을 찾고서 값이 없으면 회원가입으로, 있으면 isFirst 수정
        if (res.data == "") {
          router.push({ path: "/auth/signup1" });
        } else {
          commit("SET_USER_NICKNAME", res.data);
          router.push({ path: "/" });
        }
      })
      .catch(() => {
        console.log();
      });
  },
  login({ commit }) {
    axios({
      url: API.URL + API.ROUTES.login,
      method: "get",
      headers: { Authorization: "Bearer " },
    })
      .then((res) => {
        // console.log(res);
        localStorage.setItem("accessToken", res.data.accessToken);
        commit("SET_ACCESS_TOKEN", res.data.accessToken);
        router.push({ path: "/" });
      })
      .catch(() => {
        console.log();
      });
  },
  logout({ commit }) {
    axios({
      url: API.URL + API.ROUTES.logout,
      method: "get",
      headers: { Authorization: "Bearer " + this.getters.getAccessToken },
    })
      .then(() => {
        localStorage.removeItem("accessToken");
        commit("SET_ACCESS_TOKEN", "");
        router.push({ path: "/" });
      })
      .catch(() => {
        console.log();
      });
  },
  // 카테고리 받기
  fetchSignupData({ commit }) {
    // back이랑 api 주소 확인
    axios
      .get(API.URL + API.ROUTES.signupData)
      .then((res) => {
        // back에서 data(res) 어떻게 담겨 오는지 확인
        console.log(res);
        commit("SET_CATEGORY", res.data.category);
        commit("SET_POSSIBLE_DAY", res.data.day);
        commit("SET_POSSIBLE_TIME", res.data.time);
      })
      .catch((err) => console.log(err));
  },
};

export default {
  state,
  getters,
  mutations,
  actions,
};
