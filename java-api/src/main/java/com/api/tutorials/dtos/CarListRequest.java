package com.api.tutorials.dtos;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CarListRequest extends ListRequest{
    List<ListFilter> filters;
    List<ListSort> sorting;
}
