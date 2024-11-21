package com.example.agrisynergi_mobile.data

object dataforum {
    val forums = listOf(
        Forum(
            id = 1,
            user = User(
                id = 1,
                name = "Anton_09",
                username = "@farm_anton",
                profilePic = "https://pbs.twimg.com/profile_images/1429535486683631635/FBBjBBQ4_400x400.jpg"
            ),
            hasImage = true,
            imageUrl = "https://asset.kompas.com/crops/HRHCUdH1il053EhZJqQMrSElEOo=/0x0:1000x667/750x500/data/photo/2021/10/04/615a92b8b7402.jpg",
            numComment = 10,
            numLikes = 121,
            numMarkah = 30,
            date = "21 sep",
            text = "\"I’d like to seek some advice about my corn crop. Lately, I’ve noticed the leaves changing color—some are yellowing, and others are even drying out, though the plants are still young. I’m uncertain about what’s causing this; could it be a pest issue, perhaps a fungal disease, or maybe a nutrient deficiency? The soil and rainfall conditions haven’t really changed much from previous seasons. If there’s a specific disease or problem affecting the corn, I’d be grateful for any guidance on how to address it effectively and protect the yield.\""
        ),
        Forum(
            id = 2,
            user = User(
                id = 2,
                name = "Natural Farm",
                username = "@Farm_id",
                profilePic = "https://randomuser.me/api/portraits/men/1.jpg"
            ),
            hasImage = true,
            imageUrl = "https://cdn1-production-images-kly.akamaized.net/SqlHMMSebG4AKkNr08jgDT58NQU=/800x450/smart/filters:quality(75):strip_icc():format(webp)/kly-media-production/medias/2862825/original/093416100_1563978081-iStock-599971330.jpg",
            numComment = 15,
            numLikes = 98,
            numMarkah = 25,
            date = "22 sep",
            text = "Can anyone identify this issue with my corn plants?"
        ),
        Forum(
            id = 3,
            user = User(
                id = 3,
                name = "NadiaFarm",
                username = "@nadia_id",
                profilePic = "https://randomuser.me/api/portraits/women/2.jpg"
            ),
            hasImage = true,
            imageUrl = "https://asset.kompas.com/crops/IktcwpGJPZ6TUfpJb8ZNJYW6DLQ=/0x43:1000x710/780x390/data/photo/2022/09/22/632c01f18b16e.jpg",
            numComment = 20,
            numLikes = 150,
            numMarkah = 40,
            date = "23 sep",
            text = "What are the best practices for preventing corn diseases?"
        )
    )
}

