package com.zin.tada.controller;

import com.zin.tada.dto.ResponseDTO;
import com.zin.tada.dto.TestRequestBodyDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping
    public String testController() {
        return "Hello world!";
    }

    @GetMapping("/testGetMapping")
    public String testControllerWithPath() {
        return "Hello world! testGetMapping";
    }

    @GetMapping("/{id}")
    public String testControllerWithPathVariables(@PathVariable(required = false) Integer id) {
        return "Hello world! ID: " + id;
    }

    @GetMapping("/request/param")
    public String testControllerRequestParam(@RequestParam(required = false) Integer id) {
        return "Hello world! ID: " + id;
    }

    @GetMapping("/request/body")
    public String testControllerRequestBody(@RequestBody TestRequestBodyDTO testRequestBodyDTO) {
        return "Hello world! ID: " + testRequestBodyDTO.getId() + " Message: " + testRequestBodyDTO.getMessage();
    }

    @GetMapping("/response/body")
    public ResponseDTO<String> testControllerResponseBody() {
        List<String> list = new ArrayList<>();
        list.add("Hello world! I'm ResponseDTO");
        ResponseDTO<String> responseDTO = ResponseDTO.<String>builder().data(list).build();

        return responseDTO;
    }

    @GetMapping("response/entity")
    public ResponseEntity<?> testControllerResponseEntity () {
        List<String> list = new ArrayList<>();
        list.add("Hello world! I'm ResponseEntity. And you got 400!");

        ResponseDTO<String> responseDTO = ResponseDTO.<String>builder().data(list).build();

        return ResponseEntity.badRequest().body(responseDTO);
    }

    @GetMapping("response/entity/ok")
    public ResponseEntity<?> testControllerResposeEntityOk () {
        List<String> list = new ArrayList<>();
        list.add("Hello world! I'm ResponseEntity. And you got 200 and OK!");

        ResponseDTO<String> responseDTO = ResponseDTO.<String>builder().data(list).build();

        return ResponseEntity.ok().body(responseDTO);
    }

}
