package com.nl.abn.bt.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nl.abn.bt.model.enums.Players;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
public class GameRequest {

    @JsonProperty(value = "playerId")
    @ApiModelProperty(required = true, notes = "Id of the Player")
    private Players player;
    @JsonProperty(value = "rowNumber")
    @ApiModelProperty(required = true, notes = "rowNum id to update ship position")
    @Min(1)@Max(10)
    private int rowNum;
    @JsonProperty(value = "colNumber")
    @ApiModelProperty(required = true, notes = "colNumber id to update ship position")
    @Min(1)@Max(10)
    private int colNum;
}
