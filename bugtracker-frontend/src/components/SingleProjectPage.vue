<template>

  <NavBar/>
  <div class="container">
    <h1>PROJECT: </h1>
       <div class="outer-container">
        <div class="text-container">Selected project info
                        <div class="btn-container2">
        <a href="#" data-bs-toggle="modal" data-bs-target="#exampleModal4">
            <img src="../assets/edit.svg" alt="" width="20" height="20" class="d-inline-block align-text-top"
            data-bs-toggle="tooltip" data-bs-placement="right" title="Edit"></a>
        </div>
        </div>
          <div class="row g-3">
    <div class="col">
      <div class="ticket-info">
          <div class="row">
    <div class="col">
      <div class="text-container3">TITLE</div>
      <div class="text-container4">Ticket 3</div>
    </div>
        <div class="col">
      <div class="text-container3">PROJECT MANAGER</div>
      <div class="text-container4">Ticket 3</div>
    </div>
    <div class="col">
      <div class="text-container3">DESCRIPTION</div>
      <div class="text-container4">Random description for the purpose of nothing</div>
    </div>
    </div>
    </div>
    </div>
    </div>

    </div>

    <div class="modal" id="exampleModal4" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Edit project</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <form class="row g-3">
  <div class="col-md-12">
    <label for="inputEmail4" class="form-label">Title</label>
    <input type="email" class="form-control" id="inputEmail4">
  </div>
  <div class="col-md-12">
    <label for="inputPassword4" class="form-label">Description</label>
    <input type="password" class="form-control" id="inputPassword4">
  </div>
  <div class="col-12">
    <label for="inputState" class="form-label">Project Manager</label>
    <select id="inputState" class="form-select">
      <option selected>Choose...</option>
      <option>...</option>
    </select>
  </div>
</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary">Create new project</button>
      </div>
    </div>
  </div>
</div>


    <div class="modal" id="exampleModal3" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Edit list of assigned developers</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <form class="row g-3">

  <div class="col-12">
    <label for="inputAddress" class="form-label">Assigned Developers</label>
    <select class="form-select" multiple aria-label="multiple select example">
  <option selected>Open this select menu</option>
  <option value="1">One</option>
  <option value="2">Two</option>
  <option value="3">Three</option>
</select>
  </div>
</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary">Submit changes</button>
      </div>
    </div>
  </div>
</div>




    <div class="modal" id="exampleModal2" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">New ticket</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <form class="row g-3">
  <div class="col-md-12">
    <label for="inputEmail4" class="form-label">Title</label>
    <input type="email" class="form-control" id="inputEmail4">
  </div>
  <div class="col-md-12">
    <label for="inputPassword4" class="form-label">Description</label>
    <input type="password" class="form-control" id="inputPassword4">
  </div>
  <div class="col-12">
    <label for="inputAddress" class="form-label">Assigned Developers</label>
    <select class="form-select" multiple aria-label="multiple select example">
  <option selected>Open this select menu</option>
  <option value="1">One</option>
  <option value="2">Two</option>
  <option value="3">Three</option>
</select>
  </div>
  <div class="col-md-4">
    <label for="inputState" class="form-label">Priority</label>
    <select id="inputState" class="form-select">
      <option selected>Choose...</option>
      <option>...</option>
    </select>
  </div>
  <div class="col-md-4">
    <label for="inputState" class="form-label">Type</label>
    <select id="inputState" class="form-select">
      <option selected>Choose...</option>
      <option>...</option>
    </select>
  </div>
  <div class="col-md-4">
    <label for="inputState" class="form-label">Status</label>
    <select id="inputState" class="form-select">
      <option selected>Choose...</option>
      <option>...</option>
    </select>
  </div>
</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary">Create new ticket</button>
      </div>
    </div>
  </div>
