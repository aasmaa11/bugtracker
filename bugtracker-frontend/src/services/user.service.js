import api from './api';

// Code inspired from: https://www.bezkoder.com/vue-refresh-token/
class UserService {

    getAllDevelopers(){
        return api.get("/test/developers/")
    }

    getUserWithUsername(username){
        return api.get("/test/user/".concat(username))
    }

    getAllProjectManagers(){
        return api.get("/test/projectmanagers/")
    }

    getAllUsers(){
        return api.get("/test/users/")
    }

    getRoleForUser(username){
        return api.get("/test/user/role/".concat(username))
    }

    modifyUser(oldUsername, newUsername, newEmail, newPassword, newFirstName, newLastName){
        return api.post("/test/user/modify/".concat(oldUsername), null, {             
            
            params: {
                newUsername: newUsername,
                newEmail: newEmail,
                newPassword: newPassword,
                newFirstName: newFirstName,
                newLastName: newLastName,
              } })
    }

    modifyRoleOfUser(username, newRole){
        return api.post("/test/user/modifyRole/".concat(username), null, {             
            
            params: {
                userRole: newRole,
              } })
    }

    deleteDeveloper(username){
        return api.post("/test/developer/delete/".concat(username), null, {}) 
    }

    deleteAdmin(username){
        return api.post("/test/admin/delete/".concat(username), null, {}) 
    }

    deleteProjectManager(username){
        return api.post("/test/projectmanager/delete/".concat(username), null, {}) 
    }
}

export default new UserService();