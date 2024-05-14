<template>
  <NavBar />
  <div
    class="modal"
    id="exampleModal1"
    tabindex="-1"
    aria-labelledby="exampleModalLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">Edit ticket</h5>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body">
          <form class="row g-3">
            <div class="col-md-12">
              <label for="title" class="form-label">Title</label>
              <input class="form-control" v-model="newTitle" id="title1" />
            </div>
            <div class="col-md-12">
              <label for="project" class="form-label">Project</label>
              <select
                id="priority1"
                v-model="newTicketProject"
                @change="setDevs(newTicketProject)"
                class="form-select"
              >
                <option v-for="p in data" v-bind:value="p.id" :key="p.id">
                  {{ p.name }}
                </option>
              </select>
            </div>
            <div class="col-md-12">
              <label for="description" class="form-label">Description</label>
              <input
                class="form-control"
                v-model="newDescription"
                id="description1"
              />
            </div>
            <div class="col-12">
              <label for="devs" class="form-label">Assigned Developers</label>
              <select
                class="form-select"
                v-model="newTicketDevs"
                multiple
                aria-label="multiple select example"
              >
                <option
                  v-for="dev in selectedProjectDevs"
                  :key="dev.id"
                  v-bind:value="dev.username"
                >
                  {{ dev.firstName }} {{ dev.lastName }}
                </option>
              </select>
            </div>
            <div class="col-md-4">
              <label for="priority" class="form-label">Priority</label>
              <select id="priority1" v-model="newPriority" class="form-select">
                <option v-for="p in priorityChoices" :key="p.id">
                  {{ p }}
                </option>
              </select>
            </div>
            <div class="col-md-4">
              <label for="type" class="form-label">Type</label>
              <select id="type1" v-model="newType" class="form-select">
                <option v-for="t in typeChoices" :key="t.id">{{ t }}</option>
              </select>
            </div>
            <div class="col-md-4">
              <label for="status" class="form-label">Status</label>
              <select id="status1" v-model="newStatus" class="form-select">
                <option v-for="s in statusChoices" :key="s.id">{{ s }}</option>
              </select>
            </div>
          </form>
        </div>
        <div class="modal-footer">
          <button
            ref="CloseModify"
            type="button"
            class="btn btn-secondary"
            data-bs-dismiss="modal"
          >
            Close
          </button>
          <button
            type="button"
            class="btn btn-primary"
            v-on:click="
              modifyTicket(
                selectedTicketId,
                newTitle,
                newDescription,
                newPriority,
                newStatus,
                newType,
                newTicketProject,
                newTicketDevs
              )
            "
          >
            Save changes
          </button>
        </div>
      </div>
    </div>
  </div>

  <div
    class="modal"
    id="exampleModal3"
    tabindex="-1"
    aria-labelledby="exampleModalLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">New ticket</h5>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body">
          <form class="row g-3">
            <div class="col-md-12">
              <label for="title1" class="form-label">Title</label>
              <input class="form-control" v-model="title" id="title1" />
            </div>
            <div class="col-md-12">
              <label for="description1" class="form-label">Description</label>
              <input
                class="form-control"
                v-model="description"
                id="description1"
              />
            </div>
            <div class="col-12">
              <label for="devs1" class="form-label">Assigned Developers</label>
              <select
                class="form-select"
                v-model="ticketDevs"
                multiple
                aria-label="multiple select example"
              >
                <option
                  v-for="dev in selectedProjectDevs"
                  :key="dev.id"
                  v-bind:value="dev.username"
                >
                  {{ dev.firstName }} {{ dev.lastName }}
                </option>
              </select>
            </div>
            <div class="col-md-4">
              <label for="priority1" class="form-label">Priority</label>
              <select id="priority1" v-model="priority" class="form-select">
                <option :value="null" disabled>Choose...</option>
                <option v-for="p in priorityChoices" :key="p.id">
                  {{ p }}
                </option>
              </select>
            </div>
            <div class="col-md-4">
              <label for="type1" class="form-label">Type</label>
              <select id="type1" v-model="type" class="form-select">
                <option :value="null" disabled>Choose...</option>
                <option v-for="t in typeChoices" :key="t.id">{{ t }}</option>
              </select>
            </div>
            <div class="col-md-4">
              <label for="status1" class="form-label">Status</label>
              <select id="status1" v-model="status" class="form-select">
                <option :value="null" disabled>Choose...</option>
                <option v-for="s in statusChoices" :key="s.id">{{ s }}</option>
              </select>
            </div>
          </form>
        </div>
        <div class="modal-footer">
          <button
            ref="CloseTicket"
            type="button"
            class="btn btn-secondary"
            data-bs-dismiss="modal"
          >
            Close
          </button>
          <button
            type="button"
            class="btn btn-primary"
            v-on:click="
              createTicket(
                title,
                description,
                priority,
                status,
                type,
                selectedProjectId,
                ticketDevs
              )
            "
          >
            Create new ticket
          </button>
        </div>
      </div>
    </div>
  </div>
  <div class="container">
    <h1>PROJECTS</h1>
    <div
      class="modal"
      id="exampleModal2"
      tabindex="-1"
      aria-labelledby="exampleModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">New project</h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div class="modal-body">
            <form class="row g-3">
              <div class="col-md-12">
                <label for="inputEmail4" class="form-label">Title</label>
                <input
                  type="title"
                  class="form-control"
                  v-model="projectName"
                  id="title"
                />
              </div>
              <div class="col-md-12">
                <label for="inputPassword4" class="form-label"
                  >Description</label
                >
                <input
                  type="decription"
                  class="form-control"
                  v-model="projectDescription"
                  id="description"
                />
              </div>
              <div class="col-12">
                <label for="inputAddress" class="form-label"
                  >Assigned Developers</label
                >
                <select
                  class="form-select"
                  v-model="projectDevs"
                  multiple
                  aria-label="multiple select example"
                >
                  <option
                    v-for="dev in developers"
                    :key="dev.id"
                    v-bind:value="dev.username"
                  >
                    {{ dev.firstName }} {{ dev.lastName }}
                  </option>
                </select>
              </div>
              <div class="col-12">
                <label for="inputState" class="form-label"
                  >Project Manager</label
                >
                <select id="inputState" v-model="projectPM" class="form-select">
                  <option :value="null" disabled>Choose...</option>
                  <option
                    v-for="pm in projectManagers"
                    :key="pm.id"
                    v-bind:value="pm.username"
                  >
                    {{ pm.firstName }} {{ pm.lastName }}
                  </option>
                </select>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button
              ref="Close"
              type="button"
              class="btn btn-secondary"
              data-bs-dismiss="modal"
            >
              Close
            </button>
            <button
              ref="CreateProject"
              type="button"
              data-dismiss="modal"
              class="btn btn-primary"
              v-on:click="
                createProject(
                  projectName,
                  projectDescription,
                  projectPM,
                  projectDevs
                )
              "
            >
              Create new project
            </button>
          </div>
        </div>
      </div>
    </div>

    <div class="tables">
      <div class="row g-100">
        <div class="col">
          <div class="table-responsive">
            <DataTable
              :columns="columns"
              :data="data"
              class="table caption-top"
              width="100%"
              :options="{ pageLength: 5, lengthChange: false, select: true }"
              ref="table"
              @select="clickRow()"
            >
              <caption>
                Assigned projects
              </caption>
              <thead class="table-light">
                <tr>
                  <th scope="col">Title</th>
                  <th scope="col">Description</th>
                  <th scope="col">Project Manager</th>
                </tr>
              </thead>
            </DataTable>
          </div>
          <button type="button" class="btn btn-primary">
            <a href="#" data-bs-toggle="modal" data-bs-target="#exampleModal2"
              >New project</a
            >
          </button>
        </div>
      </div>
    </div>

    <div class="outer-container">
      <ul class="nav nav-tabs" id="myTab" role="tablist">
        <li class="nav-item" role="presentation">
          <button
            class="nav-link active"
            id="home-tab"
            data-bs-toggle="tab"
            data-bs-target="#home"
            type="button"
            role="tab"
            aria-controls="home"
            aria-selected="true"
          >
            Project information
          </button>
        </li>
        <li class="nav-item" role="presentation">
          <button
            class="nav-link"
            id="profile-tab"
            data-bs-toggle="tab"
            data-bs-target="#profile"
            type="button"
            role="tab"
            aria-controls="profile"
            aria-selected="false"
          >
            Tickets
          </button>
        </li>
      </ul>
      <div class="tab-content" id="myTabContent">
        <div
          class="tab-pane fade show active"
          id="home"
          role="tabpanel"
          aria-labelledby="home-tab"
        >
          <div class="text-container">Edit project information</div>
          <div class="form-container">
            <form class="row g-3">
              <div class="col-md-6">
                <label for="inputEmail4" class="form-label">TITLE</label>
                <input
                  type="text"
                  class="form-control"
                  v-model="selectedName"
                  id="selectedName"
                />
              </div>
              <div class="col-md-6">
                <label for="inputPassword4" class="form-label"
                  >DESCRIPTION</label
                >
                <input
                  type="text"
                  class="form-control"
                  v-model="selectedDescription"
                  id="selectedDescription"
                />
              </div>
              <div class="col-md-6">
                <label for="inputState" class="form-label"
                  >PROJECT MANAGER</label
                >
                <select
                  id="inputState"
                  v-model="selectedPm.username"
                  class="form-select"
                >
                  <option
                    v-for="pm in projectManagers"
                    :key="pm.id"
                    v-bind:value="pm.username"
                  >
                    {{ pm.firstName }} {{ pm.lastName }}
                  </option>
                </select>
              </div>
              <div class="col-md-6">
                <label for="inputAddress" class="form-label"
                  >ASSIGNED DEVELOPERS</label
                >
                <select
                  class="form-select"
                  v-model="selectedDevs"
                  multiple
                  aria-label="multiple select example"
                >
                  <option
                    v-for="dev in developers"
                    :key="dev.id"
                    v-bind:value="dev.username"
                  >
                    {{ dev.firstName }} {{ dev.lastName }}
                  </option>
                </select>
              </div>

              <div class="col-md-10">
                <button
                  type="submit"
                  v-on:click="
                    modifyProject(
                      this.selectedProjectId,
                      selectedName,
                      selectedDescription,
                      selectedPm.username,
                      selectedDevs
                    )
                  "
                  class="btn btn-primary"
                >
                  Submit changes
                </button>
              </div>
              <div class="col-md-2">
                <button
                  type="submit"
                  v-on:click="deleteProject(this.selectedProjectId)"
                  class="btn btn-danger"
                >
                  Delete project
                </button>
              </div>
            </form>
          </div>
        </div>
        <div
          class="tab-pane fade"
          id="profile"
          role="tabpanel"
          aria-labelledby="profile-tab"
        >
          <div class="text-container">
            Tickets assigned to the selected project
          </div>
          <div class="row g-100">
            <div class="table-responsive">
              <DataTable
                :columns="ticketColumns"
                :data="projectTickets"
                class="table caption-top"
                width="100%"
                :options="{
                  pageLength: 5,
                  lengthChange: false,
                  select: true,
                  autoWidth: false,
                }"
                ref="tableTicket"
                @select="clickRowTicket()"
              >
                <caption>
                  List of tickets
                </caption>
                <thead class="table-light">
                  <tr>
                    <th scope="col">Title</th>
                    <th scope="col">Author</th>
                    <th scope="col">Priority</th>
                    <th scope="col">Type</th>
                    <th scope="col">Status</th>
                    <th scope="col">Creation date</th>
                    <th scope="col">Project</th>
                    <th scope="col"></th>
                  </tr>
                </thead>
              </DataTable>

              <div class="row">
                <div class="col-md-2">
                  <button type="button" class="btn btn-primary">
                    <a
                      href="#"
                      data-bs-toggle="modal"
                      data-bs-target="#exampleModal3"
                      >New ticket</a
                    >
                  </button>
                </div>
                <div class="col-md-8">
                  <button type="button" class="btn btn-primary">
                    <a
                      href="#"
                      data-bs-toggle="modal"
                      data-bs-target="#exampleModal1"
                      >Edit ticket</a
                    >
                  </button>
                </div>
                <div class="col-md-2">
                  <button type="button" class="btn btn-danger">
                    <a href="#" v-on:click="deleteTicket(selectedTicketId)"
                      >Delete ticket</a
                    >
                  </button>
                </div>
              </div>
            </div>
          </div>
          <div class="row g-3">
            <div class="col">
              <div class="ticket-info">
                <div class="row">
                  <div class="text-container">Selected ticket information</div>
                </div>
                <div class="row">
                  <div class="col-6">
                    <div class="text-container3">TITLE</div>
                    <div class="text-container4">{{ selectedTicketTitle }}</div>
                  </div>
                  <div class="col-6">
                    <div class="text-container3">PROJECT</div>
                    <div class="text-container4">
                      {{ selectedTicketProject.name }}
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col">
                    <div class="text-container3">DESCRIPTION</div>
                    <div class="text-container4">
                      {{ selectedTicketDescription }}
                    </div>
                  </div>
                  <div class="col">
                    <div class="text-container3">AUTHOR</div>
                    <div class="text-container4">
                      {{ selectedTicketSubmitter.firstName }}
                      {{ selectedTicketSubmitter.lastName }}
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col">
                    <div class="text-container3">TYPE</div>
                    <div class="text-container4">{{ selectedTicketType }}</div>
                  </div>
                  <div class="col">
                    <div class="text-container3">PRIORITY</div>
                    <div class="text-container4">
                      {{ selectedTicketPriority }}
                    </div>
                  </div>
                  <div class="col">
                    <div class="text-container3">STATUS</div>
                    <div class="text-container4">
                      {{ selectedTicketStatus }}
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col">
                    <div class="text-container3">ASSIGNED DEVS</div>
                    <div
                      class="text-container4"
                      v-for="d in selectedTicketDevs"
                      :key="d.id"
                    >
                      {{ d.firstName }} {{ d.lastName }}
                    </div>
                  </div>
                </div>
              </div>
              <div class="ticket-hist">
                <div class="row">
                  <div class="col">
                    <div class="table-responsive">
                      <DataTable
                        :columns="historyColumns"
                        :data="historyData"
                        class="table caption-top"
                        width="100%"
                        :options="{
                          pageLength: 5,
                          lengthChange: false,
                          select: true,
                        }"
                        ref="the-tables"
                      >
                        <caption>
                          Ticket history
                        </caption>
                        <thead class="table-light">
                          <tr>
                            <th scope="col">Property changed</th>
                            <th scope="col">Old value</th>
                            <th scope="col">New value</th>
                            <th scope="col">Date of change</th>
                          </tr>
                        </thead>
                      </DataTable>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="col">
              <div class="row d-flex justify-content-center">
                <div>
                  <div
                    class="card shadow-0 border"
                    style="background-color: #f0f2f5"
                  >
                    <div class="card-body p-4">
                      <div data-mdb-input-init class="form-outline mb-4">
                        <input
                          type="text"
                          id="addANote"
                          v-model="commentMessage"
                          class="form-control"
                          placeholder="Type comment..."
                        />
                        <button type="button" class="btn btn-primary">
                          <a
                            href="#"
                            v-on:click="
                              addComment(selectedTicketId, commentMessage)
                            "
                            >Publish</a
                          >
                        </button>
                      </div>

                      <div
                        class="comments"
                        v-for="c in commentsOfCurrPage"
                        :key="c.id"
                      >
                        <div class="card mb-4">
                          <div class="card-body">
                            <p>{{ c.message }}</p>

                            <div class="d-flex justify-content-between">
                              <div class="d-flex flex-row align-items-center">
                                <p class="small mb-0 ms-2">
                                  {{ c.commenter.firstName }}
                                  {{ c.commenter.lastName }}
                                </p>
                              </div>
                              <div class="d-flex flex-row align-items-center">
                                <p class="small text-muted mb-0">
                                  {{ c.creationDate.substring(0, 19) }}
                                </p>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <p>
                        <a v-if="prevActive" class="badge" @click="gotoPrev()"
                          >Prev</a
                        >
                        <a v-else class="badgeInactive">Prev</a>
                        - {{ pageIndex + 1 }} -
                        <a v-if="nextActive" class="badge" @click="gotoNext()"
                          >Next</a
                        >
                        <a v-else class="badgeInactive">Next</a>
                      </p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div
          class="tab-pane fade"
          id="contact"
          role="tabpanel"
          aria-labelledby="contact-tab"
        >
          ...
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// Comment paging from https://stackoverflow.com/questions/62905543/pagination-for-dynamically-generated-content

