package com.nl.abn.bt.mockdata;


import com.nl.abn.bt.model.enums.Players;
import com.nl.abn.bt.model.enums.State;

import java.util.*;
import java.util.stream.Collectors;

public class MockBottleShipBoard {

    private MockBottleShipBoard(){}

    private static List<List<State>> MockTopGameBoard1;
    private static List<List<State>> MockTopGameBoard2;
    private static List<List<State>> MockBottomBoard1;
    private static List<List<State>> MockBottomBoard2;


    public static Map<Players, List<List<State>>> MockTopGameBoardView;

    public static  Map<Players, List<List<State>>> MockBottomBoardView;

    private static List<List<State>> emptyBoard() {
        List<List<State>> board = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<State> row = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                row.add(State.blank);
            }
            board.add(row);
        }
        return board;
    }



    private static List<List<State>> mockPlay1Board() {
        State[][] board = {
                {State.ship, State.ship, State.ship, State.ship, State.blank, State.blank, State.blank, State.blank, State.blank, State.blank},
                {State.blank, State.blank, State.blank, State.blank, State.blank, State.ship, State.ship, State.ship, State.blank, State.blank},
                {State.blank, State.ship, State.blank, State.blank, State.blank, State.blank, State.blank, State.blank, State.blank, State.blank},
                {State.blank, State.ship, State.blank, State.blank, State.blank, State.blank, State.blank, State.blank, State.blank, State.blank},
                {State.blank, State.ship, State.blank, State.blank, State.blank, State.blank, State.blank, State.blank, State.blank, State.blank},
                {State.blank, State.blank, State.blank, State.blank, State.blank, State.blank, State.blank, State.blank, State.blank, State.blank},
                {State.blank, State.blank, State.blank, State.blank, State.blank, State.blank, State.blank, State.blank, State.ship, State.blank},
                {State.blank, State.blank, State.blank, State.blank, State.blank, State.blank, State.blank, State.blank, State.ship, State.blank},
                {State.blank, State.blank, State.ship, State.ship, State.ship, State.ship, State.ship, State.blank, State.blank, State.blank},
                {State.blank, State.blank, State.blank, State.blank, State.blank, State.blank, State.blank, State.blank, State.blank, State.blank},
        };
        return Arrays.stream(board).map(internalArray -> Arrays.stream(internalArray).collect(Collectors.toList())).collect(Collectors.toList());
    }



    public static void intiGames(){
        MockTopGameBoard1 = emptyBoard();
        MockTopGameBoard2 = emptyBoard();

        MockBottomBoard1 = mockPlay1Board();
        MockBottomBoard2 = mockPlay1Board();

        MockBottomBoardView = new HashMap<Players, List<List<State>>>() {
            {
                put(Players.P1, MockTopGameBoard1);
                put(Players.P2, MockBottomBoard2);
            }
        };

        MockTopGameBoardView = new HashMap(new HashMap<Players, List<List<State>>>() {
            {
                put(Players.P1, MockTopGameBoard1);
                put(Players.P2, MockTopGameBoard2);
            }
        });
    }

}
