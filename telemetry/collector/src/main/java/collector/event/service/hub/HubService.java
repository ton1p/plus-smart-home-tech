package collector.event.service.hub;

import collector.event.model.hub.HubEvent;

public interface HubService {
    void sendEventToKafka(HubEvent hubEvent);
}
