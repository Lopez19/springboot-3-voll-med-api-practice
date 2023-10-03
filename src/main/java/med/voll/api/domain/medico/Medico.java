package med.voll.api.domain.medico;


import jakarta.persistence.*;
import lombok.*;
import med.voll.api.domain.direccion.Direccion;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String documento;
    private String telefono;
    private Boolean activo;
    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;
    @Embedded
    private Direccion direccion;

    public Medico(DatosRegistroMedico datosRegistroMedico) {
        this.activo = true;
        this.nombre = datosRegistroMedico.nombre();
        this.email = datosRegistroMedico.email();
        this.documento = datosRegistroMedico.documento();
        this.especialidad = datosRegistroMedico.especialidad();
        this.direccion = new Direccion(datosRegistroMedico.direccion());
        this.telefono = datosRegistroMedico.telefono();
    }

    public void actualizarDatos(DatosActualizarMedicoDto datos) {
        if (datos.nombre() != null){
            this.nombre = datos.nombre();
        }

        if (datos.documento() != null){
            this.documento = datos.documento();
        }

        if (datos.direccion() != null) {
            this.direccion = direccion.actualizarDireccion(datos.direccion());
        }
    }

    public void desactivarMedico() {
        this.activo = false;
    }
}
