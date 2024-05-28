import api from "./api";
import TokenService from "./token.service";

// Code inspired from: https://www.bezkoder.com/vue-3-authentication-jwt/
//and from: https://www.bezkoder.com/vue-refresh-token/
class AuthService {
    
  login(user) {
    return api
    // POST {username, password}
      .post('/auth/signin', {
        username: user.username,
        password: user.password
      })
      .then((response) => {
        if (response.data.accessToken) {
          TokenService.setUser(response.data);
        }

        return response.data;
      });
  }

  logout() {

    api.post('/auth/signout', {})
    .then((response) => {
      // remove JWT from Local Storage
      TokenService.removeUser();

      return response.data;
    });
    
    
  }

  register(user) {
    // POST {username, firstName, lastName, email, password}
    return api.post('/auth/signup', {
      username: user.username,
      firstName: user.firstName,
      lastName: user.lastName,
      email: user.email,
      password: user.password,
      role: ["Developer"]
    });
  }
}

export default new AuthService();