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
        Todo existTodo = getTodoById(todo.getId());
        if (todo.getDone() != null) {
            existTodo.setDone(todo.getDone());
        }
        if (todo.getText() != null) {
            existTodo.setText(todo.getText());
        }
        return todoRepository.save(existTodo);
    }

    public Integer deleteTodoItem(Integer id) {
        todoRepository.deleteById(id);
        return id;
    }
}
