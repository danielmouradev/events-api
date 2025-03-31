package com.events.api.events.service;

import com.events.api.events.dto.SubscriptionRankingByUser;
import com.events.api.events.dto.SubscriptionRankingItem;
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

import java.util.List;
import java.util.stream.IntStream;

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
        if (evt == null) {
            throw new EventNotFoundException("Evento: " + eventName + " não existe!");
        }

        //Usuário não existe e será cadastrado
        User userRec = userRepo.findByEmail(user.getEmail());
        if (userRec == null) {
            userRec = userRepo.save(user);
        }

        User indicador = null;
        if (id != null) {
            indicador = userRepo.findById(id).orElse(null);
            if (indicador == null) {
                throw new UserIndicationNotFoundException("Usuário " + id + " indicador não existe!");
            }
        }

        //Usuário já foi inscrito no evento
        Subscription tmpSub = subRepo.findByEventAndSubscriber(evt, userRec);
        if (tmpSub != null) {
            throw new SubscriptionConflictException("Já existe inscrição para o usuário " + userRec.getName() + " no evento " + evt.getTitle());
        }

        Subscription subs = new Subscription();
        subs.setEvent(evt);
        subs.setSubscriber(userRec);
        subs.setIndication(indicador);
        Subscription res = subRepo.save(subs);
        return new SubscriptionResponse(res.getSubscriptionNumber(), "http://codecraft.com/subscription/" + res.getEvent().getPrettyName() + "/" + res.getSubscriber().getUserId());
    }

    public List<SubscriptionRankingItem> getCompleteRanking(String prettyName) {
        Event evt = evtRepo.findByPrettyName(prettyName);
        if (evt == null) {
            throw new EventNotFoundException("Ranking do evento " + prettyName + " não existe!");

        }
        return subRepo.generateRanking(evt.getEventId());
    }

    public SubscriptionRankingByUser getRankingByUser(String prettyName, Integer id){
        List<SubscriptionRankingItem> ranking = getCompleteRanking(prettyName);
        SubscriptionRankingItem item = ranking.stream().
                filter(i -> i.id().equals(id)). findFirst().orElse(null);
        if (item == null){
            throw new UserIndicationNotFoundException("Não há inscrições com indicação do usuário " + id);
        }
        Integer posicao = IntStream.range(0, ranking.size())
                .filter(pos -> ranking.get(pos).id().equals(id))
                .findFirst().getAsInt();
        return new SubscriptionRankingByUser(item, posicao+1);
    }
}
