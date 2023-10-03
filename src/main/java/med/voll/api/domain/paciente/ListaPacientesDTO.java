package med.voll.api.domain.paciente;

public record ListaPacientesDTO(
        Long id,
        String nombre,
        String email,
        String documento
) {

    public ListaPacientesDTO(Paciente paciente) {
        this(
                paciente.getId(),paciente.getNombre(), paciente.getEmail(), paciente.getDocumento()
        );
    }
}
