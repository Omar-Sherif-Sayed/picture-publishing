package com.picture.publishing.utils;

import com.picture.publishing.dtos.ErrorDto;
import com.picture.publishing.enums.ErrorCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class ImageUtil {

    private static final Logger logger = LogManager.getLogger(ImageUtil.class);

    public static final String UPLOAD_DIR = "src/main/resources/static/pictures";
    public static final String UPLOAD_FOLDER = "pictures";

    private ImageUtil() { }

    /**
     * return Dimension width and height of an image <br>
     * if (image != null) -> If image = null means that the upload is not an image format
     *
     * @return Dimension
     */
    public static Dimension getImageDimension(MultipartFile image, List<ErrorDto> errors) {

        try {
            BufferedImage bufferedImage = ImageIO.read(image.getInputStream());
            if (bufferedImage != null)
                return new Dimension(bufferedImage.getWidth(), bufferedImage.getHeight());
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Error in ImageUtil in getImageDimension()", e);
            errors.add(new ErrorDto(ErrorCode.NOT_IMAGE_FILE));
        }
        return null;
    }

    public static void upload(String fileName, MultipartFile multipartFile, List<ErrorDto> errors) {

        var uploadPath = Paths.get(UPLOAD_DIR);

        try {

            if (!Files.exists(uploadPath))
                Files.createDirectories(uploadPath);

            var inputStream = multipartFile.getInputStream();
            var filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            logger.error("Error in ImageUtil in upload()", e);
            errors.add(new ErrorDto(ErrorCode.NOT_IMAGE_FILE));
        }
    }

    public static void validateSize(MultipartFile image, List<ErrorDto> errors) {

        if (image.getSize() > 2000000)
            errors.add(new ErrorDto(ErrorCode.NOT_SUPPORTED_SIZE));

    }

    public static String fileExtension(MultipartFile image, List<ErrorDto> errors) {
        var originalFilename = image.getOriginalFilename();
        if (originalFilename == null) {
            errors.add(new ErrorDto(ErrorCode.FILE_NOT_HAS_NAME));
            return null;
        }

        var originalFilenameSplitLength = originalFilename.split("\\.").length;
        if (originalFilenameSplitLength < 2) {
            errors.add(new ErrorDto(ErrorCode.FILE_NOT_HAS_EXTENSION));
            return null;
        }

        var fileExtension = originalFilename.split("\\.")[originalFilenameSplitLength - 1];
        List<String> validExtensions = List.of("PNG", "JPG", "GIF");

        if (!validExtensions.contains(fileExtension.toUpperCase()))
            errors.add(new ErrorDto(ErrorCode.NOT_SUPPORTED_EXTENSION));

        return fileExtension;
    }

    public static void delete(String fileName) {
        var path = FileSystems.getDefault().getPath(UPLOAD_DIR + "/" + fileName);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            logger.error("Error in ImageUtil in delete()", e);
        }
    }

}
