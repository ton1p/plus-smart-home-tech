package ru.yandex.practicum.service.handler.snapshot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.ClimateSensorAvro;
import ru.yandex.practicum.kafka.telemetry.event.ConditionOperationAvro;
import ru.yandex.practicum.model.ScenarioAction;
import ru.yandex.practicum.model.ScenarioCondition;
import ru.yandex.practicum.repository.ScenarioActionRepository;
import ru.yandex.practicum.repository.ScenarioConditionRepository;
import ru.yandex.practicum.service.AnalyzerGrpcService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ClimateSensorHandler extends BaseSensorHandler implements SnapshotStateHandler<ClimateSensorAvro> {
    private final ScenarioConditionRepository scenarioConditionRepository;
    private final AnalyzerGrpcService analyzerGrpcService;
    private final ScenarioActionRepository scenarioActionRepository;

    @Override
    public String getType() {
        return ClimateSensorAvro.class.getSimpleName();
    }

    @Override
    public void handle(String hubId, String sensorId, ClimateSensorAvro payload) {
        List<ScenarioCondition> scenarioConditions = scenarioConditionRepository.findAllBySensorIdAndSensorHubId(sensorId, hubId);

        if (scenarioConditions.isEmpty()) {
            return;
        }

        for (ScenarioCondition scenarioCondition : scenarioConditions) {
            ConditionOperationAvro operation = scenarioCondition.getCondition().getOperation();
            String scenarioName = scenarioCondition.getScenario().getName();
            List<ScenarioAction> scenarioActions = scenarioActionRepository.findAllByScenarioId(scenarioCondition.getScenario().getId());

            if (scenarioActions.isEmpty()) {
                return;
            }
            
            switch (operation) {
                case EQUALS: {
                    switch (scenarioCondition.getCondition().getType()) {
                        case TEMPERATURE: {
                            if (scenarioCondition.getCondition().getValue() == payload.getTemperatureC()) {
                                executeScenarioActions(hubId, scenarioActions, scenarioName, analyzerGrpcService);
                            }
                            break;
                        }
                        case HUMIDITY: {
                            if (scenarioCondition.getCondition().getValue() == payload.getHumidity()) {
                                executeScenarioActions(hubId, scenarioActions, scenarioName, analyzerGrpcService);
                            }
                            break;
                        }
                        case CO2LEVEL: {
                            if (scenarioCondition.getCondition().getValue() == payload.getCo2Level()) {
                                executeScenarioActions(hubId, scenarioActions, scenarioName, analyzerGrpcService);
                            }
                            break;
                        }
                        case null, default:
                            throw new IllegalStateException("Unexpected value: " + operation);
                    }

                    break;
                }
                case GREATER_THAN: {
                    switch (scenarioCondition.getCondition().getType()) {
                        case TEMPERATURE: {
                            if (scenarioCondition.getCondition().getValue() > payload.getTemperatureC()) {
                                executeScenarioActions(hubId, scenarioActions, scenarioName, analyzerGrpcService);
                            }
                            break;
                        }
                        case HUMIDITY: {
                            if (scenarioCondition.getCondition().getValue() > payload.getHumidity()) {
                                executeScenarioActions(hubId, scenarioActions, scenarioName, analyzerGrpcService);
                            }
                            break;
                        }
                        case CO2LEVEL: {
                            if (scenarioCondition.getCondition().getValue() > payload.getCo2Level()) {
                                executeScenarioActions(hubId, scenarioActions, scenarioName, analyzerGrpcService);
                            }
                            break;
                        }
                        case null, default:
                            throw new IllegalStateException("Unexpected value: " + operation);
                    }
                    break;
                }
                case LOWER_THAN: {
                    switch (scenarioCondition.getCondition().getType()) {
                        case TEMPERATURE: {
                            if (scenarioCondition.getCondition().getValue() < payload.getTemperatureC()) {
                                executeScenarioActions(hubId, scenarioActions, scenarioName, analyzerGrpcService);
                            }
                            break;
                        }
                        case HUMIDITY: {
                            if (scenarioCondition.getCondition().getValue() < payload.getHumidity()) {
                                executeScenarioActions(hubId, scenarioActions, scenarioName, analyzerGrpcService);
                            }
                            break;
                        }
                        case CO2LEVEL: {
                            if (scenarioCondition.getCondition().getValue() < payload.getCo2Level()) {
                                executeScenarioActions(hubId, scenarioActions, scenarioName, analyzerGrpcService);
                            }
                            break;
                        }
                        case null, default:
                            throw new IllegalStateException("Unexpected value: " + operation);
                    }

                    break;
                }
                case null, default:
                    throw new IllegalStateException("Unexpected value: " + operation);
            }
        }
    }

}
