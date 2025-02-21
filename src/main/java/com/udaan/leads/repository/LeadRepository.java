package com.udaan.leads.repository;

import com.udaan.leads.entity.restaurant.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeadRepository extends MongoRepository<Restaurant,String>
{
    List<Restaurant> findByCurrentManagerId(String currentManagerId);

    @Query("{ 'restaurantId': ?0 }")
    Restaurant findByRestaurantId(String restaurantId);

}
