import axios from "axios";


// Code inspired from: https://www.bezkoder.com/vue-refresh-token/
const instance = axios.create({
  baseURL: "http://localhost:8080/api",
  headers: {
    "Content-Type": "application/json",
  },
});

export default instance;