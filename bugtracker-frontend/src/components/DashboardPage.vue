<template>
  <NavBar />
  <div class="container">
    <h1>DASHBOARD</h1>
    <div class="thecontent">
      <div class="row">
        <div class="col">
          <p>Tickets by priority</p>
          <div class="pie-container">
            <canvas id="planet-chart"></canvas>
          </div>
        </div>
        <div class="col">
          <p>Tickets by type</p>
          <div class="pie-container">
            <canvas id="planet-chart2"></canvas>
          </div>
        </div>
        <div class="col">
          <p>Tickets by status</p>
          <div class="pie-container">
            <canvas id="planet-chart3"></canvas>
          </div>
        </div>
      </div>

      <div class="row">
        <div class="col">
          <div class="table-responsive">
            <table class="table caption-top">
              <caption v-if="currentUserIsAdminOrPM">
                All Projects
              </caption>
              <caption v-else>
                Assigned Projects
              </caption>
              <thead class="table-light">
                <tr>
                  <th scope="col">Title</th>
                  <th scope="col">Description</th>
                  <th scope="col">Project Manager</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="project in projects" :key="project.id">
                  <td>{{ project.name }}</td>
                  <td>{{ project.description }}</td>
                  <td>
                    {{ project.projectManager.firstName }}
                    {{ project.projectManager.lastName }}
                  </td>
                </tr>
              </tbody>
            </table>
            <div class="btn-container">
              <router-link :to="'/projects'">
                <button type="button" class="btn btn-outline-primary">
                  View all
                </button>
              </router-link>
            </div>
          </div>
        </div>
        <div class="col">
          <div class="table-responsive">
            <table class="table caption-top">
              <caption v-if="currentUserIsAdminOrPM">
                All Tickets
              </caption>
              <caption v-else>
                Assigned Tickets
              </caption>
              <thead class="table-light">
                <tr>
                  <th scope="col">Title</th>
                  <th scope="col">Description</th>
                  <th scope="col">Priority</th>
                  <th scope="col">Type</th>
                  <th scope="col">Status</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="ticket in tickets" :key="ticket.id">
                  <td>{{ ticket.title }}</td>
                  <td>{{ ticket.description }}</td>
                  <td>{{ ticket.priority }}</td>
                  <td>{{ ticket.type }}</td>
                  <td>{{ ticket.status }}</td>
                </tr>
              </tbody>
            </table>
            <div class="btn-container">
              <router-link :to="'/tickets'"
                ><button type="button" class="btn btn-outline-primary">
                  View all
                </button></router-link
              >
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
//https://www.youtube.com/watch?v=8dH-KhIpqd4&ab_channel=LearnDeveloperCode
//https://github.com/bezkoder/vue-3-authentication-jwt/blob/master/src/components/Profile.vue
//https://github.com/bezkoder/spring-boot-security-postgresql/blob/master/src/main/java/com/bezkoder/spring/security/postgresql/security/jwt/JwtUtils.java
import Chart from "chart.js";
import ProjectService from "../services/project.service";
import TicketService from "../services/ticket.service";
import NavBar from "./NavBar.vue";
import EventBus from "../common/EventBus";

