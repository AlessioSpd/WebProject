package com.Tidy.service;

import com.Tidy.common.ServiceTemplate;
import com.Tidy.dto.AmministratoreDto;
import com.Tidy.dto.ClienteDto;
import com.Tidy.entity.Amministratore;
import com.Tidy.entity.Cliente;
import com.Tidy.exception.BaseCustomException;
import com.Tidy.repository.AmministratoreRepository;
import com.Tidy.criteria.AmministratoreCriteria;
import com.Tidy.request.ChangePasswordRequest;
import com.Tidy.request.LoginRequest;
import com.Tidy.specification.AmministratoreSpecificationBuilder;
import com.Tidy.mapper.AmministratoreBidirectionalMapper;
import com.Tidy.util.Auth;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AmministratoreService extends ServiceTemplate<Amministratore, AmministratoreDto, AmministratoreCriteria, AmministratoreSpecificationBuilder, AmministratoreBidirectionalMapper, AmministratoreRepository> {

    protected AmministratoreService(AmministratoreRepository repository, AmministratoreBidirectionalMapper mapper, AmministratoreSpecificationBuilder specificationBuilder) {
        super(repository, mapper, specificationBuilder);
    }

    @Override
    public String[] getHeaders() {
        return new String[0];
    }

    @Override
    public String[] populate(Amministratore entity) {
        return new String[0];
    }

    @Override
    protected boolean eligibleToDelete(Long id) {
        return true;
    }

    @Override
    public String getEntityName() {
        return null;
    }

    public List<AmministratoreDto> getAll() {
        return getRepository().findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public boolean exists(String email){
        Amministratore amministratore = repository.findByEmail(email);
        return amministratore != null;
    }

    public AmministratoreDto login(LoginRequest request) throws BaseCustomException {
        Amministratore amministratore = repository.findByEmail(request.getEmail());
        if (Auth.checkPassword(request.getPassword(), amministratore.getPassword())) {
            return mapper.toDto(amministratore);
        } else {
            throw new BaseCustomException("Password errata", HttpStatus.BAD_REQUEST);
        }
    }

    public AmministratoreDto saveAmministratore(AmministratoreDto amministratore){
        amministratore.setPassword(Auth.encryptPassword(amministratore.getPassword()));
        Amministratore entity = mapper.toEntity(amministratore);
        return mapper.toDto(repository.save(entity));
    }

    public AmministratoreDto changePassword(Long id, ChangePasswordRequest changePasswordRequest) throws BaseCustomException {
        Amministratore amministratore = getEntity(id);
        if(Auth.checkPassword(changePasswordRequest.getOldPassword(), amministratore.getPassword())){
            amministratore.setPassword(Auth.encryptPassword(changePasswordRequest.getNewPassword()));
            repository.save(amministratore);
            return mapper.toDto(amministratore);
        }
        throw new BaseCustomException("La password attuale non corrisponde", HttpStatus.UNAUTHORIZED);
    }

    public AmministratoreDto getByEmail(String email){
        return mapper.toDto(repository.findByEmail(email));
    }

    public boolean permaDelete(Long id) throws BaseCustomException {
        Amministratore entity = getEntity(id);
        repository.delete(entity);
        return true;
    }
}