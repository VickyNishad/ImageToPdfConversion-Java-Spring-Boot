/**
 * 
 */
package com.imagetopdf.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.imagetopdf.process.ProcessController;

/**
 * 
 */
@RestController
@RequestMapping("/api/v1/pdf")
public class ImageToPdfController implements ProcessController{

	
	@Override
	public ResponseEntity<?> doConvertIMGToPDF(@RequestBody MultipartFile file) {
		// TODO Auto-generated method stub
		return null;
	}


}
