package com.tejas.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tejas.entity.StudentEnqEntity;

public interface StudentEnqRepo extends JpaRepository<StudentEnqEntity, Integer> {

}
