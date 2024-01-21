package com.tejas.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tejas.entity.EnqStatusEntity;

public interface EnqStatusRepo extends JpaRepository<EnqStatusEntity, Integer> {

}
