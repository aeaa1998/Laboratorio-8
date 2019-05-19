package com.partners.laboratorio7

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.Result
import com.partners.laboratorio7.App.Companion.selectedInventary
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ScanCode : AppCompatActivity(),  ZXingScannerView.ResultHandler{
    var scannerView: ZXingScannerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scannerView = ZXingScannerView(this)
        setContentView(scannerView)

    }

    override fun handleResult(p0: Result?) {
        var b = false
        selectedInventary?.rowsId?.forEach {

        }
        if (b==false){
            Toast.makeText(application.applicationContext, "No fue un qr valido o ya agrego el elemento", Toast.LENGTH_LONG).show()
        }
        onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        scannerView?.stopCamera()
    }

    override fun onResume() {
        super.onResume()
        scannerView?.setResultHandler(this)
        scannerView?.startCamera()
    }
}
