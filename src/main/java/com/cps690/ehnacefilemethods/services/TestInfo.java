package com.cps690.ehnacefilemethods.services;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.cps690.ehnacefilemethods.Dto.FileInfo;
import com.cps690.ehnacefilemethods.Dto.UserInfoDto;

public interface TestInfo {
public Map<String, Object> getUserInfo(String keyData,String filePath);

public String saveUser(UserInfoDto userDto);

public Map<String, Object> encryptionDecryption(String numberOfRecoreds,String Key,String filePath);

public Map<String, Object> compressionAndDecompressionInfo(String numberOfRecords,String keyData, String filePath);

public Map<String, Object> compareWithOtherHybrideModel(String numberOfRecords, String specialPath);

public List<?> getInof(String filePath);
public String  deleteAllFileInFolder(String filePath);
}
