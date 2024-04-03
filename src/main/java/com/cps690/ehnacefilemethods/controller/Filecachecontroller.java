package com.cps690.ehnacefilemethods.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import com.cps690.ehnacefilemethods.Dto.UserInfoDto;
import com.cps690.ehnacefilemethods.services.TestInfo;

import lombok.RequiredArgsConstructor;
import com.cps690.ehnacefilemethods.Dto.ResponseObject;

@RestController
@CrossOrigin("*")
@RequestMapping("/cachemethods")
public class Filecachecontroller {
	private static final String String = null;
	@Autowired
	private TestInfo testInfo; 
	RestTemplate restTemplate= new RestTemplate();
	String filePath="D:/cachestore/service1/";
	String specialPath="D:/cachestore/serviceSpecial1/";
	
	/**
	 * This method is helps to store the cache data and retrive the cache data
	 * It is for taking requesting with paramas and pass service method
	 * @param keyData is number of records get from Data base.
	
	 * @return  "responseCode": 200,"responseMessage": "Success","statusMessage": "Data retrived success","data": object of info encryption and decryption
	 */
	@GetMapping(value = "/postandgetcache")
    public ResponseEntity<?> getUserTasks(@RequestParam(required = false)  String keyData){
        try{
        	return new ResponseEntity<>(new ResponseObject(200, "Success", "Data retrived success", testInfo.getUserInfo(keyData,filePath+"postandgetcache.txt")), HttpStatus.OK);
        } 
        catch(Exception e){
          return new ResponseEntity<>(new ResponseObject(500, "Fail", e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
        
    }
	@PostMapping(value="/saveUserInfo")
	public String saveUserInfo(@RequestBody UserInfoDto userDto) {
		try {
			System.out.println("ddd"+userDto.getFullName());
			testInfo.saveUser(userDto);
			return "Successfully save data";
		}catch(Exception e) {
			 return "Error Occures at save info";
		}
	}
	
	/**
	 * This method is helps to only encryption and decryption of our enhance method
	 * It is for taking requesting with paramas and pass service method 
	 * @param keyData is type of process either encryption or decryption.
	*@param numberOfRecords number of records get from Data base
	 * @return  "responseCode": 200,"responseMessage": "Success","statusMessage": "Data retrived success","data": object of info encryption and decryption
	 */
	@GetMapping(value="/getEncryptionOrDeEncryption")
	public ResponseEntity<?> encryptionAndDeCryption(@RequestParam(required = false) String numberOfRecords,@RequestParam String keyData) {
		return new ResponseEntity<>(new ResponseObject(200, "Success", "Data retrived success", testInfo.encryptionDecryption(numberOfRecords,keyData,filePath+"getEncryptionOrDeEncryption.txt")),HttpStatus.OK);	
	}
	/**
	 * This method is helps to only compression and decompression of our enhance method
	 * It is for taking requesting with paramas and pass service method 
	 * @param keyData is type of process either compression  or decompression.
	*@param numberOfRecords number of records get from Data base
	 * @return  "responseCode": 200,"responseMessage": "Success","statusMessage": "Data retrived success","data": object of info compression and decompression
	 */
	@GetMapping(value="/getCompressionAndDecompression")
	public ResponseEntity<?> compressionAndDecompression(@RequestParam(required = false) String numberOfRecords,@RequestParam(required = false) String keyData) {
		return new ResponseEntity<>(new ResponseObject(200, "Success", "Data retrived success",testInfo.compressionAndDecompressionInfo(numberOfRecords,keyData,filePath+"getCompressionAndDecompression.txt")),HttpStatus.OK);	
	}
	/**
	 * This method is helps to traditional  encryption and decryption of  technique
	 * It is for taking requesting with paramas and pass service method 
	 * @param keyData is type of process either encryption or decryption.
   	*@param numberOfRecords number of records get from Data base
	 * @return  "responseCode": 200,"responseMessage": "Success","statusMessage": "Data retrived success","data": object of info encryption and decryption
	 */
	@GetMapping(value="/getComparOtherHybrideModuls")
	public ResponseEntity<?> comparesionOtherHybrideMethod(@RequestParam(required = false) String numberOfRecords) {
		return new ResponseEntity<>(new ResponseObject(200, "Success", "Data retrived success",testInfo.compareWithOtherHybrideModel(numberOfRecords,specialPath)),HttpStatus.OK);
	}
	/**
	 * This method is helps for consistancy process
	 * It is for taking requesting with paramas and pass service method 
	 * @param no more request parameter but it is calling every few second for monitoring the system
	 * @return  "responseCode": 200,"responseMessage": "Success","statusMessage": "Data retrived success","data": object of info encryption and decryption
	 */
	@GetMapping(value="/getListOfFiles")
	public ResponseEntity<?> getListOfFiles() {
		return new ResponseEntity<>(new ResponseObject(200, "Success", "Data Access success",testInfo.getInof(filePath)),HttpStatus.OK);	
	}
  /**
   * Delete all the files in cache location*/
	
	@GetMapping(value="/deleteAllFiles")
	public ResponseEntity<?> deleteAllFiles() {
		return new ResponseEntity<>(new ResponseObject(200, "Success", "Data Access success",testInfo.deleteAllFileInFolder(filePath)),HttpStatus.OK);	
	}
	
	
	
	@GetMapping(value="/testingRestTemp")
	public String testRestTemplet(){
		HttpHeaders headers =new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		System.out.println(headers.getDate());
		HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
		System.out.println(requestEntity.toString());
		ResponseEntity<String> responsData= restTemplate.exchange("https://jsonplaceholder.typicode.com/todos/1", HttpMethod.GET,requestEntity,String.class);
		String userInfo=responsData.getBody();
		System.out.println("Printing the user data"+userInfo+"heders data"+responsData.getHeaders());
		return new String("It is works");
				
		
	}
  
}