import NavBar from "./NavBar.vue";
import axios from "axios";
var backendUrl = "http://localhost:8080";
import DataTable from "datatables.net-vue3";
import DataTablesCore from "datatables.net";
import "datatables.net-select";
import "datatables.net-responsive";
import "datatables.net-select-dt";
DataTable.use(DataTablesCore);

var AXIOS = axios.create({
  baseURL: backendUrl,
});

export default {
  name: "ProjectPage",
  components: {
    NavBar,
    DataTable,
  },
  data() {
    return {
      commentMessage: "",
      historyData: [],
      data: [],
      newTicketProject: "",
      newTicketDevs: "",
      newTitle: "",
      newDescription: "",
      newPriority: "",
      priority: "null",
      type: "null",
      status: "null",
      newStatus: "",
      newType: "",
      projectTickets: [],
      projectManagers: [],
      developers: [],
      errorGetProjects: "",
      errorCreateComment: "",
      errorDeleteTicket: "",
      errorGetProject: "",
      errorCreateProject: "",
      errorDeleteProject: "",
      errorAssignDevsToTicket: "",
      errorAssignProjectToTicket: "",
      errorCreateTicket: "",
      errorAssignPMtoProject: "",
      errorGetDevelopers: "",
      errorGetProjectTickets: "",
      errorModifyProject: "",
      errorSetDevs: "",
      priorityChoices: ["None", "Low", "Medium", "High"],
      typeChoices: ["Bug", "FeatureRequest", "DocRequest", "Other"],
      statusChoices: ["New", "Open", "InProgress", "Resolved", "AddInfo"],
      errorGetPMs: "",
      projectName: "",
      projectDescription: "",
      projectPM: "null",
      projectDevs: [],
      selectedName: "",
      selectedDescription: "",
      selectedDevs: [],
      selectedProjectDevs: [],
      selectedPm: "",
      selectedTickets: "",
      selectedProjectId: "",
      selectedTicketSubmitter: "",
      selectedTicketTitle: "",
      selectedTicketProject: "",
      selectedTicketDescription: "",
      selectedTicketDevs: [],
      selectedTicketTA: [],
      selectedComments: [],
      selectedTicketPriority: "",
      selectedTicketType: "",
      selectedTicketStatus: "",
      selectedTicketId: "",
      ticketColumns: [
        { data: "title" },
        {
          render: function (data, type, row) {
            return row.submitter.firstName + " " + row.submitter.lastName;
          },
        },
        { data: "priority" },
        { data: "type" },
        { data: "status" },
        {
          render: function (data, type, row) {
            return row.creationDate.substring(0, 10);
          },
        },
        { data: "project.name" },
      ],
      historyColumns: [
        { data: "propertyChanged" },
        { data: "oldValueOfProperty" },
        { data: "newValueOfProperty" },
        {
          render: function (data, type, row) {
            return row.dateOfChange.substring(0, 10);
          },
        },
      ],
      r: "",
      columns: [
        { data: "name" },
        { data: "description" },
        {
          render: function (data, type, row) {
            return (
              row.projectManager.firstName + " " + row.projectManager.lastName
            );
          },
        },
      ],
      pageSize: 3,
      pageIndex: 0,
      prevActive: false,
      nextActive: false,
    };
  },
  computed: {
    pages: function () {
      if (this.selectedComments.length % this.pageSize == 0) {
        return this.selectedComments.length / this.pageSize;
      } else {
        return Math.floor(this.selectedComments.length / this.pageSize) + 1;
      }
    }, // https://stackoverflow.com/questions/5927109/sort-objects-in-arraylist-by-date
    commentsOfCurrPage: function () {
      return this.selectedComments.slice(
        this.pageSize * this.pageIndex,
        this.pageSize * (this.pageIndex + 1)
      );
    },
  },
  created: function () {
    // Initializing  items from backend
    AXIOS.get("/projects/")
      .then((response) => {
        // JSON responses are automatically parsed.
        this.data = response.data;
      })
      .catch((e) => {
        this.errorGetProjects = e;
      });

    AXIOS.get("/developers/")
      .then((response) => {
        // JSON responses are automatically parsed.
        this.developers = response.data;
        console.log(this.developers);
      })
      .catch((e) => {
        this.errorGetDevelopers = e;
      });

    AXIOS.get("/projectmanagers/")
      .then((response) => {
        // JSON responses are automatically parsed.
        this.projectManagers = response.data;
        console.log(this.projectManagers);
      })
      .catch((e) => {
        this.errorGetPMs = e;
      });
  },

  methods: {
    setDevs(ticketProject) {
      AXIOS.get("/project/".concat(ticketProject), {}, {})
        .then((response) => {
          // JSON responses are automatically parsed.
          this.r = response.data;
          this.selectedProjectDevs = this.r.assignedDevelopers;
        })
        .catch((e) => {
          this.errorGetProject = e;
          var errorMsg = e.response.data.message;
          console.log(errorMsg);
        });
    },
    clickRowTicket() {
      const table = this.$refs.tableTicket.dt;
      var ticket = table.row(".selected").data();
      if (ticket != null) {
        this.selectedTicketSubmitter = ticket.submitter;
        this.selectedTicketId = ticket.id;
        this.selectedTicketTitle = ticket.title;
        this.selectedTicketProject = ticket.project.id;
        this.selectedTicketDescription = ticket.description;
        this.selectedTicketDevs = ticket.assignedDevelopers;
        this.selectedTicketTA = ticket.ticketAttachments;
        this.selectedTicketPriority = ticket.priority;
        this.selectedTicketType = ticket.type;
        this.selectedTicketStatus = ticket.status;
        this.selectedComments = ticket.comments;
        this.newTicketProject = ticket.project.id;
        this.newTicketDevs = ticket.assignedDevelopers;
        this.newTitle = ticket.title;
        this.newDescription = ticket.description;
        this.newPriority = ticket.priority;
        this.newStatus = ticket.status;
        this.newType = ticket.type;
        this.historyData = [];
        if (ticket.ticketHistories != null) {
          for (var h of ticket.ticketHistories) {
            this.historyData.push(h);
          }
        }
      }

      this.pageIndex = 0;
      this.prevActive = false;
      if (this.pageIndex < this.pages - 1) {
        this.nextActive = true;
      } else {
        this.nextActive = false;
      }
    },
    clickRow() {
      const table = this.$refs.table.dt;
      var project = table.row(".selected").data();

      this.selectedDevs = [];
      this.selectedProjectDevs = project.assignedDevelopers;

      this.selectedProjectId = project.id;
      this.selectedName = project.name;
      this.selectedDescription = project.description;
      console.log(project.assignedDevelopers);
      for (var d of project.assignedDevelopers) {
        this.selectedDevs.push(d.username);
      }
      console.log(this.selectedDevs);
      this.selectedPm = project.projectManager;
      this.selectedTickets = project.tickets;

      // Initializing  items from backend
      AXIOS.get("/project/tickets/".concat(this.selectedProjectId))
        .then((response) => {
          // JSON responses are automatically parsed.
          this.projectTickets = response.data;
        })
        .catch((e) => {
          this.errorGetProjectTickets = e;
        });
    },

    // TODO: how to change password???
    createProject: function (
      name,
      description,
      projectManagerUsername,
      projectDevs
    ) {
      AXIOS.post(
        "/project/create/",
        {},
        {
          params: {
            name: name,
            description: description,
            projectManagerUsername: projectManagerUsername,
          },
        }
      )
        .then((response) => {
          // JSON responses are automatically parsed.
          this.r = response.data;
          var projectId = this.r.id;

          AXIOS.post(
            "/project/setDevs/".concat(projectId),
            {},
            {
              params: {
                devUsernames: projectDevs.reduce((f, s) => `${f},${s}`),
              },
            }
          )
            .then((response) => {
              // JSON responses are automatically parsed.
              this.data.push(response.data);
              this.projectDescription = "";
              this.projectName = "";
              this.projectPM = "";
              this.projectDevs = [];
              this.$refs.Close.click();
            })
            .catch((e) => {
              this.errorSetDevs = e;
            });
        })
        .catch((e) => {
          this.errorCreateProject = e;
          var errorMsg = e.response.data.message;
          console.log(errorMsg);
        });
    },

    modifyProject: function (
      projectId,
      name,
      description,
      projectManagerUsername,
      projectDevs
    ) {
      AXIOS.post(
        "/project/modify/",
        {},
        {
          params: {
            projectId: projectId,
            name: name,
            description: description,
          },
        }
      )
        .then((response) => {
          // JSON responses are automatically parsed.
          this.r = response.data;
          var projectId = this.r.id;

          AXIOS.post(
            "/project/assignPm/".concat(projectManagerUsername),
            {},
            {
              params: { projectId: projectId },
            }
          )
            .then((response) => {
              // JSON responses are automatically parsed.
              this.r = response.data;
              console.log("THIS IS RESP DATA %o", this.r);
            })
            .catch((e) => {
              this.errorAssignPMtoProject = e;
            });

          AXIOS.post(
            "/project/setDevs/".concat(projectId),
            {},
            {
              params: {
                devUsernames: projectDevs.reduce((f, s) => `${f},${s}`),
              },
            }
          )
            .then((response) => {
              // JSON responses are automatically parsed.
              this.r = response.data;
              console.log("THIS IS RESP DATA %o", this.r);
            })
            .catch((e) => {
              this.errorSetDevs = e;
            });

          this.$router.push("/assignedProjects");
        })
        .catch((e) => {
          this.errorModifyProject = e;
          var errorMsg = e.response.data.message;
          console.log(errorMsg);
        });
    },
    deleteProject: function (projectId) {
      var indexProject = this.data.map((x) => x.id).indexOf(projectId);
      AXIOS.post("/project/delete/".concat(projectId), {}, {})
        .then((response) => {
          // JSON responses are automatically parsed.
          this.r = response.data;
          // Remove item from array
          if (indexProject !== -1) {
            this.data.splice(indexProject, 1);
          }
        })
        .catch((e) => {
          this.errorDeleteProject = e;
          var errorMsg = e.response.data.message;
          console.log(errorMsg);
        });
    },

    createTicket: function (
      title,
      description,
      priority,
      status,
      type,
      ticketProject,
      ticketDevs
    ) {
      AXIOS.post(
        "/ticket/create/",
        {},
        {
          params: {
            title: title,
            description: description,
            priority: priority,
            status: status,
            type: type,
            projectId: ticketProject,
            submitterUsername: localStorage.getItem("token"),
          },
        }
      )
        .then((response) => {
          // JSON responses are automatically parsed.
          this.r = response.data;
          var ticketId = this.r.id;

          AXIOS.post(
            "/ticket/assignDevs/".concat(ticketId),
            {},
            {
              params: {
                devUsernames: ticketDevs.reduce((f, s) => `${f},${s}`),
              },
            }
          )
            .then((response) => {
              // JSON responses are automatically parsed.
              this.projectTickets.push(response.data);
              this.title = "";
              this.description = "";
              this.ticketDevs = [];
              this.priority = "";
              this.status = "";
              this.type = "";

              this.$refs.CloseTicket.click();
            })
            .catch((e) => {
              this.errorAssignDevsToTicket = e;
            });
        })
        .catch((e) => {
          this.errorCreateTicket = e;
          var errorMsg = e.response.data.message;
          console.log(errorMsg);
        });
    },

    modifyTicket: function (
      ticketId,
      title,
      description,
      priority,
      status,
      type,
      ticketProject,
      ticketDevs
    ) {
      AXIOS.post(
        "/ticket/modify/".concat(ticketId),
        {},
        {
          params: {
            title: title,
            description: description,
            priority: priority,
            status: status,
            type: type,
          },
        }
      )
        .then((response) => {
          // JSON responses are automatically parsed.
          this.r = response.data;

          AXIOS.post(
            "/ticket/assignDevs/".concat(ticketId),
            {},
            {
              params: {
                devUsernames: ticketDevs.reduce((f, s) => `${f},${s}`),
              },
            }
          )
            .then((response) => {
              // JSON responses are automatically parsed.
              this.r = response.data;
            })
            .catch((e) => {
              this.errorAssignDevsToTicket = e;
            });

          AXIOS.post(
            "/ticket/project/".concat(ticketId),
            {},
            {
              params: { projectId: ticketProject },
            }
          )
            .then((response) => {
              // JSON responses are automatically parsed.
              this.r = response.data;
            })
            .catch((e) => {
              this.errorAssignProjectToTicket = e;
            });

          this.historyData = this.r.ticketHistories;
          this.$refs.CloseModify.click();
        })
        .catch((e) => {
          this.errorModifyTicket = e;
          var errorMsg = e.response.data.message;
          console.log(errorMsg);
        });
    },
    gotoPrev: function () {
      if (this.pageIndex > 0) {
        this.pageIndex -= 1;
        this.nextActive = true;
        if (this.pageIndex > 0) {
          this.prevActive = true;
        } else {
          this.prevActive = false;
        }
      }
    },
    gotoNext: function () {
      if (this.pageIndex < this.pages - 1) {
        this.pageIndex += 1;
        this.prevActive = true;
        if (this.pageIndex < this.pages - 1) {
          this.nextActive = true;
        } else {
          this.nextActive = false;
        }
      }
    },
    addComment: function (ticketId, message) {
      AXIOS.post(
        "/ticket/createComment/".concat(ticketId),
        {},
        {
          params: {
            commenterUsername: localStorage.getItem("token"),
            message: message,
          },
        }
      )
        .then((response) => {
          // JSON responses are automatically parsed.
          this.r = response.data;
          this.selectedComments = this.r.comments;
          this.commentMessage = "";
        })
        .catch((e) => {
          this.errorCreateComment = e;
          var errorMsg = e.response.data.message;
          console.log(errorMsg);
        });
    },
    deleteTicket: function (ticketId) {
      var indexTicket = this.projectTickets.map((x) => x.id).indexOf(ticketId);
      AXIOS.post("/ticket/delete/".concat(ticketId), {}, {})
        .then((response) => {
          // JSON responses are automatically parsed.
          this.r = response.data;
          // Remove item from array
          if (indexTicket !== -1) {
            this.projectTickets.splice(indexTicket, 1);
          }
        })
        .catch((e) => {
          this.errorDeleteTicket = e;
          var errorMsg = e.response.data.message;
          console.log(errorMsg);
        });
    },
  },
};
</script>

