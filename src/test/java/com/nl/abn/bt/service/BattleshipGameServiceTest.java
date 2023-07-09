package com.nl.abn.bt.service;

import com.nl.abn.bt.helper.MissilePosition;
import com.nl.abn.bt.mockdata.MockBottleShipBoard;
import com.nl.abn.bt.mockdata.MockDataProvider;
import com.nl.abn.bt.model.GameResponse;
import com.nl.abn.bt.model.enums.Players;
import com.nl.abn.bt.model.enums.State;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BattleshipGameServiceTest {
    BattleshipGameService battleshipGameService;
    @Mock
    MissilePosition missilePosition;

    @BeforeEach
    public void setup() {
        battleshipGameService = new BattleshipGameService(missilePosition);
        MockBottleShipBoard.intiGames();
    }

    @Nested
    @DisplayName("All service the Games Details")
    class RequestGetSaveRecipesDetails {

        @Test
        void testGetTopBoardDetailsDetails() {
            GameResponse gameResponse = battleshipGameService.showBottomPlayerBoard(Players.P1);
            assertNotNull(gameResponse);
            assertEquals("P1", gameResponse.getResult());
            assertEquals(10, gameResponse.getBoard().size());
            assertEquals("ship", gameResponse.getBoard().get(0).get(1).toString());

        }

        @Test
        void testGetBottomBoardDetailsDetails() {
            GameResponse gameResponse = battleshipGameService.showTopPlayerBoard(Players.P1);
            assertNotNull(gameResponse);
            assertEquals("P1", gameResponse.getResult());
            assertEquals(10, gameResponse.getBoard().size());
            assertEquals("blank", gameResponse.getBoard().get(0).get(1).toString());

        }
        @Test
        void testGetTopBoardWhenDetailsISNotFound() {
            IllegalArgumentException invalidRequestException;
            invalidRequestException = Assertions.<IllegalArgumentException>assertThrows(IllegalArgumentException.class, () ->
                    battleshipGameService.showTopPlayerBoard(Players.valueOf("P3")));
            assertEquals("No enum constant com.nl.abn.bt.model.enums.Players.P3", invalidRequestException.getMessage());
        }
        @Test
        void testGetBottomBoardWhenDetailsISNotFound() {
            IllegalArgumentException invalidRequestException = Assertions.assertThrows(IllegalArgumentException.class, () ->
                    battleshipGameService.showBottomPlayerBoard(Players.valueOf("P3")));
            assertEquals("No enum constant com.nl.abn.bt.model.enums.Players.P3", invalidRequestException.getMessage());
        }

        @Test
        void testFireTheMissileTheGameEnd() {
            when(missilePosition.isGameEnded()).thenReturn("Games is Ended");
            GameResponse gameResponse = battleshipGameService.fireMissiles(MockDataProvider.buildGameRequest(1,2,"P1"));
            assertNotNull(gameResponse);
            assertEquals("Games is Ended", gameResponse.getResult());
            assertNull(gameResponse.getBoard());
            verify(missilePosition, times(1)).isGameEnded();
            verify(missilePosition, times(0)).topPlayerBoard(Players.P2);
        }
        @Test
        void testFireTheMissileToContinueTheGame() {
            when(missilePosition.fireMissile(any())).thenReturn(State.hit);
            when(missilePosition.isAllShipsHit(any())).thenReturn(false);
            when(missilePosition.topPlayerBoard(any())).thenReturn(MockBottleShipBoard.MockBottomBoardView.get("P1"));
            GameResponse gameResponse = battleshipGameService.fireMissiles(MockDataProvider.buildGameRequest(1,2,"P1"));
            assertNotNull(gameResponse);
            assertEquals("hit", gameResponse.getResult());
        }
        @Test
        void testStartNewGame() {
            GameResponse gameResponse = battleshipGameService.startNewGame();
            assertNotNull(gameResponse);
            assertEquals("Please fire the Missiles", gameResponse.getResult());
            assertNull(gameResponse.getBoard());
        }


        @Test
        void testFireTheMissileWhenPlayerIsWinTheGame() {
            when(missilePosition.fireMissile(any())).thenReturn(State.hit);
            when(missilePosition.isAllShipsHit(any())).thenReturn(true);
            GameResponse gameResponse = battleshipGameService.fireMissiles(MockDataProvider.buildGameRequest(1,2,"P1"));
            assertNotNull(gameResponse);
            assertEquals("P1 has won the game! Game has ended.  Please start a new game.", gameResponse.getResult());
        }



    }
}