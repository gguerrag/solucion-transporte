package cl.duoc.procesadorhorarios.controller;

import cl.duoc.procesadorhorarios.model.HorarioMsg;
import cl.duoc.procesadorhorarios.service.ProcesadorListener;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProcesadorController {

  private final ProcesadorListener listener;

  public ProcesadorController(ProcesadorListener listener) {
    this.listener = listener;
  }

  @GetMapping("/procesador/ultimo")
  public HorarioMsg ultimo() {
    return listener.getUltimo();
  }

  @PostMapping("/procesador/test")
  public String test() { return "OK"; }

  @PutMapping("/procesador/reglas")
  public String reglas() { return "OK"; }

  @DeleteMapping("/procesador/cache")
  public String cache() { return "OK"; }
}