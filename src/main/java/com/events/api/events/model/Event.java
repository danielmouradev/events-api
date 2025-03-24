package com.events.api.events.model;

/*Model = Classes que geralmente estão associadas as a tabela no banco de dados
* Define os dados que a aplicação lida
* Usa anotações (@Id, GeneratedValue, @Table, @Column, @Entity
* representa os objetos que serão armazenados*/

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity /*Uma entidade do JPA para acesso ao BD*/
@Table(name = "tbl_event") /*Nome da tabela*/
public class Event {

    @Id /*Recebe um valor id*/
    @GeneratedValue(strategy =  GenerationType.IDENTITY) /*Gera um valor de auto incrementação*/
    @Column(name = "event_id")
    private Integer eventId;

    @Column(name = "title", length = 255, nullable = false) /*Nome da coluna, tamanho e se pode haver valor nulo*/
    private String title;

    @Column(name = "pretty_name", length = 255, nullable = false, unique = true) //unique: se o valor deve ser único no banco
    private String prettyName;

    @Column(name = "location", length = 255, nullable = false)
    private String location;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrettyName() {
        return prettyName;
    }

    public void setPrettyName(String prettyName) {
        this.prettyName = prettyName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
