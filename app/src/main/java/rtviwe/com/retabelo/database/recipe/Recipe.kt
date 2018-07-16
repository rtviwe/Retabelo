package rtviwe.com.retabelo.database.recipe

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Recipe(
        var name: String,
        var photo: String,
        var body: String
) {

    constructor() : this("", "", "")
}