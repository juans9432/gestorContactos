package co.edu.uniquindio.gestorcontactos.modelo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class Contacto {
    private String nombre, apellido, numeroDeTelefono, correoElectronico;
    private LocalDate fechaDeNacimiento;

}
