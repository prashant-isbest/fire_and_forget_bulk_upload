package com.prashant.bulkteamcreation.repository;

import com.prashant.bulkteamcreation.model.Team;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TeamRepository extends ReactiveMongoRepository<Team, ObjectId> {

}
