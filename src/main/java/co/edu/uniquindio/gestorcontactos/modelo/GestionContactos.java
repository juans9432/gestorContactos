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


    /**
     * metodo para agregar un contacto
     * @param nombre
     * @param apellido
     * @param numeroDeTelefono
     * @param correoElectronico
     * @param fechaDeNacimiento
     * @throws Exception
     */
    public void agregarContacto(String nombre, String apellido, String numeroDeTelefono, String correoElectronico, LocalDate fechaDeNacimiento) throws Exception{

        if(nombre.isEmpty() || apellido.isEmpty() || numeroDeTelefono.isEmpty() || correoElectronico.isEmpty())
            throw new Exception("Todos los campos son obligatorios");

        if(fechaDeNacimiento.isAfter(LocalDate.now()))
            throw new Exception("la fecha de nacimiento no puede ser despues de la fecha actual");

        if(contactoExiste(numeroDeTelefono))
            throw new Exception("ya existe un contacto registrado con este numero");

        if(validarNumero(numeroDeTelefono))
            throw new Exception("el numero de telefono dado es invalido");

        if(validarCorreo(correoElectronico))
            throw new Exception("el correo brindado es invalido");


        Contacto nuevoContacto = Contacto.builder()
                .nombre(nombre)
                .apellido(apellido)
                .numeroDeTelefono(numeroDeTelefono)
                .correoElectronico(correoElectronico)
                .fechaDeNacimiento(fechaDeNacimiento)
                .build();

        listaContactos.add(nuevoContacto);
    }

    /**
     * metodo para verificar si un contacto ya existe
     * @param numero
     * @return
     */
    public boolean contactoExiste(String numero){
        for(Contacto contacto : listaContactos){
            if(contacto.getNumeroDeTelefono().equals(numero)){
                return true;
            }
        }
        return false;
    }


    /**
     * metodo para listar los contactos
     * @return
     */
    public List<Contacto> listarContactos(){
        return new ArrayList<>(listaContactos);
    }


    /**
     * metodo para actualizar un contacto
     * @param contactoActualizado
     * @throws Exception
     */
    public void actualizarContacto(Contacto contactoActualizado) throws Exception{
        if(validarCorreo(contactoActualizado.getCorreoElectronico()))
            throw new Exception("ingrese un correo valido");

        for (int i = 0; i < listaContactos.size(); i++) {
            Contacto contacto = listaContactos.get(i);
            if (contacto.getNumeroDeTelefono().equals(contactoActualizado.getNumeroDeTelefono())) {
                // Reemplazar el contacto antiguo con el nuevo
                listaContactos.set(i, contactoActualizado);
                return;
            }
        }
    }


    /**
     * metodo para eliminar un contacto
     * @param contactoAEliminar
     */
    public void eliminarContacto(Contacto contactoAEliminar) {
        listaContactos.removeIf(contacto -> contacto.getNumeroDeTelefono().equals(contactoAEliminar.getNumeroDeTelefono()));
    }

    /**
     * metodo para buscar un contacto segun un filtro
     * @param valor
     * @param criterio
     * @return
     */
    public Contacto leerContacto(String valor, String criterio) {
        for (Contacto contacto : listaContactos) {
            if (criterio.equalsIgnoreCase("Nombre") && contacto.getNombre().equalsIgnoreCase(valor)) {
                return contacto;
            } else if (criterio.equalsIgnoreCase("Numero") && contacto.getNumeroDeTelefono().equals(valor)) {
                return contacto;
            }
        }
        return null; // Si no se encuentra el contacto
    }

    /**
     * metodo para listar los filtros
     * @return
     */
    public ArrayList<String> listarFiltros() {
        ArrayList<String> filtros = new ArrayList<>();
        filtros.add("Nombre");
        filtros.add("Numero");

        return filtros;
    }

    /**
     * metodo para validar un numero de telefono
     * @param numero
     * @return
     */
    public Boolean validarNumero(String numero){
        if(numero.matches("\\d{10}")){
            return false;
        }
        return true;
    }

    /**
     * metodo para validar el correo electronico
     */
    public boolean validarCorreo(String correo){
        if(!correo.contains("@")){
            return true;
        }
        return false;
    }
}
