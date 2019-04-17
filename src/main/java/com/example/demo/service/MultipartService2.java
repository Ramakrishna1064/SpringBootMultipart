package com.example.demo.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.dto.DBFile;
import com.example.demo.model.FileStorageException;
import com.example.demo.repository.MultipartRepository1;
import org.springframework.util.StringUtils;

@Service
public class MultipartService2 {

	@Autowired
	private MultipartRepository1 multipartRepository1;

	public DBFile storeFile(MultipartFile file) {
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}
			DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes());
			return multipartRepository1.save(dbFile);

		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}
}
