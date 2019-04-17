package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.dto.DBFile;

public interface MultipartRepository1 extends JpaRepository<DBFile, Integer>{
	
}
