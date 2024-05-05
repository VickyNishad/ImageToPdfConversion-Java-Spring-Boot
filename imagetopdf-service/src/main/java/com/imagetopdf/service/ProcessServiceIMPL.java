package com.imagetopdf.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageInputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.imagetopdf.process.ProcessService;

@Service
public abstract class ProcessServiceIMPL implements ProcessService {

	private final static String INPUTPATH = "";
	private final static String OUTPUTPATH = "";

	@Override
	public ResponseEntity<?> doConvertIMGToPDF(MultipartFile file) {
		// TODO Auto-generated method stub
		return null;
	}

	public ResponseEntity<?> ImageToPdf() {
		try {

//			String filePath = "C:\\Users\\vicky\\OneDrive\\Desktop\\Image\\KYC-Form.pdf";

//			List<PDField> fields = extractFieldNames(filePath);
//			 try {
//		            List<String> fieldNames = extractFieldNames(filePath);
//		            if (!fieldNames.isEmpty()) {
//		                System.out.println("Field names in the PDF form:");
////		                for (String fieldName : fieldNames) {
////		                    System.out.println(fieldName);
////		                }
//		            } else {
//		                System.out.println("No fields found in the PDF form.");
//		            }
//		        } catch (IOException e) {
//		            e.printStackTrace();
//		        }
//			

			BufferedImage image = ImageIO.read(new File(INPUTPATH));
			// Check image orientation and rotate if necessary
			int orientation = getImageOrientation(INPUTPATH); // Default orientation (no rotation)

			// Rotate image if necessary
			if (orientation == 6 || orientation == 8) { // 6: 90 degrees clockwise, 8: 90 degrees counter-clockwise
				int width = image.getWidth();
				int height = image.getHeight();
				BufferedImage rotatedImage = new BufferedImage(height, width, image.getType());
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						rotatedImage.setRGB(y, width - 1 - x, image.getRGB(x, y));
					}
				}
				image = rotatedImage;
			}

			PDDocument document = new PDDocument();
			PDPage page = new PDPage();
			document.addPage(page);

//			PDImageXObject pdImage = LosslessFactory.createFromImage(document, image);
//			PDPageContentStream contentStream = new PDPageContentStream(document, page);
//			float scale = 1f;
//			contentStream.drawImage(pdImage, 0, 0,pdImage.getWidth()*scale, pdImage.getHeight()*scale);

			float pageWidth = page.getMediaBox().getWidth();
			float pageHeight = page.getMediaBox().getHeight();
			float imageWidth = image.getWidth();
			float imageHeight = image.getHeight();

			float scale = Math.min(pageWidth / imageWidth, pageHeight / imageHeight);

			PDImageXObject pdImage = LosslessFactory.createFromImage(document, image);
			PDPageContentStream contentStream = new PDPageContentStream(document, page);

			float offsetX = (pageWidth - imageWidth * scale) / 2;
			float offsetY = (pageHeight - imageHeight * scale) / 2;
			contentStream.drawImage(pdImage, offsetX, offsetY, imageWidth * scale, imageHeight * scale);

			contentStream.close();

			document.save(OUTPUTPATH);
			document.close();

			System.out.println("PDF created successfully");
			return new ResponseEntity<String>(HttpStatus.OK).ok("PDF created successfully");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static int getImageOrientation(String imagePath) throws IOException {
		ImageInputStream input = ImageIO.createImageInputStream(new File(imagePath));
		ImageReader reader = ImageIO.getImageReaders(input).next();
		reader.setInput(input);
		IIOMetadata metadata = reader.getImageMetadata(0);
//		if (metadata != null) {
//			String[] names = metadata.getMetadataFormatNames();
//			for (String name : names) {
//				System.out.println("Format name: " + name);
//				IIOMetadataNode root = (IIOMetadataNode)metadata.getAsTree(name);
//				printNode(root, 0);
//			}
//		}

		int orientation = 1; // Default orientation
		if (metadata != null) {
			IIOMetadataNode root = (IIOMetadataNode) metadata.getAsTree(metadata.getNativeMetadataFormatName());
			IIOMetadataNode orientationNode = (IIOMetadataNode) root.getElementsByTagName("tiff:Orientation").item(0);
			if (orientationNode != null) {
				orientation = Integer.parseInt(orientationNode.getAttribute("value"));
			}
		}
		input.close();
		return orientation;
	}

}
