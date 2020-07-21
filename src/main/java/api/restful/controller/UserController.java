package api.restful.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;

import api.restful.model.user.AuthUser;
import api.restful.handler.CustomMessage;
import api.restful.services.AuthorizationServiceImpl;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private AuthorizationServiceImpl authorizationServiceImpl;

	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST, produces = "application/json")
	public CustomMessage signUp(@RequestBody AuthUser user) {
		try {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			this.authorizationServiceImpl.createUser(user);
			return new CustomMessage(200, "Saved");
		}  catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", e);
	   	}
	}
}