package com.cellbeans.hspa.fileuploadutility;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.applicationproperty.Propertyconfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RestUploadController {

    private final Logger logger = LoggerFactory.getLogger(RestUploadController.class);

    private Propertyconfig Propertyconfig;
    private String UPLOADED_FOLDER = Propertyconfig.getprofileimagepath();
    private String UPLOADED_PATIENT_FILES = Propertyconfig.getPatientFiles();

    @PostMapping("/api/upload")
    public ResponseEntity<?> uploadFile(@RequestHeader("X-tenantId") String tenantName, @RequestPart("file") MultipartFile uploadfile) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("i am in ");
        if (uploadfile.isEmpty()) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }
        try {
            saveUploadedFiles(Arrays.asList(uploadfile));
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/api/uploadpatientfile")
    public ResponseEntity<?> uploadPatientFile(@RequestHeader("X-tenantId") String tenantName, @RequestPart("file") MultipartFile uploadfile) {
        TenantContext.setCurrentTenant(tenantName);
        if (uploadfile.isEmpty()) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }
        try {
            savePatientFiles(Arrays.asList(uploadfile));
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        System.out.println("UPLOADED_PATIENT_FILES : " + UPLOADED_PATIENT_FILES);
        return new ResponseEntity(UPLOADED_PATIENT_FILES + uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
    }

    // 3.1.2 Multiple file upload
    @PostMapping("/api/upload/multi")
    public ResponseEntity<?> uploadFileMulti(@RequestHeader("X-tenantId") String tenantName, @RequestParam("extraField") String extraField, @RequestParam("files") MultipartFile[] uploadfiles) {
        TenantContext.setCurrentTenant(tenantName);
        //  logger.debug("Multiple file upload!");
        // Get file name
        String uploadedFileName = Arrays.stream(uploadfiles).map(x -> x.getOriginalFilename()).filter(x -> !StringUtils.isEmpty(x)).collect(Collectors.joining(" , "));
        if (StringUtils.isEmpty(uploadedFileName)) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }
        try {
            saveUploadedFiles(Arrays.asList(uploadfiles));
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Successfully uploaded - " + uploadedFileName, HttpStatus.OK);
    }

    private void saveUploadedFiles(List<MultipartFile> files) throws IOException {
//        System.out.println("upload directory : " + UPLOADED_FOLDER);
        for (MultipartFile file : files) {
//            System.out.println("fileIsEmpty : " + file.isEmpty());
            if (file.isEmpty()) {
                continue; //next pls
            }
//            System.out.println("continue : ");
            byte[] bytes = file.getBytes();
            Path path = Paths.get(new File(UPLOADED_FOLDER) + "/" + file.getOriginalFilename());
//            System.out.println("path : " + path.toFile().getAbsolutePath());
            Files.write(path, bytes);
        }
    }

    private void savePatientFiles(List<MultipartFile> files) throws IOException {
        for (MultipartFile file : files) {
            System.out.println("files : " + files.isEmpty());
            System.out.println("UPLOADED_FOLDER : " + UPLOADED_FOLDER);
            System.out.println("files : " + files.isEmpty());
            if (file.isEmpty()) {
                continue; //next pls
            }
            byte[] bytes = file.getBytes();
            File destFile = new File(UPLOADED_FOLDER);
//            destFile.mkdir();
            Path path = Paths.get(destFile.getAbsolutePath() + "/" + file.getOriginalFilename());
            // Path path = Paths.get(UPLOADED_PATIENT_FILES + file.getOriginalFilename());
            Files.write(path, bytes);
        }
    }

}
