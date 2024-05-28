<template>
  <NavBar />
  <div class="container">
    <h1>ADMINISTRATION</h1>

    <div class="theContent">
      <div class="tables">
        <div class="row">
          <div class="col">
            <div class="table-responsive">
              <DataTable
                :columns="columns"
                :data="data"
                class="table caption-top"
                width="100%"
                :options="{
                  pageLength: 5,
                  lengthChange: false,
                  select: true,
                  select: {
                    style: 'single',
                    toggleable: false,
                  },
                }"
                ref="table"
                @select="clickRow()"
              >
                <caption>
                  Organization
                </caption>
                <thead class="table-light">
                  <tr>
                    <th scope="col">Username</th>
                    <th scope="col">First Name</th>
                    <th scope="col">Last Name</th>
                    <th scope="col">Email</th>
                  </tr>
                </thead>
              </DataTable>
            </div>
          </div>
        </div>
      </div>

      <div class="outer-container">
        <div class="text-container">Edit user information</div>

        <div class="form-container">
          <form class="row g-3">
            <span v-if="errorModifyRole != ''">{{ errorModifyRole }}</span>
            <span v-if="errorModifyUser != ''">{{ errorModifyUser }}</span>
            <span v-if="errorDeleteUser != ''">{{ errorDeleteUser }}</span>
            <span v-if="errorSelectUser != ''">{{ errorSelectUser }}</span>
            <div class="col-md-6">
              <label for="selectedFirstName" class="form-label"
                >FIRST NAME</label
              >
              <input
                type="text"
                class="form-control"
                v-model="selectedFirstName"
                id="selectedFirstName"
              />
            </div>
            <div class="col-md-6">
              <label for="selectedLastName" class="form-label">LAST NAME</label>
              <input
                type="text"
                class="form-control"
                v-model="selectedLastName"
                id="selectedLastName"
              />
            </div>
            <div class="col-md-6">
              <label for="selectedUsername" class="form-label">USERNAME</label>
              <input
                type="text"
                class="form-control"
                v-model="selectedUsername"
                id="selectedUsername"
              />
            </div>
            <div class="col-md-6">
              <label for="selectedEmail" class="form-label">EMAIL</label>
              <input
                type="text"
                class="form-control"
                v-model="selectedEmail"
                id="selectedEmail"
              />
            </div>
            <div class="col-md-6">
              <label for="role" class="form-label">ROLE</label>
              <select v-model="selectedRole" id="role" class="form-select">
                <option :value="null" disabled>Choose...</option>
                <option value="Developer">Developer</option>
                <option value="ProjectManager">Project Manager</option>
                <option value="Admin">Administrator</option>
              </select>
            </div>

            <div class="col-md-10">
              <button
                type="button"
                v-on:click="
                  modifyUser(
                    oldUsername,
                    selectedUsername,
                    selectedEmail,
                    '',
                    selectedFirstName,
                    selectedLastName,
                    selectedRole
                  )
                "
                class="btn btn-primary"
              >
                Submit
              </button>
            </div>
            <div class="col-md-2">
              <button
                type="button"
                v-on:click="deleteUser(oldUsername, selectedRole)"
                class="btn btn-danger"
              >
                Remove user
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import NavBar from "./NavBar.vue";
import DataTable from "datatables.net-vue3";
import DataTablesCore from "datatables.net";
import "datatables.net-select";
import "datatables.net-responsive";
import "datatables.net-select-dt";
import UserService from "../services/user.service";
import EventBus from "../common/EventBus";

DataTable.use(DataTablesCore);

