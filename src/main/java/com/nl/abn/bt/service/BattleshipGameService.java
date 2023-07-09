package com.nl.abn.bt.service;

import com.nl.abn.bt.config.BottleShipBoard;
import com.nl.abn.bt.constants.AppConstants;
import com.nl.abn.bt.helper.MissilePosition;
import com.nl.abn.bt.model.GameRequest;
import com.nl.abn.bt.model.GameResponse;
import com.nl.abn.bt.model.enums.Players;
import com.nl.abn.bt.model.enums.State;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@SuperBuilder(toBuilder = true)
public class BattleshipGameService {

    private final MissilePosition missilePosition;

    @Autowired
    public BattleshipGameService(MissilePosition missilePosition) {
        this.missilePosition = missilePosition;
        this.startNewGame();
    }

    /**
     *
     * @param player id
     * @return play board
     */
    public GameResponse showTopPlayerBoard(final Players player) {
        log.debug("showTopPlayerBoard player id {}",player);
        return buildGameResponse(player.name(), BottleShipBoard.topGameBoardView.get(player));
    }

    /**
     * This is method to get the details of player bottom board
     * @param player id
     * @return player bottom board
     */
    public GameResponse showBottomPlayerBoard(final Players player) {
        log.debug("showBottomPlayerBoard player id {}",player);
        return buildGameResponse(player.name(), BottleShipBoard.bottomBoardView.get(player));
    }

    /**
     * Just begin the new game
     * @return begin the new Game
     */
    public GameResponse startNewGame() {
         BottleShipBoard.intiGames();
        log.info("begin the new game");
        return buildGameResponse(AppConstants.NEW_GAME, null);
    }

    /**
     *
     * @param request rumNum,ColNum
     * @return player board
     */
    public GameResponse fireMissiles(final GameRequest request) {
        // check the whether game has ended
        String statusOfGame = missilePosition.isGameEnded();
        if (statusOfGame != null) {
            log.debug("the game has ended and game status is {}", statusOfGame);
            return buildGameResponse(statusOfGame, null);
        }
        return buildGameResponse(statusOfGamesResult(request), missilePosition.topPlayerBoard(request.getPlayer()));
    }

    /**
     *
     * @param request rumNum,ColNum
     * @return result of game
     */

     private String statusOfGamesResult(final GameRequest request){
         // update ship and board
         String winResult = missilePosition.fireMissile(request).toString();
         if (missilePosition.isAllShipsHit(request.getPlayer())) {
             log.debug("the all ship has hit and the game has end {}", winResult);
             winResult = request.getPlayer().name() + AppConstants.WIN_RESULT;
         }
         return winResult;
     }

    /**
     *
     * @param player id
     * @param board details
     * @return build of game
     */
    private GameResponse buildGameResponse(final String player, final List<List<State>> board) {
        return GameResponse.builder()
                .result(player)
                .board(board).build();
    }
}
