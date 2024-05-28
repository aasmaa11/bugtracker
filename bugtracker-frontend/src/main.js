import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import vuetify from './plugins/vuetify'
import { loadFonts } from './plugins/webfontloader'
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap"
import store from "./store";
import { FontAwesomeIcon } from './plugins/font-awesome'
import setupInterceptors from './services/setupInterceptors';


// Code inspired from: https://www.bezkoder.com/vue-refresh-token/
loadFonts()
setupInterceptors(store);

createApp(App)
  .use(router)
  .use(vuetify)
  .use(store)
  .component("font-awesome-icon", FontAwesomeIcon)
  .mount('#app')
