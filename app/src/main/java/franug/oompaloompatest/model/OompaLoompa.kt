package franug.oompaloompatest.model

data class OompaLoompa(
    val first_name: String? = null,
    val last_name: String? = null,
    val image: String? = null,
    val id: Int? = null,
    val favorite: Favorite? = null,
    val profession: String? = null,
    val email: String? = null,
    val age: Int? = null,
    val country: String? = null,
    val height: Int? = null,
    val gender: String? = null,

)

data class Favorite(
    val color: String? = null,
    val food: String? = null,
    val random_string: String? = null,
    val song: String? = null
)
