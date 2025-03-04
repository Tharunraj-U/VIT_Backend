package com.tharun.lamma.Pitch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/map")
public class MapController {

    @Autowired
    private PathDataService pathDataService;

    @PostMapping("/track-hit")
    public String trackPotholeHit(@RequestParam Long userId,
                                  @RequestParam double latitude,
                                  @RequestParam double longitude) {
        pathDataService.registerPotholeHit(userId, latitude, longitude);
        return "Pothole hit recorded!";
    }

    @GetMapping()
    public List<PathData> getPotholeLocations() {
        return pathDataService.getAllPotholeHits();
    }
}
