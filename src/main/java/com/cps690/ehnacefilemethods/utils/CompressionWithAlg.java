package com.cps690.ehnacefilemethods.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.github.luben.zstd.ZstdInputStream;
import com.github.luben.zstd.ZstdOutputStream;

public class CompressionWithAlg {
	public void compressMetho(String filePath, AttributeSetter attributeSetter) {

		try {
			Path path = Paths.get(filePath);
			String fileContent = Files.readString(path);
			System.out.println("File content as a string:");
			byte[] dataToCompress = fileContent.getBytes();
			OutputStream fos = new FileOutputStream(filePath);
			try (ZstdOutputStream zstdOutputStream = new ZstdOutputStream(fos)) {
				zstdOutputStream.write(dataToCompress);
			}
			attributeSetter.setUserDefinedAttribute(filePath, "kCMP", "cmp");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String decompressMetho(String filePath) throws FileNotFoundException {
		InputStream fis = new FileInputStream(filePath);
		try (ZstdInputStream zstdInputStream = new ZstdInputStream(fis)) {
			byte[] buffer = new byte[9996];
			int bytesRead;
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			while ((bytesRead = zstdInputStream.read(buffer)) != -1) {
				output.write(buffer, 0, bytesRead);
			}
			String stringData = new String(output.toByteArray());
			System.out.println("sample  " + stringData);
			return stringData;
		} catch (IOException e) {
			e.printStackTrace();

		}
		return filePath;

	}
}
