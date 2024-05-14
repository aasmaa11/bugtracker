<template>

  <NavBar/>
  <div class="container">
    <h1>DASHBOARD</h1>
    <div class="dashboard">
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
    </div>
    <div class = "tables">
    <div class="row g-100">
      
      <div class="col ">
        <div class="table-responsive">
        <table class="table caption-top">
          <caption>Projects</caption>
          <thead class="table-light">
            <tr>
              <th scope="col">Title</th>
              <th scope="col">Description</th>
              <th scope="col">Project Manager</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="project in projects" :key=project.id>
              <td>{{project.name}}</td>
              <td>{{project.description}}</td>
              <td>{{project.projectManager.firstName}} {{project.projectManager.lastName}}</td>
            </tr>
          </tbody>
        </table>
        <div class="btn-container">
          <router-link :to="'/assignedProjects'">
          <button type="button" class="btn btn-outline-primary">View all</button>
          </router-link>
        </div>
        
        </div>
      </div>
      <div class="col">
        <table class="table caption-top">
          <caption>Assigned tickets</caption>
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
            <tr v-for="ticket in tickets" :key=ticket.id>
              <td>{{ticket.title}}</td>
              <td>{{ticket.description}}</td>
              <td>{{ticket.priority}}</td>
              <td>{{ticket.type}}</td>
              <td>{{ticket.status}}</td>
            </tr>
          </tbody>
          
        </table>
        <div class="btn-container">
          <router-link :to="'/createdTickets'"><button type="button" class="btn btn-outline-primary">View all</button></router-link>
        </div>
      </div>
    </div>
    </div>
</div>
</template>

<script>
import Chart from 'chart.js'
import NavBar from './NavBar.vue'
import axios from 'axios'
var backendUrl = 'http://localhost:8080'

var AXIOS = axios.create({
  baseURL: backendUrl
})


