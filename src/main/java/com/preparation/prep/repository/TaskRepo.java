package com.preparation.prep.repository;


import com.preparation.prep.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepo extends JpaRepository<Task,Long> {

    @Query("select t from Task t where t.user.id=:userId")
    public List<Task> findByUserId(long userId);

}
