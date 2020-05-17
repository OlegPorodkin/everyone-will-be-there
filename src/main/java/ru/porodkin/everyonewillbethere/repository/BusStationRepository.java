package ru.porodkin.everyonewillbethere.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.porodkin.everyonewillbethere.entity.BusStation;

public interface BusStationRepository extends JpaRepository<BusStation, Long> {
}
