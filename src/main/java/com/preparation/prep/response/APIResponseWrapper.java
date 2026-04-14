package com.preparation.prep.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIResponseWrapper<T>{

    private String message;

    private T data;

    private boolean success;


}
