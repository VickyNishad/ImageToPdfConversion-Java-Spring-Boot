/**
 * 
 */
package com.imagetopdf.process;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 */
public interface ProcessController {
	
	public ResponseEntity<?> doConvertIMGToPDF(MultipartFile file);
	

}
