package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    PacienteRepository pacienteRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaPaciente> registrarPaciente(
            @RequestBody
            @Valid
            PacienteDTO pacienteDTO,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        Paciente paciente = pacienteRepository.save(new Paciente(pacienteDTO));
        DatosRespuestaPaciente datosRespuestaPaciente = new DatosRespuestaPaciente(paciente);

        URI uri = uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).body(datosRespuestaPaciente);
    }

    @GetMapping
    public ResponseEntity<Page<ListaPacientesDTO>> getPacientes(
            @PageableDefault(
                    size = 10
            )
            Pageable paginacion
    ) {
        return ResponseEntity.ok(
                pacienteRepository.findByActivoTrue(paginacion).map(ListaPacientesDTO::new)
        );
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaPaciente> actualizarPaciente(
            @RequestBody
            DatosActualizarPacienteDto actualizarPacienteDto
    ) {
        Paciente paciente = pacienteRepository.getReferenceById(actualizarPacienteDto.id());
        paciente.actualizarInformacion(actualizarPacienteDto);

        return ResponseEntity.ok(new DatosRespuestaPaciente(paciente));
    }

    /**
     * DELETE LOGIC
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliinarPaciente(@PathVariable Long id) {
        Paciente paciente = pacienteRepository.getReferenceById(id);
        paciente.desactivarPaciente();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaPaciente> getPaciente(
            @PathVariable
            Long id
    ) {
        Paciente paciente = pacienteRepository.getReferenceById(id);
        return ResponseEntity.ok(new DatosRespuestaPaciente(paciente));
    }

}
