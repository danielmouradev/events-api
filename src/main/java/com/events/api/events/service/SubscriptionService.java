package com.events.api.events.service;

import com.events.api.events.dto.SubscriptionResponse;
import com.events.api.events.exception.SubscriptionConflictException;
import com.events.api.events.exception.EventNotFoundException;
import com.events.api.events.exception.UserIndicationNotFoundException;
import com.events.api.events.model.Event;
import com.events.api.events.model.Subscription;
import com.events.api.events.model.User;
import com.events.api.events.repository.EventRepo;
import com.events.api.events.repository.SubscriptionRepo;
import com.events.api.events.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

    @Autowired
    private EventRepo evtRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SubscriptionRepo subRepo;

    public SubscriptionResponse createNewSubscription(String eventName, User user, Integer id) {

        //recuperar evento pelo nome
        Event evt = evtRepo.findByPrettyName(eventName);
        //Tentativa de inscrever um usuário em um evento nulo
        if (evt == null){
            throw new EventNotFoundException("Evento: " + eventName + " não existe!");
        }

        //Usuário não existe e será cadastrado
        User userRec = userRepo.findByEmail(user.getEmail());
        if (userRec == null){
            userRec = userRepo.save(user);
        }

        User indicador = userRepo.findById(id).orElse(null);
        if (indicador == null){
            throw new UserIndicationNotFoundException("Usuário " + id + " indicador não existe!");
        }

        //Usuário já foi inscrito no evento
        Subscription tmpSub = subRepo.findByEventAndSubscriber(evt, userRec);
        if (tmpSub != null){
            throw new SubscriptionConflictException("Já existe inscrição para o usuário " + userRec.getName() + " no evento " + evt.getTitle());
        }

        Subscription subs = new Subscription();
        subs.setEvent(evt);
        subs.setSubscriber(userRec);
        subs.setIndication(indicador);
        Subscription res = subRepo.save(subs);
        return new SubscriptionResponse(res.getSubscriptionNumber(), "http://codecraft.com/subscription/" + res.getEvent().getPrettyName()+"/"+ res.getSubscriber().getUserId());
    }
}
