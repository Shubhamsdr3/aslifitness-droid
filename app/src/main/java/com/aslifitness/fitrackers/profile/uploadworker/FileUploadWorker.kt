package com.aslifitness.fitrackers.profile.uploadworker

import android.content.Context
import android.net.Uri
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.io.IOException
import java.util.concurrent.CountDownLatch


/**
 * Created by shubhampandey
 */
class FileUploadWorker(private val context: Context, params: WorkerParameters): CoroutineWorker(context, params) {

    private val storage: FirebaseStorage by lazy { FirebaseStorage.getInstance() }
    companion object {
        const val IMAGE_PATH = "image_path"
        const val FILE_NAME = "file_name"
        const val PROGRESS = "progress"
        const val DOWNLOAD_URL = "download_url"
    }

    override suspend fun doWork(): Result {
        val imagePath = inputData.getString(IMAGE_PATH)
        val fileName = inputData.getString(FILE_NAME) ?: ""
        if (imagePath.isNullOrEmpty()) Result.failure()
        return try {
            uploadImage(imagePath)
        } catch (exception: Exception) {
            exception.printStackTrace()
            Result.failure()
        } catch (e: IOException) {
            e.printStackTrace()
            Result.failure()
        }
    }

    private fun uploadImage(imagePath: String?): Result {
        val outputData = Data.Builder()
        val countDown = CountDownLatch(2)
        val compressedImgPath = ImageUtil.compressImage(context, imagePath)
        val storageReference = storage.reference
        val file = Uri.fromFile(File(compressedImgPath))
        val imageStorageReference = storageReference.child("images/${file.lastPathSegment}")
        var progress = 0.0
        val uploadTask = imageStorageReference.putFile(file)
        uploadTask.addOnProgressListener {
            progress = (100.0 * it.bytesTransferred) / it.totalByteCount
//            setProgressAsync(workDataOf(PROGRESS to progress)) // FIXME
        }
        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                throw task.exception!!
            }
            countDown.countDown()
            return@continueWithTask imageStorageReference.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri: Uri = task.result
                val imageUrl = downloadUri.toString()
                outputData.putString(DOWNLOAD_URL, imageUrl)
            }
            countDown.countDown()
        }
        countDown.await()
        return Result.success(outputData.build())
    }
}