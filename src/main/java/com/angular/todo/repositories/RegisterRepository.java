package com.angular.todo.repositories;


import com.angular.todo.models.Register;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RegisterRepository extends JpaRepository<Register, Long> {

    //used to load users data by the username
    @Query("select u from user u where u.username = :username")
    Register getDataByUsername(String username);
}
