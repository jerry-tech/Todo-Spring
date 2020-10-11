package com.angular.todo.Logic;

import com.angular.todo.models.Todo;
import com.angular.todo.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class Data {

    @Autowired
    private TodoRepository todoRepository;

    public List<Todo> getTodo(Long userId){
        List<Todo> getTodo = todoRepository.getAllTodoByUser_ID(userId);

        return getTodo;
    }

    public Todo getSingleTodo(Long userId, Long todoId){
        return todoRepository.getSingleTodo(todoId, userId);
    }

    @Transactional
    public boolean deleteTodo(Long userId, Long todoId){

        int deleteTodo = todoRepository.deleteTodoById(userId,todoId);

        if(deleteTodo > 0){
            return true;
        }
        return false;
    }

    @Transactional
    public boolean updateTodo(String name, String ratings, String content, String status, Long user_id, Long todo_id){

        int updateTodo = todoRepository.updateTodoById(name, ratings, content, status, todo_id, user_id);

        if(updateTodo > 0){
            return true;
        }

        return false;
    }

}
