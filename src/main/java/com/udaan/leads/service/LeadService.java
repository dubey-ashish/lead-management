package com.udaan.leads.service;

import com.udaan.leads.entity.restaurant.LeadStatus;
import com.udaan.leads.entity.restaurant.Restaurant;
import com.udaan.leads.repository.LeadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class LeadService
{
    @Autowired
    LeadRepository leadRepository;

    //Save a Lead
    public Restaurant saveLead(Restaurant restaurant)
    {
        return leadRepository.save(restaurant);
    }

    //Get a Lead by id
    public Optional<Restaurant> getLeadById(String id)
    {
        return Optional.of(leadRepository.findByRestaurantId(id));
    }

    //Get all leads
    public Optional<Iterable<Restaurant>> getAllLeads()
    {
        return Optional.of(leadRepository.findAll());
    }

    //Get all leads for a specific KAM-Id
    public Optional<Iterable<Restaurant>> getLeadsByKAMId(String id)
    {
        return Optional.ofNullable(leadRepository.findByCurrentManagerId(id));
    }

    //Assign/Update KAM. Records time of appointment and maintains a previous KAM id.
    public Optional<Restaurant> assignKAM(String restaurantId, String newManagerId) {

        Optional<Restaurant> optionalRestaurant = Optional.ofNullable(leadRepository.findByRestaurantId(restaurantId));

        if (optionalRestaurant.isPresent()) {
            Restaurant restaurant = optionalRestaurant.get();
            restaurant.setPreviousManagerId(restaurant.getCurrentManagerId());
            restaurant.setCurrentManagerId(newManagerId);
            restaurant.setManagerSince(Instant.now());
            leadRepository.save(restaurant);
        }

        return optionalRestaurant;
    }


    //Change status of a Lead
    public Optional<Restaurant> changeStatus(String restaurantId, LeadStatus status) {

        Optional<Restaurant> optionalRestaurant = Optional.ofNullable(leadRepository.findByRestaurantId(restaurantId));

        if (optionalRestaurant.isPresent()) {
            Restaurant restaurant = optionalRestaurant.get();
            restaurant.setLeadStatus(status);
            leadRepository.save(restaurant);
        }
        return optionalRestaurant;
    }

















}
