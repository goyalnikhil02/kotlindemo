package codemwnci.bootdemo;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UploadController {

	@Autowired
	private FileStorageService fileStorageService;

	@PostMapping("/uploadFile")
	public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file,
			@RequestParam("username") String uname) throws FileStorageException {
		String fileName = fileStorageService.storeFile(file,uname);

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/"+uname+"/")
				.path(fileName).toUriString();

		return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
	}
	
	
	@PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files,@RequestParam("username") String uname) {
        return Arrays.asList(files)
                .stream()
                .map(file -> {
					try {
						return uploadFile(file,uname);
					} catch (FileStorageException e) {
						e.printStackTrace();
					}
					return null;
				})
                .collect(Collectors.toList());
    }
	
	@GetMapping("/downloadFile/{uname}/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String uname,@PathVariable String fileName, HttpServletRequest request) throws MyFileNotFoundException {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(uname,fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            //logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
        	String ext=fileName.substring(fileName.lastIndexOf("."));
            if (ext.equals(".docx")) {
            	contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
                }
                if(ext.equals(".doc"))
                {
                	
                	contentType = "application/msword";
                }
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
