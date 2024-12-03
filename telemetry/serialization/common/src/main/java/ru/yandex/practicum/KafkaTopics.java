package ru.yandex.practicum;

public class KafkaTopics {
    public static final String SENSORS = "telemetry.sensors.v1";
    public static final String HUBS = "telemetry.hubs.v1";
    public static final String SNAPSHOTS = "telemetry.snapshots.v1";

    private KafkaTopics() {
        throw new UnsupportedOperationException();
    }
}
