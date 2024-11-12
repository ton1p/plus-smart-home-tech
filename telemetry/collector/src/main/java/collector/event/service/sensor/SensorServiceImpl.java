package collector.event.service.sensor;

import collector.event.kafka.KafkaClient;
import collector.event.kafka.KafkaTopics;
import collector.event.model.sensor.ClimateSensorEvent;
import collector.event.model.sensor.LightSensorEvent;
import collector.event.model.sensor.MotionSensorEvent;
import collector.event.model.sensor.SensorEvent;
import collector.event.model.sensor.SwitchSensorEvent;
import collector.event.model.sensor.TemperatureSensorEvent;
import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.kafka.telemetry.collector.event.ClimateSensorAvro;
import ru.yandex.practicum.kafka.telemetry.collector.event.LightSensorAvro;
import ru.yandex.practicum.kafka.telemetry.collector.event.MotionSensorAvro;
import ru.yandex.practicum.kafka.telemetry.collector.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.collector.event.SwitchSensorAvro;
import ru.yandex.practicum.kafka.telemetry.collector.event.TemperatureSensorAvro;

@Service
@RequiredArgsConstructor
public class SensorServiceImpl implements SensorService {
    private final KafkaClient kafkaClient;

    @Override
    public void sendEventToKafka(SensorEvent sensorEvent) {
        Object payload;
        switch (sensorEvent.getType()) {
            case LIGHT_SENSOR_EVENT -> {
                LightSensorEvent lightSensorEvent = (LightSensorEvent) sensorEvent;
                payload = new LightSensorAvro(lightSensorEvent.getLinkQuality(), lightSensorEvent.getLuminosity());
            }
            case MOTION_SENSOR_EVENT -> {
                MotionSensorEvent motionSensorEvent = (MotionSensorEvent) sensorEvent;
                payload = new MotionSensorAvro(
                        motionSensorEvent.getLinkQuality(),
                        motionSensorEvent.isMotion(),
                        motionSensorEvent.getVoltage()
                );
            }
            case CLIMATE_SENSOR_EVENT -> {
                ClimateSensorEvent climateSensorEvent = (ClimateSensorEvent) sensorEvent;
                payload = new ClimateSensorAvro(
                        climateSensorEvent.getTemperatureC(),
                        climateSensorEvent.getHumidity(),
                        climateSensorEvent.getCo2Level()
                );
            }
            case SWITCH_SENSOR_EVENT -> {
                SwitchSensorEvent switchSensorEvent = (SwitchSensorEvent) sensorEvent;
                payload = new SwitchSensorAvro(switchSensorEvent.isState());
            }
            case TEMPERATURE_SENSOR_EVENT -> {
                TemperatureSensorEvent temperatureSensorEvent = (TemperatureSensorEvent) sensorEvent;
                payload = new TemperatureSensorAvro(
                        temperatureSensorEvent.getTemperatureC(),
                        temperatureSensorEvent.getTemperatureF()
                );
            }
            case null, default -> throw new IllegalStateException("Unexpected value: " + sensorEvent.getType());
        }
        SensorEventAvro sensorEventAvro = new SensorEventAvro(
                sensorEvent.getId(),
                sensorEvent.getHubId(),
                sensorEvent.getTimestamp(),
                payload
        );
        ProducerRecord<String, SpecificRecordBase> producerRecord = new ProducerRecord<>(
                KafkaTopics.SENSORS,
                sensorEvent.getHubId(),
                sensorEventAvro
        );
        kafkaClient.getProducer().send(producerRecord);
    }
}
