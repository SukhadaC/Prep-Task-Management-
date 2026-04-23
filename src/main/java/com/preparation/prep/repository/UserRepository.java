package com.preparation.prep.repository;


import com.preparation.prep.entity.TaskUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<TaskUser,Long> {

    public TaskUser findByUsername(String username);
}
