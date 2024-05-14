<template>

  <NavBar/>
  <div class="container">
    <h1>ADMINISTRATION</h1>
  
    <div class = "tables">
    <div class="row g-100">
      
      <div class="col ">
        <div class="table-responsive">
    <DataTable
      :columns="columns"
      :data="data"
      class="table caption-top"
      width="100%"
      :options="{ pageLength: 5, lengthChange: false, select: true}" 
      ref="table"
      @select="clickRow()"
    >
          <caption>Organization</caption>
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
    
    <div class="outer-container">
        <div class="text-container">Edit user information</div>
        
    <div class="form-container">
        <form class="row g-3">
  <div class="col-md-6">
    <label for="inputEmail4" class="form-label">FIRST NAME</label>
    <input type="text" class="form-control" v-model="selectedFirstName" id="selectedFirstName">
  </div>
  <div class="col-md-6">
    <label for="inputPassword4" class="form-label">LAST NAME</label>
    <input type="text" class="form-control" v-model="selectedLastName" id="selectedLastName">
  </div>
  <div class="col-md-6">
    <label for="inputCity" class="form-label">USERNAME</label>
    <input type="text" class="form-control" v-model="selectedUsername" id="selectedUsername">
  </div>
    <div class="col-md-6">
    <label for="inputCity" class="form-label">EMAIL</label>
    <input type="email" class="form-control" v-model="selectedEmail" id="selectedEmail">
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
    <button type="submit" v-on:click="modifyUser(oldUsername, selectedUsername, selectedEmail, 
    '', selectedFirstName, selectedLastName, selectedRole)" class="btn btn-primary">Submit</button>
  </div>
    <div class="col-md-2">
    <button type="submit" v-on:click="deleteUser(oldUsername, selectedRole)" class="btn btn-danger">Remove user</button>
  </div>
</form>
</div>


    </div>

    
    </div>
</div>
</template>

<script>

import NavBar from './NavBar.vue'
import axios from 'axios'
var backendUrl = 'http://localhost:8080'
import DataTable from 'datatables.net-vue3';
import DataTablesCore from 'datatables.net';
import 'datatables.net-select';
import 'datatables.net-responsive';
import 'datatables.net-select-dt';
DataTable.use(DataTablesCore);

var AXIOS = axios.create({
  baseURL: backendUrl
})


