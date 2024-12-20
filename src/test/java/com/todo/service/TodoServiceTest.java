package com.todo.service;

import com.todo.entity.Todo;
import com.todo.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {
    @InjectMocks
    TodoService todoService;

    @Mock
    TodoRepository mockedTodoRepository;

    @Test
    void should_return_the_given_todos_when_getAllTodoList() {
        //given
        when(mockedTodoRepository.findAll()).thenReturn(List.of(new Todo(1, "111", false)));

        //when
        List<Todo> allEmployees = todoService.getTodoList();

        //then
        assertEquals(1, allEmployees.size());
        assertEquals("111", allEmployees.get(0).getText());
        assertEquals(false, allEmployees.get(0).getDone());
    }

    @Test
    void should_return_the_given_todo_when_getById() {
        //given
        when(mockedTodoRepository.findById(1)).thenReturn(Optional.of(new Todo(1, "111", false)));

        //when
        Todo todo = todoService.getTodoById(1);

        //then
        assertEquals("111", todo.getText());
        assertEquals(false, todo.getDone());
    }

    @Test
    void should_return_the_created_todo_when_create_given_a_todo() {
        //given
        Todo todo = new Todo(1, "111", false);
        when(mockedTodoRepository.save(todo)).thenReturn(todo);

        //when
        Todo createdTodo = todoService.addTodoItem(todo);

        //then
        assertEquals("111", createdTodo.getText());
    }
}
