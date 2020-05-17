package ru.porodkin.everyonewillbethere.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.porodkin.everyonewillbethere.entity.BusStation;
import ru.porodkin.everyonewillbethere.entity.City;
import ru.porodkin.everyonewillbethere.entity.Voyage;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.ZoneId;

@Component
public class VoyageSpecification {

    public Specification<Voyage> byFromDest(String fromDest) {
        return (root, query, cb) -> {
            query.distinct(true);

            Join<Voyage, BusStation> busStationJoin = root.join("pointOfDeparture");

            Join<BusStation, City> cityJoin = busStationJoin.join("city");

            Predicate likeName = cb.like(cityJoin.get("name"), "%"+fromDest+"%");

            return cb.and(likeName);
        };
    }

    public Specification<Voyage> byToDest(String toDest) {
        return (root, query, cb) -> {
            query.distinct(true);

            Join<Voyage, BusStation> busStationJoin = root.join("pointOfDestination");

            Join<BusStation, City> cityJoin = busStationJoin.join("city");

            Predicate likeName = cb.like(cityJoin.get("name"), "%"+toDest+"%");

            return cb.and(likeName);
        };
    }

    public Specification<Voyage> byDate(String date) {
        LocalDate d = LocalDate.parse(date);
        return (root, query, cb) -> {
            return cb.and(cb.greaterThanOrEqualTo(
                    root.get("orderDate"), d.atStartOfDay(ZoneId.systemDefault())
            ));
        };
    }
}
