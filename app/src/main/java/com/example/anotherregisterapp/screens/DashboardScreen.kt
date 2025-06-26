package com.example.anotherregisterapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.House
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.anotherregisterapp.R
import com.example.anotherregisterapp.Routes.PROFILE
import com.example.anotherregisterapp.database.User
import com.example.anotherregisterapp.database.UserDatabase
import com.example.anotherregisterapp.database.UserRepository
import com.example.anotherregisterapp.database.viewModels.AuthViewModel
import com.example.anotherregisterapp.screens.design.NameCard
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    userId: Long? = null
){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val context = LocalContext.current
    val dao = UserDatabase.getInstance(context).userDao()
    val repo = UserRepository(dao)
    val authVm = viewModel<AuthViewModel>(
        key = "authViewModel",
        factory = AuthViewModel.Factory(repo)
    )

    val currentUser by authVm.currentUser.observeAsState()

    LaunchedEffect(userId) {
        userId?.let {
            authVm.getUserById(it)
        }
    }


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContentColor = Color.White,
            ) {
                DrawerContent(navController, currentUser)
            }
        },
        gesturesEnabled = true,
    ) {
        Scaffold(
            modifier = modifier,
            topBar = {
                val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
                    state = rememberTopAppBarState()
                )
                TopBar(
                    scrollBehavior = scrollBehavior,
                    userName = currentUser?.userName ?: "UserName",
                    onOpenDrawer = {
                        scope.launch{
                            drawerState.apply {
                                if (isClosed) drawerState.open() else drawerState.close()
                            }
                        }
                    },
                )
            },
            containerColor = Color(0xFF2196F3),

        ) { paddingValues ->

            ScreenContent(paddingValues = paddingValues)
        }
    }

}


@Composable
fun ScreenContent(paddingValues: PaddingValues){
    Spacer(modifier = Modifier.height(25.dp))
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding())
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize(),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(
                    top = 18.dp,
                    bottom = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                item {
                    Text(
                        text = "Persons List",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF1A1A1A),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 8.dp),
                    )
                }

                items(count = 10) { index ->
                    NameCard()
                }
            }
        }
    }

}


@Composable
fun DrawerContent(navController: NavController, currentUser: User?){

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            UserProfileHeader(user = currentUser)

            Spacer(modifier = Modifier.height(16.dp))

            DrawerMenuItem(
                icon = Icons.Outlined.Folder,
                text = "My Files",
                backgroundColor = Color(0xFF6200EA),
                onClick = {  }
            )
            
            DrawerMenuItem(
                icon = Icons.Default.Share,
                text = "Shared with me",
                onClick = {  }
            )

            DrawerMenuItem(
                icon = Icons.Default.Star,
                text = "Starred",
                onClick = {  }
            )

            Spacer(modifier = Modifier.height(16.dp))

            NavigationDrawerItem(
                label = {
                    Text(
                        "Profile",
                        fontSize = 16.sp,
                        color = Color(0xFF555555),
                        fontWeight = FontWeight.Medium
                    )
                },
                selected = false,
                onClick = { navController.navigate(PROFILE) },
                icon = {
                    Icon(
                        Icons.Default.AccountCircle,
                        contentDescription = "Profile",
                        tint = Color(0xFF555555)
                    )
                },
                colors = NavigationDrawerItemDefaults.colors(
                    unselectedContainerColor = Color.Transparent
                )
            )

            NavigationDrawerItem(
                label = {
                    Text(
                        "Home",
                        fontSize = 16.sp,
                        color = Color(0xFF555555),
                        fontWeight = FontWeight.Medium
                    )
                },
                selected = false,
                onClick = {  },
                icon = {
                    Icon(
                        Icons.Default.House,
                        contentDescription = "Home",
                        tint = Color(0xFF555555)
                    )
                },
                colors = NavigationDrawerItemDefaults.colors(
                    unselectedContainerColor = Color.Transparent
                )
            )



        }
}



@Composable
fun UserProfileHeader(user: User?){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(Color(0xFFE0E0E0)),
            contentAlignment = Alignment.Center
        ){
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile Picture",
                modifier = Modifier.size(36.dp),
                tint = Color(0xFF757575)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = user?.userName ?: "Usuario",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1A1A1A)
            )
            Text(
                text = user?.email ?: "email@example.com",
                fontSize = 14.sp,
                color = Color(0xFF757575)
            )
        }

    }

}


@Composable
fun DrawerMenuItem(
    icon: ImageVector,
    text: String,
    backgroundColor: Color? = null,
    onClick: () -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(
                    color = backgroundColor ?: Color.Transparent,
                    shape = RoundedCornerShape(6.dp)
                ),
            contentAlignment = Alignment.Center
        ){
            Icon(
                imageVector = icon,
                contentDescription = text,
                modifier = Modifier.size(20.dp),
                tint = if (backgroundColor != null) Color.White else Color(0xFF555555)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = text,
            fontSize = 16.sp,
            color = Color(0xFF1A1A1A),
            fontWeight = FontWeight.Medium
        )
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    onOpenDrawer: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    userName: String = "UserName"
) {

    TopAppBar(
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF2196F3)),
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                tint = Color.White,
                modifier = Modifier
                    .padding(start = 18.dp, end = 8.dp)
                    .size(28.dp)
                    .clickable {
                        onOpenDrawer()
                    }
            )
        },
        title = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Welcome,",
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = Color.White
                )
                Text(
                    text = "${userName}!",
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    color = Color.White
                )

            }
        },
        actions = {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Account",
                tint = Color.White,
                modifier = Modifier
                    .padding(start = 8.dp, end = 16.dp)
                    .size(30.dp)
            )
        }
    )
}



@Preview
@Composable
fun DashboardScreenPreview() {
    DashboardScreen(
        navController = NavController(LocalContext.current),
    )
}

@Preview
@Composable
fun UserProfileHeaderPreview(){
    UserProfileHeader(
        user = User(
            userId = 1L,
            userName = "John Doe",
            email = "",
            password = ""
        )
    )
}


@Preview
@Composable
fun DrawerContentPreview(){
    DrawerContent(
        navController = NavController(LocalContext.current),
        currentUser = User(
            userId = 1L,
            userName = "John Doe",
            email = "aaaaaa@gmail.com",
            password = ""
        )
    )
}