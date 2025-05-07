package com.bugradasdelen.notzy.presentation.view.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bugradasdelen.notzy.R
import com.bugradasdelen.notzy.databinding.ActivityOcrCaptureBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class OcrCaptureActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOcrCaptureBinding
    private lateinit var cameraExecutor: ExecutorService
    private var imageCapture: ImageCapture? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOcrCaptureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }

        binding.captureButton.setOnClickListener { captureImage() }
        binding.backButton.setOnClickListener { finish() }

        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun captureImage() {
        val imageCapture = imageCapture ?: return

        imageCapture.takePicture(
            ContextCompat.getMainExecutor(this), object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    processImage(image)
                }

                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(
                        this@OcrCaptureActivity,
                        "Image capture failed: ${exception.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    @OptIn(ExperimentalGetImage::class)
    private fun processImage(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image ?: return

        val image = InputImage.fromMediaImage(
            mediaImage, imageProxy.imageInfo.rotationDegrees
        )

        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        Log.d("BugraLogger", "processImage: $image")

        recognizer.process(image).addOnSuccessListener { visionText ->
            Log.d("BugraLogger", "processImage: ${visionText.text}")
            // Create a new note with the recognized text
            val ocrText = visionText.text
            if (ocrText.isNotEmpty()) {
                val intent = Intent(this, NoteDetailActivity::class.java)
                intent.putExtra("ocr_text", ocrText)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(
                    this, getString(R.string.no_text_detected), Toast.LENGTH_SHORT
                ).show()
            }
            imageProxy.close()
        }.addOnFailureListener { e ->
            Toast.makeText(
                this, "Text recognition failed: ${e.message}", Toast.LENGTH_SHORT
            ).show()
            imageProxy.close()
        }.addOnCanceledListener {
            Toast.makeText(
                this, "Text recognition canceled", Toast.LENGTH_SHORT
            ).show()
            imageProxy.close()
        }.addOnCompleteListener {
            Toast.makeText(
                this, "Text recognition completed", Toast.LENGTH_SHORT
            ).show()
            imageProxy.close()
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
            }

            imageCapture =
                ImageCapture.Builder().setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                    .build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )
            } catch (exc: Exception) {
                Toast.makeText(
                    this, "Camera initialization failed: ${exc.message}", Toast.LENGTH_SHORT
                ).show()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(
                    this, getString(R.string.permissions_not_granted), Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}