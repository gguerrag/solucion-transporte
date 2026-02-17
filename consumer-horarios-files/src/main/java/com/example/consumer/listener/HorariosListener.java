package com.example.consumer.listener;

import com.example.consumer.dto.HorarioMsg;
import com.example.consumer.service.HorarioFileWriterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class HorariosListener {

    private final HorarioFileWriterService writer;
    private final ObjectMapper mapper = new ObjectMapper();

    public HorariosListener(HorarioFileWriterService writer) {
        this.writer = writer;
    }

    @RabbitListener(queues = "${app.queues.horarios}")
    public void recibir(String payload) throws Exception {
        HorarioMsg msg = mapper.readValue(payload, HorarioMsg.class);
        var path = writer.write(msg);
        System.out.println("[HOR] Archivo generado: " + path.toAbsolutePath());
    }
}
