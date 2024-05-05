package com.imagetopdf.process;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ProcessService {
	public ResponseEntity<?> doConvertIMGToPDF(MultipartFile file);
//	public ResponseEntity<?> getImageOrientation(MultipartFile file);
//	public ResponseEntity<?> getImageOrientation(String fileName);
}
