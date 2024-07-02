package com.example.login2

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.login2.ui.theme.Login2Theme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Login2Theme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    mainApp()
                }
            }
        }
    }
}
@Composable
fun mainApp(){
    val navController = rememberNavController()
    val firebaseAuth =FirebaseAuth.getInstance()

    val context= LocalContext.current
    NavHost(navController = navController, startDestination = "Login_Page"){
        composable("Login_Page"){
            logins_page(navController=navController,firebaseAuth=firebaseAuth,context=context)
        }
        composable("Sign_Up"){
            Sign_up(navController = navController,firebaseAuth=firebaseAuth,context=context)
        }
        composable("User_Home"){
            User_Home(navController=navController)
        }
        composable("Book_Vehicle"){
            Vehicle(navController = navController)
        }
        composable("Vehicle_Home"){
            Vehicle_Homes(navController = navController)
        }
        composable("Choose_Route"){
            Choose_Route(navController = navController)
        }
        composable("Hire_Vehicle"){
            FetchDataFromFirestore(navController = navController)
        }
    }
}
// Main function of the Login page
@Composable
fun logins_page(navController: NavController,firebaseAuth: FirebaseAuth,context: Context) {
    Scaffold(
//        adding login text to top bar
        topBar = { TopAppBar (
            title = {Text(text="Login", color = Color.White)},
            backgroundColor =Color.Blue)
        }
    ) {
//        makking the total in one surface
        Surface(
            modifier = Modifier
                .padding(40.dp)
                .border(border = BorderStroke(1.dp, color = Color.Yellow)),
            border = BorderStroke(5.dp, color = Color.Blue),
            shape = RectangleShape
        ) {
            var email_id by rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(TextFieldValue(""))
            }
            val id = email_id.text
            var pass_word by rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(
                    TextFieldValue("")
                )
            }
            val pass = pass_word.text
            val context = LocalContext.current
            Scaffold {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .background(Color.Transparent),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
//            logo code
                    Image(painter = painterResource(id = R.drawable.bike1), contentDescription = "Logo ",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp))

                    space(5)
                    Text(text="Login", fontWeight = FontWeight.Black, modifier = Modifier.padding(0.dp,20.dp,0.dp,0.dp), fontSize = 40.sp)
                    space(15)
//            email texfield code
                    TextField(
                        value=email_id,
                        onValueChange = { email_id =it},
                        label = {
                            Column{
                                Text(text="Email")
                            }
                        },
                        leadingIcon={
                            Icon(Icons.Default.Email,
                                contentDescription = "email")
                        },
                        trailingIcon ={ IconButton(onClick ={ email_id=TextFieldValue("")}) {

                        }
                            Icon(Icons.Default.Clear,
                                contentDescription = "Clear")
                        },
                        modifier = Modifier
                            .padding(5.dp)
                            .background(color = Color.White),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )
                    space(5)
//            password text field code of login
                    TextField(
                        value = pass_word,
                        onValueChange = { pass_word=it},
                        label={
                            Column{
                                Text(text="Password")
                            }
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Lock,
                                contentDescription = "null")
                        },
                        trailingIcon = { IconButton(onClick ={ pass_word= TextFieldValue("") }) {

                        }
                            Icon(Icons.Default.Clear,
                                contentDescription = "Clear")
                        },
                        modifier= Modifier
                            .padding(5.dp)
                            .background(color = Color.White),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )
                    space(20)
//            login button code of sign in
                    Button(onClick = { Toast.makeText(context,"Clicked",Toast.LENGTH_SHORT).show()
                        signIn(email_id.text,pass_word.text,firebaseAuth,context,navController)
                    },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)) {
                        Text(text="Login",color=Color.White)
                    }
                    space(15)
//            text code with clicable
                    Text(text="not yet Registered ?", modifier = Modifier
                        .clickable { Toast.makeText(context,"redirecting to signup page",Toast.LENGTH_SHORT).show()
                            navController.navigate("Sign_Up")},
                        color = Color.Blue)
                    space(15)
                }
            }
        }
    }

}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Sign_up(navController: NavController,firebaseAuth: FirebaseAuth,context: Context) {
//variable for types of vehicles
    val options = listOf("Auto Rikshaw ","Car","Bike")
    var expanded by remember {mutableStateOf(false)}
    var selectedOptionText by remember { mutableStateOf(options[0])}
//variables for the types of route
//    val route=listOf("BT010203","Av012356","NM235689","JC123265","SA7896544","BA78965")
    var routes1 by remember{ mutableStateOf(false) }
    // Create a string value to store the selected city

    var number by remember { mutableStateOf("") }
    var name by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue(
                ""
            )
        )
    }
