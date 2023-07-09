package com.nl.abn.bt.service;


import com.nl.abn.bt.dto.UserDetails;
import com.nl.abn.bt.dto.repository.UserRepository;
import com.nl.abn.bt.exception.ServiceException;
import com.nl.abn.bt.model.AuthorisationRequest;
import com.nl.abn.bt.model.AuthorisationResponse;
import com.nl.abn.bt.security.JWTAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SuperBuilder(toBuilder = true)
public class LoginPersistenceService {

    private final UserRepository userRepository;
    private final JWTAuthorizationFilter jwtAuthorizationFilter;


    /**
     *
     * @param request Auth
     * @return true or false
     */
    public AuthorisationResponse getAccessToken(AuthorisationRequest request) {
        List<UserDetails> userDetails = userRepository.findLoginsByUsernameAndPassword(request.getUserName(), request.getPassword());
        log.debug("user details {}", CollectionUtils.isEmpty(userDetails));
        if(CollectionUtils.isEmpty(userDetails)){
            log.error("unauthorized user name{}",request.getUserName());
            throw new BadCredentialsException("unauthorized user");
        }
        return buildTokenDetails(jwtAuthorizationFilter.getJWTToken(request.getUserName()));

    }



    /**
     *
     * @param request authRequest
     * @return AuthorisationResponse
     */
    public AuthorisationResponse registerUserDetails(AuthorisationRequest request) {
        List<UserDetails> userDetails = userRepository.findLoginsByUsername(request.getUserName());
        log.debug("user details {}", userDetails);
        if (CollectionUtils.isEmpty(userDetails)) {
            userRepository.save(buildUserDetails(request));
            log.debug("user details saved successfully{}", userDetails);
            return AuthorisationResponse.builder().response("User has created successfully").userName(request.getUserName()).build();
        } else {
            log.error("user already exits{}", userDetails);
            throw new ServiceException("User already exits");
        }
    }

    /**
     *
     * @param request auth
     * @return build request
     */
    private UserDetails buildUserDetails(AuthorisationRequest request) {
        return UserDetails.builder()
                .username(request.getUserName())
                .password(request.getPassword()).build();
    }

    /**
     *
     * @param  token value
     * @return build request
     */
    private AuthorisationResponse buildTokenDetails(String token) {
        return AuthorisationResponse.builder()
                .token(token)
                .currentDate(new Date()).build();
    }


}
