package com.nl.abn.bt.config;


import com.nl.abn.bt.model.enums.Players;
import com.nl.abn.bt.model.enums.State;

import java.util.*;
import java.util.stream.Collectors;

public class BottleShipBoard {

    private BottleShipBoard(){}

    public static Map<Players, List<List<State>>> topGameBoardView;

    public static  Map<Players, List<List<State>>> bottomBoardView;

    private static List<List<State>> createEmptyPlayerBoard() {
        List<List<State>> column = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<State> row = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                row.add(State.blank);
            }
            column.add(row);
        }
        return column;
    }



    private static List<List<State>> createPlayer2Board() {
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

    private static List<List<State>> createPlayer1Board() {

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
        bottomBoardView = new HashMap<Players, List<List<State>>>() {
            {
                put(Players.P1, createPlayer1Board());
                put(Players.P2, createPlayer2Board());
            }
        };

        topGameBoardView = new HashMap(new HashMap<Players, List<List<State>>>() {
            {
                put(Players.P1,  createEmptyPlayerBoard());
                put(Players.P2, createEmptyPlayerBoard());
            }
        });

    }

    }
