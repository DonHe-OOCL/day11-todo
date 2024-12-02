package com.todo.controller;

import com.todo.entity.Todo;
import com.todo.service.TodoService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TodoController {

    private TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    public List<Todo> getTodos() {
        return todoService.getTodoList();
    }

}
