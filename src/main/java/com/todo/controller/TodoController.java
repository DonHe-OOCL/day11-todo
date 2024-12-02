package com.todo.controller;

import com.todo.entity.Todo;
import com.todo.service.TodoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("todos")
public class TodoController {

    private TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public List<Todo> getTodos() {
        return todoService.getTodoList();
    }

    @GetMapping("/{id}")
    public Todo getTodoById(@PathVariable Integer id) {
        return todoService.getTodoById(id);
    }

    @PostMapping
    public Todo addTodo(@RequestBody Todo todo) {
        return todoService.addTodoItem(todo);
    }

    @PutMapping
    public Todo updateTodoItem(@RequestBody Todo todo) {
        return todoService.updateTodoItem(todo);
    }

    @DeleteMapping("/{id}")
    public Integer deleteTodoById(@PathVariable Integer id) {
        todoService.deleteTodoItem(id);
        return id;
    }
}
