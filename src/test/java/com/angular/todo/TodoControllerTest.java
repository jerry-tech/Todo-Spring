package com.angular.todo;

import com.angular.todo.models.Todo;
import com.angular.todo.repositories.TodoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class TodoControllerTest {

    @Autowired//autowiring the todoRepository
    private TodoRepository todoRepository;

    //method used to test method getAllTodo
    @Test//using the @Test annotation
    public void getAllTodo(){
        List<Todo> todoList = todoRepository.getAllTodoByUser_ID(Long.valueOf("2"));
        assertTrue(todoList.size() > 0);

    }
    @Test//using the @Test annotation
    public void getSingleTodo(){
        //testing getSingleTodo by using a userId of 2 and todoId of 4
        Todo getSingleTodo = todoRepository.getSingleTodo(Long.valueOf("4"), Long.valueOf("2"));
        assertFalse(getSingleTodo.toString().isEmpty());
    }

    @Test//using the @Test annotation //method used to test getAllTodo
    public void getAllIndividualTodo(){
        List<Todo> getTodo = todoRepository.getAllTodoByUser_ID(Long.valueOf("2"));
        assertTrue(getTodo.size() > 0);
    }

    @Test//using the @Test annotation //method used to test todoUpdate
    @Transactional//@Transactional annotation is used when we use @Query annotation for modifying column
    public void updateTodo(){
        int updateTodo = todoRepository.updateTodoById("My Bank","4.2",
                "This is very private", "complete", Long.valueOf("4"),
                Long.valueOf("2"));

        assertTrue(updateTodo > 0);
    }

    @Test//using the @Test annotation //method used to test deleteTodo
    @Transactional//@Transactional annotation is used when we use @Query annotation for modifying column
    public void deleteTodo(){
        int deleteTodo = todoRepository.deleteTodoById(Long.valueOf("2"), Long.valueOf("4"));

        assertTrue(deleteTodo > 0);
    }

}
