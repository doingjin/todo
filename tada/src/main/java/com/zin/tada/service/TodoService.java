package com.zin.tada.service;

import com.zin.tada.model.TodoEntity;
import com.zin.tada.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TodoService {
    // @Service => 스테레오타입 어노테이션이며, 내부에 @Component 어노테이션을 가지고 있다.

    @Autowired
    private TodoRepository repository;


    public String testService () {
        // TodoEntity 생성
        TodoEntity entity = TodoEntity.builder().title("My first todo item").build();
        // TodoEntity 저장
        repository.save(entity);
        // TodoEntity 검색
        TodoEntity savedEntity = repository.findById(entity.getId()).get();

        return savedEntity.getTitle();
    }

    public List<TodoEntity> createTodo (TodoEntity entity) {

        // Validation
        validateEntity(entity);

        repository.save(entity);

        log.info("Entity Id : {} is saved", entity.getUserId());

        return repository.findByUserId(entity.getUserId());
    }



    private void validateEntity (TodoEntity entity) {
        if (entity == null) {
            log.warn("Entity cannot be null");

            throw new RuntimeException("Entity cannot be null");
        }

        if (entity.getUserId() == null) {
            log.warn("Unknown User");

            throw new RuntimeException("Unknown User");
        }
    }


}
