package ru.porodkin.everyonewillbethere.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Set;

@Data
@Entity
public class BusStation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String address;
    private String phone;
    private LocalTime startTime;
    private LocalTime endTime;
    @ManyToOne
    private City city;

    @OneToMany
    private Set<Voyage> voyages;
}
