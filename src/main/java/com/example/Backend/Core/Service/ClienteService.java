package com.example.Backend.Core.Service;

import com.example.Backend.Core.Models.Cliente;
import com.example.Backend.Core.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    @Autowired
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }


    public List<Cliente> findAll(){
        return clienteRepository.findAll();
    }

    public Cliente findByIdCliente(String idCliente){
        return clienteRepository.findByIdCliente(idCliente);
    }

    public Cliente registrarCliente(Cliente cliente, String encodedPassword) throws Exception{
        //Validacion del idCliente o cedula para que no existan duplicados
        Cliente existe = findByIdCliente(cliente.getIdCliente());
        if (existe != null){
            throw new Exception("El cliente con cedula "+cliente.getIdCliente()+" ya esta registrado");
        }
        //Validacion del idCliente o cedula
        if (!cliente.getIdCliente().matches("^\\d{10}$")){
            throw new Exception("Formato de cedula inválido: "+cliente.getIdCliente());
        }

        //Validacion del nombre y apellido
        if (!cliente.getNombre().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{1,30}$")) {
            throw new Exception("El nombre contiene caracteres no permitidos: " + cliente.getNombre());
        }
        if (!cliente.getApellido().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{1,30}$")) {
            throw new Exception("El apellido contiene caracteres no permitidos: " + cliente.getApellido());
        }

        //Validacion del telefono
        if (!cliente.getTelefono().matches("^\\d{10}$")){
            throw new Exception("Formato de telefono incorrecto, solo se aceptan numero" +cliente.getTelefono());
        }

        //Validacion del correo
        if (!cliente.getCorreo().matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")){
            throw new Exception("Formato de correo electronico inválido: "+ cliente.getCorreo());
        }

        //Validar correo duplicado
        if (clienteRepository.findByCorreo(cliente.getCorreo()) != null){
            throw new Exception("El correo ya esta registrado en otra cuenta: "+cliente.getCorreo());
        }

        cliente.setPassword(encodedPassword);
        return clienteRepository.save(cliente);
    }

    public Cliente updateCliente(String idCliente, Cliente cliente) throws Exception{
        Cliente existe = findByIdCliente(idCliente);
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
            if(!existe.getCorreo().equals(cliente.getCorreo())){
                if (clienteRepository.findByCorreo(cliente.getCorreo()) != null){
                    throw new Exception("El correo ya esta registrado en otra cuenta: "+existe.getCorreo());
                }
            }

            existe.setNombre(cliente.getNombre());
            existe.setApellido(cliente.getApellido());
            existe.setDireccion(cliente.getDireccion());
            existe.setTelefono(cliente.getTelefono());
            existe.setCorreo(cliente.getCorreo());
            return clienteRepository.save(existe);
        }else {
            throw new Exception("El cliente con cedula "+idCliente+" no existe");
        }
    }

    public boolean deleteCliente(String idCliente) throws Exception{
        Cliente existe = findByIdCliente(idCliente);
        if (existe != null){
            clienteRepository.delete(existe);
            return true;
        } else {
            throw new Exception("El cliente con cedula "+idCliente+" no existe");
        }
    }
}

