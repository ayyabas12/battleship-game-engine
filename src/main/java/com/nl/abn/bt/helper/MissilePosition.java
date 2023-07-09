package com.nl.abn.bt.helper;


import com.nl.abn.bt.config.BottleShipBoard;
import com.nl.abn.bt.constants.AppConstants;
import com.nl.abn.bt.model.GameRequest;
import com.nl.abn.bt.model.enums.Players;
import com.nl.abn.bt.model.enums.State;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.stream.Stream;

@Component
@Slf4j
public class MissilePosition {

    public State fireMissile(final GameRequest request) {
        return updatePlayBoard(request.getRowNum() - 1, request.getColNum() - 1, request.getPlayer());
    }

    private State updatePlayBoard(int row, int col, Players currPlayer) {
      log.debug("update the players board ");
        var oppPlayer = findOppPlayer(currPlayer);
        var topBoard = BottleShipBoard.topGameBoardView.get(currPlayer);
        var bottomBoard = BottleShipBoard.bottomBoardView.get(oppPlayer);
        State fireState;
        if ((State.ship).equals(bottomBoard.get(row).get(col))) {
            bottomBoard.get(row).set(col, State.hit);
            topBoard.get(row).set(col, State.hit);
            fireState = State.hit;
        } else if(!((State.hit).equals(bottomBoard.get(row).get(col)))) {
            bottomBoard.get(row).set(col, State.miss);
            topBoard.get(row).set(col, State.miss);
            fireState = State.miss;
        }else{
            fireState =State.hit;
        }
        BottleShipBoard.bottomBoardView.put(oppPlayer, bottomBoard);
        BottleShipBoard.topGameBoardView.put(currPlayer, topBoard);
        log.debug("update the players top and bottom board ");
        return fireState;
    }

    private Players findOppPlayer(Players player) {
        if (Players.P1.equals(player)) {
            return Players.P2;
        }
        return Players.P1;
    }

    public boolean isAllShipsHit(Players p) {
        var oppPlayer = findOppPlayer(p);
        var board = BottleShipBoard.bottomBoardView.get(oppPlayer);
        return board.stream().flatMap(Stream::of).filter(e -> e.contains(State.ship)).count() <= 0;
    }

    public List<List<State>> topPlayerBoard(Players p) {
        return BottleShipBoard.topGameBoardView.get(findOppPlayer(p));
    }

    public String isGameEnded() {
        if (isAllShipsHit(Players.P1)) {
            return AppConstants.PLAYER_2 + AppConstants.RESULT;
        }
        if (isAllShipsHit(Players.P2)) {
            return AppConstants.PLAYER_1 + AppConstants.RESULT;
        }
        return null;
    }


}
