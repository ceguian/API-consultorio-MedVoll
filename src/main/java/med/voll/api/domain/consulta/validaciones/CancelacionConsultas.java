package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosBorrarConsulta;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class CancelacionConsultas {
    public void validar(DatosBorrarConsulta datosBorrarConsulta, LocalDateTime fecha){
        var ahora = LocalDateTime.now();
        var diferencia1Hr = Duration.between(ahora, fecha).toHours()<24;
        if(diferencia1Hr){
            throw new ValidationException("Las consultas deben de cancelarse con 24 hrs de anticipación. ");
        }
    }
}
