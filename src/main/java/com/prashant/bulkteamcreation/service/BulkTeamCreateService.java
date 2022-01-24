package com.prashant.bulkteamcreation.service;

import java.util.List;
import java.util.Random;

import com.prashant.bulkteamcreation.controller.TeamCreateRequestDTO;
import com.prashant.bulkteamcreation.model.BulkTeamCreateResponse;
import com.prashant.bulkteamcreation.model.FailedTeamDataAndReason;
import com.prashant.bulkteamcreation.model.Team;
import com.prashant.bulkteamcreation.repository.BulkTeamCreateResponseRepository;
import com.prashant.bulkteamcreation.repository.TeamRepository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class BulkTeamCreateService {

    @Autowired
    private BulkTeamCreateResponseRepository bulkTeamCreateResponseRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Async
    public void validateItems(List<TeamCreateRequestDTO> bulkTeams, BulkTeamCreateResponse btcr) {

        bulkTeamCreateResponseRepository.save(btcr).subscribe();

        // repeat for each team
        bulkTeams.stream().forEach(teamCreationRequestDTO -> {
            TeamCreateRequestDTO t = teamCreationRequestDTO;

            // validate team name
            // if validation failed create FailedTeamData
            if (!validateTeamName(t.getTeamName())) {
                FailedTeamDataAndReason ftdar = FailedTeamDataAndReason.builder()
                        .teamFailedInputData(t)
                        .reason("The name is not valid ")
                        .build();
                int failCount = btcr.getReport().getFailedTeamCount();
                btcr.getReport().setFailedTeamCount(failCount + 1);
                btcr.getReport().getFailedTeamsDataAndReason().add(ftdar);
            } else {
                // create Team
                Team ateam = Team.builder().isActive(true).teamName(t.getTeamName()).parentTeamName(t.getParentTeam())
                        .build();
                teamRepository.save(ateam).subscribe();
                int insertCount = btcr.getReport().getInsertedTeamCount();
                btcr.getReport().setInsertedTeamCount(insertCount + 1);
                btcr.getReport().getInsertedTeamNames().add(t.getTeamName());
            }
            log.info("team validation done for " + t.getTeamName());
        });
        // after all possible team inserted

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            // Intentional delay in case of large data
            System.out.println("Thread was not able to sleep");
        }
        btcr.setStatus("completed");
        if (btcr.getReport().getFailedTeamCount() == 0) {
            btcr.getReport().setResult("all_teams_inserted");
        } else {
            btcr.getReport().setResult("failed_to_insert_some_teams");
        }

        bulkTeamCreateResponseRepository.save(btcr).subscribe();
    }

    public Mono<BulkTeamCreateResponse> getResponse(ObjectId objectId) {

        return bulkTeamCreateResponseRepository.findById(objectId);
    }

    private Boolean validateTeamName(String teamName) {
        // TODO
        Random random = new Random();
        return random.nextBoolean();
    }

}
