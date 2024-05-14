import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import AdminView from '../views/AdminView.vue'
import AllTicketView from '../views/AllTicketView.vue'
import CreatedTicketView from '../views/CreatedTicketView.vue'
import DashboardView from '../views/DashboardView.vue'
import LoginView from '../views/LoginView.vue'
import MyAccountView from '../views/MyAccountView.vue'
import ProjectView from '../views/ProjectView.vue'
import SignupView from '../views/SignupView.vue'
import SignupAdminView from '../views/SignupAdminView.vue'
import SignupPMView from '../views/SignupPMView.vue'
import SingleProjectView from '../views/SingleProjectView.vue'

const routes = [
  {
    path: '/',
    name: 'HomeView',
    component: HomeView
  },
  {
    path: "/adminAccount/administration",
    name: "AdminView",
    component: AdminView,
  },
  {
    path: "/adminAccount/allTickets",
    name: "AllTicketView",
    component: AllTicketView,
  },
  {
    path: "/createdTickets",
    name: "CreatedTicketView",
    component: CreatedTicketView,
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
    path: "/assignedProjects",
    name: "ProjectView",
    component: ProjectView,
  },
  {
    path: "/signup",
    name: "SignupView",
    component: SignupView,
  },
  {
    path: "/signupadmin",
    name: "SignupAdminView",
    component: SignupAdminView,
  },
  {
    path: "/signuppm",
    name: "SignupPMView",
    component: SignupPMView,
  },
  {
    path: "/projectInfo",
    name: "SingleProjectView",
    component: SingleProjectView,
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },

]
const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})
export default router