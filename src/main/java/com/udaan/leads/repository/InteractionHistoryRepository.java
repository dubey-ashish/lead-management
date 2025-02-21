package com.udaan.leads.repository;

import com.udaan.leads.entity.interactions.InteractionHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InteractionHistoryRepository extends MongoRepository<InteractionHistory,String>
{

}
