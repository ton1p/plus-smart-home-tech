package collector.event.service.sensor;

import collector.event.model.sensor.SensorEvent;

public interface SensorService {
    void sendEventToKafka(SensorEvent sensorEvent);
}
