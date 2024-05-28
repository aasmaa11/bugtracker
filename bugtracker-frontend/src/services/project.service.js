import api from './api';

// Code inspired from https://www.bezkoder.com/vue-refresh-token/
class ProjectService {
    getAllProjects(){
        return api.get("/test/projects")
    }

    getAssignedProjectForDev(devUsername){
        return api.get("/test/developer/projects/".concat(devUsername))
    }

    getProjectWithId(id){
        return api.get("/test/project/".concat(id))
    }

    createProject(name, description, projectManagerUsername){
        return api.post("/test/project/create/", null,{      
            params: {
            name: name,
            description: description,
            projectManagerUsername: projectManagerUsername,
          }  })
    }

    assignDevsToProject(id, devUsernames){
        return api.post("/test/project/setDevs/".concat(id), null,{
             params: {
            devUsernames: devUsernames
          }})
    }

    modifyProject(projectId, name, description){
        return api.post("/test/project/modify/", null, {    
                    
            params: {
                projectId: projectId,
            name: name,
            description: description,
          } })
    }

    assignPMToProject(projectId, projectManagerUsername){
        return api.post("/test/project/assignPm/".concat(projectManagerUsername), null, { 
            
            params: {
                projectId: projectId,
          } })
    }

    deleteProject(id){
        return api.post("/test/project/delete/".concat(id), null, {}) 
    }
}

export default new ProjectService();