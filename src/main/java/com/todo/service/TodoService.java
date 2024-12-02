package com.todo.service;

import com.todo.entity.Todo;
import com.todo.exception.TodoItemNotFoundException;
import com.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    private TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getTodoList() {
        return todoRepository.findAll();
    }

    public Todo getTodoById(Integer id) {
        return todoRepository.findById(id).orElseThrow(TodoItemNotFoundException::new);
    }

    public Todo addTodoItem(Todo todo) {
        return todoRepository.save(todo);
    }

    public Todo updateTodoItem(Todo todo) {
        if (todoRepository.existsById(todo.getId())) {
            return todoRepository.save(todo);
        } else {
            throw new TodoItemNotFoundException();
        }
    }
}