<style scoped>
.text-container3 {
  color: rgb(73, 73, 73);
  font-size: 12px;
}
h1 {
  margin-top: 100px;
  font-size: 20px;
  color: rgb(43, 43, 43);
}

label {
  font-size: 13px;
}

.tables {
  margin-top: 30px;
  width: 100%;
}

.tables .col {
  margin: 10px;
  border: 1px solid rgba(255, 255, 255, 0);
  border-radius: 5px;
  box-shadow: 5px 5px 10px #b9b9b9;
  padding-top: 10px;
  padding-bottom: 20px;
}
.text-container {
  padding: 10px;
  color: gray;
  background-color: white;
}

.outer-container {
  margin-top: 30px;
  border: 1px solid rgba(255, 255, 255, 0);
  border-radius: 5px;
  box-shadow: 5px 5px 10px #b9b9b9;

  margin-bottom: 50px;
  background-color: rgb(246, 246, 246);
}

.tables .row .table {
  background-color: white;
}

.outer-container .row {
  padding: 20px;
}

.comment .row {
  margin-top: 5px;
  padding: 5px;
}

.ticket-info .row {
  background-color: white;
  border: 1px solid rgba(255, 255, 255, 0);
  border-radius: 5px;
}

.ticket-hist {
  margin-top: 10px;
}

