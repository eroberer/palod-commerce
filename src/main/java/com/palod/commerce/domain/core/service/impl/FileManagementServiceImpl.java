package com.palod.commerce.domain.core.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.palod.commerce.domain.core.service.FileManagementService;

@Service
public class FileManagementServiceImpl implements FileManagementService {
	
	private Path rootDirectory;
	
	@Value("${upload.directory:upload}")
	private String uploadDirectory;

	@PostConstruct
	public void initUploadPath() throws IOException {
		this.rootDirectory = Paths.get(uploadDirectory);

		if (Files.notExists(rootDirectory)) {
			Files.createDirectory(rootDirectory);
		}
	}

	@Override
	public String saveFile(String fileName, InputStream inputStream) throws IOException {
		String uniqueFileName = UUID.randomUUID().toString() + fileName;

		Path target = rootDirectory.resolve(uniqueFileName);
		Files.copy(inputStream, target);

		return target.toAbsolutePath().toString();
	}

	@Override
	public void deleteFile(String fileName) throws IOException {
		Files.deleteIfExists(Paths.get(fileName));
	}

}
