package com.cps690.ehnacefilemethods.utils;

import java.nio.file.*;
import java.nio.file.attribute.UserDefinedFileAttributeView;

import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.io.IOException;

@Component
public class AttributeSetter {

	// Method to set a custom attribute for a file base on the requriment
	public void setUserDefinedAttribute(String fileRoute, String attribute, String value) throws IOException {
		Path filePath = Paths.get(fileRoute);
		UserDefinedFileAttributeView view = Files.getFileAttributeView(filePath, UserDefinedFileAttributeView.class);
		if (view == null) {
			System.out.println("User Defined FileAttribute View not supported on for this system.");
			return;
		}
		if (attribute.length() > view.name().length()) {
			throw new IllegalArgumentException("Attribute name is too long.");
		}
		view.write(attribute, ByteBuffer.wrap(value.getBytes()));
	}

	// Method to get a custom attribute from a file
	public String getUserDefinedAttribute(String filePath, String arttributeKey) throws IOException {
		Path file = Paths.get(filePath);
		if (Files.exists(file)) {
			// Proceed with reading attributes
			UserDefinedFileAttributeView view = Files.getFileAttributeView(file, UserDefinedFileAttributeView.class);
			ByteBuffer readBuffer = ByteBuffer.allocate(view.size(arttributeKey));
			view.read(arttributeKey, readBuffer);
			readBuffer.flip();
			String retrievedValue = new String(readBuffer.array());
			return retrievedValue;
		} else {
			System.out.println("File not found: ");
		}
		return null;

	}
}
