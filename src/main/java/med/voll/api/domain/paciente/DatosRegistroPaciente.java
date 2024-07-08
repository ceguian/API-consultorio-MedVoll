package med.voll.api.domain.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.direccion.DatosDireccion;


public record DatosRegistroPaciente(@NotBlank
                                    String nombre,
                                    @NotBlank
                                    String email,
                                    @NotNull
                                    @Valid
                                    DatosDireccion direccion,
                                    @NotBlank
                                    String telefono) {
}
