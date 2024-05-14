<template>
<div class="containers">
<div class="row">
<h3>Log in as a developer</h3>
<div class="mb-3">
  <label for="formGroupExampleInput" class="form-label">Username</label>
  <input type="text" v-model="devUsername" class="form-control" id="formGroupExampleInput" placeholder="">
</div>
<div class="mb-3">
  <label for="formGroupExampleInput2" class="form-label">Password</label>
  <input type="text"  v-model="devPassword" class="form-control" id="formGroupExampleInput2" placeholder="">
</div>
  <div class="mb-3">
    <button type="submit" class="btn btn-primary"
    v-on:click="logIn(devUsername, devPassword)">Log in</button>
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
  name: 'LoginPage',
  data () {
    return {
      item:'',
      devUsername:'',
      devPassword:'',
      errorLogin:'',
      response: []
    }
  },

  methods: {

   logIn: function(username, password){
   
    AXIOS.get('/developer/'.concat(username))
    .then(response => {
      if(password.localeCompare(response.data.password) == 0){
        let token = username;
        localStorage.setItem('token', token);
        this.$router.push('/dashboard');
      }else{
        
        this.errorLogin="Wrong password";
      }
      
    })
    .catch(e => {
      this.errorLogin = e
      var errorMsg = e.response.data.message
      console.log(errorMsg)
      
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
    width: 400px;
    border: 1px solid rgb(183, 183, 183);
    border-radius: 5px;
    padding-top: 50px;
}
h3{
    margin-bottom: 50px;
}

</style>