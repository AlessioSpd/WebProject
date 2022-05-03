package com.Tidy.service;
import com.Tidy.common.ServiceTemplate;
import com.Tidy.dto.ClienteDto;
import com.Tidy.dto.LocaleDto;
import com.Tidy.entity.Cliente;
import com.Tidy.entity.Locale;
import com.Tidy.exception.BaseCustomException;
import com.Tidy.repository.ClienteRepository;
import com.Tidy.criteria.ClienteCriteria;
import com.Tidy.request.ChangePasswordRequest;
import com.Tidy.request.LoginRequest;
import com.Tidy.specification.ClienteSpecificationBuilder;
import com.Tidy.mapper.ClienteBidirectionalMapper;
import com.Tidy.util.Auth;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService extends ServiceTemplate<Cliente, ClienteDto, ClienteCriteria, ClienteSpecificationBuilder, ClienteBidirectionalMapper, ClienteRepository> {

    protected ClienteService(ClienteRepository repository, ClienteBidirectionalMapper mapper, ClienteSpecificationBuilder specificationBuilder) {
        super(repository, mapper, specificationBuilder);
    }

    @Override
    public String[] getHeaders() {
        return new String[0];
    }

    @Override
    public String[] populate(Cliente entity) {
        return new String[0];
    }

    @Override
    protected boolean eligibleToDelete(Long id) {
        return false;
    }

    @Override
    public String getEntityName() {
        return null;
    }

    public List<ClienteDto> getAll() {
        return getRepository().findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public ClienteDto getByEmail(String email){
        return mapper.toDto(repository.findByEmail(email));
    }

    public ClienteDto saveCliente(ClienteDto cliente){
        cliente.setPassword(Auth.encryptPassword(cliente.getPassword()));
        Cliente entity = mapper.toEntity(cliente);
        return mapper.toDto(repository.save(entity));
    }

    public ClienteDto login(LoginRequest request) throws BaseCustomException {
        Cliente cliente = repository.findByEmail(request.getEmail());
        if (Auth.checkPassword(request.getPassword(), cliente.getPassword())) {
            return mapper.toDto(cliente);
        } else {
            throw new BaseCustomException("Password errata", HttpStatus.BAD_REQUEST);
        }
    }


    public ClienteDto changePassword(Long id, ChangePasswordRequest changePasswordRequest) throws BaseCustomException {
        Cliente cliente = getEntity(id);
        if(Auth.checkPassword(changePasswordRequest.getOldPassword(), cliente.getPassword())){
            cliente.setPassword(Auth.encryptPassword(changePasswordRequest.getNewPassword()));
            repository.save(cliente);
            return mapper.toDto(cliente);
        }
        throw new BaseCustomException("La password attuale non corrisponde", HttpStatus.UNAUTHORIZED);
    }

    public boolean exists(String email){
        Cliente cliente = repository.findByEmail(email);
        return cliente != null;
    }
}