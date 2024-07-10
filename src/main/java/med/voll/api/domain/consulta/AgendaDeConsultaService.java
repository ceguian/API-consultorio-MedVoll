package med.voll.api.domain.consulta;

import med.voll.api.domain.consulta.validaciones.CancelacionConsultas;
import med.voll.api.domain.consulta.validaciones.ValidadorDeConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgendaDeConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private List<ValidadorDeConsultas> validaciones;

    @Autowired
    private CancelacionConsultas cancelacionConsultas;

    public DatosDetalleConsulta agendar(DatosAgendarConsulta datosAgendarConsulta) {

        if (!pacienteRepository.findById(datosAgendarConsulta.idPaciente()).isPresent()){
            throw new ValidacionDeIntegridad("Este id para el paciente no fue encontrado.");
            //excepciones personalizadas
        }
        if(datosAgendarConsulta.idMedico() != null && !medicoRepository.existsById(datosAgendarConsulta.idMedico())){
            throw new ValidacionDeIntegridad("Este id para el médico no fue encontrado.");
        }

        //validaciones

        validaciones.forEach(v->v.validar(datosAgendarConsulta));

        Paciente paciente= pacienteRepository.findById(datosAgendarConsulta.idPaciente()).get();

        Medico medico = escogerMedico(datosAgendarConsulta);

        if(medico==null){
            throw new ValidacionDeIntegridad("No existen médicos disponibles para este horario y especialidad.");
        }

        var consulta = new Consulta(null,medico, paciente, datosAgendarConsulta.fecha(),true, Motivo.NA);
        consultaRepository.save(consulta);

        return new DatosDetalleConsulta(consulta);

    }

    private Medico escogerMedico(DatosAgendarConsulta datosAgendarConsulta) {

        if(datosAgendarConsulta.idMedico()!=null){
            return medicoRepository.getReferenceById(datosAgendarConsulta.idMedico());
        }
        if(datosAgendarConsulta.especialidad()==null){
            throw  new ValidacionDeIntegridad("Debe seleccionarse una especialidad para el médico.");
        }
        return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datosAgendarConsulta.especialidad(), datosAgendarConsulta.fecha());
    }

    public Consulta borrar(DatosBorrarConsulta datosBorrarConsulta) {
        if(!consultaRepository.existsById(datosBorrarConsulta.id())){
            throw new ValidacionDeIntegridad("La consulta no se encuentra registrada.");
        }
        var response = consultaRepository.findById(datosBorrarConsulta.id()).get();
        var fecha = response.getFecha();
        cancelacionConsultas.validar(datosBorrarConsulta,fecha);
        response.desactivarConsulta();
        response.setMotivo(datosBorrarConsulta.motivo());
        return consultaRepository.findById(datosBorrarConsulta.id()).get();
    }
}
