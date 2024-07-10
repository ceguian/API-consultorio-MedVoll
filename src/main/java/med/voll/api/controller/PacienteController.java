package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    //@RequestBody: indica a la BD que la información enviada es el cuerpo
    //@Valid: valida la información enviada
    public ResponseEntity<DatosRespuestaPaciente> registrarMetodo(@RequestBody @Valid DatosRegistroPaciente datosRegistroPaciente,
                                                                  UriComponentsBuilder uriComponentsBuilder) {
        Paciente paciente = pacienteRepository.save(new Paciente(datosRegistroPaciente));
        DatosRespuestaPaciente datosRespuestaPaciente = new DatosRespuestaPaciente(
                paciente.getId(),
                paciente.getNombre(),
                paciente.getEmail(),
                paciente.getTelefono(),
                new DatosDireccion(paciente.getDireccion().getCalle(),
                        paciente.getDireccion().getDistrito(),
                        paciente.getDireccion().getCiudad(),
                        paciente.getDireccion().getNumero(),
                        paciente.getDireccion().getComplemento()));
        URI url = uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaPaciente);
        //Return 201 created
        //URL donde encontrar al médico
    }

    @GetMapping
    //Page nos permite realizar consultas de los datos por medio de páginas
    //PageableDefault nos permite cambiar las propiedades default de un Pageable
    public ResponseEntity<Page<DatosListadoPaciente>> pacienteList(@PageableDefault(size = 10, sort = "nombre") Pageable paginacion) {
        return ResponseEntity.ok(pacienteRepository.findByActivoTrue(paginacion).map(DatosListadoPaciente::new));
    }

    @PutMapping
    //@Transactional: es un metadato que especifica que una interfaz, clase o método debe tener semántica transaccional.
    //asegura que la información enviada se vea reflajada en la BD
    @Transactional
    //ResponseEntity: envia una respuesta clara de la peticion enviada, siempre debe retornar un ResponseEntity
    public ResponseEntity actualizarPaciente(@RequestBody @Valid DatosActualizarPaciente datosActualizarPaciente) {
        Paciente paciente = pacienteRepository.getReferenceById(datosActualizarPaciente.id());
        paciente.actualizarDatos(datosActualizarPaciente);
        return ResponseEntity.ok(new DatosRespuestaPaciente(paciente.getId(),
                paciente.getNombre(),
                paciente.getEmail(),
                paciente.getTelefono(),
                new DatosDireccion(paciente.getDireccion().getCalle(),
                        paciente.getDireccion().getDistrito(),
                        paciente.getDireccion().getCiudad(),
                        paciente.getDireccion().getNumero(),
                        paciente.getDireccion().getComplemento())));
    }

    //DELETE LOGICO
    //mapeamiento por URL siendo el id dinamico añadiendo {nombre_atributo}
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity borrarPaciente(@PathVariable Long id) {
        Paciente paciente = pacienteRepository.getReferenceById(id);
        paciente.desactivarPaciente();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaPaciente> retornoDatosPaciente(@PathVariable Long id) {
        Paciente paciente = pacienteRepository.getReferenceById(id);
        var datosPaciente = new DatosRespuestaPaciente(paciente.getId(),
                paciente.getNombre(),
                paciente.getEmail(),
                paciente.getTelefono(),
                        new DatosDireccion(paciente.getDireccion().getCalle(),
                        paciente.getDireccion().getDistrito(),
                        paciente.getDireccion().getCiudad(),
                        paciente.getDireccion().getNumero(),
                        paciente.getDireccion().getComplemento()));
        return ResponseEntity.ok(datosPaciente);
    }
}


