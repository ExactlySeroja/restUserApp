package com.seroja.restUserApp.service;

import com.seroja.restUserApp.dto.UserDto;
import com.seroja.restUserApp.entity.User;
import com.seroja.restUserApp.mapper.UserMapper;
import com.seroja.restUserApp.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper userMapper;
    @Value("${min.age}")
    public Integer minAge;

    public List<UserDto> listAll() {
        return userMapper.toDTOList(repository.findAll());
    }

    public UserDto save(UserDto userDto) {
        if (!ageValidation(userDto))
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "User too young");
        User userToSave = userMapper.toUser(userDto);
        return userMapper.toDto(repository.save(userToSave));
    }

    private User get(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "User was not found!"));
    }

    public UserDto getDto(int id) {
        return userMapper.toDto(repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "User was not found!")));
    }

    public void delete(int id) {
        repository.deleteById(id);
    }

    public void update(UserDto newUserDto, int id) {
        User exsistUser = get(id);
        User update = userMapper.toUser(newUserDto);
        userMapper.update(exsistUser, update);
        repository.save(exsistUser);
    }

    public List<UserDto> getInRange(LocalDate maxAge, LocalDate minAge) {
        return userMapper.toDTOList(repository.getUsersInRange(maxAge, minAge));
    }

    private boolean ageValidation(UserDto userDto) {
        return Year.now().getValue() - userDto.getBirthDate().getYear() >= minAge;
    }
}
