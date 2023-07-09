package com.nl.abn.bt.web;


import com.nl.abn.bt.exception.RestExceptionHandler;
import com.nl.abn.bt.model.GameRequest;
import com.nl.abn.bt.model.GameResponse;
import com.nl.abn.bt.model.enums.Players;
import com.nl.abn.bt.service.BattleshipGameService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@RestController
@Slf4j
@RequestMapping("/api/frontend-service")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SuperBuilder(toBuilder = true)
public class BattleshipGameController {

    private final BattleshipGameService battleshipGameService;

    @GetMapping("/bottom-player-board")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Gets the player BottomPlayerBoard details", response = GameResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = RestExceptionHandler.class),
            @ApiResponse(code = 400, message = "Bad Request", response = RestExceptionHandler.class)
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "X-AUTH-USER", value = "Encrypted user name", required = true, paramType = "header", dataTypeClass = String.class)})

    @ApiOperation(notes = "Gets the player BottomPlayerBoard details", produces = "application/json", value = "Gets the player BottomPlayerBoard details")
    public ResponseEntity<GameResponse> getBottomPlayerBoard(@RequestParam("id") Players playId) {
        return ResponseEntity.ok(battleshipGameService.showBottomPlayerBoard(playId));
    }

    @GetMapping("/top-player-board")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieve TopPlayerBoard details", response = GameResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = RestExceptionHandler.class),
            @ApiResponse(code = 400, message = "Bad Request", response = RestExceptionHandler.class)
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "X-AUTH-USER", value = "Encrypted user name", required = true, paramType = "header", dataTypeClass = String.class)})
    @ApiOperation(notes = "Gets the TopPlayerBoard details", produces = "application/json", value = "Gets the TopPlayerBoard details")
    public ResponseEntity<GameResponse> getTopPlayerBoard(@RequestParam("id") Players playId) {
        return ResponseEntity.ok(battleshipGameService.showTopPlayerBoard(playId));
    }


    @PostMapping("/fire-missile")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "fire the Missile ", response = GameResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = RestExceptionHandler.class),
            @ApiResponse(code = 400, message = "Bad Request", response = RestExceptionHandler.class)
    })
    @ApiOperation(notes = "fire the Missile", produces = "application/json", value = "fire the Missile")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "X-AUTH-USER", value = "Encrypted user name", required = true, paramType = "header", dataTypeClass = String.class)})
    public ResponseEntity<GameResponse> fireMissile(@Valid @RequestBody GameRequest request) {
        return ResponseEntity.ok(battleshipGameService.fireMissiles(request));


    }

    @GetMapping("/new-game")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "fire the Missile ", response = GameResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = RestExceptionHandler.class),
            @ApiResponse(code = 400, message = "Bad Request", response = RestExceptionHandler.class)
    })
    @ApiOperation(notes = "start the new Game", produces = "application/json", value = "start the new Game")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "X-AUTH-USER", value = "Encrypted user name", required = true, paramType = "header", dataTypeClass = String.class)})
    public ResponseEntity<GameResponse> startNewGame() {
        return ResponseEntity.ok(battleshipGameService.startNewGame());


    }


}
