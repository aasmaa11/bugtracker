<template>
  <NavBar />
  <div class="container">
    <div class="hey">
      <div class="header">
        <h3>Log in</h3>
      </div>
      <div class="row">
        <div class="col-md-12">
          <Form
            class="d-flex flex-column"
            @submit="handleLogin"
            :validation-schema="schema"
          >
            <div class="form-group mb-12">
              <label for="username">Username</label>
              <Field name="username" type="text" class="form-control" />
              <ErrorMessage name="username" class="error-feedback" />
            </div>
            <div class="form-group mb-12">
              <label for="password">Password</label>
              <Field name="password" type="password" class="form-control" />
              <ErrorMessage name="password" class="error-feedback" />
            </div>

            <div class="form-group mb-12">
              <button class="btn btn-primary btn-block" :disabled="loading">
                <span
                  v-show="loading"
                  class="spinner-border spinner-border-sm"
                ></span>
                <span>Login</span>
              </button>
            </div>

            <div class="form-group mb-12">
              <div v-if="message" class="alert alert-danger" role="alert">
                {{ message }}
              </div>
            </div>
          </Form>
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
  name: "LoginPage",
  components: {
    NavBar,
    Form,
    Field,
    ErrorMessage,
  },
  data() {
    const schema = yup.object().shape({
      username: yup.string().required("Username is required!"),
      password: yup.string().required("Password is required!"),
    });
    return {
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
  created() {
    if (this.loggedIn) {
      this.$router.push("/dashboard");
    }
  },

  methods: {
    handleLogin(user) {
      this.loading = true;

      this.$store.dispatch("auth/login", user).then(
        () => {
          this.$router.push("/dashboard");
        },
        (error) => {
          this.loading = false;
          this.message =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();
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
  padding: 50px;
  width: fit-content;
  background-color: white;
  margin-top: 150px;
  border: 1px solid rgb(183, 183, 183);
  border-radius: 5px;
  padding-top: 30px;
  padding-bottom: 0px;
  margin-bottom: 50px;
}

.form-group {
  font-size: 15px;
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

.mb-12 {
  margin-bottom: 20px !important;
}
</style>
