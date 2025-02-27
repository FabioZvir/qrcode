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
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.platform.PlatformView

class QRCaptureView(private val context: Context, id: Int, binding: FlutterPlugin.FlutterPluginBinding) :
    PlatformView, MethodCallHandler {

    private var barcodeView: BarcodeView? = null
    private val activity = context as Activity
    private val channel: MethodChannel = MethodChannel(binding.binaryMessenger, "plugins/qr_capture/method_$id")

    init {
        channel.setMethodCallHandler(this)
        checkAndRequestPermission(null)

        barcodeView = BarcodeView(activity)
        barcodeView?.decodeContinuous(object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult) {
                channel.invokeMethod("onCaptured", result.text)
            }
            override fun possibleResultPoints(resultPoints: List<ResultPoint>) {}
        })
        barcodeView?.resume()

        activity.application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityPaused(p0: Activity) {
                if (p0 == activity) barcodeView?.pause()
            }
            override fun onActivityResumed(p0: Activity) {
                if (p0 == activity) barcodeView?.resume()
            }
            override fun onActivityStarted(p0: Activity) {}
            override fun onActivityDestroyed(p0: Activity) {}
            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {}
            override fun onActivityStopped(p0: Activity) {}
            override fun onActivityCreated(p0: Activity, p1: Bundle) {}
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
