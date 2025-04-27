package com.cl.users.api.dto;

import lombok.Data;

@Data
public class PhoneDTO {
    private String number;
    private String citycode;
    private String contrycode;
}
