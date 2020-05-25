package ru.porodkin.everyonewillbethere.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

//@Data
@Entity
@Getter
@Setter
//@ToString
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "city")
    private Set<BusStation> busStations;
}
