package com.seroja.restUserApp.service;

import com.seroja.restUserApp.dto.UserDto;
import com.seroja.restUserApp.entity.User;
import com.seroja.restUserApp.mapper.UserMapper;
import com.seroja.restUserApp.mockData.UserMockData;
import com.seroja.restUserApp.repos.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(SpringExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository repository;
    @InjectMocks
    private UserService userService;
    @Mock
    private UserMapper mapper;
    private final User user1 = UserMockData.getUser1();
    private final User user2 = UserMockData.getUser2();
    private final UserDto userDto1 = UserMockData.getUserDto1();
    private final UserDto userDto2 = UserMockData.getUserDto2();

    @BeforeEach
    public void setUp() {
        userService.minAge = 18;
    }

    @Test
    public void shouldSave() {
        given(mapper.toDto(user1)).willReturn(userDto1);
        given(mapper.toUser(userDto1)).willReturn(user1);
        given(repository.save(user1)).willReturn(user1);

        UserDto savedUser = userService.save(userDto1);

        assertThat(savedUser).isEqualTo(userDto1);
    }

    @Test
    public void shouldThrowExceptionWithoutSaving() {
        org.junit.jupiter.api.Assertions.assertThrows(ResponseStatusException.class,
                () -> userService.save(UserMockData.getUserDtoWithSmallAge()));
    }

    @Test
    public void shouldReturnListOfUsers() {
        given(repository.findAll()).willReturn(List.of(user1, user2));
        given(mapper.toDTOList(List.of(user1, user2))).willReturn(List.of(userDto1, userDto2));

        List<UserDto> users = userService.listAll();

        assertThat(users).isNotNull();
        assertThat(users.size()).isEqualTo(2);
    }

    @Test
    public void shouldGetUserById() {
        given(repository.findById(user2.getId())).willReturn(Optional.of(user2));
        given(mapper.toDto(user2)).willReturn(userDto2);

        UserDto savedUser = userService.getDto(userDto2.getId());

        assertThat(savedUser).isNotNull();
    }

    @Test
    public void shouldGetUsersInBirthDateRange() {
        userService.getInRange(LocalDate.now().minusDays(1), LocalDate.now());
        Mockito.verify(repository).getUsersInRange(LocalDate.now().minusDays(1), LocalDate.now());
        Mockito.verify(mapper).toDTOList(any());
    }

    @Test
    public void shouldDeleteUser() {
        int userId = 1;
        willDoNothing().given(repository).deleteById(userId);

        userService.delete(userId);

        Mockito.verify(repository).deleteById(userId);
    }

    @Test
    public void shouldUpdateUser() {
        given(repository.findById(user1.getId())).willReturn(Optional.of(user1));
        given(mapper.toUser(userDto2)).willReturn(user2);

        userService.update(userDto2, user1.getId());

        Mockito.verify(mapper).update(user1, user2);
        Mockito.verify(repository).save(user1);
    }

}