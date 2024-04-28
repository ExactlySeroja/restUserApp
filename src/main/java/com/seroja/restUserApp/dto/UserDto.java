package com.seroja.restUserApp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.seroja.restUserApp.entity.User}
 */

@Data
@AllArgsConstructor
@Builder
public class UserDto implements Serializable {
    private Integer id;
    @Email
    private String email;
    private String firstName;
    private String secondName;
    @Past
    private LocalDate birthDate;
    private String address;
    @Pattern(regexp = "(\\+38|0)[0-9]{9}")
    private String phoneNumber;
}