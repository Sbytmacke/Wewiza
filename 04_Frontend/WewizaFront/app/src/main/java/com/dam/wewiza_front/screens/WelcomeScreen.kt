package com.dam.wewiza_front.screens

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dam.wewiza_front.R
import com.dam.wewiza_front.viewModels.WelcomeScreenViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider


val FirsNeue = FontFamily(
    Font(R.font.tt_firs_neue_trial_regular),
    Font(R.font.tt_firs_neue_trial_bold, FontWeight.Bold),
    )

val Mermaid = FontFamily(
    Font(R.font.mermaid1001)
)

val Butler = FontFamily(
    Font(R.font.butler_medium),
    Font(R.font.butler_bold, FontWeight.Bold),
    Font(R.font.butler_extrabold, FontWeight.ExtraBold),

)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(
    viewModel: WelcomeScreenViewModel,
    navController: NavController
) {
    Scaffold() {
        BodyContent(viewModel, navController)
    }

}

@Composable
fun BodyContent(viewModel: WelcomeScreenViewModel, navController: NavController) {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()
    ) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)

            viewModel.signInWithGoogleCredential(credential, navController)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    Box(modifier = Modifier.run {
        background(Color(0xFFD0C2DC))
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.provisional_logo),
                contentDescription = "Logo",
                modifier = Modifier.size(300.dp)
            )
            Text(
                "¡Bienvenido a Wewiza!", style = TextStyle(
                    fontSize = 30.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Butler, color = Color(0xFF2E2C31)
                )
            )


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 100.dp)
            ) {
                Button(
                    onClick = {viewModel.navigateToLoginScreen(navController)},
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .padding(start = 20.dp)
                        .padding(end = 20.dp)
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFBB84E8), // Color hexadecimal convertido a Color
                        contentColor = Color.White // Color del texto en el botón
                    ),
                    shape = MaterialTheme.shapes.medium


                ) {
                    Text("Iniciar Sesión", style = TextStyle(fontSize = 20.sp), fontFamily = FirsNeue)
                }


                Button(
                    onClick = { viewModel.navigateToRegisterScreen(navController)},
                    modifier = Modifier
                        .padding(top = 15.dp)
                        .padding(start = 20.dp)
                        .padding(end = 20.dp)
                        .padding(bottom = 20.dp)
                        .fillMaxWidth()
                        .height(50.dp)
                        .border(3.dp, Color(0xFFBB84E8), RoundedCornerShape(10.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD0C2DC), contentColor = Color(0xFFBB84E8)
                    ),
                    shape = MaterialTheme.shapes.medium,

                    ) {
                    Row(

                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Registrarse", style = TextStyle(fontSize = 20.sp), fontFamily = FirsNeue)
                    }

                }
            }




            Column(
                modifier = Modifier.fillMaxSize().padding(bottom = 30.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(16.dp))
                    Divider(
                        color = Color(0xFFBB84E8), thickness = 3.dp, modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "O inicia con",
                        modifier = Modifier.padding(horizontal = 8.dp),
                        style = TextStyle(fontSize = 17.sp),
                        fontFamily = FirsNeue
                    )
                    Divider(
                        color = Color(0xFFBB84E8), thickness = 3.dp, modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }

                Button(
                    onClick = {
                        viewModel.loginWithGoogle(context, launcher,navController)
                    },
                    modifier = Modifier
                        .height(40.dp)
                        .width(100.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White, // Fondo blanco
                        contentColor = Color.Black // Texto negro
                    ),
                    shape = MaterialTheme.shapes.small // Bordes ligeramente redondeados
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.google),
                            contentDescription = "google",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }
        }
    }
}



