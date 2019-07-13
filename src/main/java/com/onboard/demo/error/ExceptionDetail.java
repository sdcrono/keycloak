package com.onboard.demo.error;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionDetail {
    private int status;
    private Error detail;
}
