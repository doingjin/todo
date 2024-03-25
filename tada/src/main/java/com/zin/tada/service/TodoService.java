package com.zin.tada.service;

import com.zin.tada.model.TodoEntity;
import com.zin.tada.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TodoService {
    // @Service => 스테레오타입 어노테이션이며, 내부에 @Component 어노테이션을 가지고 있다.

    @Autowired
    private TodoRepository repository;


    public String testService() {
        // TodoEntity 생성
        TodoEntity entity = TodoEntity.builder().title("My first todo item").build();
        // TodoEntity 저장
        repository.save(entity);
        // TodoEntity 검색
        TodoEntity savedEntity = repository.findById(entity.getId()).get();

        return savedEntity.getTitle();
    }

    public List<TodoEntity> createTodo(TodoEntity entity) {

        // Validation
        validateEntity(entity);

        repository.save(entity);

        log.info("Entity Id : {} is saved", entity.getUserId());

        return repository.findByUserId(entity.getUserId());
    }

    /**
     * 유저아이디에 따라 TodoList 조회
     * @param userId 유저아이디
     * @return List<TodoEntity>
     */
    public List<TodoEntity> retrieve(final String userId) {
        return repository.findByUserId(userId);
    }

    /**
     *  할 일 업데이트
     * @param entity TodoEntity
     * @return List<TodoEntity>
     */
    public List<TodoEntity> updateTodo(TodoEntity entity) {

        validateEntity(entity);

        Optional<TodoEntity> original = repository.findById(entity.getId());

        original.ifPresent(todo -> {
            // original이 존재하면 새로 넣을 title & done todo에 set!
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());

            // DB에 새 값 저장
            repository.save(todo);
        });

        return repository.findByUserId(entity.getUserId());
    }

    /**
     * 할 일 삭제
     * @param entity TodoEntity
     * @return List<TodoEntity>
     */
    public List<TodoEntity> deleteTodo(TodoEntity entity) {

        validateEntity(entity);

        try {
            repository.delete(entity);

        } catch (Exception e) {
            log.error("Error deleting entity ", entity.getId(), e);

            throw new RuntimeException("Error deleting entity " + entity.getId());
        }

        return repository.findByUserId(entity.getUserId());
    }


    /**
     * 엔티티 객체 및 유저아이디 밸리데이션
     * @param entity TodoEntity
     */
    private void validateEntity(TodoEntity entity) {
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
