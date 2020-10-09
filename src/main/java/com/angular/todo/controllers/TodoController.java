package com.angular.todo.controllers;

import com.angular.todo.JWT.JsonWebToken;
import com.angular.todo.Logic.Data;
import com.angular.todo.models.Register;
import com.angular.todo.models.Todo;
import com.angular.todo.repositories.RegisterRepository;
import com.angular.todo.repositories.TodoRepository;
import com.angular.todo.services.ResponseHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/todo")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    /*----------------------------------------*/
    @Autowired
    private RegisterRepository registerRepository;

    @Autowired//Autowiring the response handler for the entity response handler
    ResponseHandler responseHandler;

    @Autowired
    JsonWebToken jsonWebToken;

    @Autowired
    private Data data;

    //method used to get all todo
    @GetMapping
    public ResponseEntity<?> list(@RequestHeader("Authorization") String JWT) {

        //getting the Jwt token from the header

        if (this.jsonWebToken.isTokenExpired(JWT)) {

            //building custom success response
            return responseHandler.generateResponse(HttpStatus.FORBIDDEN, false, "JWT has Expired", null);

        } else {
            //decrypting the Jwt token to get the Username from the token
            final String username = jsonWebToken.extractUsername(JWT);

            //getting Users data from input the users data
            Register getUserId = registerRepository.getDataByUsername(username);

            //checking if the UserId isEmpty
            if (getUserId.getUser_id().toString().isEmpty()) {

                //building custom error response
                return responseHandler.generateResponse(HttpStatus.NOT_FOUND, false, "UserId Not Found.", null);
            } else {
                List<Todo> records = data.getTodo(getUserId.getUser_id());

                //Used to get User's todos by specifying the user Id

                if (records != null) {

                    //building custom success response
                    return responseHandler.generateResponse(HttpStatus.OK, true, "Getting todo Successful", records);
                } else {

                    //building custom error response
                    return responseHandler.generateResponse(HttpStatus.NOT_FOUND, false, "No todo Available", null);
                }
            }

        }
    }

    //method used to get individual todo
    @GetMapping
    @RequestMapping("{todoId}")
    public ResponseEntity<?> get(@PathVariable Long todoId, @RequestHeader("Authorization") String JWT) {

        //getting the Jwt token from the header

        //decrypting the Jwt token to get the Username from the token
        final String username = jsonWebToken.extractUsername(JWT);

        //getting Users data from input the users data
        Register getUserId = registerRepository.getDataByUsername(username);

        Todo getSingleTodo = data.getSingleTodo(getUserId.getUser_id(), todoId);

        if (!getSingleTodo.toString().isEmpty()) {
            //building custom success response
            return responseHandler.generateResponse(HttpStatus.OK, true, "Getting all todo", getSingleTodo);
        }

        return responseHandler.generateResponse(HttpStatus.FORBIDDEN, false, "Error in getting single todo.", getSingleTodo);
    }


    //method used to create todo
    @PostMapping
    public ResponseEntity<?> create(@RequestBody final Todo todo, @RequestHeader("Authorization") String JWT) {

        //getting the Jwt token from the header


        if (this.jsonWebToken.isTokenExpired(JWT)) {

            //building custom error response
            return responseHandler.generateResponse(HttpStatus.FORBIDDEN, false, "JWT has Expired", null);

        } else {
            //decrypting the Jwt token to get the Username from the token
            final String username = jsonWebToken.extractUsername(JWT);

            //getting Users data from input the username
            Register getUserId = registerRepository.getDataByUsername(username);

            //checking if the UserId isEmpty
            if (getUserId.getUser_id().toString().isEmpty()) {

                //building custom error response
                return responseHandler.generateResponse(HttpStatus.NOT_FOUND, false, "UserId Not Found.", null);

            } else {
                //getting the individual user todo values
                String name = todo.getName();
                String content = todo.getContent();
                String rating = todo.getRating();
                Long userId = getUserId.getUser_id();

                //adding the userTodo using the constructor of the Todos Class
                Todo manualAddTodo = new Todo(name, rating, content, userId);

                //used to commit and save data into the database
                Todo createTodo = todoRepository.saveAndFlush(manualAddTodo);

                if (createTodo.toString().isEmpty()) {
                    //building custom error response
                    return responseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, "Error in Creating todo.", null);
                } else {
                    //building custom success response
                    return responseHandler.generateResponse(HttpStatus.OK, true, "Todo Created Successfully", createTodo);
                }
            }
        }
    }

    //method used to delete todo
    @RequestMapping(value = "{todoId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Long todoId, @RequestHeader("Authorization") String JWT) {

        //getting the Jwt token from the header


        //decrypting the Jwt token to get the Username from the token
        final String username = jsonWebToken.extractUsername(JWT);

        //getting Users data from input the username
        Register getUserId = registerRepository.getDataByUsername(username);

        //deleting todo by userId and todoId
        boolean isDelete = data.deleteTodo(getUserId.getUser_id(), todoId);

        if (isDelete) {
            //building custom success response
            return responseHandler.generateResponse(HttpStatus.OK, true, "Todo deleted successfully", null);

        } else {
            return responseHandler.generateResponse(HttpStatus.NOT_FOUND, false, "Unable to deleted.", null);
        }
    }

    //method used to update todo
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Todo todo, @RequestHeader("Authorization") String JWT) {
        Todo existingTodo = todoRepository.getOne(id);

        //getting the Jwt token from the header


        //decrypting the Jwt token to get the Username from the token
        final String username = jsonWebToken.extractUsername(JWT);

        //getting Users data from input the users data
        Register getUserId = registerRepository.getDataByUsername(username);

        //getting users update data
        String name = todo.getName();
        String content = todo.getContent();
        String rating = todo.getRating();
        String status = todo.getStatus();
        Long todo_id = id;
        Long user_id = getUserId.getUser_id();

        //passing the update data through the constructor of the overloaded constructor of the todo class
        Todo todo1 = new Todo(user_id, todo_id, name, rating, content, status);

        boolean updateTodo = data.updateTodo(name, rating, content, status, user_id, todo_id);
        BeanUtils.copyProperties(todo1, existingTodo, "todo_id");
//        todoRepository.saveAndFlush(existingTodo);

        if (updateTodo) {
            //building custom success response
            return responseHandler.generateResponse(HttpStatus.OK, true, "Todo updated successfully.", null);
        }

        return responseHandler.generateResponse(HttpStatus.FORBIDDEN, false, "Error in updating todo.", null);
    }
}
