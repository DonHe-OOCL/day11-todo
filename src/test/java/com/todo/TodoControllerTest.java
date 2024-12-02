package com.todo;

import com.todo.entity.Todo;
import com.todo.repository.TodoRepository;
import com.todo.service.TodoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class TodoControllerTest {
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
//
//    @Test
//    void should_throw_EmployeeAgeNotValidException_when_create_given_a_employee_with_age_17() {
//        //given
//        Employee kitty = new Employee(1, "Kitty", 6, Gender.FEMALE, 8000.0);
//        //when
//        //then
//        assertThrows(EmployeeAgeNotValidException.class, () -> employeeService.create(kitty));
//        verify(mockedEmployeeRepository, never()).save(any());
//    }
//
//    @Test
//    void should_throw_EmployeeAgeNotValidException_when_create_given_a_employee_with_age_66() {
//        //given
//        Employee kitty = new Employee(1, "Kitty", 66, Gender.FEMALE, 8000.0);
//        //when
//        //then
//        assertThrows(EmployeeAgeNotValidException.class, () -> employeeService.create(kitty));
//        verify(mockedEmployeeRepository, never()).save(any());
//    }
//
//    @Test
//    void should_created_employee_active_when_create_employee() {
//        //given
//        Employee lucy = new Employee(1, "Lucy", 18, Gender.FEMALE, 8000.0);
//        //when
//        employeeService.create(lucy);
//        /* then */
//        verify(mockedEmployeeRepository).save(argThat(Employee::getActive));
//    }
//
//    @Test
//    void should_throw_EmployeeAgeSalaryNotMatchedException_when_save_given_a_employee_with_age_over_30_and_salary_below_20K() {
//        //given
//        Employee bob = new Employee(1, "Bob", 31, Gender.FEMALE, 8000.0);
//        //when
//        //then
//        assertThrows(EmployeeAgeSalaryNotMatchedException.class, () -> employeeService.create(bob));
//        verify(mockedEmployeeRepository, never()).save(any());
//    }
//
//    @Test
//    void should_throw_EmployeeInactiveException_when_update_inactive_employee() {
//        //given
//        Employee inactiveEmployee = new Employee(1, "Bob", 31, Gender.FEMALE, 8000.0);
//        inactiveEmployee.setActive(false);
//        when(mockedEmployeeRepository.findById(1)).thenReturn(Optional.of(inactiveEmployee));
//        //when
//        //then
//        assertThrows(EmployeeInactiveException.class, () -> employeeService.update(1, inactiveEmployee));
//        verify(mockedEmployeeRepository, never()).save(any());
//    }
}
