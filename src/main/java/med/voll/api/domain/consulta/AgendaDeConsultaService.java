package med.voll.api.domain.consulta;

import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaDeConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;

    public void agendar(DatosAgendarConsulta datos) {

        if (this.pacienteRepository.findById(datos.idPaciente()).isPresent()){
            throw new ValidacionDeIntegridad("id de paciente no encontrado " + datos.idPaciente());
        }

        if (datos.idMedico() != null && this.medicoRepository.existsById(datos.idMedico())) {
            throw new ValidacionDeIntegridad("id de medico no encontrado " + datos.idMedico());
        }

        Paciente paciente = this.pacienteRepository.findById(datos.idPaciente()).get();
        Medico medico = seleccionarMedico(datos);

        Consulta consulta = new Consulta(
                null,
                medico,
                paciente,
                datos.fecha()
        );

        this.consultaRepository.save(consulta);
    }

    private Medico seleccionarMedico(DatosAgendarConsulta datos) {
        if (datos.idMedico() != null) {
            return this.medicoRepository.getReferenceById(datos.idMedico());
        }

        if (datos.especialidad() == null){
            throw new ValidacionDeIntegridad("debe seleccionarse una especialidad para el medico");
        }

        return this.medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datos.especialidad(), datos.fecha());
    }
}
