package com.angular.todo.repositories;


import com.angular.todo.models.Register;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface LoginRepository extends CrudRepository<Register, Long> {
    //using the query annotation to creating custom query for login
    @Query("select u from user u WHERE u.username = :username")
    //creating a method in the interface
    Register getUserByUsername(@Param("username") String Username);
}
