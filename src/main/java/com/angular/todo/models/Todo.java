package com.angular.todo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;


@Entity(name = "todo")
//used to solve sterilization issues
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Todo implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todo_id;

    private String name;
    private String rating;
    private String content;

//    @Column(columnDefinition = "text default ongoing")
    private String status = "ongoing";
    private Long user_id;

    public Todo(){

    }
    public Todo(String name, String rating, String content, Long user_id) {
        this.name = name;
        this.rating = rating;
        this.content = content;
        this.user_id = user_id;
    }

    public Todo(Long user_id, Long todo_id, String name, String rating, String content, String status) {
        this.user_id = user_id;
        this.todo_id = todo_id;
        this.name = name;
        this.rating = rating;
        this.content = content;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getTodo_id() {
        return todo_id;
    }

    public void setTodo_id(Long todo_id) {
        this.todo_id = todo_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }


}