//    var email by remember { mutableStateOf("") }
    var email by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }
    var password by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue("")
        )
    }
    var mobileNumber by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue("")
        )
    }
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sign Up", color = Color.White) },
                backgroundColor = Color.Blue
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Color.Transparent),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Tell us who you are")
            Spacer(modifier = Modifier.height(10.dp))
            // Create an Outlined Text Field
            // with icon and not expanded
//            the user function for dropdowm menu of users
            val users = listOf("User", "Vehicle Owner")
            var expand by remember { mutableStateOf(false) }
            var selectingUser by remember { mutableStateOf(users[0]) }
            ExposedDropdownMenuBox(
                expanded = expand,
                onExpandedChange = {
                    expand = !expand
                }
            ) {
                TextField(
                    readOnly = true,
                    value = selectingUser,
                    onValueChange = { },
                    label = {
                        Text(text = "Users")
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expand
                        )
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                )
                ExposedDropdownMenu(
                    expanded = expand,
                    onDismissRequest = {
                        expand = false
                    }
                ) {
                    users.forEach { selectionOption ->
                        DropdownMenuItem(onClick = {
                            selectingUser = selectionOption
                            expand = false
                        }) {
                            Text(text = selectionOption)
                        }
                    }
                }
            }
            space(10)

            TextField(
                value = name,
                onValueChange = { name = it },
                label = {
                    Column {
                        Text(text = "Name")

                    }
                },
                modifier = Modifier
                    .padding(5.dp)
                    .background(color = Color.Transparent),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                value = email,
                onValueChange = { email = it },
                label = {
                    Column {
                        Text(text = "email")
                    }
                },
                modifier = Modifier
                    .padding(5.dp)
                    .background(color = Color.White),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                label = {
                    Column {
                        Text(text = "Password")
                    }
                },
                modifier = Modifier
                    .padding(5.dp)
                    .background(color = Color.White),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Spacer(modifier = Modifier.height(5.dp))
            TextField(
                value = mobileNumber,
                onValueChange = { mobileNumber = it },
                label = {
                    Column {
                        Text(text = "Mobile number")
                    }
                },
                modifier = Modifier
                    .padding(5.dp)
                    .background(color = Color.White),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(5.dp))
            if (selectingUser.equals(users[1])) {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange ={
                        expanded =!expanded
                    }
                )
                {
                    TextField(
                        readOnly = true,
                        value= selectedOptionText,
                        onValueChange = { },
                        label = {
                            Text("Vehicle type")
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded =expanded)
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        }
                    ){
                        options.forEach { selectionOption ->
                            DropdownMenuItem(
                                onClick ={
                                    selectedOptionText = selectionOption
                                    expanded = false
                                }
                            ){
                                Text(text=selectionOption)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
          TextField(value = number, onValueChange = { number = it },
              label={
                  Text("Vehicle No:")
              },
              modifier = Modifier
                  .padding(5.dp)
                  .background(
                      color = Color.White
                  ))
            }else {
                Spacer(modifier = Modifier.height(0.dp))
            }
            Spacer(modifier = Modifier.height(15.dp))
//            Add Button
            Button(
                onClick = {
                    signup(selectingUser,name.text,email.text,password.text,mobileNumber.text ,selectedOptionText,number,firebaseAuth,context,navController)
                    Toast.makeText(context,"Add",Toast.LENGTH_SHORT).show()
                    navController.navigate("Login_Page")},
                shape = RectangleShape,
                modifier = Modifier
                    .padding(65.dp, 50.dp, 65.dp, 0.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                border = BorderStroke(2.dp, Color.Yellow),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
            ) {
                Text(text = "Add", fontWeight = FontWeight.Bold, color = Color.White)
            }
            Spacer(modifier = Modifier.height(10.dp))
//            clear button
            Button(
                onClick = { name = TextFieldValue("")
                    email = TextFieldValue("")
                    password=TextFieldValue("")
                    mobileNumber = TextFieldValue("")
                    Toast.makeText(context,"clear",Toast.LENGTH_SHORT).show()

                },
                shape = RectangleShape,
                modifier = Modifier
                    .padding(65.dp, 10.dp, 65.dp, 0.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                border = BorderStroke(2.dp, Color.Yellow),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
            ) {
                Text(text = "Clear", fontWeight = FontWeight.Bold, color = Color.White)
            }


        }

    }
}
//code for the drop menu of the Vehicles

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun dropDownVehicals(){
    val options = listOf("Auto Riksha","Tvs Xl"," Hero Splender","Duke"," Yamaha 160")
    var expanded by remember {mutableStateOf(false)}
    var selectedOptionText by remember { mutableStateOf(options[0])}
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange ={
            expanded =!expanded
        }
    )
    {
        TextField(
            readOnly = true,
            value= selectedOptionText,
            onValueChange = { },
            label = {
                Text("Vehicle type")
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded =expanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ){
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick ={
                        selectedOptionText = selectionOption
                        expanded = false
                    }
                ){
                    Text(text=selectionOption)
                }
            }
        }
    }
}
//code for the drop down menu of the routes

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Routes(){
    val route=listOf("BT010203","Av012356","NM235689","JC123265","SA7896544","BA78965")
    var routes1 by remember{ mutableStateOf(false) }
    var routesOfRoads by remember{ mutableStateOf(route[0])}
    ExposedDropdownMenuBox(expanded = routes1,
        onExpandedChange =  {
            routes1 =! routes1
        } ) {
        TextField(
            readOnly = false,
            value = routesOfRoads,
            onValueChange = { },
            label = {
                Column{
                    Text(text="Select Route")
                }
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = routes1)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        ExposedDropdownMenu(expanded = routes1, onDismissRequest = { routes1 = false }) {
            route.forEach { selectionOption ->
                DropdownMenuItem(onClick = { routesOfRoads = selectionOption
                    routes1=false}) {
                    Text(text= routesOfRoads)

                }

            }

        }
    }
}
// code for the user route decision page and vehicle choosing page

@Composable
fun User_Home(navController: NavController) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar (
                title = { Text(text="User Home", color =  Color.White)},
                backgroundColor = Color.Blue
            )
        }
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(5.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.height(200.dp))
            Row(modifier = Modifier
                .padding(2.dp)
                .weight(1f)){
                Image(painter = painterResource(R.drawable.route), contentDescription = "route Symbol", modifier = Modifier.size(40.dp))
                Spacer(modifier= Modifier.height(10.dp))
                Text(text="Book Vehicle",color= Color.Blue,modifier = Modifier.clickable {  Toast.makeText(context,"Booking Vehicle",Toast.LENGTH_SHORT).show()
                    navController.navigate("Book_Vehicle")})
            }
            Spacer(modifier = Modifier.height(1.dp))
            Row(modifier = Modifier
                .padding(40.dp)
                .weight(9f)){
                Image(painter = painterResource(id = R.drawable.history), contentDescription = "history Symbol", modifier = Modifier.size(40.dp))
                Spacer(modifier = Modifier.height(10.dp))
                Text(text="View/See previous rides History",color=Color.Blue, modifier = Modifier.clickable { Toast.makeText(context,"History",Toast.LENGTH_SHORT).show() })
            }

        }

    }
}

