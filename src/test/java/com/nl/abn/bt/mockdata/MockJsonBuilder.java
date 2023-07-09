package com.nl.abn.bt.mockdata;

import com.google.gson.JsonObject;

public class MockJsonBuilder {
    private JsonObject jsonObject;

    private MockJsonBuilder() {
        this.jsonObject = new JsonObject();
    }


    static MockJsonBuilder aRequest() {
        return new MockJsonBuilder();
    }

    MockJsonBuilder withProperty(String key, String value) {
        this.jsonObject.addProperty(key, value);
        return this;
    }
    MockJsonBuilder withProperty(String key, int value) {
        this.jsonObject.addProperty(key, value);
        return this;
    }
    JsonObject build() {
        return this.jsonObject;
    }

}
