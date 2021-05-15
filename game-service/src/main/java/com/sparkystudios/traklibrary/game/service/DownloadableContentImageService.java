package com.sparkystudios.traklibrary.game.service;

import com.sparkystudios.traklibrary.game.domain.DownloadableContentImage;
import com.sparkystudios.traklibrary.game.domain.ImageSize;
import com.sparkystudios.traklibrary.game.service.dto.DownloadableContentDto;
import com.sparkystudios.traklibrary.game.service.dto.ImageDataDto;
import com.sparkystudios.traklibrary.game.service.exception.UploadFailedException;
import org.springframework.web.multipart.MultipartFile;

public interface DownloadableContentImageService {

    /**
     * Given a {@link DownloadableContentDto} ID and a {@link MultipartFile}, this service method
     * will attempt to upload the given image for the given {@link DownloadableContentDto} to the
     * implemented image provider. When an image is being uploaded, a {@link DownloadableContentImage}
     * instance will be persisted, which will contain information about the file and which {@link DownloadableContentDto}
     * it is linked to. Only one {@link DownloadableContentImage} can be persisted at any one for a
     * {@link DownloadableContentDto}.
     *
     * The {@link DownloadableContentImageService} assumes that the image is stored within a different location, such as central
     * image provider such as an AWS S3 bucket. Image storage is handled by the image service.
     *
     * If the image fails to upload or persist for any reason, a {@link UploadFailedException}
     * will be thrown to the callee.
     *
     * @param downloadableContentId The ID of the {@link DownloadableContentDto} to upload an image for.
     * @param imageSize Which image size this image will be associated with.
     * @param multipartFile The image that is to be associated with the given {@link DownloadableContentDto}.
     *
     * @throws UploadFailedException Thrown if image uploading fails.
     */
    void upload(long downloadableContentId, ImageSize imageSize, MultipartFile multipartFile);

    /**
     * Given the ID of a {@link DownloadableContentDto}, this method will attempt to find the
     * {@link DownloadableContentImage} that is mapped to the given ID with the specified {@link ImageSize}. If one is found,
     * the method will invoke the image service to return the byte data of the given image file that is
     * associated with the {@link DownloadableContentImage} and return the byte data with additional information wrapped in
     * a {@link ImageDataDto} object.
     *
     * If the download of the image data fails, an {@link ImageDataDto} is still returned, but the {@link ImageDataDto#getContent()}
     * will return an empty byte array.
     *
     * @param downloadableContentId The ID of the {@link DownloadableContentDto} to retrieve image data for.
     *
     * @return An {@link ImageDataDto} object that contains the image information for the given {@link DownloadableContentDto}.
     */
    ImageDataDto download(long downloadableContentId, ImageSize imageSize);
}