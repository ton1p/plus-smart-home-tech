package ru.yandex.practicum.event.model.sensor.handler;

import ru.yandex.practicum.event.kafka.KafkaClient;
import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.KafkaTopics;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.ClimateSensorAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class ClimateSensorEventHandler implements SensorEventHandler {
    private final KafkaClient kafkaClient;

    @Override
    public SensorEventProto.PayloadCase getPayloadCase() {
        return SensorEventProto.PayloadCase.CLIMATE;
    }

    @Override
    public void handle(SensorEventProto event) {
        SensorEventAvro sensorEventAvro = SensorEventAvro.newBuilder()
                .setHubId(event.getHubId())
                .setId(event.getId())
                .setTimestamp(Instant.ofEpochSecond(event.getTimestamp().getSeconds()))
                .setPayload(new ClimateSensorAvro(
                        event.getClimate().getTemperatureC(),
                        event.getClimate().getHumidity(),
                        event.getClimate().getCo2Level()
                ))
                .build();

        ProducerRecord<String, SpecificRecordBase> producerRecord = new ProducerRecord<>(KafkaTopics.SENSORS, sensorEventAvro.getHubId(), sensorEventAvro);
        kafkaClient.getProducer().send(producerRecord);
    }
}
