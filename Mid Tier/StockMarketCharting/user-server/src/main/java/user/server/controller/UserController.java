package user.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import common.model.LoginRequest;
import common.model.Response;
import user.server.entity.User;
import user.server.model.UpdateRequest;
import user.server.service.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userService;

	private String adminServiceBaseUrl = "http://127.0.0.1:8099";

	/**
	 * User Login
	 * @param request LoginRequest
	 * @return ResponseEntity
	 */
	@PostMapping("/login")
	public ResponseEntity<Response> login(@RequestBody LoginRequest request) {
		String userName = request.getUsername();
		String passWord = request.getPassword();
		Response response = userService.login(userName, passWord);
		return ResponseEntity.status(response.getStatus()).contentType(MediaType.APPLICATION_JSON).body(response);
	}

	/**
	 * User Register
	 * @param user User
	 * @return ResponseEntity
	 */
	@PostMapping("/user")
	public ResponseEntity<Response> register(@RequestBody User user) {
		Response response = userService.regist(user);
		return ResponseEntity.status(response.getStatus()).contentType(MediaType.APPLICATION_JSON).body(response);
	}

	/**
	 * User Confirm
	 * @param id String
	 * @return ResponseEntity
	 */
	@PutMapping("/user/{id}")
	public ResponseEntity<Response> userConfirm(@PathVariable String id) {
		Response response = userService.activeUser(id);
		return ResponseEntity.status(response.getStatus()).contentType(MediaType.APPLICATION_JSON).body(response);
	}

	/**
	 * Query User Info By id
	 * @param id String
	 * @return ResponseEntity
	 */
	@GetMapping("/user/{id}")
	public ResponseEntity<Response> queryUserById(@PathVariable String id) {
		Response response = userService.queryUserById(id);
		return ResponseEntity.status(response.getStatus()).contentType(MediaType.APPLICATION_JSON).body(response);
	}

	/**
	 * Update User's password
	 * @param request UpdateRequest
	 * @return ResponseEntity
	 */
	@PutMapping("/user")
	public ResponseEntity<Response> updatePassword(@RequestBody UpdateRequest request) {
		String id = request.getId();
		String oldpwd = request.getOldPwd();
		String newpwd = request.getNewPwd();

		Response response = userService.updatePassword(id, oldpwd, newpwd);
		return ResponseEntity.status(response.getStatus()).contentType(MediaType.APPLICATION_JSON).body(response);
	}

	/**
	 * Query company
	 * @return ResponseEntity
	 */
	@GetMapping("/company")
	public ResponseEntity<Response> queryComapny() {
		return proxy("/company");
	}

	/**
	 * Query company info matched specified keyword
	 * @return ResponseEntity
	 */
	@GetMapping("/company/ajax/{keyword}")
	public ResponseEntity<Response> getCompanyNameByKeyword(@PathVariable String keyword) {
		return proxy("/company/ajax/" + keyword);
	}

	/**
	 * Query company info matched specified company name
	 * @return ResponseEntity
	 */
	@GetMapping("/company/{companyName}")
	public ResponseEntity<Response> queryCompanyByCompanyName(@PathVariable String companyName) {
		return proxy("/company/" + companyName);
	}

	/**
	 * Query IpoDetails
	 * 
	 * @return
	 */
	@GetMapping("/ipodetail")
	public ResponseEntity<Response> queryIpoDetails() {
		return proxy("/ipodetail");
	}

	/**
	 * Query IpoDetails By Company Name
	 * 
	 * @param companyName String
	 * @return
	 */
	@GetMapping("/ipodetail/{companyname}")
	public ResponseEntity<Response> queryIpoDetails(@PathVariable String companyname) {
		return proxy("/ipodetail/" + companyname);
	}

	private ResponseEntity<Response> proxy(String uriPath) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Response> response = restTemplate.getForEntity(adminServiceBaseUrl + uriPath, Response.class);
		return response;
	}
}
