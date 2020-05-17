package ru.porodkin.everyonewillbethere.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

@Data
@Entity
public class Voyage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private BusStation pointOfDeparture;
    @ManyToOne
    private BusStation pointOfDestination;

    private String travelTimeDeparture;
    private String travelTimeDestination;

    private LocalTime travelTime;

    @ElementCollection(targetClass = DayOfWeek.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "day_of_week", joinColumns = @JoinColumn(name = "voyage_id"))
    @Enumerated(EnumType.STRING)
    private Set<DayOfWeek> dayOfDeparture;

    private Long price;
}