export default {
  name: "AdminPage",
  components: {
    NavBar,
    DataTable,
  },

  data() {
    return {
      data: [],
      roles: [],
      errorModifyUser: "",
      errorModifyRole: "",
      errorGetUsers: "",
      errorGetRole: "",
      errorDeleteUser: "",
      errorSelectUser: "",
      columns: [
        { data: "username" },
        { data: "firstName" },
        { data: "lastName" },
        { data: "email" },
      ],
      r: "",
      oldUsername: "",
      selectedUsername: "",
      selectedFirstName: "",
      selectedLastName: "",
      selectedEmail: "",
      selectedRole: "null",
    };
  },
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    },
    currentUserIsAdmin() {
      if (this.currentUser && this.currentUser.roles) {
        return this.currentUser.roles.includes("ROLE_ADMIN");
      }

      return false;
    },
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    },
  },
  created() {
    if (!this.loggedIn) {
      this.$router.push("/login");
    } else {
      if (!this.currentUserIsAdmin) {
        this.$router.push("/dashboard");
      }
    }
  },
  mounted: function () {
    // Initializing  items from backend
    if (this.currentUserIsAdmin) {
      UserService.getAllUsers().then(
        (response) => {
          // JSON responses are automatically parsed.
          this.data = response.data;
        },
        (error) => {
          this.errorGetUsers =
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
    clickRow() {
      this.resetErrorMessages();
      this.errorModifyUser = "";
      this.errorModifyRole = "";
      this.errorDeleteUser = "";
      const table = this.$refs.table.dt;
      var user = table.row(".selected").data();
      this.oldUsername = user.username;
      this.selectedUsername = user.username;
      this.selectedFirstName = user.firstName;
      this.selectedLastName = user.lastName;
      this.selectedEmail = user.email;

      // Initializing  items from backend
      UserService.getRoleForUser(this.selectedUsername).then(
        (response) => {
          // JSON responses are automatically parsed.
          this.selectedRole = response.data;
          console.log(response.data);
        },
        (error) => {
          this.errorGetRole =
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

    resetErrorMessages() {
      this.errorModifyUser = "";
      this.errorModifyRole = "";
      this.errorGetUsers = "";
      this.errorGetRole = "";
      this.errorDeleteUser = "";
      this.errorSelectUser = "";
    },

    modifyUser: function (
      oldUsername,
      newUsername,
      newEmail,
      newPassword,
      newFirstName,
      newLastName,
      newRole
    ) {
      this.resetErrorMessages();
      const table = this.$refs.table.dt;
      var user = table.row(".selected").data();
      if (user == null) {
        this.errorSelectUser = "No user selected. ";
      } else {
        var indexUser = this.data.map((x) => x.username).indexOf(oldUsername);

        UserService.modifyUser(
          oldUsername,
          newUsername,
          newEmail,
          newPassword,
          newFirstName,
          newLastName
        ).then(
          (response) => {
            // JSON responses are automatically parsed.
            this.errorModifyUser = "";

            UserService.modifyRoleOfUser(this.selectedUsername, newRole).then(
              (resp) => {
                // JSON responses are automatically parsed.
                this.oldUsername = resp.data.username;
                this.data[indexUser].role = newRole;
                this.selectedRole = newRole;
                this.errorModifyRole = "";
              },
              (error) => {
                this.errorModifyRole =
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
            this.data[indexUser].username = response.data.username;
            this.selectedUsername = response.data.username;
            this.data[indexUser].email = response.data.email;
            this.selectedEmail = response.data.email;
            this.data[indexUser].firstName = response.data.firstName;
            this.selectedFirstName = response.data.firstName;
            this.data[indexUser].lastName = response.data.lastName;
            this.selectedLastName = response.data.lastName;
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
      }
    },
    deleteUser: function (username, role) {
      this.resetErrorMessages();
      const table = this.$refs.table.dt;
      var user = table.row(".selected").data();
      if (user == null) {
        this.errorSelectUser = "No user selected. ";
      } else {
        var indexUser = this.data.map((x) => x.username).indexOf(username);
        if (role == "Developer") {
          UserService.deleteDeveloper(username).then(
            (response) => {
              this.r = response.data;
              this.errorDeleteUser = "";
              this.oldUsername = "";
              this.selectedUsername = "";
              this.selectedFirstName = "";
              this.selectedLastName = "";
              this.selectedEmail = "";
              this.selectedRole = "";
              if (indexUser !== -1) {
                this.data.splice(indexUser, 1);
              }
            },
            (error) => {
              this.errorDeleteUser =
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
        } else if (role == "Admin") {
          UserService.deleteAdmin(username).then(
            (response) => {
              this.r = response.data;
              this.errorDeleteUser = "";
              this.oldUsername = "";
              this.selectedUsername = "";
              this.selectedFirstName = "";
              this.selectedLastName = "";
              this.selectedEmail = "";
              this.selectedRole = "";
              if (indexUser !== -1) {
                this.data.splice(indexUser, 1);
              }
            },
            (error) => {
              this.errorDeleteUser =
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
        } else if (role == "ProjectManager") {
          UserService.deleteProjectManager(username).then(
            (response) => {
              this.r = response.data;
              this.errorDeleteUser = "";
              this.oldUsername = "";
              this.selectedUsername = "";
              this.selectedFirstName = "";
              this.selectedLastName = "";
              this.selectedEmail = "";
              this.selectedRole = "";
              if (indexUser !== -1) {
                this.data.splice(indexUser, 1);
              }
            },
            (error) => {
              this.errorDeleteUser =
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
      }
    },
  },
};
</script>

<style scoped>
@import "datatables.net-bs5";
@import "datatables.net-dt";

h1 {
  margin-top: 100px;
  font-size: 20px;
  color: rgb(43, 43, 43);
}

label {
  font-size: 13px;
}

.tables {
  margin-top: 30px;
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.tables .row {
  width: 100%;
}

.tables .col {
  margin: 10px;
  border: 1px solid rgba(255, 255, 255, 0);
  border-radius: 5px;
  box-shadow: 5px 5px 10px #b9b9b9;
  padding-top: 10px;
  padding-bottom: 20px;
  width: 90%;
}
.text-container {
  padding: 10px;
  color: gray;
}

.outer-container {
  margin-top: 30px;
  border: 1px solid rgba(255, 255, 255, 0);
  border-radius: 5px;
  box-shadow: 5px 5px 10px #b9b9b9;
  width: 100%;
  margin-bottom: 50px;
}

.form-container {
  padding: 20px;
  width: 100%;
  background-color: rgb(249, 249, 249);
  padding-bottom: 50px;
}

form {
  width: 100%;
  margin-top: 5px;
  background-color: white;
  padding: 20px;
}

button {
  margin-top: 20px;
}

.form-container .row span {
  color: red;
}

.theContent {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
</style>
