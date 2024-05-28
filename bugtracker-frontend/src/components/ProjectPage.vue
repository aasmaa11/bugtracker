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
            <span v-if="errorModifyTicket != ''">{{ errorModifyTicket }}</span>
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
            <span v-if="errorCreateTicket != ''">{{ errorCreateTicket }}</span>
            <span v-if="errorAssignDevsToTicket != ''">{{
              errorAssignDevsToTicket
            }}</span>
            <div class="col-md-12">
              <label for="title1" class="form-label">Title</label>
              <input class="form-control" v-model="ticketTitle" id="title1" />
            </div>
            <div class="col-md-12">
              <label for="description1" class="form-label">Description</label>
              <input
                class="form-control"
                v-model="ticketDescription"
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
              <select
                id="priority1"
                v-model="ticketPriority"
                class="form-select"
              >
                <option :value="null" disabled>Choose...</option>
                <option v-for="p in priorityChoices" :key="p.id">
                  {{ p }}
                </option>
              </select>
            </div>
            <div class="col-md-4">
              <label for="type1" class="form-label">Type</label>
              <select id="type1" v-model="ticketType" class="form-select">
                <option :value="null" disabled>Choose...</option>
                <option v-for="t in typeChoices" :key="t.id">{{ t }}</option>
              </select>
            </div>
            <div class="col-md-4">
              <label for="status1" class="form-label">Status</label>
              <select id="status1" v-model="ticketStatus" class="form-select">
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
                ticketTitle,
                ticketDescription,
                ticketPriority,
                ticketStatus,
                ticketType,
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
              <span v-if="errorCreateProject != ''">{{
                errorCreateProject
              }}</span>
              <span v-if="errorSetDevs != ''">{{ errorSetDevs }}</span>
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
    <div class="theContent">
      <div class="tables">
        <div class="row">
          <div class="col">
            <div class="table-responsive">
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
            <button
              v-if="currentUserIsAdminOrPM"
              type="button"
              class="btn btn-primary"
            >
              <a
                href="#/"
                data-bs-toggle="modal"
                data-bs-target="#exampleModal2"
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
            <div class="afterTabTitle">
              <div class="form-container">
                <form class="row g-3" v-if="currentUserIsAdminOrPM">
                  <span v-if="errorModifyProject != ''">{{
                    errorModifyProject
                  }}</span>
                  <span v-if="errorSetDevsInModify != ''">{{
                    errorSetDevsInModify
                  }}</span>
                  <span v-if="errorAssignPMtoProjectInModify != ''">{{
                    errorAssignPMtoProjectInModify
                  }}</span>
                  <span v-if="errorDeleteProject != ''">{{
                    errorDeleteProject
                  }}</span>
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
                      v-if="currentUserIsAdminOrPM"
                      type="button"
                      v-on:click="
                        modifyProject(
                          selectedProjectId,
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
                      v-if="currentUserIsAdminOrPM"
                      type="button"
                      v-on:click="deleteProject(selectedProjectId)"
                      class="btn btn-danger"
                    >
                      Delete project
                    </button>
                  </div>
                </form>
                <div
                  class="projectInfoContainer"
                  v-if="!currentUserIsAdminOrPM"
                >
                  <div class="row g-3">
                    <div class="col-md-6">
                      <label for="pname" class="form-label">TITLE</label>
                      <p>{{ selectedName }}</p>
                    </div>
                    <div class="col-md-6">
                      <label for="pdescription" class="form-label"
                        >DESCRIPTION</label
                      >
                      <p>{{ selectedDescription }}</p>
                    </div>
                    <div class="col-md-6">
                      <label for="ppm" class="form-label"
                        >PROJECT MANAGER</label
                      >
                      <p>
                        {{ selectedPm.firstName }} {{ selectedPm.lastName }}
                      </p>
                    </div>
                    <div class="col-md-6">
                      <label for="pdevs" class="form-label"
                        >ASSIGNED DEVELOPERS</label
                      >
                      <div v-for="dev in selectedDevsObj" :key="dev.id">
                        <p>{{ dev.firstName }} {{ dev.lastName }}</p>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
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
                <span v-if="errorSelectTicket != ''">{{
                  errorSelectTicket
                }}</span>
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
                    select: {
                      style: 'single',
                      toggleable: false,
                    },
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
                        href="#/"
                        data-bs-toggle="modal"
                        data-bs-target="#exampleModal3"
                        >New ticket</a
                      >
                    </button>
                  </div>
                  <div class="col-md-8">
                    <button type="button" class="btn btn-primary">
                      <a
                        href="#/"
                        v-on:click="checkIfTicketSelected()"
                        data-bs-target="#exampleModal1"
                        >Edit ticket</a
                      >
                    </button>
                  </div>
                  <div class="col-md-2">
                    <button type="button" class="btn btn-danger">
                      <a href="#/" v-on:click="deleteTicket(selectedTicketId)"
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
                    <div class="text-container">
                      Selected ticket information
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-6">
                      <div class="text-container3">TITLE</div>
                      <div class="text-container4">
                        {{ selectedTicketTitle }}
                      </div>
                    </div>
                    <div class="col-6">
                      <div class="text-container3">PROJECT</div>
                      <div class="text-container4">
                        {{ selectedTicketProjectName }}
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
                      <div class="text-container4">
                        {{ selectedTicketType }}
                      </div>
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
                              href="#/"
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
  </div>
</template>

<script>
// Comment paging from https://stackoverflow.com/questions/62905543/pagination-for-dynamically-generated-content
import { Modal } from "bootstrap";
import NavBar from "./NavBar.vue";
import DataTable from "datatables.net-vue3";
import DataTablesCore from "datatables.net";
import "datatables.net-select";
import "datatables.net-responsive";
import "datatables.net-select-dt";
import ProjectService from "../services/project.service";
import TicketService from "../services/ticket.service";
import UserService from "../services/user.service";
import EventBus from "../common/EventBus";
DataTable.use(DataTablesCore);

export default {
  name: "ProjectPage",
  components: {
    NavBar,
    DataTable,
  },
  data() {
    return {
      modalInstance: null,
      ticketTitle: "",
      ticketDescription: "",
      ticketPriority: "null",
      ticketStatus: "null",
      ticketType: "null",
      commentMessage: "",
      historyData: [],
      data: [],
      ticketDevs: [],
      newTicketProject: "",
      newTicketDevs: [],
      newTitle: "",
      newDescription: "",
      newPriority: "",
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
      errorAssignDevsToTicketInModify: "",
      errorAssignProjectToTicket: "",
      errorAssignProjectToTicketInModify: "",
      errorCreateTicket: "",
      errorAssignPMtoProject: "",
      errorAssignPMtoProjectInModify: "",
      errorGetDevelopers: "",
      errorGetProjectTickets: "",
      errorModifyProject: "",
      errorModifyTicket: "",
      errorSetDevs: "",
      errorSetDevsInModify: "",
      errorGetPMs: "",
      errorSelectTicket: "",
      priorityChoices: ["None", "Low", "Medium", "High"],
      typeChoices: ["Bug", "FeatureRequest", "DocRequest", "Other"],
      statusChoices: ["New", "Open", "InProgress", "Resolved", "AddInfo"],

      projectName: "",
      projectDescription: "",
      projectPM: "null",
      projectDevs: [],
      selectedName: "",
      selectedDescription: "",
      selectedDevs: [],
      selectedDevsObj: [],
      selectedProjectDevs: [],
      selectedPm: "",
      selectedTickets: "",
      selectedProjectId: "",
      selectedTicketSubmitter: "",
      selectedTicketTitle: "",
      selectedTicketProject: "",
      selectedTicketProjectName: "",
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
            return row.creationDate.substring(0, 19);
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
            return row.dateOfChange.substring(0, 19);
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
    }, // https://stackoverflow.com/questions/5927109/sort-objects-in-arraylist-by-date
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
      // Initializing  items from backend
      if (this.currentUserIsAdminOrPM) {
        ProjectService.getAllProjects().then(
          (response) => {
            // JSON responses are automatically parsed.
            this.data = response.data.slice(0, 3);
            console.log(this.data);
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

        UserService.getAllDevelopers().then(
          (response) => {
            // JSON responses are automatically parsed.
            this.developers = response.data;
            console.log(this.developers);
          },
          (error) => {
            this.errorGetDevelopers =
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

        UserService.getAllProjectManagers().then(
          (response) => {
            // JSON responses are automatically parsed.
            this.projectManagers = response.data;
            console.log(this.projectManagers);
          },
          (error) => {
            this.errorGetPMs =
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
        ProjectService.getAssignedProjectForDev(this.currentUser.username).then(
          (response) => {
            // JSON responses are automatically parsed.
            this.data = response.data.slice(0, 3);
            console.log(this.data);
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
      }
    }
  },

  methods: {
    setDevs(ticketProject) {
      ProjectService.getProjectWithId(ticketProject).then(
        (response) => {
          // JSON responses are automatically parsed.
          this.r = response.data;
          this.selectedProjectDevs = this.r.assignedDevelopers;
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
    },
    clickRowTicket() {
      this.resetErrorMessages();
      const table = this.$refs.tableTicket.dt;
      var ticket = table.row(".selected").data();
      if (ticket != null) {
        this.selectedTicketSubmitter = ticket.submitter;
        this.selectedTicketId = ticket.id;
        this.selectedTicketTitle = ticket.title;
        this.selectedTicketProject = ticket.project.id;
        this.selectedTicketProjectName = ticket.project.name;
        this.selectedTicketDescription = ticket.description;
        this.selectedTicketDevs = ticket.assignedDevelopers;
        this.selectedTicketTA = ticket.ticketAttachments;
        this.selectedTicketPriority = ticket.priority;
        this.selectedTicketType = ticket.type;
        this.selectedTicketStatus = ticket.status;
        this.selectedComments = ticket.comments;
        this.newTicketProject = ticket.project.id;
        if (ticket.assignedDevelopers != null) {
          for (var d of ticket.assignedDevelopers) {
            this.newTicketDevs.push(d.username);
          }
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
      this.resetErrorMessages();
      this.selectedTicketSubmitter = "";
      this.selectedTicketId = "";
      this.selectedTicketTitle = "";
      this.selectedTicketProject = "";
      this.selectedTicketProjectName = "";
      this.selectedTicketDescription = "";
      this.selectedTicketDevs = [];
      this.selectedTicketPriority = "";
      this.selectedTicketType = "";
      this.selectedTicketStatus = "";
      this.selectedComments = [];
      this.historyData = [];
      const tableTicket = this.$refs.tableTicket.dt;
      tableTicket.row(".selected").deselect();
      const table = this.$refs.table.dt;
      var project = table.row(".selected").data();

      this.selectedDevs = [];
      this.selectedDevsObj = [];
      this.selectedProjectDevs = project.assignedDevelopers;

      this.selectedProjectId = project.id;
      this.selectedName = project.name;
      this.selectedDescription = project.description;
      for (var d of project.assignedDevelopers) {
        this.selectedDevs.push(d.username);
        this.selectedDevsObj.push(d);
      }
      console.log(this.selectedDevs);
      this.selectedPm = project.projectManager;
      this.selectedTickets = project.tickets;

      TicketService.getTicketsForProjectWithId(this.selectedProjectId).then(
        (response) => {
          // JSON responses are automatically parsed.
          this.projectTickets = response.data;
          // Make sure project of ticket is up to date
          for (var t of this.projectTickets) {
            t.project = project;
          }
        },
        (error) => {
          this.errorGetProjectTickets =
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

    resetErrorMessages() {
      this.errorGetProjects = "";
      this.errorCreateComment = "";
      this.errorDeleteTicket = "";
      this.errorGetProject = "";
      this.errorCreateProject = "";
      this.errorDeleteProject = "";
      this.errorAssignDevsToTicket = "";
      this.errorAssignDevsToTicketInModify = "";
      this.errorAssignProjectToTicket = "";
      this.errorAssignProjectToTicketInModify = "";
      this.errorCreateTicket = "";
      this.errorAssignPMtoProject = "";
      this.errorAssignPMtoProjectInModify = "";
      this.errorGetDevelopers = "";
      this.errorGetProjectTickets = "";
      this.errorModifyProject = "";
      this.errorModifyTicket = "";
      this.errorSetDevs = "";
      this.errorSetDevsInModify = "";
      this.errorGetPMs = "";
      this.errorSelectTicket = "";
    },

    // TODO: how to change password???
    createProject: function (
      name,
      description,
      projectManagerUsername,
      projectDevs
    ) {
      this.resetErrorMessages();
      if (projectDevs.length != 0) {
        this.errorSetDevs = "";

        ProjectService.createProject(
          name,
          description,
          projectManagerUsername
        ).then(
          (response) => {
            // JSON responses are automatically parsed.
            this.r = response.data;
            var projectId = this.r.id;

            ProjectService.assignDevsToProject(
              projectId,
              projectDevs.reduce((f, s) => `${f},${s}`)
            ).then(
              (response) => {
                // JSON responses are automatically parsed.
                this.data.push(response.data);
                this.projectDescription = "";
                this.projectName = "";
                this.projectPM = "";
                this.projectDevs = [];
                this.$refs.Close.click();
              },
              (error) => {
                this.errorSetDevs =
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
            this.errorCreateProject =
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
        this.errorSetDevs = "List of developers cannot be empty";
      }
    },

    modifyProject: function (
      projectId,
      name,
      description,
      projectManagerUsername,
      projectDevs
    ) {
      this.resetErrorMessages();
      this.errorDeleteProject = "";
      const table = this.$refs.table.dt;
      var project = table.row(".selected").data();
      if (project == null) {
        this.errorModifyProject = "No project selected. ";
      } else {
        var indexProject = this.data.map((x) => x.id).indexOf(projectId);
        if (projectDevs.length != 0) {
          this.errorSetDevsInModify = "";

          ProjectService.modifyProject(projectId, name, description).then(
            (response) => {
              // JSON responses are automatically parsed.
              this.r = response.data;
              this.data[indexProject].name = name;
              this.data[indexProject].description = description;

              var projectId = this.r.id;

              ProjectService.assignPMToProject(
                projectId,
                projectManagerUsername
              ).then(
                (response) => {
                  // JSON responses are automatically parsed.
                  this.data[indexProject].projectManager =
                    response.data.projectManager;
                },
                (error) => {
                  this.errorAssignPMtoProjectInModify =
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

              ProjectService.assignDevsToProject(
                projectId,
                projectDevs.reduce((f, s) => `${f},${s}`)
              ).then(
                (response) => {
                  // JSON responses are automatically parsed.
                  this.data[indexProject].assignedDevelopers =
                    response.data.assignedDevelopers;
                },
                (error) => {
                  this.errorSetDevsInModify =
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
              this.errorModifyProject =
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
          this.errorSetDevsInModify = "List of developers cannot be empty. ";
        }
      }
    },
    deleteProject: function (projectId) {
      this.resetErrorMessages();
      const table = this.$refs.table.dt;
      var project = table.row(".selected").data();
      if (project == null) {
        this.errorDeleteProject = "No project selected. ";
      } else {
        var indexProject = this.data.map((x) => x.id).indexOf(projectId);

        ProjectService.deleteProject(projectId).then(
          (response) => {
            // JSON responses are automatically parsed.
            this.r = response.data;

            // Reset input fields
            this.selectedName = "";
            this.selectedDescription = "";
            this.selectedPm = "";
            this.selectedDevs = [];
            // Remove item from array
            if (indexProject !== -1) {
              this.data.splice(indexProject, 1);
            }
          },
          (error) => {
            this.errorDeleteProject =
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
                this.projectTickets.push(response.data);
                this.ticketTitle = "";
                this.ticketDescription = "";
                this.ticketPriority = "null";
                this.ticketStatus = "null";
                this.ticketType = "null";
                this.selectedProjectId = "";
                this.ticketDevs = [];

                this.$refs.CloseTicket.click();
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

      var indexTicket = this.projectTickets.map((x) => x.id).indexOf(ticketId);
      if (ticketDevs.length != 0) {
        this.errorAssignDevsToTicketInModify = "";
        this.errorAssignProjectToTicketInModify = "";

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
                this.projectTickets[indexTicket].assignedDevelopers =
                  response.data.assignedDevelopers;
                this.selectedTicketDevs = response.data.assignedDevelopers;
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
                this.projectTickets[indexTicket].project =
                  response.data.project;
                this.selectedTicketProject = response.data.project.id;
                this.selectedTicketProjectName = response.data.project.name;
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
            this.projectTickets[indexTicket].title = this.r.title;
            this.selectedTicketTitle = this.r.title;
            this.projectTickets[indexTicket].description = this.r.description;
            this.selectedTicketDescription = this.r.description;
            this.projectTickets[indexTicket].priority = this.r.priority;
            this.selectedTicketPriority = this.r.priority;
            this.projectTickets[indexTicket].status = this.r.status;
            this.selectedTicketStatus = this.r.status;
            this.projectTickets[indexTicket].type = this.r.type;
            this.selectedTicketType = this.r.type;
            this.projectTickets[indexTicket].ticketHistories =
              this.r.ticketHistories;
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
      } else {
        this.errorAssignDevsToTicket = "List of developers cannot be empty. ";
      }
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
      const table = this.$refs.tableTicket.dt;
      var ticket = table.row(".selected").data();
      if (ticket == null) {
        this.errorCreateComment = "No ticket selected. ";
      } else {
        if (message == null || message == "") {
          this.errorCreateComment = "Comment cannot be empty. ";
        } else {
          var indexTicket = this.projectTickets
            .map((x) => x.id)
            .indexOf(ticketId);
          TicketService.createCommentForTicket(
            ticketId,
            this.currentUser.username,
            message
          ).then(
            (response) => {
              // JSON responses are automatically parsed.
              this.r = response.data;
              this.projectTickets[indexTicket].comments = this.r.comments;
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
      const table = this.$refs.tableTicket.dt;
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
      const table = this.$refs.tableTicket.dt;
      var ticket = table.row(".selected").data();
      if (ticket == null) {
        this.errorSelectTicket = "No ticket selected. ";
      } else {
        var indexTicket = this.projectTickets
          .map((x) => x.id)
          .indexOf(ticketId);

        TicketService.deleteTicket(ticketId).then(
          (response) => {
            // JSON responses are automatically parsed.
            this.r = response.data;
            this.historyData = [];
            this.selectedTicketTitle = "";
            this.selectedTicketProjectName = "";
            this.selectedTicketDescription = "";
            this.selectedTicketSubmitter = "";
            this.selectedTicketType = "";
            this.selectedTicketPriority = "";
            this.selectedTicketStatus = "";
            this.selectedTicketDevs = [];
            this.selectedComments = [];
            // Remove item from array
            if (indexTicket !== -1) {
              this.projectTickets.splice(indexTicket, 1);
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
  margin-left: 0px;
  margin-right: 0px;
}

.projectInfoContainer {
  width: 100%;
  margin-top: 5px;
  background-color: white;
  padding: 20px;
}

.projectInfoContainer .row p {
  margin-bottom: 0px;
}

form {
  width: 100%;
  margin-top: 5px;
  background-color: white;
  padding: 20px;
  margin-left: 0px;
  margin-right: 0px;
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

span {
  color: red;
}

.afterTabTitle {
  display: flex;
  justify-content: center;
  align-items: center;
}

.theContent {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
</style>
