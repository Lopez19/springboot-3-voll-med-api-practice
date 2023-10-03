package med.voll.api.domain.paciente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.direccion.Direccion;

@Entity(name = "Paciente")
@Table(name = "pacientes")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;
    private String documento;
    private String telefono;
    private Boolean activo;

    @Embedded
    private Direccion direccion;

    public Paciente(PacienteDTO pacienteDTO) {
        this.activo = true;
        this.nombre = pacienteDTO.nombre();
        this.email = pacienteDTO.email();
        this.documento = pacienteDTO.documento();
        this.telefono = pacienteDTO.telefono();
        this.direccion = new Direccion(pacienteDTO.direccion());
    }

    public void actualizarInformacion(DatosActualizarPacienteDto actualizarPacienteDto) {
        if (actualizarPacienteDto.nombre() != null){
            this.nombre = actualizarPacienteDto.nombre();
        }
        if (actualizarPacienteDto.telefono() != null) {
            this.telefono = actualizarPacienteDto.telefono();
        }
        if (actualizarPacienteDto.direccion() != null) {
            direccion.actualizarDireccion(actualizarPacienteDto.direccion());
        }
    }

    public void desactivarPaciente() {
        this.activo = false;
    }
}
