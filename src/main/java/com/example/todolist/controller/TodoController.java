package com.example.todolist.controller;

import com.example.todolist.dto.ResponseDTO;
import com.example.todolist.dto.TodoDTO;
import com.example.todolist.model.TodoEntity;
import com.example.todolist.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("todo")
public class TodoController {

    @Autowired
    private TodoService service;
    @GetMapping("/test")
    public ResponseEntity<?> testTodo(){
        String str = service.testService();
        List<String> list = new ArrayList<>();
        list.add(str);

        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<?> createTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDTO dto) {
        try {
            // 1.TodoEntity로 변환
            TodoEntity entity = TodoDTO.todoEntity(dto);

            // 2.id null로 초기화.
            entity.setId(null);

            // 3.임시 사용자 아이디 설정 -> 기존 temporary-user 대신 @AuthenticationPrincipal 에서 넘어온 userId로 설정
            entity.setUserId(userId);

            // 4.todoEntity 생성
            List<TodoEntity> entities = service.create(entity);

            // 5.자바 스트림을 이용해 리턴된 리스트를 todoDto 리스트로 변환
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            // 6.변환된 todoDto 리스트 이용해 ResponseDto 초기화
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder()
                    .data(dtos).build();

            // 7.responseDto리턴
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder()
                    .error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<?> retireveTodoList(@AuthenticationPrincipal String userId) {

        // 1.서비스메서드 retireve() 메서드로 TodoList 가져온다.
        List<TodoEntity> entities = service.retireve(userId);

        // 2.자바 스트림으로 엔티티를 dto로 변환
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        // 3.ResponseDto 초기화
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder()
                .data(dtos).build();

        // 4.ResponseDto 리턴
        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<?> updateTodoList(@AuthenticationPrincipal String userId,@RequestBody TodoDTO dto) {

        // 1.dto를 entity로 변환
        TodoEntity entity = TodoDTO.todoEntity(dto);

        // 2.id 초기화
        entity.setUserId(userId);

        // 3.업데이트
        List<TodoEntity> entities = service.update(entity);

        // 4.스트림으로 엔티티 dto로 변환
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        // 5.responseDto 초기화
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder()
                .data(dtos).build();

        return ResponseEntity.ok().body(response);
    }
    @DeleteMapping
    public ResponseEntity<?> deleteTodoList(@AuthenticationPrincipal String userId,@RequestBody TodoDTO dto){
        try {

            // 1.TodoEntity로 변환
            TodoEntity entity = TodoDTO.todoEntity(dto);

            // 2.임시 사용자 아이디 설정
            entity.setUserId(userId);

            // 3.entity삭제
            List<TodoEntity> entities = service.delete(entity);

            // 4.todoDto로 변환
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            // 5.response초기화
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder()
                    .data(dtos).build();

            // 6.리턴
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}
