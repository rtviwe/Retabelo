package rtviwe.com.retabelo.main.fragments.favorites

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import rtviwe.com.retabelo.database.favorite.FavoriteDatabase
import rtviwe.com.retabelo.database.favorite.FavoriteEntry

class FavoritesViewModel(app: Application) : AndroidViewModel(app) {

    var favoriteDatabase: FavoriteDatabase = FavoriteDatabase.getInstance(this.getApplication())
    var favorites: LiveData<List<FavoriteEntry>> = favoriteDatabase.favoriteDao().getAllFavorites()
}