package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.stereotype.Component;
import java.time.DayOfWeek;

@Component
public class HorarioDeFuncionamiento implements ValidadorDeConsultas {
    public void validar(DatosAgendarConsulta datosAgendarConsulta){
        var domingo = DayOfWeek.SUNDAY.equals(datosAgendarConsulta.fecha());
        var antesDeApertura = datosAgendarConsulta.fecha().getHour()<7;
        var despuesDeApertura=datosAgendarConsulta.fecha().getHour()>19;

        if(domingo||antesDeApertura|| despuesDeApertura){
            throw new ValidationException("El horario de atanción de la clínica es de Lunes a Sábado de 7:00 a 19:00 horas.");
        }
    }
}
