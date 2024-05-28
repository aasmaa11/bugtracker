import { createStore } from "vuex";
import { auth } from "./auth.module";

// Code from: https://www.bezkoder.com/vue-3-authentication-jwt/
const store = createStore({
  modules: {
    auth,
  },
});

export default store;