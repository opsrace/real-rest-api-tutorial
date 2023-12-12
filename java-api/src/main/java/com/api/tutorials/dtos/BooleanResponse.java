package com.api.tutorials.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BooleanResponse {
    private boolean success;
    private String message;

    public static BooleanResponse success() {
        return new BooleanResponse(true, "Operation success");
    }


    public static BooleanResponse failure() {
        return new BooleanResponse(false, "Operation failed");
    }

}
