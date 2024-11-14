package collector.event.service.hub;

import collector.event.kafka.KafkaClient;
import collector.event.kafka.KafkaTopics;
import collector.event.model.hub.DeviceAddedEvent;
import collector.event.model.hub.DeviceRemovedEvent;
import collector.event.model.hub.HubEvent;
import collector.event.model.hub.ScenarioAddedEvent;
import collector.event.model.hub.ScenarioRemovedEvent;
import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.kafka.telemetry.collector.event.DeviceAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.collector.event.DeviceRemovedEventAvro;
import ru.yandex.practicum.kafka.telemetry.collector.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.collector.event.ScenarioAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.collector.event.ScenarioRemovedEventAvro;

@Service
@RequiredArgsConstructor
public class HubServiceImpl implements HubService {
    private final KafkaClient kafkaClient;

    @Override
    public void sendEventToKafka(HubEvent hubEvent) {
        Object payload;
        switch (hubEvent.getType()) {
            case DEVICE_ADDED -> {
                DeviceAddedEvent deviceAddedEvent = (DeviceAddedEvent) hubEvent;
                payload = new DeviceAddedEventAvro(deviceAddedEvent.getId(), deviceAddedEvent.getDeviceType());
            }
            case DEVICE_REMOVED -> {
                DeviceRemovedEvent deviceRemovedEvent = (DeviceRemovedEvent) hubEvent;
                payload = new DeviceRemovedEventAvro(deviceRemovedEvent.getId());
            }
            case SCENARIO_ADDED -> {
                ScenarioAddedEvent scenarioAddedEvent = (ScenarioAddedEvent) hubEvent;
                payload = new ScenarioAddedEventAvro(scenarioAddedEvent.getName(), scenarioAddedEvent.getConditions(), scenarioAddedEvent.getActions());
            }
            case SCENARIO_REMOVED -> {
                ScenarioRemovedEvent scenarioRemovedEvent = (ScenarioRemovedEvent) hubEvent;
                payload = new ScenarioRemovedEventAvro(scenarioRemovedEvent.getName());
            }
            case null, default -> throw new IllegalStateException("Unexpected value: " + hubEvent.getType());
        }
        HubEventAvro hubEventAvro = new HubEventAvro(hubEvent.getHubId(), hubEvent.getTimestamp(), payload);
        ProducerRecord<String, SpecificRecordBase> producerRecord = new ProducerRecord<>(
                KafkaTopics.HUBS,
                hubEvent.getHubId(),
                hubEventAvro
        );
        kafkaClient.getProducer().send(producerRecord);
    }
}
