package ru.porodkin.everyonewillbethere.service;

import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.porodkin.everyonewillbethere.entity.BusStation;
import ru.porodkin.everyonewillbethere.entity.City;
import ru.porodkin.everyonewillbethere.repository.CityRepository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class CityService {
    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }


    public boolean saveEntity(String value) {
        String[] split = value.split("\n");
        Map<String, String> values = new HashMap<>();

        City city = new City();

        Arrays.stream(split).map(s -> s.split("=")).forEach(keyVal -> values.put(keyVal[0], keyVal[1]));

        String cityName = strip(values, "cityName");

        city.setName(cityName);

        boolean namePresent = cityRepository.findOne(Specification.where((root, query, cb) -> {
            return cb.and(cb.like(root.get("name"), cityName));
        })).isPresent();

        if (namePresent){
            return true;
        }else {
            cityRepository.save(city);
        }

        return namePresent;
    }

    private String strip(Map<String, String> values, String key) {
        return values.get(key).replace("\r", "");
    }

    public void deleteEntity(Long city) {
        try {
            City one = cityRepository.getOne(city);
            cityRepository.delete(one);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateEntity(City cityFromDb, String city) {
        String[] split = city.split("\n");
        Map<String, String> values = new HashMap<>();

        City c = new City();

        Arrays.stream(split).map(s -> s.split("=")).forEach(keyVal -> values.put(keyVal[0], keyVal[1]));

        String cityName = strip(values, "cityName");

        c.setName(cityName);

        BeanUtils.copyProperties(c, cityFromDb, "id");

        cityRepository.save(cityFromDb);
    }
}
