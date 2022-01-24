package com.prashant.bulkteamcreation.controller;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeamCreateRequestDTO {

    @JsonProperty("Team Name")
    private String teamName;
    @JsonProperty("description")
    private String description;
    @JsonProperty("Parent Team Name")
    private String parentTeam;

}
