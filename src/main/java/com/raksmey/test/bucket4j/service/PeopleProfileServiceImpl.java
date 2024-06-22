package com.raksmey.test.bucket4j.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class PeopleProfileServiceImpl implements PeopleProfileService {
    @Override
    public List<Object> getPeopleProfile() {
        return List.of(
                Map.of(
                        "username", "Tommy Shelby",
                        "age", "38",
                        "favourite", "apple"
                ),
                Map.of(
                        "username", "Kelvin Badly",
                        "age", "39",
                        "favourite", "movie"
                ),
                Map.of(
                        "username", "David Sha",
                        "age", "29",
                        "favourite", "travelling"
                )
        );
    }
}
