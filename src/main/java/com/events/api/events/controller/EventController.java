package com.events.api.events.controller;

/*Controller = Recebe e trata as requisições HTTP (GET, POST, PUT, DELETE)
* Chama o service para processar os dados
* retorna repostas para o cliente (Json, XML, HTML)*/

import com.events.api.events.model.Event;
import com.events.api.events.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController /*Indica que essa é uma classe Controller*/
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/events") //Endpoint para o POST
    public Event addNewEvent(@RequestBody Event newEvent){  //Indica que os dados da req. estão no corpo da mensagem. Converte o corpo da req. HTTP em objeto Java
        return eventService.addNewEvent(newEvent);
    }

    @GetMapping("/events") //Endpoint do GET com o evento
    public List<Event> getAllEvents(){
        return eventService.getAllEvents();
    }

    @GetMapping("/events{prettyname}") //Endpoint do GET com o pretty name
    //Path Variable = Permite pegar um valor específico da URL e passá-los como parâmetro dentro de um método
     public ResponseEntity<Event> getEventByPPrettyName(@PathVariable String prettyName){ //Pegando o evento pelo seu pretty name
        Event evt = eventService.getByPrettyName(prettyName);
        if (evt != null){
            return ResponseEntity.ok().body(evt); //Caso o evento seja true, retorna o evento
        }
        return ResponseEntity.notFound().build(); //Caso seja falso, retorna a mensagem 404
    }
    /*ReponseEntity = Resposta http, permitindo personalizar o status, cabeçalho e corpo da mensagem*/
}
