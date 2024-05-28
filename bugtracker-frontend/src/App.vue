<template>
  <v-app>
    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<script>
import EventBus from "./common/EventBus";

export default {
  name: "App",

  data: () => ({
    //
  }),
  methods: {
    // Method from: https://www.bezkoder.com/vue-refresh-token/
    logOut() {
      this.$store.dispatch("auth/logout");
      this.$router.push("/login");
    },
  },
  // Method from: https://www.bezkoder.com/vue-refresh-token/
  mounted() {
    EventBus.on("logout", () => {
      this.logOut();
    });
  },
  // Method from: https://www.bezkoder.com/vue-refresh-token/
  beforeUnmount() {
    EventBus.remove("logout");
  },
};
</script>