// Vehicle choosing by the user code
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Vehicle(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Book Vehicle", color = Color.White)
                },
                backgroundColor = Color.Blue
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
        ) {
            val options = listOf("Auto Rikshaw", "Car","Bike")
            var expanded by remember { mutableStateOf(false) }
            var selectedOptionText by remember { mutableStateOf(options[0]) }
            val a = selectedOptionText

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text(text="select Vehicle Type", color = Color.Black)
                Spacer(modifier = Modifier.height(10.dp))
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = !expanded
                    }
                )
                {
                    TextField(
                        readOnly = true,
                        value = selectedOptionText,
                        onValueChange = { },
                        label = {
                            Text("Vehicle type")
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = expanded
                            )
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        }
                    ) {
                        options.forEach { selectionOption ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedOptionText = selectionOption
                                    expanded = false
                                }
                            ) {
                                Text(text = selectionOption)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(text="Click see Routes", fontWeight = FontWeight.Black, fontSize = 16.sp, color=Color.Blue, modifier = Modifier.clickable {navController.navigate("Choose_Route")  })
            }
        }
    }
}


//code for the vehicle chossed page to choose the route page
@Composable
fun Choose_Route(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Book Vehicle", color = Color.White)
                },
                backgroundColor = Color.Blue
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
        ) {
            vehicle_type()
            Spacer(modifier = Modifier.height(5.dp))
//            lazy column code
            var i=0
            val list = listOf("A") + ((1..15).map { it.toString() })
            val To_list = listOf(
                "Jntua cea",
                "SKU",
                "tower Clock",
                "VenuGopal Nagar",
                "Sai nagar",
                "Ashok Nagar",
                "Ram Nagar",
                "Siva Nagar",
                "Chinmey nagar",
                "Naik Nagar",
                "Housing Board",
                "Collector Office",
                "Bus Stand",
                "Railway Station",
                "Iskon temple",
                "Srit college"
            )
            val from_list = listOf(
                "Jntua cea",
                "SKU",
                "tower Clock",
                "VenuGopal Nagar",
                "Sai nagar",
                "Ashok Nagar",
                "Ram Nagar",
                "Siva Nagar",
                "Chinmey nagar",
                "Naik Nagar",
                "Housing Board",
                "Collector Office",
                "Bus Stand",
                "Railway Station",
                "Iskon temple",
                "SRIT College"
            )

            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(items = list, itemContent = { item ->
                    Log.d("COMPOSE", "This get rendered $item")
                    when (item) {
                        "A" -> {
                            Row(
                                modifier = Modifier
                                    .padding(3.dp)
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = "Receiving Location",
                                    color = Color.Green,
                                    fontSize = 20.sp
                                )
                                Spacer(modifier = Modifier.width(90.dp))
                                Text(text = "Drop Location", color = Color.Yellow, fontSize = 20.sp)
                            }
                        }
                        else -> {
                            Row(
                                modifier = Modifier
                                    .padding(11.dp)
                                    .fillMaxWidth()
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.route),
                                    contentDescription = "route symbol",
                                    modifier = Modifier.size(35.dp)
                                )
                                Text(
                                    text = "Route Name:" + item + "\n From:" + from_list[i],
                                    style = TextStyle(fontSize = 10.sp),
                                    modifier = Modifier
                                        .padding(1.dp)
                                        .clickable {
                                            navController.navigate("Hire_Vehicle") },
                                    color = Color.Blue,
                                )
                                Spacer(modifier = Modifier.width(100.dp))
                                Image(
                                    painter = painterResource(id = R.drawable.route2),
                                    contentDescription = "route symbol",
                                    modifier = Modifier.size(35.dp)
                                )
                                Text(
                                    text = "Route Name:" + item + "\n To:" + To_list[i],
                                    style = TextStyle(fontSize = 10.sp),
                                    modifier = Modifier.padding(1.dp).clickable { navController.navigate("Hire_Vehicle") },
                                    color = Color.Blue
                                )
                                i+=1
                            }
                        }
                    }
                })

            }


        }
    }
}

