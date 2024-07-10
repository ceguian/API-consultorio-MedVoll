package med.voll.api.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "bearer-key")
@RestController
@ResponseBody
@RequestMapping("/consultas")
public class ConsultaController {


    @Autowired
    private AgendaDeConsultaService agendaDeConsultaService;

    @PostMapping
    @Transactional
    public ResponseEntity agendar (@RequestBody @Valid DatosAgendarConsulta datosAgendarConsulta){

        var response= agendaDeConsultaService.agendar(datosAgendarConsulta);
        return  ResponseEntity.ok(response);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity borrar (@RequestBody @Valid DatosBorrarConsulta datosBorrarConsulta){

        var response= agendaDeConsultaService.borrar(datosBorrarConsulta);
        return  ResponseEntity.ok("Consulta eliminada" + response.toString());
    }
}
