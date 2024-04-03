package com.cps690.ehnacefilemethods.servicesImp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cps690.ehnacefilemethods.Dto.CustomConfig;
import com.cps690.ehnacefilemethods.Dto.FileInfo;
import com.cps690.ehnacefilemethods.Dto.KeySample;
import com.cps690.ehnacefilemethods.Dto.ResponseObject;
import com.cps690.ehnacefilemethods.Dto.UserDto;
import com.cps690.ehnacefilemethods.Dto.UserInfoDto;
import com.cps690.ehnacefilemethods.EntityModels.UserCompInfo;
import com.cps690.ehnacefilemethods.EntityModels.UserInfo;
import com.cps690.ehnacefilemethods.RepoServicesMetha.UserFullInfoRepo;
import com.cps690.ehnacefilemethods.RepoServicesMetha.UserRepo;
import com.cps690.ehnacefilemethods.controller.KeyInfo;
import com.cps690.ehnacefilemethods.services.TestInfo;
import com.cps690.ehnacefilemethods.utils.AttributeSetter;
import com.cps690.ehnacefilemethods.utils.CompressionWithAlg;
import com.cps690.ehnacefilemethods.utils.EncyAndDency;
import com.cps690.ehnacefilemethods.utils.EnhanceUtils;
import com.cps690.ehnacefilemethods.utils.FileController;
import com.cps690.ehnacefilemethods.utils.SampleChaCha20;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import com.fasterxml.jackson.core.type.TypeReference;

import com.cps690.ehnacefilemethods.Dto.ResponseObject;
import java.io.FilenameFilter;

@Service
public class TestInfoImp implements TestInfo {
	@Autowired
	private CustomConfig customConfig;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private UserFullInfoRepo userFullInfoRepo;
	@Autowired
	private SampleChaCha20 sampleChaCha20;
	@Autowired
	private EncyAndDency encyAndDyency;
	@Value("${custom.nonce}")
	private String nonceKey;
	@Value("${custom.key}")
	private String keyval;
	
