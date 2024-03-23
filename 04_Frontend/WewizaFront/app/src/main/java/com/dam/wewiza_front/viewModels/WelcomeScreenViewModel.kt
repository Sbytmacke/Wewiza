package com.dam.wewiza_front.viewModels

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.dam.wewiza_front.constants.Constants
import com.dam.wewiza_front.navigation.AppScreens
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch
import java.lang.Exception

class WelcomeScreenViewModel: ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)

    fun signInWithGoogleCredential(credential: AuthCredential){
        viewModelScope.launch {
            try {
                auth.signInWithCredential(credential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful){
                            Log.i("Login", "Usuario logueado con google")
                        }
                    }
                    .addOnFailureListener{
                        Log.i("Login", "error al loguear con google")
                    }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }


    fun loginWithGoogle(
        context: Context,
        launcher: ManagedActivityResultLauncher<Intent, ActivityResult>,
        navController: NavController
    ) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(Constants.FIREBASE_CLIENT_ID)
            .requestEmail()
            .build()

        val googleClient = GoogleSignIn.getClient(context, gso)

        launcher.launch(googleClient.signInIntent)
        navController.navigate(route= AppScreens.HomeScreen.route)


        /**
        googleClient.signOut() // Asegurarse de que el usuario inicie sesión cada vez
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    launcher.launch(googleClient.signInIntent)
                }
            }

        */
    }


}