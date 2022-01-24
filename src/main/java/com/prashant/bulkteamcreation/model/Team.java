package com.prashant.bulkteamcreation.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
@Document(collection = "myteam")
public class Team {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;

    // @Indexed(unique = true)
    // private String teamId;
    private String teamName;
    private String parentTeamName;
    private Boolean isActive;

}