	FilenameFilter filter = new FilenameFilter() {
		@Override
		public boolean accept(File dir, String name) {
			return name.endsWith(".txt");
		}
	};
	@Override
	public Map<String, Object> getUserInfo(String keyData, String filePath) {
		Path path = Paths.get(filePath);
		try {
			if (Files.exists(path)) {

				Map<String, Object> decryptInfo = sampleChaCha20.decrypt(nonceKey, filePath);
				return decryptInfo;

			} else {
				long startTime = System.currentTimeMillis();
				List<UserCompInfo> userDetails = keyData != null
						? userFullInfoRepo.findByLimitByValue(Integer.parseInt(keyData))
						: userFullInfoRepo.findAll();

				long endTime = System.currentTimeMillis();
				long elapsedTime = endTime - startTime;
				System.out.println(userDetails.size() + "length" + "time is" + elapsedTime);
				try {
					Map<String, Object> userInfo = sampleChaCha20.encrypt(userDetails, keyval, nonceKey, filePath);
					return userInfo;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String saveUser(UserInfoDto userDto) {
		try {

			UserInfo userData = UserInfo.builder().age(userDto.getAge()).address(userDto.getAddress())
					.bloodGroup(userDto.getBloodGroup()).country(userDto.getCountry()).email(userDto.getEmail())
					.fullName(userDto.getFullName()).gender(userDto.getGender()).state(userDto.getState())
					.mobileNumber(userDto.getMobileNumber()).build();
			long id = userRepo.save(userData).getId();
			System.out.println(keyval);
			return id + "here it is your user id and user saved successfully";
		} catch (Exception e) {
			return "Error is save user";
		}
	}

	public Map<String, Object> encryptionDecryption(String numberOfRecords, String keyValue, String filePath) {
		try {
			if (keyValue.equalsIgnoreCase("encrypt")) {
				List<UserCompInfo> userDetails = keyValue != null
						? userFullInfoRepo.findByLimitByValue(Integer.parseInt(numberOfRecords))
						: userFullInfoRepo.findAll();

				Map<String, Object> objectData = encyAndDyency.encrypt(userDetails, keyval, nonceKey, filePath);
				return objectData;

			} else {
				Path path = Paths.get(filePath);
				if (Files.exists(path)) {
					Map<String, Object> userDetails = encyAndDyency.decrypt(nonceKey, filePath);
					return userDetails;
				} else {
					return null;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Map<String, Object> compressionAndDecompressionInfo(String numberOfRecords, String keyValue,
			String filePath) {
		long startTime = System.currentTimeMillis();
		Map<String, Object> compressData = new HashMap<String, Object>();
		ObjectMapper objectMapper = new ObjectMapper();
		try {

			CompressionWithAlg compDecompression = new CompressionWithAlg();
//			EnhanceUtils enhanceData = new EnhanceUtils();
//			List<UserCompInfo> userDetailsForCompression = numberOfRecords != null
//					? userFullInfoRepo.findByLimitByValue(Integer.parseInt(numberOfRecords))
//					: userFullInfoRepo.findAll();
//			String jsontoSerString = objectMapper.writeValueAsString(userDetailsForCompression);
//			enhanceData.writeDataToFile(jsontoSerString, filePath);
			compDecompression.compressMetho(filePath, new AttributeSetter());
			double size = enhanceData.fileSize(filePath);
			long compressEndTime = System.currentTimeMillis();
			long compressTime = compressEndTime - startTime;
			long startDecomTime = System.currentTimeMillis();

//			String decompressStringData = compDecompression.decompressMetho(filePath);
//			List<UserCompInfo> decompressList = objectMapper.readValue(decompressStringData,
//					new TypeReference<List<UserCompInfo>>() {
//					});
//
//			long decompEndTime = System.currentTimeMillis();
//			long finalDecompTime = decompEndTime - startDecomTime;
//			compressData.put("id", "CompAndDeComp");
//			compressData.put("name", "Our configuration Compression and Decompression method system");
//			compressData.put("fileSize", enhanceData.fileSize(filePath));
//			compressData.put("timeDiffer",
//					"Compression time: " + compressTime + "Decompression time: " + finalDecompTime);
//			compressData.put("sizeOfReco", userDetailsForCompression.size());
			return compressData;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Map<String, Object> compareWithOtherHybrideModel(String numberOfRecords, String specialFilePath) {
		EnhanceUtils encryptionUtility = new EnhanceUtils();
		Map<String, Object> traditionalCacheInfo = new HashMap<String, Object>();
		try {
			long startTime = System.currentTimeMillis();
			SecretKey aesKey = encryptionUtility.generateDESKey();
			KeyPair rsaKeyPair = encryptionUtility.generateRSAKeyPair();
			PublicKey rsaPublicKey = rsaKeyPair.getPublic();
			PrivateKey rsaPrivateKey = rsaKeyPair.getPrivate();
			List<UserCompInfo> userDetails = numberOfRecords != null? userFullInfoRepo.findByLimitByValue(Integer.parseInt(numberOfRecords)): userFullInfoRepo.findAll();

			String encryptedData = encryptionUtility.encryptWithDES(userDetails, aesKey);

			// Encrypt DES key with RSA public key
			byte[] encryptedAESSecretKey = encryptionUtility.encryptWithRSA(aesKey.getEncoded(), rsaPublicKey);

			// Store encrypted data and encrypted DES key in files
			encryptionUtility.writeDataToFile(encryptedData, specialFilePath + "encrypted_data.txt");
			encryptionUtility.writeDataToFile(Base64.getEncoder().encodeToString(encryptedAESSecretKey),
					specialFilePath + "encrypted_aes_key.txt");
			long endTime = System.currentTimeMillis();
			long enyRunTime = endTime - startTime;
			
			long startTime1 = System.currentTimeMillis();
			String loadedData = encryptionUtility.readTextFromFile(specialFilePath + "encrypted_data.txt");
			byte[] loadedAESSecretKey = Base64.getDecoder()
					.decode(encryptionUtility.readTextFromFile(specialFilePath + "encrypted_aes_key.txt"));

			// Decrypt DES key with RSA private key
			byte[] decryptedAESSecretKey = encryptionUtility.decryptWithRSA(loadedAESSecretKey, rsaPrivateKey);

			// Decrypt data with DES
			List<UserCompInfo> userInformation = encryptionUtility.decryptWithDES(loadedData,
					new SecretKeySpec(decryptedAESSecretKey, "AES"));

			long endTime1 = System.currentTimeMillis();
			long decyRunTime = endTime1 - startTime1;
			traditionalCacheInfo.put("id", "traditionalEncyAndDecy");
			traditionalCacheInfo.put("name", "traditional advance Alg Cache system");
			traditionalCacheInfo.put("fileSize", encryptionUtility.fileSize(specialFilePath + "encrypted_data.txt"));
			traditionalCacheInfo.put("timeDiffer", "encryption: " + enyRunTime + " decryption: " + decyRunTime);
			traditionalCacheInfo.put("sizeOfReco", userInformation.size());
			return traditionalCacheInfo;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<?> getInof(String filePath) {
		List<FileInfo> fileList = new ArrayList<FileInfo>();
		try {
			FilenameFilter filter = new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(".txt");
				}
			};
			File folder = new File(filePath);
			File[] files = folder.listFiles(filter);
			for (File file : files) {
				FileInfo fileInfo = new FileInfo();
				Path path = Paths.get(filePath + "/" + file.getName());
				BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
				long seconds = System.currentTimeMillis() / 1000 - attributes.lastModifiedTime().toMillis() / 1000;
				fileInfo.setFileName(file.getName());
				fileInfo.setCreationTimeInMin(seconds);
				fileInfo.setFileSize(Files.size(path));
				fileList.add(fileInfo);
			}
			return fileList;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public String deleteAllFileInFolder(String filePath) {
		// TODO Auto-generated method stub
			File folder = new File(filePath);
			File[] files = folder.listFiles(filter);
			for (File file : files) {

				Path path = Paths.get(filePath + "/" + file.getName());
					try {
						Files.delete(path);
					} catch (IOException e) {
						System.err.println(e.getMessage());
					}
			}
		return null;
	}

	
}
