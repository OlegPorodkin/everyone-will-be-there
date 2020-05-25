package ru.porodkin.everyonewillbethere.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Set;

//@Data
@Entity
@Getter
@Setter
//@ToString
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
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany
    private Set<Voyage> voyages;
}