</div>

    

    <div class = "tables">
    <div class="row g-100">
            <div class="col">
        <DataTable :options="{ pageLength: 5, lengthChange: false, select: true, select: 'single',select: {
        info: false}}" class="table caption-top">
          <caption>Tickets</caption>
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
            <tr>
              <td>Mark</td>
              <td>Otto</td>
              <td>@mdo</td>
              <td>@mdo</td>
              <td>@mdo</td>
            </tr>
            <tr>
              <td>Jacob</td>
              <td>Thornton</td>
              <td>@fat</td>
              <td>@mdo</td>
              <td>@mdo</td>
            </tr>
          </tbody>
          
        </DataTable>
        <div class="btn-container">
        <button type="button" class="btn btn-primary">
          <a href="#" data-bs-toggle="modal" data-bs-target="#exampleModal2">New ticket</a>
        </button>
        </div>
      </div>
      <div class="col ">
        <div class="table-responsive">
        <DataTable :options="{ pageLength: 5, lengthChange: false }" id="example" class="table caption-top">
          <caption>Assigned Developers</caption>
          <thead class="table-light">
            <tr>
              <th scope="col">Name</th>
              <th scope="col">Username</th>
              <th scope="col">Email</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>Mark</td>
              <td>Otto</td>
              <td>@mdo</td>
            </tr>
            <tr>
              <td>Jacob</td>
              <td>Thornton</td>
              <td>@fat</td>
            </tr>
          </tbody>
        </DataTable>
        <div class="btn-container">
                  <button type="button" class="btn btn-primary">
          <a href="#" data-bs-toggle="modal" data-bs-target="#exampleModal3">Edit List</a>
        </button>
        </div>
        
        </div>
      </div>

    </div>
    </div>

        <div class="outer-container">
        <div class="text-container">Selected ticket info</div>
          <div class="row g-3">
    <div class="col">
      <div class="ticket-info">
          <div class="row">
    <div class="col-3">
      <div class="text-container3">TITLE</div>
      <div class="text-container4">Ticket 3</div>
    </div>
    <div class="col-9">
      <div class="text-container3">DESCRIPTION</div>
      <div class="text-container4">Random description for the purpose of nothing</div>
    </div>
  </div>
    <div class="row">
    <div class="col">
    <div class="text-container3">TYPE</div>
      <div class="text-container4">Ticket 3</div>
    </div>
    <div class="col">
            <div class="text-container3">PRIORITY</div>
      <div class="text-container4">Ticket 3</div>
    </div>
    <div class="col">
           <div class="text-container3">STATUS</div>
      <div class="text-container4">Ticket 3</div>
    </div>
  </div>
    <div class="row">
    <div class="col">
            <div class="text-container3">ASSIGNED DEVS</div>
      <div class="text-container4">Ticket 3</div>
    </div>
    <div class="col">
            <div class="text-container3">TICKET ATTACHMENTS</div>
      <div class="text-container4">Ticket 3</div>
    </div>
  </div>
      </div>
      <div class="ticket-hist">
      <div class="row">
    <div class="col">
    
               <div class="table-responsive">
        <DataTable :options="{ pageLength: 5, lengthChange: false}" id="example" class="table caption-top">
          <caption>Ticket history</caption>
          <thead class="table-light">
            <tr>
              <th scope="col">Property changed</th>
              <th scope="col">Old value</th>
              <th scope="col">New value</th>
              <th scope="col">Date of change</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>jahmood</td>
              <td>Boualam</td>
              <td>@mdo</td>
              <td>@mdo</td>
            </tr>
            <tr>
              <td>Mahmood</td>
              <td>Boualam</td>
              <td>@mdo</td>
              <td>@mdo</td>
            </tr>
            <tr>
              <td>Mahmood</td>
              <td>Boualam</td>
              <td>@mdo</td>
              <td>@mdo</td>
            </tr>
            <tr>
              <td>Mahmood</td>
              <td>Boualam</td>
              <td>@mdo</td>
              <td>@mdo</td>
            </tr>
            <tr>
              <td>Mahmood</td>
              <td>Boualam</td>
              <td>@mdo</td>
              <td>@mdo</td>
            </tr>
            <tr>
              <td>Mahmood</td>
              <td>Boualam</td>
              <td>@mdo</td>
              <td>@mdo</td>
            </tr>
            <tr>
              <td>Mahmood</td>
              <td>Boualam</td>
              <td>@mdo</td>
              <td>@mdo</td>
            </tr>
            <tr>
              <td>Mahmood</td>
              <td>Boualam</td>
              <td>@mdo</td>
              <td>@mdo</td>
            </tr>
          </tbody>
        </DataTable>
        
        </div>
      </div>
      </div>
      </div>
    </div>
    <div class="col">
           <div class="comment">
        <div class="text-container">Comments</div>
        <form class="row g-3">

  <div class="col-12">
    <input type="text" class="form-control" id="inputAddress" placeholder="">
  </div>
    <div class="col-12">
    <button type="submit" class="btn btn-primary">Publish</button>
  </div>

</form>
      </div>
    </div>
  </div>

    </div>
</div>
</template>

<script>
import NavBar from './NavBar.vue'
import DataTable from 'datatables.net-vue3';
import DataTablesCore from 'datatables.net';
import 'datatables.net-select';
import 'datatables.net-responsive';
import 'datatables.net-select-dt';
DataTable.use(DataTablesCore);

export default {
  name: 'SingleProjectPage',
  components: {
    NavBar,
    DataTable
  },
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

.tables{
  width: 100%;
}

.tables .col{
  margin: 10px;
  border: 1px solid rgba(255, 255, 255, 0);
  border-radius: 5px;
  box-shadow: 5px 5px 10px #b9b9b9;
}

.btn-container button a{
  text-decoration: none;
  color: white;
}

.btn-container2{
  margin-left: 10px;
}

.btn-container button{
  margin-bottom: 20px;
}


.outer-container{
     margin-top: 30px;
  border: 1px solid rgba(255, 255, 255, 0);
  border-radius: 5px;
  box-shadow: 5px 5px 10px #b9b9b9; 
  
  margin-bottom: 50px;
  background-color: rgb(246, 246, 246);

}

.outer-container .row{
    padding: 20px;
    
}




.comment .row{
    margin-top: 5px;
    padding: 5px;
    
}


.ticket-info .row{
background-color: white;
      border: 1px solid rgba(255, 255, 255, 0);
  border-radius: 5px;
}

.ticket-hist{
    margin-top: 10px;

}

.ticket-hist .row{
background-color: white;
      border: 1px solid rgba(255, 255, 255, 0);
  border-radius: 5px;
}


.comment{
    background-color: white;
    padding: 10px;
    padding-bottom: 50px;
      border: 1px solid rgba(255, 255, 255, 0);
  border-radius: 5px;
}

.text-container{
    display: flex;
    flex-direction: row;
    padding: 10px;
    color: gray;
    background-color: white;
}

.text-container3{
    color: rgb(73, 73, 73);
    font-size: 12px;
}
</style>