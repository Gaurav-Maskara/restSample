package com.negd.umang.coir.exceptionhandler;


import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import com.negd.umang.coir.response.pojo.CoirMasterResPojo;


@ControllerAdvice
public class CoirExceptionController {
	
	@Autowired
	private CoirMasterResPojo coirMasterResPojo;
	
	 @ExceptionHandler({Exception.class})
	 public ResponseEntity<?> exception(Exception exception) {
		   
		      coirMasterResPojo.setPd(null);
		      coirMasterResPojo.setRs("F");
		      coirMasterResPojo.setRc("503");
		      coirMasterResPojo.setRd(exception.getLocalizedMessage());
		   
	      return new ResponseEntity<>(coirMasterResPojo, HttpStatus.SERVICE_UNAVAILABLE);
	 }
  
   
   @ExceptionHandler({RestClientException.class, URISyntaxException.class})
   public ResponseEntity<?> exception(RestClientException ex) {
	   
	      coirMasterResPojo.setPd(null);
	      coirMasterResPojo.setRs("F");
	      coirMasterResPojo.setRc("503");
	      coirMasterResPojo.setRd("Failed to get response from department");
	   
      return new ResponseEntity<>(coirMasterResPojo, HttpStatus.SERVICE_UNAVAILABLE);
   }
   
   
   @ExceptionHandler({MethodArgumentNotValidException.class})
   public ResponseEntity<?> exception(MethodArgumentNotValidException ex) {
	      coirMasterResPojo.setPd(null);
	      coirMasterResPojo.setRs("F");
	      coirMasterResPojo.setRc("400");
	      ex.getBindingResult().getFieldErrors().forEach(error -> 
	 	  coirMasterResPojo.setRd( error.getDefaultMessage()));
      return new ResponseEntity<>(coirMasterResPojo, HttpStatus.BAD_REQUEST);
   } 
   
   
   @ExceptionHandler({BasicValidationException.class})
   public ResponseEntity<?> exception(BasicValidationException ex) {
	   
	      coirMasterResPojo.setPd(null);
	      coirMasterResPojo.setRs("F");
	      coirMasterResPojo.setRc("400");
	      coirMasterResPojo.setRd(ex.getLocalizedMessage());
	   
      return new ResponseEntity<>(coirMasterResPojo, HttpStatus.BAD_REQUEST);
   }
   
   
   @ExceptionHandler({NoSuchAlgorithmException.class, NoSuchPaddingException.class, InvalidKeyException.class, InvalidAlgorithmParameterException.class, IllegalBlockSizeException.class, BadPaddingException.class, UnsupportedEncodingException.class})
   public ResponseEntity<?> exception(NoSuchAlgorithmException ex) {
	   
	      coirMasterResPojo.setPd(null);
	      coirMasterResPojo.setRs("F");
	      coirMasterResPojo.setRc("503");
	      coirMasterResPojo.setRd("Error in decrypting department response");
	   
      return new ResponseEntity<>(coirMasterResPojo, HttpStatus.SERVICE_UNAVAILABLE);
   }
   
   @ExceptionHandler({HttpClientErrorException.class})
   public ResponseEntity<?> exception(HttpClientErrorException ex) {
	   
	      coirMasterResPojo.setPd(null);
	      coirMasterResPojo.setRs("F");
	      coirMasterResPojo.setRc("503");
	      coirMasterResPojo.setRd("Department API is down");
	   
      return new ResponseEntity<>(coirMasterResPojo, HttpStatus.SERVICE_UNAVAILABLE);
   }
  
}
