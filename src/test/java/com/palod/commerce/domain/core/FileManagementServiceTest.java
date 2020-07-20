package com.palod.commerce.domain.core;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.palod.commerce.domain.core.service.impl.FileManagementServiceImpl;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class FileManagementServiceTest {

	@Autowired
	private FileManagementServiceImpl fileManagementService;

	@Value("${upload.directory}")
	private String uploadDirectoryName;

	@Test
	public void whenInitUploadPath_thenCreateUploadPath() throws IOException {
		deleteFilesInUploadPath();

		Path path = Paths.get(uploadDirectoryName);
		Files.deleteIfExists(path);

		fileManagementService.initUploadPath();

		assertTrue(Files.exists(path));
	}

	@Test
	public void givenFileNameAndInputStream_whenSaveFile_thenCreateFile() throws IOException {
		String fileName = "testFile";
		byte[] data = "testdata".getBytes();
		InputStream inputStream = new ByteArrayInputStream(data);

		String savedFilePath = fileManagementService.saveFile(fileName, inputStream);

		assertTrue(Files.exists(Paths.get(savedFilePath)));
		assertTrue(Arrays.equals(data, Files.readAllBytes(Paths.get(savedFilePath))));
	}

	@Test
	public void givenExistFileName_whenDeleteFile_thenDeleteFile() throws IOException {
		Path filePath = Paths.get(uploadDirectoryName).resolve("deleteTestFile");
		Files.copy(new ByteArrayInputStream("testdata".getBytes()), filePath);

		String absoluteFilePath = filePath.toAbsolutePath().toString();

		assertTrue(Files.exists(Paths.get(absoluteFilePath)));

		fileManagementService.deleteFile(absoluteFilePath);

		assertFalse(Files.exists(Paths.get(absoluteFilePath)));
	}

	private void deleteFilesInUploadPath() throws IOException {
		Files.walk(Paths.get(uploadDirectoryName))
			.sorted(Comparator.reverseOrder())
			.map(Path::toFile)
			.forEach(File::delete);
	}

}