//code for the drop down of  the vehicle type


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun vehicle_type(){
    val options = listOf("Auto Riksha", "Car", "Bike")
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }
    val a = selectedOptionText

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(text="select Vehicle Type", color = Color.Black)
        Spacer(modifier = Modifier.height(10.dp))
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        )
        {
            TextField(
                readOnly = true,
                value = selectedOptionText,
                onValueChange = { },
                label = {
                    Text("Vehicle type")
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        onClick = {
                            selectedOptionText = selectionOption
                            expanded = false
                        }
                    ) {
                        Text(text = selectionOption)
                    }
                }
            }
        }
        Text(text= "Selected vehicle is "+ vehicles(a), color = Color.Blue, fontSize = 13.sp, fontWeight = FontWeight.Bold)
    }
}
// code for vehicle owner Home page

@Composable
fun Vehicle_Homes(navController: NavController) {
    var Switched by remember { mutableStateOf(false) }
    var context = LocalContext.current
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text="Vehicle Owner Home", color = Color.White)},
                backgroundColor = Color.Blue
            )
        }
    ){

        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(5.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ){  Spacer(modifier = Modifier.height(30.dp))
            Row(modifier = Modifier
                .padding(6.dp)
                .weight(2f)){
                Text(if(Switched) "On  Duty" else "Off  Duty", fontSize = 20.sp)
                Spacer(modifier = Modifier.height(10.dp))
                Switch(checked = Switched,
                    onCheckedChange = { Switched = it},
                    modifier = Modifier
                        .padding(5.dp),
                    colors = SwitchDefaults.colors(checkedThumbColor = Color.Green,
                        checkedTrackColor = Color.Magenta))

            }
            Spacer(modifier = Modifier.height(5.dp))
            Row(modifier = Modifier
                .padding(6.dp)
                .weight(8f)){
                Image(painter = painterResource(R.drawable.route), contentDescription = "route Symbol", modifier = Modifier.size(40.dp))
                Spacer(modifier= Modifier.height(100.dp))
                Text(text="VIEW AND ACCEPTS THE RIDES",color= Color.Blue,modifier = Modifier.clickable {  Toast.makeText(context,"History page",Toast.LENGTH_SHORT).show() })
            }

        }
        if(Switched){
            Toast.makeText(context,"Signed in",Toast.LENGTH_SHORT).show()
            Toast.makeText(context,"Updating the Location",Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(context,"Duty Off Succesfully",Toast.LENGTH_SHORT).show()
        }

    }
}

