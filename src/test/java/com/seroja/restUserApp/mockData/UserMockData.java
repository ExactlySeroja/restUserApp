package com.seroja.restUserApp.mockData;

import com.seroja.restUserApp.dto.UserDto;
import com.seroja.restUserApp.entity.User;

import java.time.LocalDate;

public class UserMockData {

    public static UserDto getUserDto1() {
        return UserDto.builder().id(1).email("superemail@gmail.com")
                .firstName("John").secondName("Doe").birthDate(LocalDate.of(2000, 1, 13))
                .address("USA").phoneNumber("+38403123456").build();
    }

    public static UserDto getUserDto2() {
        return UserDto.builder().id(2).email("amazing@gmail.com")
                .firstName("Dobrinya").secondName("Plov").birthDate(LocalDate.of(1980, 6, 23))
                .address("Ukraine").phoneNumber("+38453683713").build();
    }

    public static UserDto getUserDtoWithSmallAge() {
        return UserDto.builder().id(3).email("famous@gmail.com")
                .firstName("Vik").secondName("Bor").birthDate(LocalDate.of(2009, 9, 9))
                .address("Germany").phoneNumber("+38554424568").build();
    }

    public static User getUser1() {
        return User.builder().id(1).email("superemail@gmail.com")
                .firstName("John").secondName("Doe").birthDate(LocalDate.of(2000, 1, 13))
                .address("USA").phoneNumber("+38403123456").build();
    }

    public static User getUser2() {
        return User.builder().id(2).email("amazing@gmail.com")
                .firstName("Dobrinya").secondName("Plov").birthDate(LocalDate.of(1980, 6, 23))
                .address("Ukraine").phoneNumber("+38453683713").build();
    }

}
