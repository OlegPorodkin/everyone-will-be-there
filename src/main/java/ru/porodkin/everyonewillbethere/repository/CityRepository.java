package ru.porodkin.everyonewillbethere.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.porodkin.everyonewillbethere.entity.City;

public interface CityRepository extends JpaRepository<City, Long>, JpaSpecificationExecutor<City> {
}
