package ru.porodkin.everyonewillbethere.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import ru.porodkin.everyonewillbethere.entity.Voyage;
import ru.porodkin.everyonewillbethere.repository.BusStationRepository;
import ru.porodkin.everyonewillbethere.repository.VoyageRepository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

@Service
public class VoyageService {
    private final VoyageRepository voyageRepository;
    private final BusStationRepository busStationRepository;

    public VoyageService(VoyageRepository voyageRepository, BusStationRepository busStationRepository) {
        this.voyageRepository = voyageRepository;
        this.busStationRepository = busStationRepository;
    }


    public void saveEntity(String voyage) {
        Voyage voy = getVoyage(voyage);

        voyageRepository.save(voy);
    }

    public void deleteEntity(Long voyage) {
        try {
            Voyage one = voyageRepository.getOne(voyage);
            voyageRepository.delete(one);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateEntity(Voyage voyageFromDb, String voyage) {
        Voyage voy = getVoyage(voyage);
        BeanUtils.copyProperties(voy, voyageFromDb, "id");

        voyageRepository.save(voyageFromDb);
    }

    private Voyage getVoyage(String voyage) {
        String[] split = voyage.split("\n");
        Map<String, String> values = new HashMap<>();

        Voyage voy = new Voyage();
        Set<DayOfWeek> dayOfWeeks = new HashSet<>();

        Arrays.stream(split).map(s -> s.split("=")).forEach(keyVal -> {
            if (keyVal[0].equals("daysToDeparts")) {
                String s = keyVal[1];
                String replace = s.replace("\r", "");
                dayOfWeeks.add(DayOfWeek.of(Integer.parseInt(replace)));
            } else {
                values.put(keyVal[0], keyVal[1]);
            }
        });

        voy.setPrice(Long.valueOf(strip(values, "price")));
        voy.setTravelTime(LocalTime.parse(strip(values, "travelTime")));
        voy.setDayOfDeparture(dayOfWeeks);
        voy.setTravelTimeDeparture(strip(values, "travelTimeDeparture"));
        voy.setTravelTimeDestination(strip(values, "travelTimeDestination"));
        voy.setPointOfDeparture(busStationRepository.getOne(Long.valueOf(strip(values, "pointOfDeparture"))));
        voy.setPointOfDestination(busStationRepository.getOne(Long.valueOf(strip(values, "pointOfDestination"))));
        return voy;
    }

    private String strip(Map<String, String> values, String key) {
        return values.get(key).replace("\r", "");
    }
}
