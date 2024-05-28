<template>
  <NavBar />
  <div class="container">
    <div class="hey">
      <div class="header">
        <h3>Create a developer account</h3>
      </div>
      <div class="row">
        <div class="col-md-12">
          <Form @submit="handleRegister" :validation-schema="schema">
            <div class="d-flex flex-column" v-if="!successful">
              <div class="form-group mb-12">
                <label for="username">Username</label>
                <Field name="username" type="text" class="form-control" />
                <ErrorMessage name="username" class="error-feedback" />
              </div>
              <div class="form-group mb-12">
                <label for="firstName">First Name</label>
                <Field name="firstName" type="firstName" class="form-control" />
                <ErrorMessage name="firstName" class="error-feedback" />
              </div>
              <div class="form-group mb-12">
                <label for="lastName">Last Name</label>
                <Field name="lastName" type="lastName" class="form-control" />
                <ErrorMessage name="lastName" class="error-feedback" />
              </div>
              <div class="form-group mb-12">
                <label for="email">Email</label>
                <Field name="email" type="email" class="form-control" />
                <ErrorMessage name="email" class="error-feedback" />
              </div>
              <div class="form-group mb-12">
                <label for="password">Password</label>
                <Field name="password" type="password" class="form-control" />
                <ErrorMessage name="password" class="error-feedback" />
              </div>

              <div class="form-group mb-12">
                <button class="btn btn-primary" :disabled="loading">
                  <span
                    v-show="loading"
                    class="spinner-border spinner-border-sm"
                  ></span>
                  Sign Up
                </button>
              </div>
            </div>
          </Form>
          <div
            v-if="message"
            class="alert"
            :class="successful ? 'alert-success' : 'alert-danger'"
          >
            {{ message }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Form, Field, ErrorMessage } from "vee-validate";
import * as yup from "yup";
import NavBar from "./NavBar.vue";

export default {
  name: "SignupPage",
  components: {
    NavBar,
    Form,
    Field,
    ErrorMessage,
  },
  data() {
    const schema = yup.object().shape({
      username: yup
        .string()
        .required("Username is required!")
        .min(3, "Must be at least 3 characters!")
        .max(20, "Must be maximum 20 characters!"),
      firstName: yup
        .string()
        .required("First name is required!")
        .min(3, "Must be at least 3 characters!")
        .max(20, "Must be maximum 20 characters!"),
      lastName: yup
        .string()
        .required("Last name is required!")
        .min(3, "Must be at least 3 characters!")
        .max(20, "Must be maximum 20 characters!"),
      email: yup
        .string()
        .required("Email is required!")
        .email("Email is invalid!")
        .max(50, "Must be maximum 50 characters!"),
      password: yup
        .string()
        .required("Password is required!")
        .min(6, "Must be at least 6 characters!")
        .max(40, "Must be maximum 40 characters!"),
    });

    return {
      successful: false,
      loading: false,
      message: "",
      schema,
    };
  },
  // Methods inspired from: https://www.bezkoder.com/vue-3-authentication-jwt/
  // and from: https://www.bezkoder.com/vue-refresh-token/
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    },
  },
  mounted() {
    if (this.loggedIn) {
      this.$router.push("/dashboard");
    }
  },
  methods: {
    handleRegister(user) {
      this.message = "";
      this.successful = false;
      this.loading = true;

      this.$store.dispatch("auth/register", user).then(
        (data) => {
          this.message = data.message;
          this.successful = true;
          this.loading = false;
        },
        (error) => {
          this.message =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();
          this.successful = false;
          this.loading = false;
        }
      );
    },
  },
};
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.hey {
  padding: 30px;
  width: fit-content;
  background-color: white;
  margin-top: 100px;
  border: 1px solid rgb(183, 183, 183);
  border-radius: 5px;
  padding-top: 30px;
  padding-bottom: 0px;
  margin-bottom: 50px;
}

.mb-12 {
  margin-bottom: 20px !important;
}

.form-group {
  font-size: 15px;
  margin-bottom: -50px;
}

.form-group label {
  margin-bottom: 5px;
}

.error-feedback {
  color: red;
}

.header {
  text-align: center;
  margin-bottom: 30px;
}
</style>