//code for the hiring of the driver:
@Composable
fun Hire_Vehicle(navController: NavController){
    val context= LocalContext.current
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
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text="vehicle No: Ap 02 NM 1234 \n \n Driver name: SubbaRao", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(50.dp))
            Row( modifier = Modifier.fillMaxWidth()){
                Image(painter = painterResource(id = R.drawable.click), contentDescription ="click", modifier = Modifier.size(30.dp) )
                Text(text="Click see vehicle on map", fontWeight = FontWeight.Black, modifier = Modifier.clickable {  Toast.makeText(context,"directing to maps",Toast.LENGTH_SHORT).show()})
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)) {
                Button(onClick = { /*TODO*/ },
                    border = BorderStroke(2.dp, color = Color.Yellow),
                    modifier = Modifier.size(150.dp, 60.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)) {
                    Text(text="Hire Vehicle", color = Color.White)
                }
                Spacer(modifier = Modifier.width(50.dp))
                Button(onClick = { /*TODO*/ },
                    border = BorderStroke(2.dp, color = Color.Yellow),
                    modifier = Modifier.size(150.dp, 60.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)) {
                    Text(text="Share a trip", color = Color.White)
                }
            }

        }
    }
}

private fun vehicles(a:String):String{
    var b=a
    return b.toString()
}
@Composable
fun space(a:Int){
    Spacer(modifier = Modifier.height(a.dp))
}
//signup(selectingUser,name.text,email.text,password.text,mobileNumber.text ,selectedOptionText,number,firebaseAuth,context,navController)
fun signup(users:String,names:String,emailid:String,password:String,Mobilenumber:String,selectedOptionText:String,number: String,firebaseAuth:FirebaseAuth,context: Context,navController: NavController){
    firebaseAuth.createUserWithEmailAndPassword(emailid,password)
        .addOnCompleteListener { task->
            if (task.isSuccessful) {
                Toast.makeText(context, "Registration successful", Toast.LENGTH_SHORT).show()
                addDataToFirebase(users, names, emailid,password, Mobilenumber, selectedOptionText ,number,context,firebaseAuth,navController)
                navController.navigate("Login_Page")
            }
            else{
                Toast.makeText(context,"failed to registered ",Toast.LENGTH_SHORT).show()
            }
        }
}
fun signIn(id: String,password: String,firebaseAuth: FirebaseAuth,context: Context,navController: NavController){
    var auth: FirebaseAuth = Firebase.auth
    firebaseAuth.signInWithEmailAndPassword(id,password)
        .addOnCompleteListener { task->
            if (task.isSuccessful){
                Toast.makeText(context,"Successfully Login",Toast.LENGTH_SHORT).show()
                navController.navigate("User_Home")
            }
            else{
                Toast.makeText(context,"Sign In failed",Toast.LENGTH_SHORT).show()
                Toast.makeText(context,"Check email and password",Toast.LENGTH_SHORT).show()
            }
        }
}

//(selectingUser,name.text,email.text,mobile_no.text,password ,selectedOptionText,number,firebaseAuth,context,navController)
//users, names, emailid,password, Mobilenumber, selectedOptionText ,number,context,firebaseAuth,navController)
//addDataToFirebase(users, names, emailid,password, Mobilenumber, selectedOptionText ,number,context,firebaseAuth,navController)
fun addDataToFirebase(users: String,names: String,email: String,password:String,Number: String,VehicleType: String,vehicleNo: String,context:Context,firebaseAuth: FirebaseAuth,navController: NavController) {
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    val dbDetails: CollectionReference = db.collection(users)
    val detail1 = detailOwner(users, names, email,password, Number, VehicleType, vehicleNo)
    val detail2 = detailUser(users, names, email,password, Number )
    if (users.equals("Vehicle Owner")) {
        dbDetails.add(detail1)
    } else {
        dbDetails.add(detail2)
    }
        .addOnSuccessListener {
            Toast.makeText(context, "Added data", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener { e ->
            Toast.makeText(context, "Failed to add data "+e.message, Toast.LENGTH_SHORT).show()
        }
}
@Preview(showBackground = true, showSystemUi = true )
@Composable
fun DefaultPreview() {
    Login2Theme {
        mainApp()
    }
}

//"User", "Vehicle Owner"