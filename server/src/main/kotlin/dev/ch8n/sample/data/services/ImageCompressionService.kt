package dev.ch8n.sample.data.services

import dev.ch8n.sample.routes.collection.CollectionRoutes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.coobird.thumbnailator.Thumbnails
import java.io.File
import javax.imageio.ImageIO


enum class ImageResolution {
    P480,
    P720,
    P1080,
}


class ImageCompressionService {


    suspend fun compressAndSaveImage(inputFile: File, resolution: ImageResolution): String? =
        withContext(Dispatchers.IO) {
            val height = when (resolution) {
                ImageResolution.P480 -> 480
                ImageResolution.P720 ->  720
                ImageResolution.P1080 -> 1080
            }

            val outputDir = when (resolution) {
                ImageResolution.P480 -> CollectionRoutes.IMAGE_COLLECTION_SERVER_PATH.P480
                ImageResolution.P720 -> CollectionRoutes.IMAGE_COLLECTION_SERVER_PATH.P720
                ImageResolution.P1080 -> CollectionRoutes.IMAGE_COLLECTION_SERVER_PATH.P1080
            }

            val outputFileName = inputFile.name

            val outputDirFile = File(outputDir)
            if (outputDirFile.exists().not()) outputDirFile.mkdirs()

            val resizedJpgImageFile = File(outputDirFile, outputFileName)

            val originalImage = ImageIO.read(inputFile)
            val aspectRatio = originalImage.width.toDouble() / originalImage.height.toDouble()
            val width = (height * aspectRatio).toInt()

            Thumbnails.of(inputFile)
                .size(width, height)
                .outputFormat("jpg")
                .outputQuality(0.8f)
                .toFile(resizedJpgImageFile)

            return@withContext resizedJpgImageFile.absolutePath
        }
}