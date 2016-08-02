package kz.gbk.eprocurement.web.api.controllers.files

import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import org.apache.commons.lang3.StringUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.multipart.MultipartFile

import java.nio.file.Files
import java.nio.file.Paths
import java.text.SimpleDateFormat

@Controller
class FileUploadController {

    static class FileUploadException extends RuntimeException {
        FileUploadException(String message) {
            super(message)
        }

        FileUploadException(String message, Throwable throwable) {
            super(message, throwable)
        }
    }

    static final Logger logger = LoggerFactory.getLogger(FileUploadController.class)

    @Autowired
    Environment env

    @RequestMapping(value = '/api/files/upload', method = RequestMethod.POST)
    @ResponseBody
    FileMetaData uploadFile(@RequestParam('uploadFile') MultipartFile uploadFile) {
        try {
            String origFilename = uploadFile.getOriginalFilename()

            SimpleDateFormat dateFormat = new SimpleDateFormat('yyyy-MM-dd')

            String rootDir = env.getProperty('app.paths.uploadedFiles')
            String relDir = '/' + dateFormat.format(new Date())

            Files.createDirectories(Paths.get(rootDir, relDir))

            String filename = UUID.randomUUID().toString() + '.' + StringUtils.substringAfterLast(origFilename, '.')

            String filepath = Paths.get(rootDir, relDir, filename).toString()

            File output = new File(filepath)
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(output))
            IOUtils.copy(uploadFile.inputStream, stream)
            stream.close()

            return new FileMetaData(
                    size: FileUtils.byteCountToDisplaySize(uploadFile.size),
                    bytes: uploadFile.size,
                    path: relDir + '/' + filename,
                    mimeType: uploadFile.contentType,
                    modified: new Date(output.lastModified())
            )
        } catch (Exception ex) {
            String msg = 'An exception occurred when trying to upload a file'
            logger.error(msg, ex)
            throw new FileUploadException(msg, ex)
        }
    }

    @ExceptionHandler(FileUploadException.class)
    @ResponseBody
    ResponseEntity<?> handleFileUploadException(FileUploadException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    }
}
