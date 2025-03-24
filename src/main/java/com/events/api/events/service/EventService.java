package com.events.api.events.service;

/*Service = Contém a regra de negócios da aplicação (cálculos, validação e etc)
* chama o repository para acesso ao BD*/

import com.events.api.events.model.Event;
import com.events.api.events.repository.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service //Indica que essa é uma classe de serviço
public class EventService {

    @Autowired //Cria uma injeção automática de dependências
    private EventRepo eventRepo;


    //Método para a adição de um novo evento
    public Event addNewEvent (Event event){
        //Criando um pretty name através do título do evento
        event.setPrettyName(event.getTitle().toLowerCase().replaceAll(" ", "-"));
        return eventRepo.save(event);
    }


    //Método para listar todos os eventos
    public List<Event> getAllEvents(){
        return (List<Event>) eventRepo.findAll();
    }

    //Método para trazer um evento pelo seu pretty name
    public Event getByPrettyName(String prettyName){
        return eventRepo.findByPrettyName(prettyName);
    }
}
