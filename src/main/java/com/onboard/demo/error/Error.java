package com.onboard.demo.error;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Error {
    private String message;
    private Date timestamp;
}
