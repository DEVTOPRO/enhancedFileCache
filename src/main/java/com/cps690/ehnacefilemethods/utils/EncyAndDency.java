package com.cps690.ehnacefilemethods.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;

import org.bouncycastle.crypto.engines.ChaCha7539Engine;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.cps690.ehnacefilemethods.Dto.UserDto;
import com.cps690.ehnacefilemethods.EntityModels.UserCompInfo;
import com.cps690.ehnacefilemethods.controller.KeyInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class EncyAndDency {
	public String keyData;

	@Autowired
	public EncyAndDency(ApplicationContext applicationContext) {
//		this.keyInfo = applicationContext.getBean(KeyInfo.class);
	}

	@Autowired
	AttributeSetter attributeSetter;

	public byte[] base64Decoder(String keyString) {
		return Base64.getDecoder().decode(keyString);
	}
// Here the all method are sample for but the sample refernces 
	public Map<String,Object> encrypt(List<UserCompInfo> userDto, String key, String nonceKey, String filePath,String... optionalParams) throws Exception {
		long startTime = System.currentTimeMillis();
		Map<String,Object> cacheInfo=new HashMap<String,Object>();
		String actualFilePath = filePath;
		Security.addProvider(new BouncyCastleProvider());
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(userDto);
			objectOutputStream.close();
			EnhanceUtils enhanceData = new EnhanceUtils();
			byte[] serializedData = byteArrayOutputStream.toByteArray();
			byte[] keyBytes = base64Decoder(key);
			byte[] nonSam = base64Decoder(nonceKey);
			keyData = keyEncryption(keyBytes);
			ChaCha7539Engine chaCha20 = new ChaCha7539Engine();
			KeyParameter keyParam = new KeyParameter(keyBytes);
			ParametersWithIV parameters = new ParametersWithIV(keyParam, nonSam);
			chaCha20.init(true, parameters);
			byte[] encryptedData = new byte[serializedData.length];
			chaCha20.processBytes(serializedData, 0, serializedData.length, encryptedData, 0);

			String EncyData = Base64.getEncoder().encodeToString(encryptedData);
			System.out.println("Ency data:   " + EncyData);
			enhanceData.deletingFile(actualFilePath);
			enhanceData.writeDataToFile(EncyData, actualFilePath);
			double size = enhanceData.fileSize(actualFilePath);
			System.out.println(size);
			System.out.println("length of the list" + userDto.size());
			attributeSetter.setUserDefinedAttribute(actualFilePath,"kCMP", "ncmp");
			long endTime = System.currentTimeMillis();
			long elapsedTime = endTime - startTime;
			cacheInfo.put("id", "Ency");
			cacheInfo.put("name", "Only our Encryption method Cache system");
			cacheInfo.put("fileSize", enhanceData.fileSize(actualFilePath));
			cacheInfo.put("timeDiffer", elapsedTime);
			cacheInfo.put("sizeOfReco", userDto.size());
			return cacheInfo;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}

	public Map<String,Object> decrypt(String nonceKey, String filePath,String... optionalParams) throws Exception {
		long startTime = System.currentTimeMillis();
		Map<String,Object> cacheInfo=new HashMap<String,Object>();
		byte[] nonce = base64Decoder(nonceKey);
		String keyInof = keyDecryption(keyData);
		byte[] keyData = Base64.getDecoder().decode(keyInof);
		String sampleText;
		EnhanceUtils enhanceData = new EnhanceUtils();
		sampleText = enhanceData.readTextFromFile(filePath);
		try {
			byte[] encryptedBytes = Base64.getDecoder().decode(sampleText);
			ChaCha7539Engine chaCha20 = new ChaCha7539Engine();
			KeyParameter keyParam = new KeyParameter(keyData);
			ParametersWithIV parameters = new ParametersWithIV(keyParam, nonce);
			chaCha20.init(false, parameters);
			byte[] decryptedData = new byte[encryptedBytes.length];
			chaCha20.processBytes(encryptedBytes, 0, encryptedBytes.length, decryptedData, 0);
			
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decryptedData);
			ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
			List<UserDto> data = (List<UserDto>) objectInputStream.readObject();
			objectInputStream.close();
			System.out.println("check actual data" + data.size());
			long endTime = System.currentTimeMillis();
			long elapsedTime = endTime - startTime;
			cacheInfo.put("id", "Dency");
			cacheInfo.put("name", "Only our Dencryption method Cache system");
			cacheInfo.put("fileSize", enhanceData.fileSize(filePath));
			cacheInfo.put("timeDiffer", elapsedTime);
			cacheInfo.put("sizeOfReco", data.size());
			return cacheInfo;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String keyEncryption(byte[] key) throws Exception {
		Security.addProvider(new BouncyCastleProvider());
		byte[] plaintextBytes = key;
		Cipher cipher = Cipher.getInstance("ECIES", "BC");
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDH", "BC");
		keyPairGenerator.initialize(new ECGenParameterSpec("secp256r1"));
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		PublicKey publicKey = keyPair.getPublic();
		PrivateKey privateKey = keyPair.getPrivate();
		byte[] privateKeyBytes = privateKey.getEncoded();
		String privateKeyBase64 = Base64.getEncoder().encodeToString(privateKeyBytes);
		byte[] publicKeyBytes = publicKey.getEncoded();
		KeyFactory keyFactory = KeyFactory.getInstance("ECDH", "BC");
		X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(publicKeyBytes);
		PublicKey recipientPublicKey = keyFactory.generatePublic(pubKeySpec);

		cipher.init(Cipher.ENCRYPT_MODE, recipientPublicKey);

		byte[] ciphertext = cipher.doFinal(plaintextBytes);
		String encryptText = Base64.getEncoder().encodeToString(ciphertext);
		String actualKeyData = encryptText + "@#" + privateKeyBase64;

		return actualKeyData;
	}

	public String keyDecryption(String accessKey) throws Exception {

		String[] keyInfo = accessKey.split("@#");
		System.out.println(keyInfo[1]);
		Security.addProvider(new BouncyCastleProvider());

		byte[] ciphertextBytes = Base64.getDecoder().decode(keyInfo[0]);
		Cipher cipher = Cipher.getInstance("ECIES", "BC");

		byte[] privateKeyBytes = Base64.getDecoder().decode(keyInfo[1]);
		KeyFactory keyFactory = KeyFactory.getInstance("ECDH", "BC");
		PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
		PrivateKey recipientPrivateKey = keyFactory.generatePrivate(privKeySpec);
		cipher.init(Cipher.DECRYPT_MODE, recipientPrivateKey);
		byte[] plaintextBytes = cipher.doFinal(ciphertextBytes);
		System.out.println("deceee   " + Base64.getEncoder().encodeToString(plaintextBytes));
		return Base64.getEncoder().encodeToString(plaintextBytes);
	}

}
