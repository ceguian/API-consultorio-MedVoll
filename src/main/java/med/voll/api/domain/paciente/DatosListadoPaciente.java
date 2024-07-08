package med.voll.api.domain.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.medico.Medico;

public record DatosListadoPaciente(Long id,
                                   String nombre,
                                   String email,
                                   String telefono) {
    public DatosListadoPaciente (Paciente paciente){
        this(paciente.getId(), paciente.getNombre(),paciente.getTelefono(), paciente.getEmail());
    }
}

