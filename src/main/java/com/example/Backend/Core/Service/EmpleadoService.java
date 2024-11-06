package com.example.Backend.Core.Service;

import com.example.Backend.Core.Models.Empleado;
import com.example.Backend.Core.Repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoService {
    @Autowired
    private final EmpleadoRepository empleadoRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public List<Empleado> findAll(){
        return empleadoRepository.findAll();
    }

    public Empleado findByIdEmpleado(String idEmpleado){
        return empleadoRepository.findByIdEmpleado(idEmpleado);
    }

    public Empleado registrarEmpleado(Empleado empleado, String encodedPassword) throws Exception{
        //Validacion de que no existan empleados con el mismo idEmpleado
        Empleado existe = findByIdEmpleado(empleado.getIdEmpleado());
        if (existe != null){
            throw new Exception("El empleado con cedula "+empleado.getIdEmpleado()+" ya esta registrado");
        }

        //Validacion del idCliente o cedula
        if (!empleado.getIdEmpleado().matches("^\\d{10}$")){
            throw new Exception("Formato de cedula inválido: "+empleado.getIdEmpleado());
        }

        //Validacion del nombre y apellido
        if (!empleado.getNombre().matches("[A-Za-záéíóúÁÉÍÓÚñÑ ]+")) {
            throw new Exception("El nombre contiene caracteres no permitidos: " + empleado.getNombre());
        }
        if (!empleado.getApellido().matches("[A-Za-záéíóúÁÉÍÓÚñÑ ]+")) {
            throw new Exception("El apellido contiene caracteres no permitidos: " + empleado.getApellido());
        }

        //Validacion en telefono
        if (!empleado.getTelefono().matches("^\\d{10}$")){
            throw new Exception("El telefono contiene caracteres que no son numéricos");
        }

        //Validacion formato del correo
        if (!empleado.getCorreo().matches("^[\\w\\.-]+@[\\w\\.-]+\\.\\w+$")){
            throw new Exception("Formato de correo electronico inválido: "+ empleado.getCorreo());
        }

        //Validar correo duplicado
        if (empleadoRepository.findByCorreo(empleado.getCorreo()) != null){
            throw new Exception("El correo ya esta registrado en otra cuenta: "+empleado.getCorreo());
        }

        empleado.setPassword(encodedPassword);
        return empleadoRepository.save(empleado);
    }

    public Empleado updateEmpleado(String idEmpleado, Empleado empleado) throws Exception{
        Empleado existe = findByIdEmpleado(idEmpleado);
        if (existe != null){
            //Validacion del nombre y apellido
            if (!existe.getNombre().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{1,30}$")) {
                throw new Exception("El nombre contiene caracteres no permitidos: " + existe.getNombre());
            }
            if (!existe.getApellido().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{1,30}$")) {
                throw new Exception("El apellido contiene caracteres no permitidos: " + existe.getApellido());
            }

            //Validacion del telefono
            if (!existe.getTelefono().matches("^\\d{10}$")){
                throw new Exception("Formato de telefono incorrecto, solo se aceptan numero" +existe.getTelefono());
            }

            //Validacion formato del correo
            if (!existe.getCorreo().matches("^[\\w\\.-]+@[\\w\\.-]+\\.\\w+$")){
                throw new Exception("Formato de correo electronico inválido: "+ existe.getCorreo());
            }

            //Validar correo duplicado
            if(!existe.getCorreo().equals(empleado.getCorreo())){
                if (empleadoRepository.findByCorreo(empleado.getCorreo()) != null){
                    throw new Exception("El correo ya esta registrado en otra cuenta: "+existe.getCorreo());
                }
            }

            existe.setNombre(empleado.getNombre());
            existe.setApellido(empleado.getApellido());
            existe.setDireccion(empleado.getDireccion());
            existe.setTelefono(empleado.getTelefono());
            existe.setCorreo(empleado.getCorreo());
            return empleadoRepository.save(existe);
        }else {
            throw new Exception("El emplado con cedula "+idEmpleado+" no existe");
        }
    }

    public boolean deleteEmpleado(String idEmpleado) throws Exception{
        Empleado existe = findByIdEmpleado(idEmpleado);
        if (existe != null){
            empleadoRepository.delete(existe);
            return true;
        } else {
            throw new Exception("El emplado con cedula "+idEmpleado+" no existe");
        }
    }
}
