<template>
<nav class="navbar fixed-top navbar-expand-lg navbar-light" style="background-color: #e3f2fd;">
  <div class="container-fluid">
    <RouterLink class="navbar-brand" :to="'/dashboard'">
      <img :src="require(`../assets/bug.svg`)" alt="" width="30" height="24" class="d-inline-block align-text-top">
      Bug Tracker
    </RouterLink>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item" v-for="link in links" :key="link.id">
          <router-link class="nav-link" :to="link.link">{{ link.text }}</router-link>
        </li>
      </ul>
      <ul class="navbar-nav">
        <li class="nav-item">
          <router-link class="nav-link" :to="'/myAccount'">My Account</router-link>
        </li>
      </ul>

    </div>
  </div>
</nav>
</template>

<script>
export default {
  name: 'NavBar',
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
          link: '/assignedProjects',
        },
        {
          id: 3,
          text: "Tickets",
          link: "/createdTickets",
        }, 
        {
          id: 4,
          text: "Administration",
          link: '/adminAccount/administration',
        },
      ]
    };
  },
}
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
.nav-item{
margin-left: 30px;
}

</style>
