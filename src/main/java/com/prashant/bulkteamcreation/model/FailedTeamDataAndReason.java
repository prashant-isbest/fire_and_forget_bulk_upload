package com.prashant.bulkteamcreation.model;

import com.prashant.bulkteamcreation.controller.TeamCreateRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FailedTeamDataAndReason {

    private TeamCreateRequestDTO teamFailedInputData;
    private String reason;
}
