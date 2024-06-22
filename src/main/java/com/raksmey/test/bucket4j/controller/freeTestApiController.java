package com.raksmey.test.bucket4j.controller;


import com.raksmey.test.bucket4j.service.PeopleProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/free")
public class freeTestApiController {

    @Autowired
    private PeopleProfileService profileService;

    @GetMapping("/people/profile")
    public ResponseEntity<?> getPeopleProfile() {
        return ResponseEntity.ok().body(profileService.getPeopleProfile());
    }
}
