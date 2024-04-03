package com.cps690.ehnacefilemethods.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import com.cps690.ehnacefilemethods.Dto.UserDto;
import com.cps690.ehnacefilemethods.EntityModels.UserCompInfo;
import com.cps690.ehnacefilemethods.EntityModels.UserInfo;
import com.cps690.ehnacefilemethods.Dto.UserDto;

public class EnhanceUtils {
	public double fileSize(String filePath) throws IOException {
		Path path = Paths.get(filePath);
		double fileSizeKB = 0;
		if (Files.exists(path)) {
			long fileSizeBytes = Files.size(path);
			fileSizeKB = fileSizeBytes / 1024.0;
			System.out.println("File Size (Bytes): " + fileSizeKB);
		}
		return fileSizeKB;
	}

	public void writeDataToFile(String data, String filePath) {
		try (FileWriter fileWriter = new FileWriter(filePath, true)) {
			fileWriter.write(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String readTextFromFile(String filePath) {
		StringBuilder text = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				text.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return text.toString();

	}

	public String generateNonce() {
		SecureRandom secureRandom = new SecureRandom();
		byte[] nonce = new byte[12]; // 96 bits
		secureRandom.nextBytes(nonce);
		return Base64.getEncoder().encodeToString(nonce);
	}

	public void deletingFile(String filePath) {
		Path path = Paths.get(filePath);
		try {
			Files.delete(path);
			System.out.println("File deleted successfully.");
		} catch (IOException e) {
			System.err.println("Failed to delete the file: " + e.getMessage());
		}
	}

	public ObjectOutputStream inputStremData(List<UserCompInfo> userDetails) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
		objectOutputStream.writeObject(userDetails);
		objectOutputStream.close();
		return objectOutputStream;
	}

	public List<UserDto> outputStremData(byte[] decryptedData) throws Exception {
		try {
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decryptedData);
			ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
			@SuppressWarnings("unchecked")
			List<UserCompInfo> data = (List<UserCompInfo>) objectInputStream.readObject();
			objectInputStream.close();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public SecretKey generateDESKey() throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		return keyGenerator.generateKey();
	}

	public KeyPair generateRSAKeyPair() throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(2048);
		return keyPairGenerator.generateKeyPair();
	}

	public String encryptWithDES(List<UserCompInfo> userDetails, SecretKey key) throws Exception {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
		objectOutputStream.writeObject(userDetails);
		objectOutputStream.close();
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return Base64.getEncoder().encodeToString(cipher.doFinal(byteArrayOutputStream.toByteArray()));
	}

	public List<UserCompInfo>  decryptWithDES(String data, SecretKey key) throws Exception {
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(data));
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decryptedData);
		ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
		@SuppressWarnings("unchecked")
		List<UserCompInfo>  userData= (List<UserCompInfo>) objectInputStream.readObject();
		objectInputStream.close();
		System.out.println("check actual data compare alg" + userData.size());
		return userData ;
	}

	public byte[] encryptWithRSA(byte[] data, PublicKey publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}

	public  byte[] decryptWithRSA(byte[] data, PrivateKey privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}



	public static byte[] loadFromFile(String filename) throws IOException {
		try (FileInputStream fis = new FileInputStream(filename)) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int b;
			while ((b = fis.read()) != -1) {
				bos.write(b);
			}
			return bos.toByteArray();
		}
	}

}
