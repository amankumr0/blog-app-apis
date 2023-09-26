package com.thakur.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


import com.thakur.blog.services.FileService;

@Component
public class FileServiceImpl implements FileService{
	

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		String name = file.getOriginalFilename();
		
		String randomId = UUID.randomUUID().toString();
		String fileName = randomId.concat(name.substring(name.lastIndexOf(".")));
		
	
		
		String filePath = path+ File.separator + fileName;
		
		File f = new File(path);
		if(!f.exists()) {
			f.mkdir();
		}
		
		Files.copy(file.getInputStream(),Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
		
		return fileName;
	}

	@Override
	public InputStream getResource(String path , String  fileName) throws FileNotFoundException {
		String file = path+ File.separator + fileName;
		InputStream is = new FileInputStream(file);	
		return is;
	}

}
