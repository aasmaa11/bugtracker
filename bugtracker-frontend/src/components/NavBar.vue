<template>
  <nav
    class="navbar fixed-top navbar-expand-lg navbar-light"
    style="background-color: #e3f2fd"
  >
    <div class="container-fluid">
      <RouterLink v-if="currentUser" class="navbar-brand" :to="'/dashboard'">
        <img
          :src="require(`../assets/bug.svg`)"
          alt=""
          width="30"
          height="24"
          class="d-inline-block align-text-top"
        />
        Bug Tracker
      </RouterLink>
      <RouterLink v-if="!currentUser" class="navbar-brand" :to="'/signup'">
        <img
          :src="require(`../assets/bug.svg`)"
          alt=""
          width="30"
          height="24"
          class="d-inline-block align-text-top"
        />
        Bug Tracker
      </RouterLink>
      <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul v-if="currentUser" class="navbar-nav mr-auto">
          <li class="nav-item" v-for="link in links" :key="link.id">
            <router-link class="nav-link" :to="link.link">{{
              link.text
            }}</router-link>
          </li>
          <li v-if="showAdminItem" class="nav-item">
            <router-link class="nav-link" :to="'/administration'"
              >Administration</router-link
            >
          </li>
        </ul>
        <ul v-if="currentUser" class="navbar-nav ml-auto">
          <li class="nav-item">
            <router-link class="nav-link" :to="'/myAccount'">
              <font-awesome-icon icon="user" />
              {{ currentUser.username }}
            </router-link>
          </li>
          <li class="nav-item">
            <a class="nav-link" href @click.prevent="logOut">
              <font-awesome-icon icon="sign-out-alt" />LogOut
            </a>
          </li>
        </ul>
        <ul v-if="!currentUser" class="navbar-nav ml-auto">
          <li class="nav-item">
            <router-link to="/signup" class="nav-link">
              <font-awesome-icon icon="user-plus" />Sign Up
            </router-link>
          </li>
          <li class="nav-item">
            <router-link to="/login" class="nav-link">
              <font-awesome-icon icon="sign-in-alt" />Login
            </router-link>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script>
export default {
  name: "NavBar",
  data() {
    return {
      links: [
        {
          id: 1,
          text: "Dashboard",
          link: "/dashboard",
        },
        {
          id: 2,
          text: "Projects",
          link: "/projects",
        },
        {
          id: 3,
          text: "Tickets",
          link: "/tickets",
        },
      ],
    };
  },
  // Methods inspired from: https://www.bezkoder.com/vue-3-authentication-jwt/
  // and from: https://www.bezkoder.com/vue-refresh-token/
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    },
    showAdminItem() {
      if (this.currentUser && this.currentUser.roles) {
        return this.currentUser.roles.includes("ROLE_ADMIN");
      }

      return false;
    },
  },
  methods: {
    logOut() {
      this.$store.dispatch("auth/logout");
      this.$router.push("/login");
    },
  },
};
</script>

<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  font-size: 20px;
}
.nav-item {
  margin-left: 30px;
}
</style>
