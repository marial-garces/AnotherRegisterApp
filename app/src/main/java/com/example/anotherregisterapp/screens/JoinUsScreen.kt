package com.example.anotherregisterapp.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.anotherregisterapp.R
import com.example.anotherregisterapp.Routes.LOGIN
import com.example.anotherregisterapp.database.UserDatabase
import com.example.anotherregisterapp.database.UserRepository
import com.example.anotherregisterapp.database.viewModels.AuthViewModel
import com.example.anotherregisterapp.screens.design.AuthOptions
import com.example.anotherregisterapp.screens.design.MyToggleButton

@Composable
fun JoinUsScreen(navController: NavController) {
    var selectedTab by remember { mutableStateOf("Login") }

    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFF2196F3)),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Image(
                painter = painterResource( id = R.drawable.profile_blue),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxWidth(0.25f)
                    .aspectRatio(1.5f),
            )

            Box(
                modifier = Modifier.fillMaxSize()
            ){
                Card(
                    modifier = Modifier.fillMaxSize(),
                    shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 36.dp, bottom = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {

                        Column(
                            modifier = Modifier.padding(horizontal = 76.dp)
                        ) {
                            MyToggleButton(
                                selectedTab = selectedTab,
                                onTabSelected = { tab -> selectedTab = tab }
                            )
                        }

                        if( selectedTab == "Login"){
                            LoginContent(navController = NavController(LocalContext.current))
                        }else {
                            RegisterContent(navController = NavController(LocalContext.current))
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun LoginContent(navController: NavController){

    val context = LocalContext.current.applicationContext
    val dao = UserDatabase.getInstance(context.applicationContext).userDao()
    val repo = UserRepository(dao)
    val authVm = viewModel<AuthViewModel>(
        key = "authViewModel",
        factory = AuthViewModel.Factory(repo)
    )

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    val loginResult = authVm.loginResult.observeAsState()

    loginResult.value?.let {result ->
        if (result.isSuccess) {
            val user = result.getOrNull()
            navController.navigate("dashboard")
        } else {
            Toast.makeText(context, "Login failed",
                Toast.LENGTH_SHORT).show()
        }
    }


    Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {

        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            singleLine = true,
            trailingIcon = {
                androidx.compose.material3.Icon(
                    Icons.Outlined.Mail,
                    null,
                    tint = Color(0xFF2196F3)
                )
            }
        )

        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { showPassword = !showPassword }) {
                    androidx.compose.material3.Icon(
                        imageVector = if (showPassword) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                        contentDescription = null,
                        tint = Color(0xFF2196F3)
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
        ){
            Checkbox(
                checked = false,
                onCheckedChange = null
            )
            Text(
                text = "Remember me",
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 8.dp, top = 4.dp),
                color = Color(0xFF2196F3),
                fontWeight = FontWeight.SemiBold
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            Arrangement.End
        ){
            Text(
                text = "Forgot Password?",
                fontSize = 12.sp,
                modifier = Modifier.padding(end = 8.dp),
                fontWeight = FontWeight.SemiBold,

                )
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp, horizontal = 10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2196F3)
            ),
            onClick = {}
        ) {
            Text(
                text = "Log In",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(PaddingValues(vertical = 6.dp, horizontal = 10.dp))
            )
        }


        Column(modifier = Modifier.padding(start = 90.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(CenterHorizontally),
                horizontalArrangement = Arrangement.spacedBy(20.dp),

                ) {
                AuthOptions(image = R.drawable.google, tint = Color.Unspecified)
                AuthOptions(image = R.drawable.facebok, tint = Color.Unspecified)
                AuthOptions(image = R.drawable.apple, tint = Color.Unspecified)
            }
        }

    }


}



@Composable
fun RegisterContent(navController: NavController){

    val context = LocalContext.current.applicationContext
    val dao = UserDatabase.getInstance(context.applicationContext).userDao()
    val repo = UserRepository(dao)

    val authVm: AuthViewModel = viewModel(
        key = "AuthViewModel",
        factory = AuthViewModel.Factory(repo)
    )

    val username = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    val registerResult = authVm.registerResult.observeAsState()

    LaunchedEffect(registerResult) {
        registerResult.value?.let { result ->
            if (result.isSuccess) {
                val userId = result.getOrNull()
                Toast.makeText(
                    context,
                    "Registration successful! User ID: ${result.getOrNull()}",
                    Toast.LENGTH_SHORT
                ).show()
                navController.navigate(LOGIN)
            } else {
                Toast.makeText(
                    context,
                    "Registration failed: ${result.exceptionOrNull()?.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    Column(verticalArrangement = Arrangement.spacedBy(15.dp)){

        OutlinedTextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text("Username") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            singleLine = true,
            trailingIcon = { Icon(
                Icons.Outlined.AccountCircle,
                null,
                tint = Color(0xFF2196F3)
            ) }
        )

        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            singleLine = true,
            trailingIcon = { Icon(
                Icons.Outlined.Mail,
                null,
                tint = Color(0xFF2196F3)
            ) }
        )

        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(
                        imageVector = if (showPassword) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                        contentDescription = null,
                        tint = Color(0xFF2196F3)
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp)
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp, horizontal = 10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2196F3)
            ),
            onClick = {}
        ) {
            Text(
                text = "Sign Up",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(PaddingValues(vertical = 6.dp, horizontal = 10.dp))
            )
        }


        Column(modifier = Modifier.padding(start = 90.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(CenterHorizontally),
                horizontalArrangement = Arrangement.spacedBy(20.dp),

                ) {
                AuthOptions(image = R.drawable.google, tint = Color.Unspecified)
                AuthOptions(image = R.drawable.facebok, tint = Color.Unspecified)
                AuthOptions(image = R.drawable.apple, tint = Color.Unspecified)
            }
        }

    }




}





@Preview
@Composable
fun JoinUsScreenPreview() {
    JoinUsScreen(navController = NavController(context = LocalContext.current))
}

@Preview
@Composable
fun LoginPreview() {
    LoginContent(navController = NavController(context = LocalContext.current))
}

@Preview
@Composable
fun RegisterPreview() {
    RegisterContent(navController = NavController(context = LocalContext.current))
}