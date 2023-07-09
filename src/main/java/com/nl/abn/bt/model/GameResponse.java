package com.nl.abn.bt.model;


import com.nl.abn.bt.model.enums.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameResponse {

    private String result;
    private List<List<State>> board;

}