export default {
  name: 'AdminPage',
  components: {
    NavBar,
    DataTable
  },

    data () {
    return {
      data: [],
      roles: [],
      errorModifyUser: '',
      errorModifyRole: '',
      errorGetUsers: '',
      errorGetRole: '',
      errorDeleteUser: '',
      columns: [
        { data: 'username' },
        { data: 'firstName' },
        { data: 'lastName' },
        { data: 'email' },

      ],
      r: '',
      oldUsername: '',
      selectedUsername: '',
      selectedFirstName: '',
      selectedLastName: '',
      selectedEmail: '',
      selectedRole: 'null',
      
    }
  },
    mounted: function () {

    // Initializing  items from backend
    AXIOS.get('/users')
    .then(response => {
      // JSON responses are automatically parsed.
      this.data = response.data;


      
    })
    .catch(e => {
      this.errorGetUsers = e;
    })
            

  },
       methods: {
          clickRow() {
              const table = this.$refs.table.dt;
              var user = table.row('.selected').data();
              this.oldUsername = user.username;
              this.selectedUsername = user.username;
              this.selectedFirstName = user.firstName;
              this.selectedLastName = user.lastName;
              this.selectedEmail = user.email;


                  // Initializing  items from backend
    AXIOS.get('/user/role/'.concat(user.username))
    .then(response => {
      // JSON responses are automatically parsed.
      this.selectedRole = response.data;
      console.log(response.data);
    })
    .catch(e => {
      this.errorGetRole = e;
    })
      },

         modifyUser: function(oldUsername, newUsername, newEmail, newPassword, newFirstName, newLastName, newRole){
       AXIOS.post('/user/modify/'.concat(oldUsername), {}, {
        params: {
          newUsername: newUsername,
          newEmail: newEmail,
          newPassword: newPassword,
          newFirstName: newFirstName,
          newLastName: newLastName
        }})
    .then(response => {
      // JSON responses are automatically parsed.
      
      this.selectedUsername = response.data.username
      this.selectedEmail = response.data.email
      this.selectedPassword = response.data.password
      this.selectedFirstName = response.data.firstName
      this.selectedLastName = response.data.lastName

      AXIOS.post('/user/modifyRole/'.concat(this.selectedUsername), {}, {
        params: {
          userRole: newRole
        }})
      .then(response => {
      // JSON responses are automatically parsed.
      this.oldUsername = response.data.username
      this.selectedRole = newRole
    })
        .catch(e => {
        this.errorModifyRole= e
        var errorMsg = e.response.data.message
        console.log(errorMsg)
      
    })
    })
    .catch(e => {
        this.errorModifyUser= e
        var errorMsg = e.response.data.message
        console.log(errorMsg)
      
    })
   },
    deleteUser : function(username, role){
      if (role == 'Developer'){
           AXIOS.post('/developer/delete/'.concat(username), {}, {})
              .then(response => {
                this.r = response.data
                this.oldUsername= ''
                this.selectedUsername= ''
                this.selectedFirstName= ''
                this.selectedLastName= ''
                this.selectedEmail= ''
                this.selectedRole= ''
              })
              .catch(e => {
                  this.errorDeleteUser= e
                  var errorMsg = e.response.data.message
                  console.log(errorMsg)
                
              })
      } else if (role == 'Admin'){
           AXIOS.post('/admin/delete/'.concat(username), {}, {})
              .then(response => {
                this.r = response.data
                this.oldUsername= ''
                this.selectedUsername= ''
                this.selectedFirstName= ''
                this.selectedLastName= ''
                this.selectedEmail= ''
                this.selectedRole= ''
                this.$router.push('/adminAccount/administration');
              })
              .catch(e => {
                  this.errorDeleteUser= e
                  var errorMsg = e.response.data.message
                  console.log(errorMsg)
                
              })
      } else if (role == 'ProjectManager'){
           AXIOS.post('/projectmanager/delete/'.concat(username), {}, {})
              .then(response => {
                this.r = response.data
                this.oldUsername= ''
                this.selectedUsername= ''
                this.selectedFirstName= ''
                this.selectedLastName= ''
                this.selectedEmail= ''
                this.selectedRole= ''
                this.$router.push('/adminAccount/administration');
              })
              .catch(e => {
                  this.errorDeleteUser= e
                  var errorMsg = e.response.data.message
                  console.log(errorMsg)
                
              })
      }

   },
  }
    
}
</script>

<style scoped>
@import 'datatables.net-bs5';
@import 'datatables.net-dt';

h1{
  margin-top:100px;
  font-size: 20px;
  color: rgb(43, 43, 43);
}

label{
    font-size: 13px;
}

.tables{
  margin-top: 30px;
  width: 100%;
  

}

.tables .col{
  margin: 10px;
  border: 1px solid rgba(255, 255, 255, 0);
  border-radius: 5px;
  box-shadow: 5px 5px 10px #b9b9b9;
  padding-top: 10px;
  padding-bottom: 20px;
}
.text-container{
    padding: 10px;
    color: gray;
}

.outer-container{
     margin-top: 30px;
  border: 1px solid rgba(255, 255, 255, 0);
  border-radius: 5px;
  box-shadow: 5px 5px 10px #b9b9b9; 
  width: 100%;
  margin-bottom: 50px;

}

.form-container{

    padding: 20px;
    width: 100%;
    background-color: rgb(249, 249, 249);
    padding-bottom: 50px;
    
}

form{

    width: 100%;
    margin-top: 5px;
    background-color: white;
    padding: 20px;
}

button{
    margin-top: 20px;
}

</style>