<template>
  <NavBar />

  <div class="container">
    <h1>TICKETS</h1>
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
              <span v-if="errorCreateTicket != ''">{{
                errorCreateTicket
              }}</span>
              <span v-if="errorAssignDevsToTicket != ''">{{
                errorAssignDevsToTicket
              }}</span>
              <div class="col-md-12">
                <label for="title1" class="form-label">Title</label>
                <input class="form-control" v-model="title" id="title1" />
              </div>
              <div class="col-md-12">
                <label for="project1" class="form-label">Project</label>
                <select
                  id="priority1"
                  v-model="ticketProject"
                  @change="setDevsCreate(ticketProject)"
                  class="form-select"
                >
                  <option :value="null" disabled>Choose...</option>
                  <option v-for="p in projects" v-bind:value="p.id" :key="p.id">
                    {{ p.name }}
                  </option>
                </select>
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
                <label for="devs1" class="form-label"
                  >Assigned Developers</label
                >
                <select
                  class="form-select"
                  v-model="ticketDevs"
                  multiple
                  aria-label="multiple select example"
                >
                  <option
                    v-for="dev in projectDevsCreate"
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
                  <option v-for="s in statusChoices" :key="s.id">
                    {{ s }}
                  </option>
                </select>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button
              ref="CloseCreate"
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
                  ticketProject,
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
              <span v-if="errorModifyTicket != ''">{{
                errorModifyTicket
              }}</span>
              <span v-if="errorAssignDevsToTicketInModify != ''">{{
                errorAssignDevsToTicketInModify
              }}</span>
              <span v-if="errorAssignProjectToTicketInModify != ''">{{
                errorAssignProjectToTicketInModify
              }}</span>
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
                  <option v-for="p in projects" v-bind:value="p.id" :key="p.id">
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
                    v-for="dev in projectDevsModify"
                    :key="dev.id"
                    v-bind:value="dev.username"
                  >
                    {{ dev.firstName }} {{ dev.lastName }}
                  </option>
                </select>
              </div>
              <div class="col-md-4">
                <label for="priority" class="form-label">Priority</label>
                <select
                  id="priority1"
                  v-model="newPriority"
                  class="form-select"
                >
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
                  <option v-for="s in statusChoices" :key="s.id">
                    {{ s }}
                  </option>
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
    <div class="theContent">
      <div class="tables">
        <div class="row">
          <div class="col">
            <div class="table-responsive">
              <span v-if="errorSelectTicket != ''">{{
                errorSelectTicket
              }}</span>
              <DataTable
                :columns="columns"
                :data="data"
                class="table caption-top"
                width="100%"
                :options="{
                  pageLength: 5,
                  lengthChange: false,
                  select: true,
                  select: {
                    style: 'single',
                    toggleable: false,
                  },
                }"
                ref="table"
                @select="clickRow()"
              >
                <caption v-if="currentUserIsAdminOrPM">
                  All tickets
                </caption>
                <caption v-else>
                  Assigned tickets
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
            </div>
            <div class="row g-3">
              <div class="col-md-2">
                <button type="button" class="btn btn-primary">
                  <a
                    href="#/"
                    data-bs-toggle="modal"
                    data-bs-target="#exampleModal2"
                    >New ticket</a
                  >
                </button>
              </div>
              <div class="col-md-8">
                <button type="button" class="btn btn-primary">
                  <a
                    target="_blank"
                    v-on:click="checkIfTicketSelected()"
                    data-bs-target="#exampleModal1"
                    >Edit ticket</a
                  >
                </button>
              </div>
              <div class="col-md-2">
                <button
                  type="button"
                  v-on:click="deleteTicket(selectedTicketId)"
                  class="btn btn-danger"
                >
                  Delete ticket
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
                <div class="col-6">
                  <div class="text-container3">TITLE</div>
                  <div class="text-container4">{{ selectedTitle }}</div>
                </div>
                <div class="col-6">
                  <div class="text-container3">PROJECT</div>
                  <div class="text-container4">{{ selectedProject.name }}</div>
                </div>
              </div>
              <div class="row">
                <div class="col">
                  <div class="text-container3">DESCRIPTION</div>
                  <div class="text-container4">{{ selectedDescription }}</div>
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
                  <div class="text-container4">{{ selectedType }}</div>
                </div>
                <div class="col">
                  <div class="text-container3">PRIORITY</div>
                  <div class="text-container4">{{ selectedPriority }}</div>
                </div>
                <div class="col">
                  <div class="text-container3">STATUS</div>
                  <div class="text-container4">{{ selectedStatus }}</div>
                </div>
              </div>
              <div class="row">
                <div class="col">
                  <div class="text-container3">ASSIGNED DEVS</div>
                  <div
                    class="text-container4"
                    v-for="d in selectedDevs"
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
                        select: false,
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
                      <span v-if="errorCreateComment != ''">{{
                        errorCreateComment
                      }}</span>
                      <input
                        type="text"
                        id="addANote"
                        v-model="commentMessage"
                        class="form-control"
                        placeholder="Type comment..."
                      />
                      <button type="button" class="btn btn-primary">
                        <a
                          target="_blank"
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
    </div>
  </div>
