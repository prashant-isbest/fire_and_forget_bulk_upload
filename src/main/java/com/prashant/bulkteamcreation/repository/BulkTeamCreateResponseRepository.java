package com.prashant.bulkteamcreation.repository;

import com.prashant.bulkteamcreation.model.BulkTeamCreateResponse;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BulkTeamCreateResponseRepository extends ReactiveMongoRepository<BulkTeamCreateResponse, ObjectId> {

}
