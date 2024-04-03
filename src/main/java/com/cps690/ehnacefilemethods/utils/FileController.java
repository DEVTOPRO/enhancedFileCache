package com.cps690.ehnacefilemethods.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class FileController {
	FilenameFilter filter = new FilenameFilter() {
		@Override
		public boolean accept(File dir, String name) {
			return name.endsWith(".txt");
		}
	};
// it is helpful for the continoution monitoring of the file cache system 
	@Before("execution(* java.nio.file.Files.delete(..)) && args(path)")
	public void monitorFileDeletion(String filePath) {
		try {
			File folder = new File(filePath);
			File[] files = folder.listFiles(filter);
			for (File file : files) {
				System.out.println(file.getName());
				Path path = Paths.get(filePath + "/" + file.getName());
				BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
				long currentTime = new Date().getTime();
				long fileModifiedTime = attributes.lastModifiedTime().toMillis();
				long timeDifference = currentTime - fileModifiedTime;
				System.out.println("Attempted deletion of file within the set time period: "
						+ attributes.lastModifiedTime().toMillis());
				if (timeDifference > 120000) {
					System.out.println("Attempted deletion of file within the set time period: " + path);
					try {
						Files.delete(path);
						System.out.println("File deleted successfully.");
					} catch (IOException e) {
						System.err.println("Failed to delete the file: " + e.getMessage());
					}
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
			// Handle exceptions related to file attributes or deletion
		}

	}
}
