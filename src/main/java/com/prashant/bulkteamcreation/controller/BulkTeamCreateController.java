package com.prashant.bulkteamcreation.controller;

import java.util.ArrayList;
import java.util.List;

import com.prashant.bulkteamcreation.model.BulkTeamCreateResponse;
import com.prashant.bulkteamcreation.model.FailedTeamDataAndReason;
import com.prashant.bulkteamcreation.model.BulkTeamCreateResponse.Report;
import com.prashant.bulkteamcreation.service.BulkTeamCreateService;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class BulkTeamCreateController {

    @Autowired
    private BulkTeamCreateService bulkTeamCreateService;

    @PostMapping("/bulk-team")
    public ResponseEntity<String> uploadBulkTeamJsonData(@RequestBody List<TeamCreateRequestDTO> bulkTeams) {

        // calls some intenal service for processing and get id for that processing and

        Report rprt = Report.builder().failedTeamCount(0)
                .failedTeamsDataAndReason(new ArrayList<FailedTeamDataAndReason>())
                .insertedTeamNames(new ArrayList<String>()).insertedTeamCount(0)
                .result("result will be available shortly , try again after sometime").build();

        ObjectId u = new ObjectId();
        BulkTeamCreateResponse btcr = BulkTeamCreateResponse.builder().id(u).status("processing").report(rprt).build();

        bulkTeamCreateService.validateItems(bulkTeams, btcr);

        // return the ObjectId for the BulkTeamCreateResponse document
        return new ResponseEntity<>(btcr.getId().toString(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/bulk-team-creation-status")
    public Mono<BulkTeamCreateResponse> bulkTeamCreationStatus(@RequestParam String bulkCreationId) {

        ObjectId objectId = new ObjectId(bulkCreationId);
        Mono<BulkTeamCreateResponse> mbtcr = bulkTeamCreateService.getResponse(objectId);
        return mbtcr;

    }

}
