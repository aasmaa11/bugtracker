import { createRouter, createWebHistory } from 'vue-router'
import AdminView from '../views/AdminView.vue'
import TicketView from '../views/TicketView.vue'
import DashboardView from '../views/DashboardView.vue'
import LoginView from '../views/LoginView.vue'
import MyAccountView from '../views/MyAccountView.vue'
import ProjectView from '../views/ProjectView.vue'
import SignupView from '../views/SignupView.vue'

const routes = [
  {
    path: "/administration",
    name: "AdminView",
    component: AdminView,
  },
  {
    path: "/tickets",
    name: "TicketView",
    component: TicketView,
  },
  {
    path: "/dashboard",
    name: "DashboardView",
    component: DashboardView,
  },
  {
    path: "/login",
    name: "LoginView",
    component: LoginView,
  },
  {
    path: "/myAccount",
    name: "MyAccountView",
    component: MyAccountView,
  },
  {
    path: "/projects",
    name: "ProjectView",
    component: ProjectView,
  },
  {
    path: "/signup",
    name: "SignupView",
    component: SignupView,
  },

]
const router = createRouter({
  //history: createWebHashHistory(),
  history: createWebHistory(process.env.BASE_URL),
  routes
})
export default router