package com.api.tutorials.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListSort {
    @Schema(name = "field", example = "model")
    private String field;
    private Boolean ascending=Boolean.TRUE;
}
