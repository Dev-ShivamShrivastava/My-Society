package com.indigo.mysociety.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.indigo.mysociety.R
import com.indigo.mysociety.navigations.ScreensRoutes
import com.indigo.mysociety.utils.MyFontFamily
import kotlinx.coroutines.delay


@Composable
fun Splash(onNavigate: (screenName: String) -> Unit){
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loader))
    val progress by animateLottieCompositionAsState(composition,iterations = LottieConstants.IterateForever)
    // Navigate after 3 seconds
    LaunchedEffect(Unit) {
        delay(1000) // 3 seconds
        onNavigate(ScreensRoutes.SignIn.name)
    }
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (centerRef, progressRef) = createRefs()

        Column(modifier = Modifier.fillMaxWidth().wrapContentHeight().constrainAs(centerRef) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }, horizontalAlignment = Alignment.CenterHorizontally) {

            ConstraintLayout(
                modifier = Modifier.size(120.dp)
                    .border(2.dp, Color.Black, shape = CircleShape) // Border
                    .background(Color.Transparent, shape = CircleShape) // Background
            ) {
                val center = createRef()
                Text("S", fontSize = 46.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(20.dp).constrainAs(center){
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }, fontFamily = MyFontFamily)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text("MY SOCIETY", fontSize = 26.sp, fontWeight = FontWeight.Bold, fontFamily = MyFontFamily)
            Spacer(modifier = Modifier.height(5.dp))
            Text("For all you Home service needs", fontSize = 16.sp, fontWeight = FontWeight.Medium)
        }



        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(150.dp).constrainAs(progressRef){
                top.linkTo(centerRef.bottom)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
    }
}



@Preview(showBackground = true)
@Composable
fun SplashPreview() {
    Splash{

    }
}

