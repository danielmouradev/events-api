package com.events.api.events.controller;

import com.events.api.events.dto.ErrorMessage;
import com.events.api.events.dto.SubscriptionResponse;
import com.events.api.events.exception.SubscriptionConflictException;
import com.events.api.events.exception.EventNotFoundException;
import com.events.api.events.exception.UserIndicationNotFoundException;
import com.events.api.events.model.Subscription;
import com.events.api.events.model.User;
import com.events.api.events.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubscriptionController {

    @Autowired
    private SubscriptionService service;

    @PostMapping({"/subscription/{prettyName}", "/subscription/{prettyName}/{id}"})
    public ResponseEntity<?> createSubscription(@PathVariable String prettyName,
                                                @RequestBody User subscriber,
                                                @PathVariable (required = false)Integer id) {
        try {
            SubscriptionResponse res = service.createNewSubscription(prettyName, subscriber, id);
            if (res != null) {
                return ResponseEntity.ok(res);
            }
        } catch (EventNotFoundException ex) {
            return ResponseEntity.status(404).body(new ErrorMessage(ex.getMessage()));
        }
        catch (SubscriptionConflictException ex){
            return ResponseEntity.status(409).body(new ErrorMessage(ex.getMessage()));
        }
        catch (UserIndicationNotFoundException ex){
            return ResponseEntity.status(404).body(new ErrorMessage(ex.getMessage()));
        }
        return ResponseEntity.badRequest().build();
    }
}