</template>

<script>
// Comment paging from https://stackoverflow.com/questions/62905543/pagination-for-dynamically-generated-content
import { Modal } from "bootstrap";
import NavBar from "./NavBar.vue";
import ProjectService from "../services/project.service";
import TicketService from "../services/ticket.service";
import DataTable from "datatables.net-vue3";
import DataTablesCore from "datatables.net";
import "datatables.net-select";
import "datatables.net-responsive";
import "datatables.net-select-dt";
import EventBus from "../common/EventBus";

DataTable.use(DataTablesCore);

export default {
  name: "TicketPage",
  components: {
    NavBar,
    DataTable,
  },
  props: {
    ticketType: String,
  },
  data() {
    return {
      historyData: [],
      data: [],
      projects: [],
      developers: [],
      commentMessage: "",
      errorCreateTicket: "",
      errorModifyTicket: "",
      errorDeleteTicket: "",
      errorCreateComment: "",
      errorSelectTicket: "",
      errorAssignDevsToTicket: "",
      errorAssignDevsToTicketInModify: "",
      errorAssignProjectToTicket: "",
      errorAssignProjectToTicketInModify: "",
      errorGetProjects: "",
      errorGetProject: "",
      errorGetTickets: "",
      errorGetDevelopers: "",
      selectedTicketSubmitter: "",
      selectedTitle: "",
      selectedProject: "",
      selectedDescription: "",
      selectedDevs: [],
      selectedTA: [],
      selectedComments: [],
      selectedPriority: "",
      selectedType: "",
      selectedStatus: "",
      selectedTicketId: "",
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

      columns: [
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
            return row.creationDate.substring(0, 19);
          },
        },
        { data: "project.name" },
      ],
      ticketProject: "null",
      ticketDevs: "",
      projectDevs: [],
      title: "",
      description: "",
      priority: "null",
      status: "null",
      type: "null",
      newTicketProject: "",
      projectDevsModify: [],
      newTicketDevs: [],
      newTitle: "",
      newDescription: "",
      newPriority: "",
      newStatus: "",
      newType: "",
      priorityChoices: ["None", "Low", "Medium", "High"],
      typeChoices: ["Bug", "FeatureRequest", "DocRequest", "Other"],
      statusChoices: ["New", "Open", "InProgress", "Resolved", "AddInfo"],
      projectDevsCreate: [],
      pageSize: 3,
      pageIndex: 0,
      prevActive: false,
      nextActive: false,
    };
  },
  // Methods inspired from: https://www.bezkoder.com/vue-3-authentication-jwt/
  // and from: https://www.bezkoder.com/vue-refresh-token/
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    },
    currentUserIsAdminOrPM() {
      if (this.currentUser && this.currentUser.roles) {
        return (
          this.currentUser.roles.includes("ROLE_ADMIN") ||
          this.currentUser.roles.includes("ROLE_PROJECTMANAGER")
        );
      }

      return false;
    },
    pages: function () {
      if (this.selectedComments.length % this.pageSize == 0) {
        return this.selectedComments.length / this.pageSize;
      } else {
        return Math.floor(this.selectedComments.length / this.pageSize) + 1;
      }
    },
    commentsOfCurrPage: function () {
      return this.selectedComments.slice(
        this.pageSize * this.pageIndex,
        this.pageSize * (this.pageIndex + 1)
      );
    },
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    },
  },

  created: function () {
    if (!this.loggedIn) {
      this.$router.push("/login");
    } else {
      ProjectService.getAllProjects().then(
        (response) => {
          // JSON responses are automatically parsed.
          this.projects = response.data;
        },
        (error) => {
          this.errorGetProjects =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();

          if (error.response && error.response.status === 403) {
            EventBus.dispatch("logout");
          }
        }
      );

      if (this.currentUserIsAdminOrPM) {
        // Initializing  items from backend
        TicketService.getAllTickets().then(
          (response) => {
            // JSON responses are automatically parsed.
            this.data = response.data;
          },
          (error) => {
            this.errorGetTickets =
              (error.response &&
                error.response.data &&
                error.response.data.message) ||
              error.message ||
              error.toString();

            if (error.response && error.response.status === 403) {
              EventBus.dispatch("logout");
            }
          }
        );
      } else {
        // Initializing  items from backend
        TicketService.getAssignedTicketsForDev(this.currentUser.username).then(
          (response) => {
            // JSON responses are automatically parsed.
            this.data = response.data;
          },
          (error) => {
            this.errorGetTickets =
              (error.response &&
                error.response.data &&
                error.response.data.message) ||
              error.message ||
              error.toString();

            if (error.response && error.response.status === 403) {
              EventBus.dispatch("logout");
            }
          }
        );
      }
    }
  },

  methods: {
    setDevs(ticketProject) {
      ProjectService.getProjectWithId(ticketProject).then(
        (response) => {
          // JSON responses are automatically parsed.
          this.r = response.data;
          this.projectDevsModify = this.r.assignedDevelopers;
        },
        (error) => {
          this.errorGetProject =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();

          if (error.response && error.response.status === 403) {
            EventBus.dispatch("logout");
          }
        }
      );
    },

    setDevsCreate(ticketProject) {
      ProjectService.getProjectWithId(ticketProject).then(
        (response) => {
          // JSON responses are automatically parsed.
          this.r = response.data;
          this.projectDevsCreate = this.r.assignedDevelopers;
        },
        (error) => {
          this.errorGetProject =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();

          if (error.response && error.response.status === 403) {
            EventBus.dispatch("logout");
          }
        }
      );
    },

    clickRow() {
      this.resetErrorMessages();
      const table = this.$refs.table.dt;
      var ticket = table.row(".selected").data();
      if (ticket != null) {
        this.selectedTicketSubmitter = ticket.submitter;
        this.selectedTicketId = ticket.id;
        this.selectedTitle = ticket.title;
        this.selectedProject = ticket.project;
        this.selectedDescription = ticket.description;
        this.selectedDevs = ticket.assignedDevelopers;
        this.selectedTA = ticket.ticketAttachments;
        this.selectedPriority = ticket.priority;
        this.selectedType = ticket.type;
        this.selectedStatus = ticket.status;
        this.selectedComments = ticket.comments;
        this.newTicketProject = ticket.project.id;

        ProjectService.getProjectWithId(this.newTicketProject).then(
          (response) => {
            // JSON responses are automatically parsed.
            this.r = response.data;
            this.projectDevsModify = this.r.assignedDevelopers;
          },
          (error) => {
            this.errorGetProject =
              (error.response &&
                error.response.data &&
                error.response.data.message) ||
              error.message ||
              error.toString();

            if (error.response && error.response.status === 403) {
              EventBus.dispatch("logout");
            }
          }
        );

        for (var dev of ticket.assignedDevelopers) {
          this.newTicketDevs.push(dev.username);
        }

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
        this.pageIndex = 0;
        this.prevActive = false;
        if (this.pageIndex < this.pages - 1) {
          this.nextActive = true;
        } else {
          this.nextActive = false;
        }
      }
    },

    resetErrorMessages() {
      this.errorSelectTicket = "";
      this.errorCreateTicket = "";
      this.errorModifyTicket = "";
      this.errorDeleteTicket = "";
      this.errorCreateComment = "";
      this.errorAssignDevsToTicket = "";
      this.errorAssignDevsToTicketInModify = "";
      this.errorAssignProjectToTicket = "";
      this.errorAssignProjectToTicketInModify = "";
      this.errorGetProjects = "";
      this.errorGetProject = "";
      this.errorGetTickets = "";
      this.errorGetDevelopers = "";
    },
    // TODO: how to change password???
    createTicket: function (
      title,
      description,
      priority,
      status,
      type,
      ticketProject,
      ticketDevs
    ) {
      this.resetErrorMessages();
      if (ticketDevs.length != 0) {
        this.errorAssignDevsToTicket = "";

        TicketService.createTicket(
          title,
          description,
          priority,
          status,
          type,
          ticketProject,
          this.currentUser.username
        ).then(
          (response) => {
            // JSON responses are automatically parsed.
            this.r = response.data;
            var ticketId = this.r.id;

            TicketService.assignDevsToTicket(
              ticketId,
              ticketDevs.reduce((f, s) => `${f},${s}`)
            ).then(
              (response) => {
                // JSON responses are automatically parsed.
                this.data.push(response.data);
                this.title = "";
                this.description = "";
                this.ticketProject = "";
                this.ticketDevs = [];
                this.priority = "";
                this.type = "";
                this.status = "";
                this.$refs.CloseCreate.click();
              },
              (error) => {
                this.errorAssignDevsToTicket =
                  (error.response &&
                    error.response.data &&
                    error.response.data.message) ||
                  error.message ||
                  error.toString();

                if (error.response && error.response.status === 403) {
                  EventBus.dispatch("logout");
                }
              }
            );
          },
          (error) => {
            this.errorCreateTicket =
              (error.response &&
                error.response.data &&
                error.response.data.message) ||
              error.message ||
              error.toString();

            if (error.response && error.response.status === 403) {
              EventBus.dispatch("logout");
            }
          }
        );
      } else {
        this.errorAssignDevsToTicket = "List of developers cannot be empty. ";
      }
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
      this.resetErrorMessages();
      var indexTicket = this.data.map((x) => x.id).indexOf(ticketId);

      TicketService.modifyTicket(
        ticketId,
        title,
        description,
        priority,
        status,
        type
      ).then(
        (response) => {
          // JSON responses are automatically parsed.
          this.r = response.data;

          TicketService.assignDevsToTicket(
            ticketId,
            ticketDevs.reduce((f, s) => `${f},${s}`)
          ).then(
            (response) => {
              // JSON responses are automatically parsed.
              this.data[indexTicket].assignedDevelopers =
                response.data.assignedDevelopers;
              this.selectedDevs = response.data.assignedDevelopers;
            },
            (error) => {
              this.errorAssignDevsToTicketInModify =
                (error.response &&
                  error.response.data &&
                  error.response.data.message) ||
                error.message ||
                error.toString();

              if (error.response && error.response.status === 403) {
                EventBus.dispatch("logout");
              }
            }
          );

          TicketService.assignProjectToTicket(ticketId, ticketProject).then(
            (response) => {
              // JSON responses are automatically parsed.
              this.data[indexTicket].project = response.data.project;

              this.selectedProject = response.data.project;
            },
            (error) => {
              this.errorAssignProjectToTicketInModify =
                (error.response &&
                  error.response.data &&
                  error.response.data.message) ||
                error.message ||
                error.toString();

              if (error.response && error.response.status === 403) {
                EventBus.dispatch("logout");
              }
            }
          );
          this.data[indexTicket].title = this.r.title;
          this.selectedTitle = this.r.title;
          this.data[indexTicket].description = this.r.description;
          this.selectedDescription = this.r.description;
          this.data[indexTicket].priority = this.r.priority;
          this.selectedPriority = this.r.priority;
          this.data[indexTicket].status = this.r.status;
          this.selectedStatus = this.r.status;
          this.data[indexTicket].type = this.r.type;
          this.selectedType = this.r.type;
          this.data[indexTicket].ticketHistories = this.r.ticketHistories;
          this.historyData = this.r.ticketHistories;
          this.$refs.CloseModify.click();
        },
        (error) => {
          this.errorModifyTicket =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();

          if (error.response && error.response.status === 403) {
            EventBus.dispatch("logout");
          }
        }
      );
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
      this.resetErrorMessages();
      const table = this.$refs.table.dt;
      var ticket = table.row(".selected").data();
      if (ticket == null) {
        this.errorCreateComment = "No ticket selected. ";
      } else {
        if (message == null || message == "") {
          this.errorCreateComment = "Comment cannot be empty. ";
        } else {
          var indexTicket = this.data.map((x) => x.id).indexOf(ticketId);
          TicketService.createCommentForTicket(
            ticketId,
            this.currentUser.username,
            message
          ).then(
            (response) => {
              // JSON responses are automatically parsed.
              this.r = response.data;
              this.data[indexTicket].comments = this.r.comments;
              this.selectedComments = this.r.comments;
              this.commentMessage = "";
              if (this.pageIndex < this.pages - 1) {
                this.nextActive = true;
              } else {
                this.nextActive = false;
              }
            },
            (error) => {
              this.errorCreateComment =
                (error.response &&
                  error.response.data &&
                  error.response.data.message) ||
                error.message ||
                error.toString();

              if (error.response && error.response.status === 403) {
                EventBus.dispatch("logout");
              }
            }
          );
        }
      }
    },
    checkIfTicketSelected: function () {
      this.resetErrorMessages();
      const table = this.$refs.table.dt;
      var ticket = table.row(".selected").data();

      if (ticket == null) {
        this.errorSelectTicket = "No ticket selected. ";
      } else {
        this.modalInstance = new Modal(
          document.getElementById("exampleModal1"),
          {
            target: "#my-modal",
          }
        );
        this.modalInstance.show();
      }
    },
    deleteTicket: function (ticketId) {
      this.resetErrorMessages();
      const table = this.$refs.table.dt;
      var ticket = table.row(".selected").data();
      if (ticket == null) {
        this.errorSelectTicket = "No ticket selected. ";
      } else {
        var indexTicket = this.data.map((x) => x.id).indexOf(ticketId);

        TicketService.deleteTicket(ticketId).then(
          (response) => {
            // JSON responses are automatically parsed.
            this.r = response.data;
            this.selectedTitle = "";
            this.selectedProject = "";
            this.selectedDescription = "";
            this.selectedTicketSubmitter = "";
            this.selectedType = "";
            this.selectedPriority = "";
            this.selectedStatus = "";
            this.selectedDevs = [];
            this.historyData = [];
            this.selectedComments = [];
            // Remove item from array
            if (indexTicket !== -1) {
              this.data.splice(indexTicket, 1);
            }
          },
          (error) => {
            this.errorDeleteTicket =
              (error.response &&
                error.response.data &&
                error.response.data.message) ||
              error.message ||
              error.toString();

            if (error.response && error.response.status === 403) {
              EventBus.dispatch("logout");
            }
          }
        );
      }
    },
  },
};
</script>

<style scoped>
@import "datatables.net-bs5";
@import "datatables.net-dt";

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

.theContent {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.tables {
  margin-top: 30px;
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.tables .row {
  width: 100%;
}
.tables .col {
  margin: 10px;
  border: 1px solid rgba(255, 255, 255, 0);
  border-radius: 5px;
  box-shadow: 5px 5px 10px #b9b9b9;
  padding-top: 10px;
  padding-bottom: 20px;
  width: 90%;
}
.text-container {
  padding: 10px;
  color: gray;
  background-color: white;
}

.tables button {
  margin-top: 10px;
}
.outer-container {
  margin-top: 30px;
  border: 1px solid rgba(255, 255, 255, 0);
  border-radius: 5px;
  box-shadow: 5px 5px 10px #b9b9b9;
  width: 100%;
  margin-bottom: 50px;

  background-color: rgb(246, 246, 246);
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

.card-body button {
  margin-top: 10px;
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

span {
  color: red;
}
</style>
