package franug.oompaloompatest.model

import com.google.gson.annotations.SerializedName

data class OompaLoompa(
    @SerializedName("first_name")
    val firstName: String? = null,
    @SerializedName("last_name")
    val lastName: String? = null,
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
    @SerializedName("random_string")
    val randomString: String? = null,
    val song: String? = null
)
