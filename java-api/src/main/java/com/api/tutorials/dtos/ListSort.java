package com.api.tutorials.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListSort {
    @Schema(name = "field", example = "model")
    private String field;
    private Boolean ascending=Boolean.TRUE;
}
