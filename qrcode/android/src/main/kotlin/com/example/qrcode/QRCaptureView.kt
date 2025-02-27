package com.example.qrcode

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.BarcodeView
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.platform.PlatformView

class QRCaptureView(
    private val context: Context,
    id: Int,
    private val channel: MethodChannel
) : PlatformView, MethodCallHandler {

    private var barcodeView: BarcodeView? = null
    private val activity = context as Activity

    init {
        channel.setMethodCallHandler(this)
        checkAndRequestPermission(null)

        barcodeView = BarcodeView(activity)
        barcodeView?.decodeContinuous(object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult) {
                channel.invokeMethod("onCaptured", result.text)
            }

            override fun possibleResultPoints(resultPoints: List<ResultPoint>) {
                // Implemente a lógica para lidar com os pontos de resultado, se necessário
            }
        })
        barcodeView?.resume()

        // Registrar callbacks do ciclo de vida da atividade
        activity.application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity) {
                if (activity == this@QRCaptureView.activity) {
                    barcodeView?.pause()
                }
            }

            override fun onActivityResumed(activity: Activity) {
                if (activity == this@QRCaptureView.activity) {
                    barcodeView?.resume()
                }
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
            override fun onActivityStarted(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
            override fun onActivityDestroyed(activity: Activity) {}
        })
    }

    override fun getView(): View {
        return barcodeView!!
    }

    override fun dispose() {
        barcodeView?.pause()
        barcodeView = null
    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
            "checkAndRequestPermission" -> checkAndRequestPermission(result)
            "resume" -> barcodeView?.resume()
            "pause" -> barcodeView?.pause()
            "setTorchMode" -> {
                val isOn = call.arguments as Boolean
                barcodeView?.setTorch(isOn)
                result.success(null)
            }
            else -> result.notImplemented()
        }
    }

    private fun checkAndRequestPermission(result: MethodChannel.Result?) {
        if (hasCameraPermission()) {
            result?.success(true)
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.requestPermissions(arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST_ID)
            } else {
                result?.success(false)
            }
        }
    }

    private fun hasCameraPermission(): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                activity.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        const val CAMERA_REQUEST_ID = 513469796
    }
}