<template>
<div class="containers">
<div class="row">
<h3>Create a developer account</h3>
<div class="mb-3">
  <label for="username1" class="form-label">Username</label>
  <input type="text" v-model="newDev.username" class="form-control" id="username1" placeholder="">
</div>
<div class="mb-3">
  <label for="email2" class="form-label">First name</label>
  <input type="text" v-model="newDev.firstName" class="form-control" id="email2" placeholder="">
</div>
<div class="mb-3">
  <label for="lastName" class="form-label">Last name</label>
  <input type="text" v-model="newDev.lastName" class="form-control" id="lastName" placeholder="">
</div>
<div class="mb-3">
  <label for="firstName" class="form-label">Email</label>
  <input type="text" v-model="newDev.email" class="form-control" id="firstName" placeholder="">
</div>
<div class="mb-3">
  <label for="pwd" class="form-label">Password</label>
  <input type="text" v-model="newDev.password" class="form-control" id="pwd" placeholder="">
</div>
  <div class="mb-3">
    <button type="submit" class="btn btn-primary" 
    v-on:click="createDev(newDev.username, newDev.email,
           newDev.password, newDev.firstName, newDev.lastName)">Sign up</button>
  </div>
  </div>
  </div>
</template>

<script>
import axios from 'axios'
var backendUrl = 'http://localhost:8080'

var AXIOS = axios.create({
  baseURL: backendUrl
})

export default {
  name: 'SignupPage',
    data() {
    return {
      devs: [],
      newDev: {
        username: '',
        email: '',
        password: '',
        firstName: '',
        lastName: ''
      },
      errorCreateDev: '',
      response: []

    };
  },
  methods: {
   createDev: function(username, email, password, firstName, lastName){
   
    AXIOS.post('/developer/create', {}, {
        params: {
         username: username,
         email: email,
         password: password,
         firstName: firstName,
         lastName: lastName
        }})
    .then(response => {
      this.devs.push(response.data)
      // Initialize all variables for the next function call
      this.errorCreateDev = ''
      this.newDev.username = ''
      this.newDev.email = ''
      this.newDev.password = ''
      this.newDev.firstName = ''   
      this.newDev.lastName = '' 
      
      this.$router.push('/login');
    })
    .catch(e => {
      var errorMsg = e.response.data.message
      console.log(errorMsg)
      this.errorCreateDev = errorMsg
    })
   },
  }
}
</script>

<style scoped>


.containers{
      height: 100vh;
  width: 100vw;
	background: linear-gradient(-45deg, #9fffff, #3cb9e7, #c6e8ff, rgb(35, 213, 174));
	background-size: 400% 400%;
	animation: gradient 15s ease infinite;
    display: flex;
  flex-direction:column;
  width: 100%;
  align-items: center;
  justify-content: center;
}

@keyframes gradient {
	0% {
		background-position: 0% 50%;
	}
	50% {
		background-position: 100% 50%;
	}
	100% {
		background-position: 0% 50%;
	}
}


.row{
    background-color:white;
    padding: 30px;
    width: 500px;
    border: 1px solid rgb(183, 183, 183);
    border-radius: 5px;
    padding-top: 50px;
}
h3{
    margin-bottom: 40px;
}

</style>