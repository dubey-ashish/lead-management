package com.udaan.leads.repository;

import com.udaan.leads.entity.interactions.InteractionPlan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface InteractionPlanRepository extends MongoRepository<InteractionPlan, String> {

    @Query("{'nextCallDate': { $gte: ?0, $lt: ?1 }}")
    List<InteractionPlan> findByNextCallDate(Instant start, Instant end);

    InteractionPlan findByRestaurantId(String leadId);
}
