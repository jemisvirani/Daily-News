package com.code.newsapp.presentation.drawer

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.PrivacyTip
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.code.newsapp.R
import com.code.newsapp.presentation.news.model.response.Article
import com.code.newsapp.presentation.news.viewmodel.SearchNewsViewModel
import com.code.newsapp.presentation.search.SearchScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp


@Composable
fun DrawerScreen(
    navigateToDetails: (Article?) -> Unit,
    searchNewsViewModel: SearchNewsViewModel,
    navController: NavHostController,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
        ModalDrawerSheet(modifier = Modifier.padding(end = 150.dp)) {
            DrawerContent(drawerState = drawerState, scope = scope)
        }
    }, gesturesEnabled = true) {
        Scaffold(topBar = {
            TopBar(onOpenDrawer = {
                scope.launch {
                    drawerState.apply {
                        if (isClosed) open() else close()
                    }
                }
            })
        }) { padding ->
            ScreenContent(
                modifier = Modifier.padding(padding),
                navigateToDetails,
                searchNewsViewModel,
                navController,
            )
        }
    }

}

@Composable
fun ScreenContent(
    modifier: Modifier = Modifier,
    navigateToDetails: (Article?) -> Unit,
    searchNewsViewModel: SearchNewsViewModel,
    navController: NavHostController,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 5.sdp,
                start = 5.sdp,
                end = 5.sdp
            )
            .statusBarsPadding()
    ) {
        Spacer(modifier = Modifier.height(40.sdp))

        SearchScreen(navigateToDetails, searchNewsViewModel = searchNewsViewModel,navController)

        Spacer(modifier = Modifier.height(5.sdp))

    }


}

@Composable
fun DrawerContent(drawerState: DrawerState, scope: CoroutineScope) {
    Column(modifier = Modifier.fillMaxWidth(),horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Image(
            modifier = Modifier
                .padding(top = 15.sdp, bottom = 25.sdp)
                .size(80.sdp).fillMaxWidth(),
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = null
        )
    }
    val context = LocalContext.current

//    Text(
//        text = "Daily News",
//        fontSize = 24.ssp,
//        textAlign = TextAlign.Center,
//        modifier = Modifier
//            .padding(top = 13.sdp, bottom = 13.sdp)
//            .fillMaxWidth(),
//        color = colorResource(R.color.green),
//        fontWeight = FontWeight.Bold
//    )
    HorizontalDivider(color = colorResource(R.color.black))

    Spacer(modifier = Modifier.height(10.sdp))

    NavigationDrawerItem(icon = {
        Icon(
            imageVector = Icons.Rounded.PrivacyTip,
            contentDescription = "Privacy",
            modifier = Modifier.size(24.sdp),
            tint = colorResource(R.color.green)
        )
    }, label = {
        Text(text = "Privacy", fontSize = 14.ssp, fontWeight = FontWeight.Medium)
    }, selected = false, onClick = {
        scope.launch {
            drawerState.apply {
                if (isClosed) open() else close()
                val url = "https://sites.google.com/view/dailywork" // Replace with your desired URL
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                if (intent.resolveActivity(context.packageManager) != null){
                    context.startActivity(intent)
                }
            }
        }
    })

    Spacer(modifier = Modifier.height(4.sdp))

    NavigationDrawerItem(icon = {
        Icon(
            imageVector = Icons.Rounded.Share,
            contentDescription = "Share app",
            modifier = Modifier.size(24.sdp),
            tint = colorResource(R.color.green)
        )
    }, label = {
        Text(text = "Share App", fontSize = 14.ssp, fontWeight = FontWeight.Medium)
    }, selected = false, onClick = {
        scope.launch {
            drawerState.apply {
                if (isClosed) open() else close()
                val shareText = "Check out this amazing app: https://play.google.com/store/apps/details?id=${context.packageName}"
                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, shareText)
                    type = "text/plain"
                }
                context.startActivity(
                    Intent.createChooser(shareIntent, "Daily News")
                )
            }
        }

    })

    Spacer(modifier = Modifier.height(4.sdp))

//    NavigationDrawerItem(icon = {
//        Icon(
//            imageVector = Icons.Rounded.Share,
//            contentDescription = "Share app",
//            modifier = Modifier.size(24.sdp),
//            tint = colorResource(R.color.green)
//        )
//    }, label = {
//        Text(text = "Share App", fontSize = 14.ssp, fontWeight = FontWeight.Medium)
//    }, selected = false, onClick = {
//
//    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(onOpenDrawer: () -> Unit) {
    TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
        containerColor = colorResource(R.color.input_background)
    ), navigationIcon = {
        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = "Menu",
            modifier = Modifier
                .padding(start = 16.sdp)
                .size(35.sdp)
                .clickable {
                    onOpenDrawer()
                }, tint = colorResource(R.color.green)
        )
    }, title = {
        Text(
            text = "Daily news",
            textAlign = TextAlign.Center,
            fontSize = 22.ssp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.text_title),
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 40.sdp)
        )
    }, actions = {
//        Icon(
//            imageVector = Icons.Default.Notifications,
//            contentDescription = "Menu",
//            modifier = Modifier
//                .padding(start = 16.dp, end = 16.dp)
//                .size(30.dp)
//        )
//        Icon(
//            imageVector = Icons.Default.AccountCircle,
//            contentDescription = "Menu",
//            modifier = Modifier
//                .padding(start = 16.dp, end = 16.dp)
//                .size(30.dp)
//        )
    }
    )
}