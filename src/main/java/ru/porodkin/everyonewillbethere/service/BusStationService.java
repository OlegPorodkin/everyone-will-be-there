package ru.porodkin.everyonewillbethere.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import ru.porodkin.everyonewillbethere.entity.BusStation;
import ru.porodkin.everyonewillbethere.entity.Voyage;
import ru.porodkin.everyonewillbethere.repository.BusStationRepository;
import ru.porodkin.everyonewillbethere.repository.CityRepository;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class BusStationService {

    private final BusStationRepository busStationRepository;
    private final CityRepository cityRepository;

    public BusStationService(BusStationRepository busStationRepository, CityRepository cityRepository) {
        this.busStationRepository = busStationRepository;
        this.cityRepository = cityRepository;
    }


    public void saveEntity(String station) {
        BusStation busStation = getBusStation(station);

        busStationRepository.save(busStation);
    }

    public void updateEntity(BusStation stationFromDb, String station) {
        BusStation busStation = getBusStation(station);
        BeanUtils.copyProperties(busStation, stationFromDb, "id");

        busStationRepository.save(stationFromDb);
    }

    public void deleteEntity(Long station) {
        try {
            BusStation one = busStationRepository.getOne(station);
            busStationRepository.delete(one);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BusStation getBusStation(String station) {
        String[] split = station.split("\n");
        Map<String, String> values = new HashMap<>();

        BusStation busStation = new BusStation();

        Arrays.stream(split).map(s -> s.split("=")).forEach(keyVal -> values.put(keyVal[0], keyVal[1]));

        initBusStationDeparture(values, busStation);

        busStation.setCity(cityRepository.getOne(Long.valueOf(strip(values, "city"))));
        return busStation;
    }

    private void initBusStationDeparture(Map<String, String> values, BusStation pointOfDeparture) {
        pointOfDeparture.setName(values.get("name"));
        pointOfDeparture.setAddress(values.get("address"));
        pointOfDeparture.setPhone(values.get("phone"));
        pointOfDeparture.setStartTime(LocalTime.parse(strip(values, "startTime")));
        pointOfDeparture.setEndTime(LocalTime.parse(strip(values, "endTime")));
    }

    private String strip(Map<String, String> values, String key) {
        return values.get(key).replace("\r", "");
    }
}
