package collector.event.controller;

import collector.event.model.hub.HubEvent;
import collector.event.model.sensor.SensorEvent;
import collector.event.service.hub.HubService;
import collector.event.service.sensor.SensorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final SensorService sensorService;
    private final HubService hubService;

    @PostMapping("/sensors")
    public void collectSensorEvent(@Valid @RequestBody SensorEvent sensorEvent) {
        sensorService.sendEventToKafka(sensorEvent);
    }

    @PostMapping("/hubs")
    public void collectHubEvent(@Valid @RequestBody HubEvent hubEvent) {
        hubService.sendEventToKafka(hubEvent);
    }
}
