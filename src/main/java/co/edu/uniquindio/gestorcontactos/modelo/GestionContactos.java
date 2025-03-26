package co.edu.uniquindio.gestorcontactos.modelo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class GestionContactos {
    public List<Contacto> listaContactos = new ArrayList<>();


    public void agregarContacto(String nombre, String apellido, String numeroDeTelefono, String correoElectronico, LocalDate fechaDeNacimiento){
        Contacto nuevoContacto = Contacto.builder()
                .nombre(nombre)
                .apellido(apellido)
                .numeroDeTelefono(numeroDeTelefono)
                .correoElectronico(correoElectronico)
                .fechaDeNacimiento(fechaDeNacimiento)
                .build();

        listaContactos.add(nuevoContacto);
    }

    public List<Contacto> listarContactos(){
        return new ArrayList<>(listaContactos);
    }


}
