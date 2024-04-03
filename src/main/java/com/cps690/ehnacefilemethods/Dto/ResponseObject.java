package com.cps690.ehnacefilemethods.Dto;

import lombok.Data;

@Data
public class ResponseObject {

    private int responseCode;
    private String responseMessage;
    private String statusMessage;
    private Object data;

    public ResponseObject(int responseCode, String responseMessage, String statusMessage, Object data) {
        super();
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.statusMessage = statusMessage;
        this.data = data;
    }
}
