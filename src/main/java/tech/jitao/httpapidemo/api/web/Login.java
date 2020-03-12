package tech.jitao.httpapidemo.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.jitao.httpapidemo.common.ApiResult;
import tech.jitao.httpapidemo.common.MessageException;
import tech.jitao.httpapidemo.config.auth.NoAuth;
import tech.jitao.httpapidemo.config.logging.NoLogging;
import tech.jitao.httpapidemo.service.AccountService;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@RestController(Login.PATH)
@CrossOrigin
@NoAuth
@NoLogging(omitResponse = false)
public class Login {
    static final String PATH = "/web/login";

    @Autowired
    private AccountService accountService;

    @PostMapping(PATH)
    public ApiResult process(@Validated @RequestBody Request request) {
        try {
            return ApiResult.okWithData(accountService.login(request.getUsername(), request.getPassword()));
        } catch (MessageException e) {
            return ApiResult.error(e.getMessage());
        }
    }

    private static class Request {

        @NotBlank
        @Size(max = 16)
        private String username;

        @NotBlank
        @Size(max = 40)
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
