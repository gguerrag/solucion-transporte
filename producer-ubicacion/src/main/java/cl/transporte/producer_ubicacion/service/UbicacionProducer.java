package cl.transporte.producer_ubicacion.service;

import cl.transporte.producer_ubicacion.config.RabbitConfig;
import cl.transporte.producer_ubicacion.dto.UbicacionMsg;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UbicacionProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${app.routing-key}")
    private String routingKey;

    public UbicacionProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(UbicacionMsg msg) {
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, routingKey, msg);
    }
}
