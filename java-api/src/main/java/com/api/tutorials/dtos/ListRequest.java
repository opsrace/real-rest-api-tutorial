package com.api.tutorials.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListRequest {
    private Integer pageNumber=1;
    private Integer recordPerPage=25;
}
