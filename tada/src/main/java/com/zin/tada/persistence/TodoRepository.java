package com.zin.tada.persistence;

import com.zin.tada.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String> {

    List<TodoEntity> findByUserId(String userId);

    @Query(value = "select * from todo_test.TODO t where t.userId = ?1", nativeQuery = true)
    List<TodoEntity> findeByUserIdQuery(String userId);




}
