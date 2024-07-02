package com.example.login2

import android.telephony.SmsManager
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await

//data class detailOwner1(
//    val user: String,
//    val name: String,
//    val email: String,
//    val password: String,
//    val Mobile_number: String,
//    val VehicleType: String,
//    val vehicleNo: String
//)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FetchDataFromFirestore(navController: NavController) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Hire Vehicle", color = Color.White)
                },
                backgroundColor = Color.Blue
            )
        }
    ) {
        Column(
            // adding modifier for our column
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth()
                .background(Color.White),
            // on below line adding vertical and
            // horizontal alignment for column.
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                onClick = {
                    Toast.makeText(context,
                        " sending Message to vehicle Owner",
                        Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.padding(8.dp),
                elevation = 10.dp

            ) {
                Column(
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth()
                    .background(Color.White),
                // on below line adding vertical and
                // horizontal alignment for column.
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Name : " + "K Dinesh",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold)
                    space(10)
                    Text(text="Mobile Number :"+"7993665211",fontSize = 25.sp,
                        fontWeight = FontWeight.Bold)
                    space(10)
                    Text(text=" Vehicle Type: Car",fontSize = 25.sp,
                        fontWeight = FontWeight.Bold)
                    space(100)
                    Button(onClick = {
                       /**/
                    }) {

                        Text(text="Hire Vehicle")

                    }
                }
            }
        }
    }
}




