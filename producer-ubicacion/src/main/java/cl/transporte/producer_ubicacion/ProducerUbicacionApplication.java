package cl.transporte.producer_ubicacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ProducerUbicacionApplication {
  public static void main(String[] args) {
    SpringApplication.run(ProducerUbicacionApplication.class, args);
  }
}