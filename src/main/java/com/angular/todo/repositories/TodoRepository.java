package com.angular.todo.repositories;



import com.angular.todo.models.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Query("SELECT t from todo t where t.user_id = :userId")
    List<Todo> getAllTodoByUser_ID(@Param("userId") Long userId);

    @Query("select t from todo t where t.todo_id = :todoId and t.user_id = :userId")
    Todo getSingleTodo(@Param("todoId") Long todoId, @Param("userId") Long userId);

    @Modifying
    @Query("DELETE FROM todo t where t.user_id = :userId and t.todo_id = :todoId")
    int deleteTodoById(@Param("userId") Long userId, @Param("todoId") Long todoId);

    @Modifying
    @Query("UPDATE todo t SET t.name = :name, t.rating = :rating, t.content = :content, t.status = :status where t.todo_id = :todoId and t.user_id = :userId")
    int updateTodoById(@Param("name") String name, @Param("rating") String rating, @Param("content") String content, @Param("status") String status, @Param("todoId") Long todoId, @Param("userId") Long userId);
}
