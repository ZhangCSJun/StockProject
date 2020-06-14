package gateway.server.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import common.model.AuthInfo;
import common.model.Response;


@FeignClient(value="auth-server")
public interface AuthServiceFeign {
	@PostMapping("/token")
	public ResponseEntity<Response> issueToken(@RequestBody AuthInfo request);
	
	@GetMapping("/token")
	public ResponseEntity<Response> verify(@RequestHeader("tkn") String token);
}
