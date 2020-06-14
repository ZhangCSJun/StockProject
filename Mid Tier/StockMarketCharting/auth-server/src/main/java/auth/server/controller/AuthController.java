package auth.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import auth.server.service.AuthService;
import common.constant.Constant;
import common.model.AuthInfo;
import common.model.Response;
import common.util.StringUtil;

@RestController
public class AuthController {
	
	@Autowired
	private AuthService auth;
	
	@RequestMapping(value = "/token",method = RequestMethod.POST)
	public ResponseEntity<Response> issueToken(@RequestBody AuthInfo request) {
		String userId = request.getUserId();
		Response response = auth.issueToken(userId);

		if(!StringUtil.isNull(response.getBusiness()) && 
				response.getBusiness().containsKey(Constant.BUSINESS_DATA_TOKEN) &&
				!StringUtil.isNullOrEmpty(response.getBusiness().get(Constant.BUSINESS_DATA_TOKEN).toString())) {
			String token = response.getBusiness().get(Constant.BUSINESS_DATA_TOKEN).toString();
			return ResponseEntity.status(response.getStatus()).header("tkn", token).build();
		} else {
			return ResponseEntity.status(response.getStatus()).body(response);
		}
		
	}
	
	@GetMapping("/token")
	public ResponseEntity<Response> verify(@RequestHeader("tkn") String token) {
		Response response = auth.verify(token);
		return ResponseEntity.status(response.getStatus()).body(response);
	}
}
