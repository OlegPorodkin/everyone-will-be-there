package ru.porodkin.everyonewillbethere.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.porodkin.everyonewillbethere.entity.Voyage;

public interface VoyageRepository extends JpaRepository<Voyage, Long>, JpaSpecificationExecutor<Voyage> {
}