export default {
  name: "DashboardPage",
  components: {
    NavBar,
  },
  data() {
    return {
      projects: [],
      tickets: [],
      errorGetProjects: "",
      errorGetTickets: "",
      nbNoneTicket: 0,
      nbLowTicket: 0,
      nbMediumTicket: 0,
      nbHighTicket: 0,
      nbBugTicket: 0,
      nbFRTicket: 0,
      nbDRTicket: 0,
      nbOtherTicket: 0,
      nbNewTicket: 0,
      nbOpenTicket: 0,
      nbProgressTicket: 0,
      nbResolvedTicket: 0,
      nbAddInfoTicket: 0,
      planetChartData: {
        type: "pie",
        data: {
          labels: ["None", "Low", "Medium", "High"],
          datasets: [
            {
              label: "Priority",
              data: [0, 0, 0, 0],
              backgroundColor: [
                "rgb(255, 99, 132)",
                "rgb(54, 162, 235)",
                "rgb(255, 205, 86)",
                "rgb(10, 10, 10)",
              ],
              hoverOffset: 4,
            },
          ],
        },
        options: {
          responsive: true,
        },
      },
      planetChartData2: {
        type: "pie",
        data: {
          labels: ["Bug", "FeatureRequest", "DocRequest", "Other"],
          datasets: [
            {
              label: "Type",
              data: [0, 0, 0, 0],
              backgroundColor: [
                "rgb(255, 99, 132)",
                "rgb(54, 162, 235)",
                "rgb(255, 205, 86)",
                "rgb(10, 10, 10)",
              ],
              hoverOffset: 4,
            },
          ],
        },
        options: {
          responsive: true,
        },
      },
      planetChartData3: {
        type: "pie",
        data: {
          labels: ["New", "Open", "InProgress", "Resolved", "AddInfo"],
          datasets: [
            {
              label: "Status",
              data: [0, 0, 0, 0, 0],
              backgroundColor: [
                "rgb(255, 99, 132)",
                "rgb(54, 162, 235)",
                "rgb(255, 205, 86)",
                "rgb(100, 205, 86)",
                "rgb(10, 10, 10)",
              ],
              hoverOffset: 4,
            },
          ],
        },
        options: {
          responsive: true,
        },
      },
      myChart: "",
      myChart2: "",
      myChart3: "",
    };
  },
  // Methods inspired from: https://www.bezkoder.com/vue-3-authentication-jwt/
  // and from: https://www.bezkoder.com/vue-refresh-token/
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    },
    currentUser() {
      return this.$store.state.auth.user;
    },
    currentUserIsAdminOrPM() {
      if (this.currentUser && this.currentUser.roles) {
        return (
          this.currentUser.roles.includes("ROLE_ADMIN") ||
          this.currentUser.roles.includes("ROLE_PROJECTMANAGER")
        );
      }

      return false;
    },
  },
  mounted() {
    const ctx = document.getElementById("planet-chart");
    this.myChart = new Chart(ctx, this.planetChartData);

    const ctx2 = document.getElementById("planet-chart2");
    this.myChart2 = new Chart(ctx2, this.planetChartData2);

    const ctx3 = document.getElementById("planet-chart3");
    this.myChart3 = new Chart(ctx3, this.planetChartData3);
  },
  created: function () {
    if (!this.loggedIn) {
      this.$router.push("/login");
    } else {
      if (this.currentUserIsAdminOrPM) {
        // Initializing  items from backend
        ProjectService.getAllProjects().then(
          (response) => {
            // JSON responses are automatically parsed.
            this.projects = response.data.slice(0, 3);
            console.log(this.projects);
          },
          (error) => {
            this.errorGetProjects =
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

        TicketService.getAllTickets().then(
          (response) => {
            var localTickets = response.data;

            for (var t of localTickets) {
              var priority = t.priority;
              if (priority == "Medium") {
                this.nbMediumTicket = this.nbMediumTicket + 1;
              } else if (priority == "High") {
                this.nbHighTicket = this.nbHighTicket + 1;
              } else if (priority == "Low") {
                this.nbLowTicket = this.nbLowTicket + 1;
              } else {
                this.nbNoneTicket = this.nbNoneTicket + 1;
              }

              var type = t.type;
              if (type == "Bug") {
                this.nbBugTicket = this.nbBugTicket + 1;
              } else if (type == "FeatureRequest") {
                this.nbFRTicket = this.nbFRTicket + 1;
              } else if (type == "DocRequest") {
                this.nbDRTicket = this.nbDRTicket + 1;
              } else {
                this.nbOtherTicket = this.nbOtherTicket + 1;
              }

              var status = t.status;
              if (status == "New") {
                this.nbNewTicket = this.nbNewTicket + 1;
              } else if (status == "Open") {
                this.nbOpenTicket = this.nbOpenTicket + 1;
              } else if (status == "InProgress") {
                this.nbProgressTicket = this.nbProgressTicket + 1;
              } else if (status == "Resolved") {
                this.nbResolvedTicket = this.nbResolvedTicket + 1;
              } else {
                this.nbAddInfoTicket = this.nbAddInfoTicket + 1;
              }
            }
            this.myChart.data.datasets[0].data = [
              this.nbNoneTicket,
              this.nbLowTicket,
              this.nbMediumTicket,
              this.nbHighTicket,
            ];
            this.myChart2.data.datasets[0].data = [
              this.nbBugTicket,
              this.nbFRTicket,
              this.nbDRTicket,
              this.nbOtherTicket,
            ];

            this.myChart3.data.datasets[0].data = [
              this.nbNewTicket,
              this.nbOpenTicket,
              this.nbProgressTicket,
              this.nbResolvedTicket,
              this.nbAddInfoTicket,
            ];

            this.myChart.update();
            this.myChart2.update();
            this.myChart3.update();

            // JSON responses are automatically parsed.
            this.tickets = localTickets.slice(0, 3);
          },
          (error) => {
            this.errorGetTickets =
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
      } else {
        // Initializing  items from backend
        ProjectService.getAssignedProjectForDev(this.currentUser.username).then(
          (response) => {
            // JSON responses are automatically parsed.
            this.projects = response.data.slice(0, 3);
            console.log(this.projects);
          },
          (error) => {
            this.errorGetProjects =
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

        TicketService.getAssignedTicketsForDev(this.currentUser.username).then(
          (response) => {
            var localTickets = response.data;

            for (var t of localTickets) {
              var priority = t.priority;
              if (priority == "Medium") {
                this.nbMediumTicket = this.nbMediumTicket + 1;
              } else if (priority == "High") {
                this.nbHighTicket = this.nbHighTicket + 1;
              } else if (priority == "Low") {
                this.nbLowTicket = this.nbLowTicket + 1;
              } else {
                this.nbNoneTicket = this.nbNoneTicket + 1;
              }

              var type = t.type;
              if (type == "Bug") {
                this.nbBugTicket = this.nbBugTicket + 1;
              } else if (type == "FeatureRequest") {
                this.nbFRTicket = this.nbFRTicket + 1;
              } else if (type == "DocRequest") {
                this.nbDRTicket = this.nbDRTicket + 1;
              } else {
                this.nbOtherTicket = this.nbOtherTicket + 1;
              }

              var status = t.status;
              if (status == "New") {
                this.nbNewTicket = this.nbNewTicket + 1;
              } else if (status == "Open") {
                this.nbOpenTicket = this.nbOpenTicket + 1;
              } else if (status == "InProgress") {
                this.nbProgressTicket = this.nbProgressTicket + 1;
              } else if (status == "Resolved") {
                this.nbResolvedTicket = this.nbResolvedTicket + 1;
              } else {
                this.nbAddInfoTicket = this.nbAddInfoTicket + 1;
              }
            }
            this.myChart.data.datasets[0].data = [
              this.nbNoneTicket,
              this.nbLowTicket,
              this.nbMediumTicket,
              this.nbHighTicket,
            ];
            this.myChart2.data.datasets[0].data = [
              this.nbBugTicket,
              this.nbFRTicket,
              this.nbDRTicket,
              this.nbOtherTicket,
            ];

            this.myChart3.data.datasets[0].data = [
              this.nbNewTicket,
              this.nbOpenTicket,
              this.nbProgressTicket,
              this.nbResolvedTicket,
              this.nbAddInfoTicket,
            ];

            this.myChart.update();
            this.myChart2.update();
            this.myChart3.update();

            // JSON responses are automatically parsed.
            this.tickets = localTickets.slice(0, 3);
          },
          (error) => {
            this.errorGetTickets =
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
};
</script>

<style scoped>
.container {
  margin-bottom: 50px;
  width: 100%;
}

h1 {
  margin-top: 100px;
  font-size: 20px;
  color: rgb(43, 43, 43);
}
.container .row {
  margin-top: 10px;
  width: 100%;
}

.container .row .col {
  margin: 20px;
  padding: 10px;
  border: 1px solid rgba(255, 255, 255, 0);
  border-radius: 10px;
  box-shadow: 5px 10px 18px #b9b9b9;
  width: 90%;
}

.container .row .col .table-responsive {
  display: flex;
  flex-direction: column;
  width: 100%;
  align-items: center;
  justify-content: center;
}

.dashboard .col p {
  margin-bottom: 20px;
}
.pie-container {
  display: flex;
  flex-direction: column;
  width: 100%;
  align-items: center;
  justify-content: center;
}
.pie {
  background-image: conic-gradient(orange 64%, blue 64%, blue 81%, black 81%);
  border-radius: 50%;
}

.btn-container {
  display: flex;
  flex-direction: column;
  width: 100%;
  align-items: center;
  justify-content: center;
  margin-bottom: 10px;
}

.btn-container button {
  padding: 10px;
  padding-left: 40px;
  padding-right: 40px;
}

.thecontent {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
}
</style>
