
import api from './api';

// Code inspired from: https://www.bezkoder.com/vue-refresh-token/
class TicketService {

    getAllTickets(){
        return api.get("/test/tickets/")
    }

    getAssignedTicketsForDev(devUsername){
        return api.get("/test/developer/tickets/".concat(devUsername))
    }

    getTicketsForProjectWithId(id){
        return api.get("/test/project/tickets/".concat(id))
    }

    createTicket(title, description, priority, status, type, projectId, submitterUsername){
        return api.post("/test/ticket/create/", null, {           
            params: {
                title: title,
                description: description,
                priority: priority,
                status: status,
                type: type,
                projectId: projectId,
                submitterUsername: submitterUsername,
              } })
    }

    assignDevsToTicket(ticketId, devUsernames){
        return api.post("/test/ticket/assignDevs/".concat(ticketId), null, {    
                      
            params: {
                devUsernames: devUsernames,
              } })
    }

    modifyTicket(ticketId, title, description, priority, status, type){
        return api.post("/test/ticket/modify/".concat(ticketId), null, {     
                   
            params: {
                title: title,
                description: description,
                priority: priority,
                status: status,
                type: type
              }})
    }

    assignProjectToTicket(ticketId, projectId){
        return api.post("/test/ticket/project/".concat(ticketId), null, {             
            
            params: {
                projectId: projectId,
              } })
    }

    createCommentForTicket(ticketId, commenterUsername, message){
        return api.post("/test/ticket/createComment/".concat(ticketId), null, {             
            
            params: {
                commenterUsername: commenterUsername,
                message: message,
              } })
    }

    deleteTicket(ticketId){
        return api.post("/test/ticket/delete/".concat(ticketId), null, {}) 
    }
}

export default new TicketService();