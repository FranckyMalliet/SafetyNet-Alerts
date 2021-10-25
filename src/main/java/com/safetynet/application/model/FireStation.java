package com.safetynet.application.model;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonRootName(value = "firestations")
public class FireStation {

    private String address;
    private String station;

    public FireStation(){}
}
