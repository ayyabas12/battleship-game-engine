package com.nl.abn.bt.mockdata;

import com.google.gson.JsonObject;
import com.nl.abn.bt.dto.UserDetails;
import com.nl.abn.bt.model.AuthorisationRequest;
import com.nl.abn.bt.model.AuthorisationResponse;
import com.nl.abn.bt.model.GameRequest;
import com.nl.abn.bt.model.GameResponse;
import com.nl.abn.bt.model.enums.Players;
import com.nl.abn.bt.model.enums.State;

import java.util.List;

public class MockDataProvider {

    public static JsonObject getAuthRequest() {
        return MockJsonBuilder.aRequest()
                .withProperty("username", "johnuser")
                .withProperty("password", "somepassword")
                .build();
    }

    public static JsonObject getSaveUserDetailsRequest() {
        return MockJsonBuilder.aRequest()
                .withProperty("username", "johnuser")
                .withProperty("password", "somepassword")
                .build();
    }

    public static JsonObject getInvalidLoginRequest(String userName, String password) {
        return MockJsonBuilder.aRequest()
                .withProperty("username", userName)
                .withProperty("password", password)
                .build();
    }

    public static JsonObject getInvalidLSaveRequest(String userName, String password) {
        return MockJsonBuilder.aRequest()
                .withProperty("username", userName)
                .withProperty("password", password)
                .build();
    }


    public static GameResponse getBottomPlayerBoard(String name){
        return buildGameResponse("",MockBottleShipBoard.MockBottomBoardView.get(Players.valueOf(name)));
    }

    public static JsonObject getGameRequest(int colNum, int rowNum ,String name) {
        return MockJsonBuilder.aRequest()
                .withProperty("playerId", name)
                .withProperty("rowNumber", rowNum)
                .withProperty("colNumber", colNum)
                .build();
    }
    public static GameResponse getTopPlayerBoard(String name){
        return buildGameResponse("",MockBottleShipBoard.MockTopGameBoardView.get(Players.valueOf(name)));
    }


    public static AuthorisationRequest getLoginServiceRequest(String userName, String password) {
        return AuthorisationRequest.builder().userName(userName).password(password).build();
    }

    public static AuthorisationRequest getAuthServiceRequest(String userName, String password) {
        return AuthorisationRequest.
                builder().
                userName(userName).
                password(password)
                .build();
    }

    public static AuthorisationResponse BuildAuthResponse(String userName) {
        return AuthorisationResponse.
                builder().
                userName(userName).
                response("Success")
                .build();
    }

    public static UserDetails getUserDetails(){
        return UserDetails.builder().
                username("test")
                .build();
    }
    public static GameResponse buildGameResponse(final String player, final List<List<State>> board){
        return GameResponse.builder().board(board).result(player).build();
    }

    public static GameRequest buildGameRequest(int colNumber,int rowNumber , String player){
        return GameRequest.builder()
                .colNum(colNumber)
                .rowNum(rowNumber)
                .player(Players.valueOf(player)).build();
    }
}
