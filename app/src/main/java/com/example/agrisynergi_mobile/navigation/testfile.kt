package com.example.agrisynergi_mobile.navigation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.agrisynergi_mobile.R



@Composable
fun BottomSheetScreen() {
    BottomSheetContent()
}

@Composable
fun BottomSheetContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            // Jawa Timur Box
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(Color(0xFF5B8C51), shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.iconloc),
                        contentDescription = "Location Icon",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Jawa Timur",
                        color = Color.White,
                        fontSize = 10.sp
                    )
                }
            }

            // Tonnes of Corn Box
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(Color(0xFF5B8C51), shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.iconjagung),
                        contentDescription = "Corn Icon",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "1.300 ton",
                        color = Color.White,
                        fontSize = 10.sp
                    )
                }
            }

            // Farmers Box
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(Color(0xFF5B8C51), shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.iconpetani),
                        contentDescription = "Farmers Icon",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "4 pertanian",
                        color = Color.White,
                        fontSize = 10.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .shadow(5.dp, shape = RoundedCornerShape(16.dp))
                .background(Color(0xFF5B8C51), shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
                .width(330.dp)
                .height(500.dp)
        ){
            Column(

            ) {
                Image(
                    painter = painterResource(id = R.drawable.sawahjagung),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                //box informasi wilayah
                Box(
                    modifier = Modifier
                        .border(2.dp, Color.White, shape = RoundedCornerShape(16.dp)) // White border
                        .background(Color.Transparent)
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Column {
                        Text(
                            text = "Lokasi: Blitar",
                            color = Color.White
                        )
                        Text(
                            text = "Luas Lokasi: Blitar",
                            color = Color.White
                        )
                        Text(
                            text = "Jenis Tanah: Grumusol",
                            color = Color.White
                        )
                        Text(
                            text = "Hasil Panen: 300 ton",
                            color = Color.White
                        )
                        Text(
                            text = "Hasil Pertanian: Jagung",
                            color = Color.White
                        )
                    }

                }

                Spacer(modifier = Modifier.height(16.dp))

                // Description Box
                Box(
                    modifier = Modifier
                        .border(2.dp, Color.White, shape = RoundedCornerShape(16.dp))
                        .background(Color.Transparent)
                        .padding(16.dp)
                        .fillMaxWidth()
                )  {
                    Column {
                        Text(
                            text = "Deskripsi",
                            color = Color.White
                        )
                        Text(
                            text = "Lahan subur dengan drainase baik, cocok untuk jagung. Pemantauan rutin dan pupuk organik meningkatkan hasil panen.",
                            color = Color.White
                        )
                    }

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomSheetScreenPreview() {
    BottomSheetScreen()
}


