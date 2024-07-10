package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PacienteSinConsulta implements ValidadorDeConsultas{
    @Autowired
    ConsultaRepository consultaRepository;

    public void validar(DatosAgendarConsulta datosAgendarConsulta){
        var primerHorario = datosAgendarConsulta.fecha().withHour(7);
        var ultimoHorario = datosAgendarConsulta.fecha().withHour(18);

        var pacienteConConsulta = consultaRepository.existsByPacienteIdAndFechaBetween(datosAgendarConsulta.idPaciente(),primerHorario,ultimoHorario);

        if(pacienteConConsulta){
            throw new ValidationException("No puede generar dos consultas en el mismo día.");
        }
    }
}