export default {
  name: 'DashboardPage',
  components: {
    NavBar
  },
    data () {
    return {
      projects: [],
      tickets: [],
      errorGetProjects: '',
      errorGetTickets: '',
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
      planetChartData : {
  type: 'pie',
  data: {
    labels: ['None', 'Low', 'Medium', 'High'],
    datasets: [{
    label: 'Priority',
    data: [0,0,0,0],
    backgroundColor: [
      'rgb(255, 99, 132)',
      'rgb(54, 162, 235)',
      'rgb(255, 205, 86)',
      'rgb(10, 10, 10)'
    ],
    hoverOffset: 4
  }]
  },
  options: {
    responsive: true,
    
  }
},
      planetChartData2 : {
  type: 'pie',
  data: {
    labels: ['Bug', 'FeatureRequest', 'DocRequest', 'Other'],
    datasets: [{
    label: 'Type',
    data: [0,0,0,0],
    backgroundColor: [
      'rgb(255, 99, 132)',
      'rgb(54, 162, 235)',
      'rgb(255, 205, 86)',
      'rgb(10, 10, 10)'
    ],
    hoverOffset: 4
  }]
  },
  options: {
    responsive: true,
    
  }
},
      planetChartData3 : {
  type: 'pie',
  data: {
    labels: ['New','Open', 'InProgress', 'Resolved', 'AddInfo'],
    datasets: [{
    label: 'Status',
    data: [0,0,0,0,0],
    backgroundColor: [
      'rgb(255, 99, 132)',
      'rgb(54, 162, 235)',
      'rgb(255, 205, 86)',
      'rgb(100, 205, 86)',
      'rgb(10, 10, 10)'
    ],
    hoverOffset: 4
  }]
  },
  options: {
    responsive: true,
    
  }
},
myChart : '',
myChart2 : '',
myChart3 : ''
    }
  },
    mounted() {
          
    const ctx = document.getElementById('planet-chart');
    this.myChart = new Chart(ctx, this.planetChartData);

    const ctx2 = document.getElementById('planet-chart2');
    this.myChart2 = new Chart(ctx2, this.planetChartData2);

    const ctx3 = document.getElementById('planet-chart3');
    this.myChart3 = new Chart(ctx3, this.planetChartData3);



  },
    created: function () {
    // Initializing  items from backend
    AXIOS.get('/projects/')
    .then(response => {
      // JSON responses are automatically parsed.
      this.projects = response.data.slice(0, 4);
      console.log(this.projects)
    })
    .catch(e => {
      this.errorGetProjects = e
    })

        // Initializing  items from backend
    AXIOS.get('/developer/tickets/'.concat(localStorage.getItem('token')))
    .then(response => {
      
        var localTickets = response.data

      for(var t of localTickets){
        var priority = t.priority
        if(priority == 'Medium'){
          this.nbMediumTicket = this.nbMediumTicket + 1
        }else if(priority == 'High'){
          this.nbHighTicket = this.nbHighTicket + 1
        }else if(priority == 'Low'){
          this.nbLowTicket = this.nbLowTicket + 1
        }else{
          this.nbNoneTicket = this.nbNoneTicket + 1
        }

        var type = t.type
        if(type == 'Bug'){
          this.nbBugTicket = this.nbBugTicket + 1
        }else if(type == 'FeatureRequest'){
          this.nbFRTicket = this.nbFRTicket + 1
        }else if(type == 'DocRequest'){
          this.nbDRTicket = this.nbDRTicket + 1
        }else{
          this.nbOtherTicket = this.nbOtherTicket + 1
        }

        var status = t.status
        if(status == 'New'){
          this.nbNewTicket = this.nbNewTicket + 1
        }else if(status == 'Open'){
          this.nbOpenTicket = this.nbOpenTicket + 1
        }else if(status == 'InProgress'){
          this.nbProgressTicket = this.nbProgressTicket + 1
        }else if(status == 'Resolved'){
          this.nbResolvedTicket = this.nbResolvedTicket + 1
        }else{
          this.nbAddInfoTicket = this.nbAddInfoTicket + 1
        }
      }
      this.myChart.data.datasets[0].data = [this.nbNoneTicket, 
        this.nbLowTicket, this.nbMediumTicket, this.nbHighTicket
      ]
      this.myChart2.data.datasets[0].data = [this.nbBugTicket, 
        this.nbFRTicket, this.nbDRTicket, this.nbOtherTicket
      ]

      this.myChart3.data.datasets[0].data = [this.nbNewTicket, 
        this.nbOpenTicket, this.nbProgressTicket, this.nbResolvedTicket,
        this.nbAddInfoTicket
      ]

      this.myChart.update();
      this.myChart2.update();
      this.myChart3.update();

            // JSON responses are automatically parsed.
      this.tickets = localTickets.slice(0, 4);
    })
    .catch(e => {
      this.errorGetTickets = e
    })
  },
}
</script>

<style scoped>

.container{
  margin-bottom: 50px;
}

h1{
  margin-top:100px;
  font-size: 20px;
  color: rgb(43, 43, 43);
}
.container .dashboard .row{
  margin-top:10px;
  padding: 20px;
}

.container .dashboard .row .col{
  background-color:white;
  margin:20px;
  padding: 10px;
  border: 1px solid rgba(255, 255, 255, 0);
  border-radius: 10px;
  box-shadow: 5px 10px 18px #b9b9b9;

}

.dashboard .col p{
  margin-bottom: 20px;
}
.pie-container{
  display: flex;
  flex-direction:column;
  width: 100%;
  align-items: center;
  justify-content: center;
}
.pie {
  width: 150px;
  height: 150px;
  background-image: conic-gradient(orange 64%, blue 64%, blue 81%, black 81%);
  border-radius: 50%;
}

.tables{
  width: 100%;
}

.tables .col{
  margin: 10px;
  border: 1px solid rgba(255, 255, 255, 0);
  border-radius: 5px;
  box-shadow: 5px 5px 10px #b9b9b9;
}

.btn-container{
    display: flex;
  flex-direction:column;
  width: 100%;
  align-items: center;
  justify-content: center;
  margin-bottom: 10px;
}

.btn-container button{
  padding: 10px;
  padding-left: 40px;
  padding-right: 40px;
  
}
</style>