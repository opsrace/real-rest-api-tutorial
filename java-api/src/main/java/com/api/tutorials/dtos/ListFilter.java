package com.api.tutorials.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListFilter {
    @Schema(name = "field", example = "make")
    private String field;
    @Schema(name = "values", example = "[\"Honda\", \"Alsvin\"]")
    private List<String> values;
    private ListOperator operator;
}
