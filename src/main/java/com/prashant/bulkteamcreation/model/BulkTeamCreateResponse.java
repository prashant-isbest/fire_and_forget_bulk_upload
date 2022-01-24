package com.prashant.bulkteamcreation.model;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Document(collection = "bulk_team_create_history")
@Builder
@Data
public class BulkTeamCreateResponse {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;

    private String status; // if processing still going -> processing
                           // if processing still going -> completed

    private Report report;

    @Data
    @Builder
    public static class Report {

        private Integer insertedTeamCount;
        private Integer failedTeamCount;
        private List<FailedTeamDataAndReason> failedTeamsDataAndReason;
        private List<String> insertedTeamNames;
        private String result; // failed_to_insert_some_teams , all_teams_inserted

    }

}
