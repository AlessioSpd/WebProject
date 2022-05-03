package com.Tidy.service;

import com.Tidy.common.ServiceTemplate;
import com.Tidy.dto.ClienteDto;
import com.Tidy.dto.LocaleDto;
import com.Tidy.entity.Cliente;
import com.Tidy.entity.Locale;
import com.Tidy.exception.BaseCustomException;
import com.Tidy.repository.LocaleRepository;
import com.Tidy.criteria.LocaleCriteria;
import com.Tidy.request.ChangePasswordRequest;
import com.Tidy.request.LoginRequest;
import com.Tidy.specification.LocaleSpecificationBuilder;
import com.Tidy.mapper.LocaleBidirectionalMapper;
import com.Tidy.util.Auth;
import org.apache.tomcat.jni.Local;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocaleService extends ServiceTemplate<Locale, LocaleDto, LocaleCriteria, LocaleSpecificationBuilder, LocaleBidirectionalMapper, LocaleRepository> {

    protected LocaleService(LocaleRepository repository, LocaleBidirectionalMapper mapper, LocaleSpecificationBuilder specificationBuilder) {
        super(repository, mapper, specificationBuilder);
    }

    @Override
    public String[] getHeaders() {
        return new String[0];
    }

    @Override
    public String[] populate(Locale entity) {
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

    public List<LocaleDto> getAll() {
        return getRepository().findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public LocaleDto getByEmail(String email){
        return mapper.toDto(repository.findByEmail(email));
    }

    public LocaleDto saveLocale(LocaleDto locale){
        locale.setPassword(Auth.encryptPassword(locale.getPassword()));
        Locale entity = mapper.toEntity(locale);
        return mapper.toDto(repository.save(entity));
    }

    public LocaleDto login(LoginRequest request) throws BaseCustomException {
        Locale locale = repository.findByEmail(request.getEmail());
        if (Auth.checkPassword(request.getPassword(), locale.getPassword())) {
            return mapper.toDto(locale);
        } else {
            throw new BaseCustomException("Password errata", HttpStatus.BAD_REQUEST);
        }
    }

    public LocaleDto changePassword(Long id, ChangePasswordRequest changePasswordRequest) throws BaseCustomException {
        Locale locale = getEntity(id);
        if(Auth.checkPassword(changePasswordRequest.getOldPassword(), locale.getPassword())){
            locale.setPassword(Auth.encryptPassword(changePasswordRequest.getNewPassword()));
            repository.save(locale);
            return mapper.toDto(locale);
        }
        throw new BaseCustomException("La password attuale non corrisponde", HttpStatus.UNAUTHORIZED);
    }

    public boolean exists(String email){
        Locale locale = repository.findByEmail(email);
        return locale != null;
    }
}