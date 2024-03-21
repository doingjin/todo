package com.zin.tada.controller;

import com.zin.tada.dto.ResponseDTO;
import com.zin.tada.dto.TodoDTO;
import com.zin.tada.model.TodoEntity;
import com.zin.tada.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("/test")
    public ResponseEntity<?> testTodo () {

        String str = todoService.testService();

        List<String> list = new ArrayList<>();
        list.add(str);

        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("")
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto) {
        try {
            String temporaryUserId = "temporary-user";

            TodoEntity entity = TodoDTO.toEntity(dto);

            entity.setId(null);

            // 임시유저아이디로 설정한다. 인증인가에서 수정 예정
            entity.setUserId(temporaryUserId);

            List<TodoEntity> entities = todoService.createTodo(entity);

            // 자바 스트림을 이용해 리턴된 엔티티 리스트를 dto 리스트로 변환
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            String error = e.getMessage();

            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }



}
