package com.safetynet.application.model;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;

@Data
@AllArgsConstructor
@JsonRootName(value = "firestations")
public class FireStation {

    private String fireStationAddress;
    private String stationNumber;

    public FireStation(){}
}
