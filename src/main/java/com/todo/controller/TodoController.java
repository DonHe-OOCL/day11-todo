package com.todo.controller;

import com.todo.common.ResponseEntity;
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
    public ResponseEntity<List<Todo>> getTodos() {
        return ResponseEntity.success(todoService.getTodoList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Integer id) {
        return ResponseEntity.success(todoService.getTodoById(id));
    }

    @PostMapping
    public ResponseEntity<Todo> addTodo(@RequestBody Todo todo) {
        return ResponseEntity.success(todoService.addTodoItem(todo));
    }

    @PutMapping
    public ResponseEntity<Todo> updateTodoItem(@RequestBody Todo todo) {
        return ResponseEntity.success(todoService.updateTodoItem(todo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteTodoById(@PathVariable Integer id) {
        todoService.deleteTodoItem(id);
        return ResponseEntity.success(id);
    }
}
