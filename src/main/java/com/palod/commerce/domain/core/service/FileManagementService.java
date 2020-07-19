package com.palod.commerce.domain.core.service;

import java.io.IOException;
import java.io.InputStream;

public interface FileManagementService {

	String saveFile(String fileName, InputStream inputStream) throws IOException;

	void deleteFile(String fileName) throws IOException;
}
