package com.tejas.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tejas.entity.CourseEntity;

public interface CourseRepo extends JpaRepository<CourseEntity, Integer> {

}
