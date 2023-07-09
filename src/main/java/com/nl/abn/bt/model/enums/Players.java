package com.nl.abn.bt.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.nl.abn.bt.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public enum Players {
    P1("P1"), P2("P2");
    private final String type;
}
