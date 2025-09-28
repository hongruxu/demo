package com.hongruxu.demo.entity;

import lombok.Data;

@Data
public class Result {
    Integer code;
    String message;
    Object content;
}
