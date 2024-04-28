package com.seroja.restUserApp.repos;

import com.seroja.restUserApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "select * from user where birth_date between maxDate and minDate ORDER BY birth_date  DESC", nativeQuery = true)
    List<User> getUsersInRange(LocalDate maxDate, LocalDate minDate);

}
