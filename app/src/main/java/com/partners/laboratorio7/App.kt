package com.partners.laboratorio7

import android.app.Application
import com.partners.laboratorio7.database.InventaryDb

class App: Application() {
    companion object {
        var selectedInventary: InventaryDb?=null
        var indexInventary: Int = 0
//        var indexInventary: Int = 0
    }
}