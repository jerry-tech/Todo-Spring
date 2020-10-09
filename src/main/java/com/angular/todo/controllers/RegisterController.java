package com.angular.todo.controllers;

import com.angular.todo.configurations.PassEncoder;
import com.angular.todo.models.Register;
import com.angular.todo.repositories.RegisterRepository;
import com.angular.todo.services.ResponseHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/api/v1/register")
public class RegisterController {

    @Autowired
    public RegisterRepository registerRepository;

    @Autowired
    public PassEncoder passwordEncoder;

    @Autowired
    private ResponseHandler responseHandler;

    @GetMapping
    public List<Register> list() {
        return registerRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Register get(@PathVariable Long id) {
        return registerRepository.getOne(id);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody final Register register) {
        String username = register.getUsername();
        String password = passwordEncoder.encrytePassword(register.getPassword());
        String email_add = register.getEmail_add();

        Register registerData = new Register(username, password, email_add);

        Register saveUser = registerRepository.saveAndFlush(registerData);

        //generating custom JSON response
        return responseHandler.generateResponse(HttpStatus.OK, true, "Users Registration  Successfully", saveUser);

    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        //JPA language used for deleting a record
        registerRepository.deleteById(id);

        //response for a successful delete
        return responseHandler.generateResponse(HttpStatus.OK, true, "Users record has been deleted Successfully", new HashMap<>());

    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Register register) {
        //getting a particular user's record based on the id
        Register existingTodo = registerRepository.getOne(id);
        //--------------------------
        String username = register.getUsername();
        String password = passwordEncoder.encrytePassword(register.getPassword());
        String email_add = register.getEmail_add();

        //passing user's data through the constructor of the register class
        Register reg = new Register(username,password,email_add);

        //using the Bean Util class to ignore the update of the primary key
        BeanUtils.copyProperties(reg, existingTodo, "user_id");

        //updating the record
        Register registerUpdate = registerRepository.saveAndFlush(existingTodo);

        //generating custom JSON response
        return responseHandler.generateResponse(HttpStatus.OK, true, "Users record has been Updated Successfully", registerUpdate);
    }
}
