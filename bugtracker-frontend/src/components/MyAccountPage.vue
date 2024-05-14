<template>
  <NavBar />
  <div class="container">
    <h1>MY ACCOUNT</h1>
    <div class="containers">
      <div class="row">
        <img
          src="../assets/user.svg"
          alt=""
          width="50"
          height="50"
          class="d-inline-block align-text-top"
        />

        <div class="mb-3">
          <label for="formGroupExampleInput" class="form-label"
            >First name</label
          >
          <input
            type="text"
            v-model="newParams.firstName"
            class="form-control"
            id="formGroupExampleInput"
          />
        </div>
        <div class="mb-3">
          <label for="formGroupExampleInput2" class="form-label"
            >Last name</label
          >
          <input
            type="text"
            v-model="newParams.lastName"
            class="form-control"
            id="formGroupExampleInput2"
          />
        </div>
        <div class="mb-3">
          <label for="formGroupExampleInput2" class="form-label"
            >Username</label
          >
          <input
            type="text"
            v-model="newParams.username"
            class="form-control"
            id="formGroupExampleInput2"
          />
        </div>
        <div class="mb-3">
          <label for="formGroupExampleInput2" class="form-label">Email</label>
          <input
            type="text"
            v-model="newParams.email"
            class="form-control"
            id="formGroupExampleInput2"
          />
        </div>
        <div class="mb-3">
          <label for="formGroupExampleInput2" class="form-label"
            >Password</label
          >
          <input
            type="text"
            v-model="newParams.password"
            class="form-control"
            id="formGroupExampleInput2"
          />
        </div>
        <div class="mb-3">
          <button
            type="submit"
            v-on:click="
              modifyUser(
                newParams.username,
                newParams.email,
                newParams.password,
                newParams.firstName,
                newParams.lastName
              )
            "
            class="btn btn-primary"
          >
            Submit
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import NavBar from "./NavBar.vue";
import axios from "axios";
var backendUrl = "http://localhost:8080";

var AXIOS = axios.create({
  baseURL: backendUrl,
});

export default {
  name: "MyAccountPage",
  components: {
    NavBar,
  },
  data() {
    return {
      item: "",
      newParams: {
        username: "",
        email: "",
        password: "",
        firstName: "",
        lastName: "",
      },
      errorGetUser: "",
      errorModifyUser: "",
      response: [],
    };
  },
  created: function () {
    let token = localStorage.getItem("token");
    // Initializing  items from backend
    AXIOS.get("/developer/".concat(token))
      .then((response) => {
        // JSON responses are automatically parsed.
        this.newParams.username = response.data.username;
        this.newParams.email = response.data.email;
        this.newParams.password = response.data.password;
        this.newParams.firstName = response.data.firstName;
        this.newParams.lastName = response.data.lastName;
      })
      .catch((e) => {
        this.errorGetUser = e;
      });
  },
  methods: {
    // TODO: how to change password???
    modifyUser: function (
      newUsername,
      newEmail,
      newPassword,
      newFirstName,
      newLastName
    ) {
      AXIOS.post(
        "/user/modify/".concat(localStorage.getItem("token")),
        {},
        {
          params: {
            newUsername: newUsername,
            newEmail: newEmail,
            newPassword: newPassword,
            newFirstName: newFirstName,
            newLastName: newLastName,
          },
        }
      )
        .then((response) => {
          // JSON responses are automatically parsed.
          this.newParams.username = response.data.username;
          this.newParams.email = response.data.email;
          this.newParams.password = response.data.password;
          this.newParams.firstName = response.data.firstName;
          this.newParams.lastName = response.data.lastName;
          let token = this.newParams.username;
          localStorage.setItem("token", token);
          this.$router.push("/myAccount");
        })
        .catch((e) => {
          this.errorModifyUser = e;
          var errorMsg = e.response.data.message;
          console.log(errorMsg);
        });
    },
  },
};
</script>

<style scoped>
.container {
  margin-bottom: 50px;
}

h1 {
  margin-top: 100px;
  font-size: 20px;
  color: rgb(43, 43, 43);
}

.containers {
  margin-top: 30px;
  display: flex;
  flex-direction: column;
  width: 100%;
  align-items: center;
  justify-content: center;
}
img {
  margin-bottom: 20px;
}
.row {
  background-color: white;
  padding: 30px;
  width: 500px;
  border: 1px solid rgb(183, 183, 183);
  border-radius: 5px;
  padding-top: 50px;
}
</style>
