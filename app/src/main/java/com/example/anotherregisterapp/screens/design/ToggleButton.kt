package com.example.anotherregisterapp.screens.design

import android.widget.ToggleButton
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyToggleButton(
    selectedTab: String = "Login",
    onTabSelected: (String) -> Unit = {  }
){
    val isLoginSelected = selectedTab == "Login"

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .border(
                width = 1.dp,
                color = Color(0xFF2196F3),
                shape = RoundedCornerShape(24.dp)
            )
            .background(color = Color.White, shape = RoundedCornerShape(24.dp)),
//            .padding(1.dp),
        contentAlignment = Alignment.Center
    ){
        AnimatedVisibility(
            visible = true,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(
                        x = animateDpAsState(
                            targetValue = if (isLoginSelected) 0.dp else 120.dp,
                            animationSpec = tween(300)
                        ).value
                    )
            ) {
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .fillMaxHeight()
                        .background(
                            color = Color(0xFF2196F3),
                            shape = RoundedCornerShape(24.dp)
                        )
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextButton(
                onClick = {onTabSelected("Login")},
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = if(isLoginSelected) Color.White else Color.Gray
                )
            ) {
                Text(
                    text = "Login",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }

            TextButton(
                onClick = {onTabSelected("Register")},
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = if(!isLoginSelected) Color.White else Color.Gray
                )
            ) {
                Text(
                    text = "Register",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
    }
}


@Preview
@Composable
fun ToggleButtonPreview(){
    var selectedTab by remember { mutableStateOf("Login") }

    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        MyToggleButton(
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it }
        )
    }
}