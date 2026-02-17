package cl.transporte.producer_horario.service;

import cl.transporte.producer_horario.config.RabbitConfig;
import cl.transporte.producer_horario.dto.HorarioMsg;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HorarioProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${app.routing-key}")
    private String routingKey;

    public HorarioProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(HorarioMsg msg) {
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, routingKey, msg);
    }
}
