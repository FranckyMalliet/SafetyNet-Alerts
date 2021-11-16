package com.safetynet.application.model;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

@Data
@JsonRootName(value = "firestations")
public class FireStation {

    private String address;
    private String station;

    public FireStation(){}
}
