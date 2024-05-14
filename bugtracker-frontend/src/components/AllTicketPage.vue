<template>
  <TicketPage :ticketType='ticketType'/>
</template>

<script>
//import NavBar from './components/NavBar.vue'
import TicketPage from './TicketPage.vue'

export default {
  name: 'AllTicketPage',
  components: {
    TicketPage
  },
    data(){
    return{
      ticketType:"All tickets",
    }
  },
}
</script>


