package franug.oompaloompatest.model

data class OompaLoompa(
    val first_name: String,
    val last_name: String,
    val image: String,
    val id: Int,
    val favorite: Favorite,
    val profession: String,
    val email: String,
    val age: Int,
    val country: String,
    val height: Int,
    val gender: String,

)

data class Favorite(
    val color: String,
    val food: String,
    val random_string: String,
    val song: String)
