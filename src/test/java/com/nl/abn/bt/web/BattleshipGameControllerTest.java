/*
package com.nl.abn.bt.web;

import com.nl.abn.bt.mockdata.MockBottleShipBoard;
import com.nl.abn.bt.mockdata.MockDataProvider;
import com.nl.abn.bt.service.BattleshipGameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc()
public class BattleshipGameControllerTest {

    private static final String GET_BOTTOM_BOARD_URL = "/api/frontend-service/bottom-player-board?";
    private static final String GET_TOP_BOARD_URL = "/api/frontend-service/top-player-board?";
    private static final String GET_NEW_BOARD_URL = "/api/frontend-service/new-game";
    private static final String FIRE_MISSILE_URL = "/api/frontend-service/fireMissile";
    private static final String X_AUTH_USER = "X-AUTH-USER";
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsd2lsbGlhbXMxNiIsInJvbGVzIjoidXNlciIsImlhdCI6MTUxNDQ0OTgzM30.WKMQ_oPPiDcc6sGtMJ1Y9hlrAAc6U3xQLuEHyAnM1FU";

    @Mock
    BattleshipGameService battleshipGameService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        BattleshipGameController stateController = new BattleshipGameController(battleshipGameService);
        mockMvc = MockMvcBuilders.standaloneSetup(stateController)
                .build();
        MockBottleShipBoard.intiGames();

    }

    @Nested
    @DisplayName("Games get and fire call all scenario")
    class TestGetAndSaveGamesDetails {
        @Test
        void testGetBottomBoardDetails() throws Exception {
            when(battleshipGameService.showBottomPlayerBoard(an())).thenReturn(MockDataProvider.getBottomPlayerBoard("P1"));
            mockMvc
                    .perform(MockMvcRequestBuilders.get(GET_BOTTOM_BOARD_URL + "id=P1")
                            .header(X_AUTH_USER, "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            verify(battleshipGameService, times(1)).showBottomPlayerBoard(anyString());
        }

        @Test
        void testTopPlayerBoard() throws Exception {
            mockMvc
                    .perform(MockMvcRequestBuilders.get(GET_TOP_BOARD_URL + "id=P1")
                            .header(X_AUTH_USER, "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            verify(battleshipGameService, times(1)).showTopPlayerBoard(anyString());
        }

        @Test
        void testFireMissileByHitTheShip() throws Exception {
            mockMvc
                    .perform(MockMvcRequestBuilders.post(FIRE_MISSILE_URL)
                            .header(X_AUTH_USER, "Bearer " + token)
                            .content(MockDataProvider.getGameRequest(1, 2, "P1").toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            verify(battleshipGameService, times(1)).fireMissiles(any());
        }

        @Test
        void testFireMissileByTheMissTheShip() throws Exception {
            mockMvc
                    .perform(MockMvcRequestBuilders.post(FIRE_MISSILE_URL)
                            .header(X_AUTH_USER, "Bearer " + token)
                            .content(MockDataProvider.getGameRequest(2, 1, "P1").toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            verify(battleshipGameService, times(1)).fireMissiles(any());
        }

        @Test
        void testStartTheNewGame() throws Exception {
            when(battleshipGameService.startNewGame()).thenReturn(MockDataProvider.buildGameResponse("P1",null));
            mockMvc
                    .perform(MockMvcRequestBuilders.get(GET_NEW_BOARD_URL)
                            .header(X_AUTH_USER, "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            verify(battleshipGameService, times(1)).startNewGame();
        }


        @Nested
        @DisplayName("Get and Save Board details - Service Exceptions")
        class GetAndSaveShipPlayerBoardServiceExceptions{

            @Test
            void getTopPlayerBoardWhenPlayerIdEmpty() throws Exception {
                mockMvc
                        .perform(MockMvcRequestBuilders.get(GET_TOP_BOARD_URL)
                                .header(X_AUTH_USER, "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().is4xxClientError());
                verify(battleshipGameService, times(0)).fireMissiles(any());


            }

            @Test
            void getBottomPlayerBoardWhenPlayerIdEmpty() throws Exception {
                mockMvc
                        .perform(MockMvcRequestBuilders.get(GET_BOTTOM_BOARD_URL)
                                .header(X_AUTH_USER, "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().is4xxClientError());
                verify(battleshipGameService, times(0)).fireMissiles(any());

            }

            @Test
            void fireMissileWhenPlayerIdEmpty() throws Exception {
                mockMvc
                        .perform(MockMvcRequestBuilders.post(FIRE_MISSILE_URL)
                                .header(X_AUTH_USER, "Bearer " + token)
                                .content(MockDataProvider.getGameRequest(2, 1, null).toString())
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().is4xxClientError());
                verify(battleshipGameService, times(0)).fireMissiles(any());


            }
            @Test
            void fireMissileWhenColNumberLessOne() throws Exception {
                mockMvc
                        .perform(MockMvcRequestBuilders.post(FIRE_MISSILE_URL)
                                .header(X_AUTH_USER, "Bearer " + token)
                                .content(MockDataProvider.getGameRequest(0, 1, "P1").toString())
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().is4xxClientError());
                verify(battleshipGameService, times(0)).fireMissiles(any());


            }
            @Test
            void fireMissileWhenRowNumberGraterThanTen() throws Exception {
                mockMvc
                        .perform(MockMvcRequestBuilders.post(FIRE_MISSILE_URL)
                                .header(X_AUTH_USER, "Bearer " + token)
                                .content(MockDataProvider.getGameRequest(1, 11, "P1").toString())
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().is4xxClientError());
                verify(battleshipGameService, times(0)).fireMissiles(any());


            }

        }
    }
}
*/
