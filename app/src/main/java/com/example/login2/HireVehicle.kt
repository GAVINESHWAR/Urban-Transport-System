package com.example.login2

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.login2.ui.theme.Login2Theme
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firestore.admin.v1.Index
import java.time.format.TextStyle
@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HireVehicles(navController: NavController) {
    val context = LocalContext.current
    var courseList = mutableListOf<detailOwner?>()
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    db.collection("Vehicle Owner"
    ).get()
        .addOnSuccessListener { queryDocumentSnapshots ->
            if (!queryDocumentSnapshots.isEmpty) {
                val list = queryDocumentSnapshots.documents
                for (d in list) {
                    val c: detailOwner? = d.toObject(detailOwner::class.java)
                    courseList.add(c)
                }
            } else {
                Toast.makeText(context, "No data in data base", Toast.LENGTH_SHORT).show()
            }
        }
        .addOnFailureListener {
            Toast.makeText(context, " Fail To Retrive", Toast.LENGTH_SHORT).show()
        }
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
                .fillMaxHeight()
                .fillMaxWidth()
                .background(Color.White),
            // on below line adding vertical and
            // horizontal alignment for column.
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            space(45)
            LazyColumn {
                itemsIndexed(courseList
                ) { index, item ->
                    Card(

                        onClick = {
                            Toast.makeText(context,
                                " sending Message to vehicle Owner",
                                Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.padding(8.dp),
                        elevation = 10.dp

                    ){
//                        data class detailOwner(
//                            val user: String,
//                            val name: String,
//                            val email: String,
//                            val password: String,
//                            val Mobile_number: String,
//                            val VehicleType: String,
//                            val vehicleNo: String
//                        )
                        Column(modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()) {
                            courseList[index]?.user?.let {
                                Text(
                                    // inside the text on below line we are
                                    // setting text as the language name
                                    // from our modal class.
                                    text = it,

                                    // on below line we are adding padding
                                    // for our text from all sides.
                                    modifier = Modifier.padding(4.dp),

                                    // on below line we are adding
                                    // color for our text
                                    color = Color.Blue,
                                    textAlign = TextAlign.Center
                                )
                            }
                            space(a = 5)
                            courseList[index]?.name?.let {
                                Text(
                                    // inside the text on below line we are
                                    // setting text as the language name
                                    // from our modal class.
                                    text = it,

                                    // on below line we are adding padding
                                    // for our text from all sides.
                                    modifier = Modifier.padding(4.dp),

                                    // on below line we are adding
                                    // color for our text
                                    color = Color.Blue,
                                    textAlign = TextAlign.Center
                                )
                            }
                            space(5)
                            courseList[index]?.email?.let {
                                Text(
                                    // inside the text on below line we are
                                    // setting text as the language name
                                    // from our modal class.
                                    text = it,

                                    // on below line we are adding padding
                                    // for our text from all sides.
                                    modifier = Modifier.padding(4.dp),

                                    // on below line we are adding
                                    // color for our text
                                    color = Color.Blue,
                                    textAlign = TextAlign.Center
                                )
                            }
                            space(5)
                            courseList[index]?.Mobile_number?.let {
                                Text(
                                    // inside the text on below line we are
                                    // setting text as the language name
                                    // from our modal class.
                                    text = it,

                                    // on below line we are adding padding
                                    // for our text from all sides.
                                    modifier = Modifier.padding(4.dp),

                                    // on below line we are adding
                                    // color for our text
                                    color = Color.Blue,
                                    textAlign = TextAlign.Center
                                )
                            }
                            space(5)
                            courseList[index]?.VehicleType?.let {
                                Text(
                                    // inside the text on below line we are
                                    // setting text as the language name
                                    // from our modal class.
                                    text = it,

                                    // on below line we are adding padding
                                    // for our text from all sides.
                                    modifier = Modifier.padding(4.dp),

                                    // on below line we are adding
                                    // color for our text
                                    color = Color.Blue,
                                    textAlign = TextAlign.Center
                                )

                            }
                            space(5)
                            courseList[index]?.vehicleNo?.let {
                                Text(
                                    // inside the text on below line we are
                                    // setting text as the language name
                                    // from our modal class.
                                    text = it,

                                    // on below line we are adding padding
                                    // for our text from all sides.
                                    modifier = Modifier.padding(4.dp),

                                    // on below line we are adding
                                    // color for our text
                                    color = Color.Blue,
                                    textAlign = TextAlign.Center
                                )
                            }

                        }

                    }
                }
            }
            space(46)
            Button(onClick = { /*TODO*/ },
            modifier = Modifier
                .background(color = Color.Blue)
                .size(250.dp,100.dp)) {
                Text(text="Hire Vehicel", color = Color.White)

            }

        }
    }
}