<template>
  <TicketPage :ticketType='ticketType'/>
</template>

<script>
//import NavBar from './components/NavBar.vue'
import TicketPage from './TicketPage.vue'

export default {
  name: 'CreatedTicketPage',
  components: {
    TicketPage
  },
    data(){
    return{
      ticketType:"Created tickets",
    }
  },
}
</script>