.ticket-hist .row {
  background-color: white;
  border: 1px solid rgba(255, 255, 255, 0);
  border-radius: 5px;
}

.comment {
  background-color: white;
  padding: 10px;
  padding-bottom: 50px;
  border: 1px solid rgba(255, 255, 255, 0);
  border-radius: 5px;
}

button a {
  color: white;
  text-decoration: none;
}

.outer-container {
  margin-top: 30px;
  border: 1px solid rgba(255, 255, 255, 0);
  border-radius: 5px;
  box-shadow: 5px 5px 10px #b9b9b9;
  width: 100%;
  margin-bottom: 50px;
}

.form-container {
  padding: 20px;
  width: 100%;
  background-color: rgb(249, 249, 249);
  padding-bottom: 50px;
}

form {
  width: 100%;
  margin-top: 5px;
  background-color: white;
  padding: 20px;
}

button {
  margin-top: 20px;
}

.outer-container .row .table-responsive {
  background-color: white;
  width: 100%;
}

.badge {
  color: rgb(56, 56, 56);
  text-decoration: none;
  font-family: Arial, Helvetica, sans-serif;
  font-size: 12px;
  font-weight: lighter;
}

.badgeInactive {
  color: rgb(120, 120, 120);
  text-decoration: none;
  font-family: Arial, Helvetica, sans-serif;
  font-size: 12px;
  font-weight: lighter;
}

.badgeInactive:hover {
  cursor: default;
}

.badge:hover {
  cursor: pointer;
  background-color: rgb(83, 83, 83);
  color: white;
}

.tab-pane .row .table-responsive {
  padding-top: 10px;
}
</style>
