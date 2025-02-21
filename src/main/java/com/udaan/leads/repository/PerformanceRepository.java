package com.udaan.leads.repository;

import com.udaan.leads.dto.AllRestaurantPerformances;
import com.udaan.leads.dto.SpecificRestaurantPerformance;
import com.udaan.leads.entity.order.Order;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface PerformanceRepository extends MongoRepository<Order, String> {

    //Query : Individual restaurant performance over a period of time
    @Aggregation(
            pipeline = {
                    "{ $match: { 'timeStamp': { $gte: ?1, $lt: ?2 } } }",
                    "{ $match: { 'restaurantId': ?0 } }",
                    "{ $group: { _id: { restaurantId: '$restaurantId', restaurantName: '$restaurantName', currency: '$currency' }, "
                            + "totalOrders: { $sum: 1 }, "
                            + "successOrders: { $sum: { $cond: [ { $eq: ['$orderStatus', 'SUCCESS'] }, 1, 0 ] } }, "
                            + "failedOrders: { $sum: { $cond: [ { $eq: ['$orderStatus', 'FAILED'] }, 1, 0 ] } }, "
                            + "successfulOrdersWorth: { $sum: { $cond: [ { $eq: ['$orderStatus', 'SUCCESS'] }, "
                            + "{ $reduce: { input: '$items', initialValue: 0, in: { $add: ['$$value', '$$this.cost'] } } }, 0 ] } } } }",
                    "{ $project: { _id: 0, start: { $literal: ?1 }, end: { $literal: ?2 }, "
                            + "restaurantId: '$_id.restaurantId', "
                            + "restaurantName: '$_id.restaurantName', "
                            + "totalOrders: 1, successOrders: 1, failedOrders: 1, "
                            + "successfulOrdersWorth: 1, currency: '$_id.currency' } }"
            })
    SpecificRestaurantPerformance restaurantPerformanceById(String id, Instant start, Instant end);


    //Query : All restaurants in decreasing order of n(successful orders) over a period of time
    @Aggregation(pipeline = {
            "{ $match: { timeStamp: { $gte: ?0, $lt: ?1 } } }",
            "{ $group: { " +
                    "_id: { restaurantId: '$restaurantId', restaurantName: '$restaurantName', currency: '$currency' }, " +
                    "totalOrders: { $sum: 1 }, " +
                    "successOrders: { $sum: { $cond: [ { $eq: ['$orderStatus', 'SUCCESS'] }, 1, 0 ] } }, " +
                    "failedOrders: { $sum: { $cond: [ { $eq: ['$orderStatus', 'FAILED'] }, 1, 0 ] } }, " +
                    "successfulOrdersWorth: { $sum: { $cond: [ { $eq: ['$orderStatus', 'SUCCESS'] }, " +
                    "{ $reduce: { input: { $ifNull: ['$items', []] }, initialValue: 0, in: { $add: ['$$value', '$$this.cost'] } } }, 0 ] } } " +
                    "} }",
            "{ $sort: { successOrders: -1 } }",
            "{ $group: { " +
                    "_id: null, " +
                    "start: { $min: ?0 }, " +
                    "end: { $max: ?1 }, " +
                    "totalOrders: { $sum: '$totalOrders' }, " +
                    "successfulOrders: { $sum: '$successOrders' }, " +
                    "failedOrders: { $sum: '$failedOrders' }, " +
                    "restaurants: { $push: { " +
                    "restaurantId: '$_id.restaurantId', " +
                    "restaurantName: '$_id.restaurantName', " +
                    "totalOrders: '$totalOrders', " +
                    "successOrders: '$successOrders', " +
                    "failedOrders: '$failedOrders', " +
                    "successfulOrdersWorth: '$successfulOrdersWorth', " +
                    "currency: '$_id.currency' " +
                    "} } " +
                    "} }",
            "{ $project: { _id: 0, start: 1, end: 1, totalOrders: 1, successfulOrders: 1, failedOrders: 1, restaurants: 1 } }"
    })
    AllRestaurantPerformances allRestaurantPerformances(Instant start, Instant end);


}
