package com.nl.abn.bt.web;

import com.nl.abn.bt.exception.RestExceptionHandler;
import com.nl.abn.bt.model.AuthorisationRequest;
import com.nl.abn.bt.model.AuthorisationResponse;
import com.nl.abn.bt.service.LoginPersistenceService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SuperBuilder(toBuilder = true)
public class LoginFrontEndController {


    private final LoginPersistenceService loginService;

    /**
     * @param loginRequest user details
     * @return user token
     */

    @PostMapping(value = "/login-service/authorization", consumes = "application/json")
    @ApiOperation(notes = "Gets the authorized details of the customer", produces = "application/json", value = "Gets the authorized details of the customer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved accounts", response = Boolean.class),
            @ApiResponse(code = 500, message = "Internal server error", response = RestExceptionHandler.class),
            @ApiResponse(code = 400, message = "Bad Request", response = RestExceptionHandler.class)
    })
    public ResponseEntity<AuthorisationResponse> getAccessToken(@Valid @RequestBody AuthorisationRequest loginRequest) {
        log.info("Inside login method call and user name {}", loginRequest.getUserName());
        return ResponseEntity.ok(loginService.getAccessToken(loginRequest));
    }

    /**
     * @param authorisationRequest username
     * @return saved details
     */
    @PostMapping(value = "/login-service/user-details", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully register the user details", response = Boolean.class),
            @ApiResponse(code = 500, message = "Internal server error", response = RestExceptionHandler.class),
            @ApiResponse(code = 400, message = "Bad Request", response = RestExceptionHandler.class)
    })
    public ResponseEntity<AuthorisationResponse> registerUserDetails(@Valid @RequestBody AuthorisationRequest authorisationRequest) {
        log.debug("Inside save method call and user name {}", authorisationRequest.getUserName());
        return ResponseEntity.ok(loginService.registerUserDetails(authorisationRequest));

    }
}