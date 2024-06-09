import axios from "axios";


// Code inspired from: https://www.bezkoder.com/vue-refresh-token/
const instance = axios.create({
  //baseURL: "https://personal-bugtracker-80b6079a0b29.herokuapp.com/api",
  baseURL: "http://localhost:8080/api",
  headers: {
    "Content-Type": "application/json",
  },
});

export default instance;