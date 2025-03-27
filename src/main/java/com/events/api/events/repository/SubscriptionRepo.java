package com.events.api.events.repository;

import com.events.api.events.model.Event;
import com.events.api.events.model.Subscription;
import com.events.api.events.model.User;
import org.springframework.data.repository.CrudRepository;

public interface SubscriptionRepo extends CrudRepository<Subscription, Integer> {

    public Subscription findByEventAndSubscriber(Event evt, User user);
}
