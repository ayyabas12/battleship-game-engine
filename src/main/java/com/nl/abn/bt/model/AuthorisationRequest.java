package com.nl.abn.bt.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
public class AuthorisationRequest {

    @JsonProperty(value = "username")
    @NotBlank(message = "Please enter username")
    private String userName;
    @JsonProperty(value = "password")
    @NotBlank(message = "Please enter password")
    private String password;

}
