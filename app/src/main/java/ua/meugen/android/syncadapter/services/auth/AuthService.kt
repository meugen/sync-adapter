package ua.meugen.android.syncadapter.services.auth

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * Created by meugen on 02.12.2017.
 */
class AuthService: Service() {

    private lateinit var authenticator: Authenticator

    override fun onCreate() {
        super.onCreate()
        authenticator = Authenticator(this)
    }

    override fun onBind(intent: Intent?): IBinder {
        return authenticator.iBinder
    }
}