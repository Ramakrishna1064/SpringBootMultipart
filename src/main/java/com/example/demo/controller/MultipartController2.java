package com.example.demo.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.example.demo.dto.DBFile;
import com.example.demo.model.UploadFileResponse;
import com.example.demo.repository.MultipartRepository1;
import com.example.demo.service.MultipartService2;
import com.example.demo.util.Utils;

@RestController
public class MultipartController2 {

	@Autowired
	private MultipartService2 multipartService2;

	@Autowired
	private MultipartRepository1 multipartRepository1;

	@PostMapping("/uploadFileToDB")
	public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {

		DBFile dbFile = multipartService2.storeFile(file);

		System.out.println("asda" + dbFile.getData());

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(dbFile.getId()).toUriString();

		return new UploadFileResponse(dbFile.getFile_name(), fileDownloadUri, file.getContentType(), file.getSize());
	
	}

	@PostMapping("/uploadMultipleFilesToDB")
	public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
		return Arrays.asList(files).stream().map(file -> uploadFile(file)).collect(Collectors.toList());
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getListOfFiles", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public void getListOfUsers(@RequestBody DBFile dBFile) {
		List<DBFile> list = multipartRepository1.findAll();
		list.forEach(item -> {
			try {
	            System.out.println("Multi to file is----->"+ item.getFile_name());
	            System.out.println("URL is---->"+ new File(item.getFile_name()).toURI().toURL().toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

}
