package com.events.api.events.repository;

/*Repository = Responsável por acessar e manipular os dados no BD
* Contém métodos para buscar, salvar, atualizar e deletar dados do banco
* estende (extends) métodos para o CRUD*/

import com.events.api.events.model.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepo extends CrudRepository<Event, Integer> {

    public Event findByPrettyName(String prettyName);
}
