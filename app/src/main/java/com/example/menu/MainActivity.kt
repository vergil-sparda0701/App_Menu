package com.example.appmen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import com.example.appmen.ui.theme.AppMenúTheme
import com.example.menu.R

// Actividad principal de la aplicación
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
// Habilita el modo de pantalla completa sin bordes
        enableEdgeToEdge()
// Establece el contenido de la actividad utilizando Jetpack Compose
        setContent {
            MainScreen()
        }
    }
}
// Clase de datos que representa un elemento del menú
data class MenuItem(
    val name: String, // Nombre del ítem
    val price: String, // Precio del ítem
    val imageRes: Int, // Recurso de imagen asociado
    val description: String) // Descripción del ítem

// Enumeración que define las diferentes pantallas de la aplicación
enum class Screen {
    Home, Menu, About, Contact
}
@Composable
fun MainScreen() {
    // Estado que controla si se muestra el menú
    var showMenu by remember { mutableStateOf(false) }
    // Estado que almacena el ítem seleccionado
    var selectedItem by remember { mutableStateOf<MenuItem?>(null) }
    // Estado que rastrea la pantalla actual
    var currentScreen by remember { mutableStateOf(Screen.Home) }

    // Lógica de navegación entre pantallas
    when {
        // Si hay un ítem seleccionado, muestra la pantalla de detalles
        selectedItem != null -> {
            MenuItemDetailScreen(menuItem = selectedItem!!, onBackClick = { selectedItem = null })
        }
        // Si se debe mostrar el menú, muestra la pantalla del menú
        showMenu -> {
            FastFoodMenuScreen(onBackClick = { showMenu = false }, onItemClick = { selectedItem = it })
        }
        // De lo contrario, muestra la pantalla correspondiente según el estado actual
        else -> { when (currentScreen) {
            Screen.Home -> HomeScreen(
                onStartClick = { currentScreen = Screen.Menu },
                onAboutClick = { currentScreen = Screen.About },
                onContactClick = { currentScreen = Screen.Contact }
            )
            Screen.Menu -> FastFoodMenuScreen(
                onBackClick = { currentScreen = Screen.Home },
                onItemClick = { selectedItem = it }
            )
            Screen.About -> AboutScreen(onBackClick = { currentScreen = Screen.Home })
            Screen.Contact -> ContactScreen(onBackClick = { currentScreen = Screen.Home })
        }
        }

    }
}


@Composable
fun HomeScreen(onStartClick: () -> Unit, // Acción a ejecutar al hacer clic en "Ver Menú"
               onAboutClick: () -> Unit, // Acción a ejecutar al hacer clic en "Ver Nosotros"
               onContactClick: () -> Unit) {
    // Columna que organiza los elementos verticalmente
    Column(
        modifier = Modifier.fillMaxSize(), // Ocupa todo el espacio disponible
        verticalArrangement = Arrangement.Center, // Centra los elementos verticalmente
        horizontalAlignment = Alignment.CenterHorizontally // Centra los elementos horizontalmente
    ) {
        // Texto de bienvenida
        Text(text = "Bienvenido a FastFood App", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp)) // Espacio vertical de 16dp
        MyImageAbout() // Imagen del logo
        Spacer(modifier = Modifier.height(20.dp)) // Espacio vertical de 20dp
        // Botón "Nosotros"
        Button(onClick = onAboutClick, modifier = Modifier.width(200.dp)) {
            Text(text = "Nosotros")
        }
        // Botón "Ver Menú"
        Button(onClick = onStartClick, modifier = Modifier.width(200.dp)) {
            Text(text = "Ver Menú")
        }
        // Botón "Contáctanos"
        Button(onClick = onContactClick, modifier = Modifier.width(200.dp)) {
            Text(text = "Contáctanos")
        }
    }
}


@Composable
fun AboutScreen(onBackClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Acerca de Nosotros", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        MyImageAbout()
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Somos una aplicación dedicada a ofrecer la mejor comida rápida a nuestros clientes.",
            fontSize = 16.sp,
            modifier = Modifier.padding(50.dp),
            textAlign = TextAlign.Center)

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBackClick, modifier = Modifier.width(200.dp)) {
            Text(text = "Volver al Inicio")
        }
    }
}

@Composable
fun ContactScreen(onBackClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Contáctanos", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        MyImageContact()
        Text(text = "Puedes contactarnos a través de nuestro Whatsapp: 809-555-555",
            fontSize = 16.sp,
            modifier = Modifier.padding(20.dp),
            textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Correo: contacto@fastfoodapp.com",
            fontSize = 16.sp,
            textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Redes: @fastfoodapp",
            fontSize = 16.sp,
            textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBackClick, modifier = Modifier.width(200.dp)) {
            Text(text = "Volver al Inicio")
        }
    }
}

@Composable
fun MyImageContact(){
    Image(
        painter = painterResource(R.drawable.contact),
        contentDescription = "Este es mi logo"
    )

}
@Composable
fun MyImageAbout(){
    Image(
        painter = painterResource(R.drawable.logo),
        contentDescription = "Este es mi logo"
    )

}
@Composable
fun FastFoodMenuScreen(onBackClick: () -> Unit, onItemClick: (MenuItem) -> Unit) {
    val menuItems = remember {
        listOf(
            MenuItem("Hamburguesa Clásica", "$5.99", R.drawable.burger, "Deliciosa hamburguesa con carne" +
                    "jugosa y pan suave."),
            MenuItem("Papas Fritas", "$2.99", R.drawable.fries, "Crujientes papas fritas doradas a la" +
                    "perfección."),
            MenuItem("Refresco", "$1.99", R.drawable.soda, "Bebida refrescante para acompañar tu comida."),
            MenuItem("Pollo Frito", "$6.49", R.drawable.chicken, "Trozos de pollo crujiente con especias" +
                    "especiales.")
        )
    }

    Column(modifier = Modifier.fillMaxSize().padding(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Nuestro Menú", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(menuItems.size) { index ->
                MenuItemCard(menuItems[index], onItemClick)
            }
        }
        Button(onClick = onBackClick, modifier = Modifier.padding(bottom = 16.dp).width(200.dp)) {
            Text(text = "Volver al Inicio")
        }
    }
}

@Composable
fun MenuItemCard(menuItem: MenuItem, onItemClick: (MenuItem) -> Unit) {


    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onItemClick(menuItem) }
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = menuItem.imageRes),
                contentDescription = menuItem.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = menuItem.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = menuItem.price, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
fun MenuItemDetailScreen(menuItem: MenuItem, onBackClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = menuItem.imageRes),
            contentDescription = menuItem.name,
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = menuItem.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(text = menuItem.price, fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = menuItem.description, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBackClick, modifier = Modifier.width(200.dp)) {
            Text(text = "Volver al Menú")
        }
    }
}

@Preview
@Composable
fun PreviewMenu(){
    MainScreen()
}