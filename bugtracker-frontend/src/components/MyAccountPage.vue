<template>
  <NavBar />
  <div class="container">
    <h1>MY ACCOUNT</h1>
    <div class="account">
      <div class="containers">
        <img
          src="../assets/user.svg"
          alt=""
          width="50"
          height="50"
          class="d-inline-block align-text-top"
        />
        <span>{{ errorModifyUser }}</span>
        <div class="row">
          <div class="col-md-12">
            <div class="mb-12">
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
            <div class="mb-12">
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
            <div class="mb-12">
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
            <div class="mb-12">
              <label for="formGroupExampleInput2" class="form-label"
                >Email</label
              >
              <input
                type="text"
                v-model="newParams.email"
                class="form-control"
                id="formGroupExampleInput2"
              />
            </div>
            <div class="mb-12">
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
    </div>
  </div>
</template>

<script>
import NavBar from "./NavBar.vue";
import UserService from "../services/user.service";
import EventBus from "../common/EventBus";

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
  // Methods inspired from: https://www.bezkoder.com/vue-3-authentication-jwt/
  // and from: https://www.bezkoder.com/vue-refresh-token/
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    },
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    },
  },
  created: function () {
    if (!this.loggedIn) {
      this.$router.push("/login");
    } else {
      UserService.getUserWithUsername(this.currentUser.username).then(
        (response) => {
          // JSON responses are automatically parsed.
          this.newParams.username = response.data.username;
          this.newParams.email = response.data.email;
          this.newParams.password = response.data.password;
          this.newParams.firstName = response.data.firstName;
          this.newParams.lastName = response.data.lastName;
        },
        (error) => {
          this.errorGetUser =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();

          if (error.response && error.response.status === 403) {
            EventBus.dispatch("logout");
          }
        }
      );
    }
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
      UserService.modifyUser(
        this.currentUser.username,
        newUsername,
        newEmail,
        newPassword,
        newFirstName,
        newLastName
      ).then(
        (response) => {
          this.errorModifyUser = "";
          // JSON responses are automatically parsed.
          this.newParams.username = response.data.username;
          this.newParams.email = response.data.email;
          this.newParams.password = response.data.password;
          this.newParams.firstName = response.data.firstName;
          this.newParams.lastName = response.data.lastName;
        },
        (error) => {
          this.errorModifyUser =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();

          if (error.response && error.response.status === 403) {
            EventBus.dispatch("logout");
          }
        }
      );
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

.row {
  width: 100%;
}

.account {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin-top: 30px;
}
.containers {
  background-color: white;
  padding: 30px;
  padding-right: 50px;
  padding-left: 50px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  border: 1px solid rgb(183, 183, 183);
  border-radius: 5px;
  padding-top: 50px;
}
img {
  margin-bottom: 20px;
}
span {
  color: red;
}

.mb-12 {
  margin-bottom: 20px !important;
}
</style>
