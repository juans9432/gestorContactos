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


    public void agregarContacto(String nombre, String apellido, String numeroDeTelefono, String correoElectronico, LocalDate fechaDeNacimiento) throws Exception{

        if(nombre.isEmpty() || apellido.isEmpty() || numeroDeTelefono.isEmpty() || correoElectronico.isEmpty())
            throw new Exception("Todos los campos son obligatorios");

        if(fechaDeNacimiento.isAfter(LocalDate.now()))
            throw new Exception("la fecha de nacimiento no puede ser despues de la fecha actual");

        if(contactoExiste(numeroDeTelefono))
            throw new Exception("ya existe un contacto registrado con este numero");


        Contacto nuevoContacto = Contacto.builder()
                .nombre(nombre)
                .apellido(apellido)
                .numeroDeTelefono(numeroDeTelefono)
                .correoElectronico(correoElectronico)
                .fechaDeNacimiento(fechaDeNacimiento)
                .build();

        listaContactos.add(nuevoContacto);
    }

    public boolean contactoExiste(String numero){
        for(Contacto contacto : listaContactos){
            if(contacto.getNumeroDeTelefono().equals(numero)){
                return true;
            }
        }
        return false;
    }


    public List<Contacto> listarContactos(){
        return new ArrayList<>(listaContactos);
    }


    public void actualizarContacto(Contacto contactoActualizado) {
        for (int i = 0; i < listaContactos.size(); i++) {
            Contacto contacto = listaContactos.get(i);
            if (contacto.getNumeroDeTelefono().equals(contactoActualizado.getNumeroDeTelefono())) {
                // Reemplazar el contacto antiguo con el nuevo
                listaContactos.set(i, contactoActualizado);
                return;
            }
        }
    }

    public void eliminarContacto(Contacto contactoAEliminar) {
        listaContactos.removeIf(contacto -> contacto.getNumeroDeTelefono().equals(contactoAEliminar.getNumeroDeTelefono()));
    }

    public Contacto buscarContactoPorNombre(String nombre) {
        for (Contacto contacto : listaContactos) {
            if (contacto.getNombre().equalsIgnoreCase(nombre)) {
                return contacto;
            }
        }
        return null;
    }
}
