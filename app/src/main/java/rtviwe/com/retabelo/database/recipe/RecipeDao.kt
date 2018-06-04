package rtviwe.com.retabelo.database.recipe

import android.arch.persistence.room.*
import io.reactivex.Flowable

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipe(recipeEntry: RecipeEntry)

    @Query("SELECT * FROM recipe")
    fun getAllRecipes(): Flowable<List<RecipeEntry>>

    @Query("SELECT * FROM recipe WHERE id = :id")
    fun getRecipeById(id: Int): Flowable<List<RecipeEntry>>

    @Query("SELECT * FROM recipe WHERE isFavorite = 1")
    fun getFavoriteRecipes(): Flowable<List<RecipeEntry>>

    @Query("DELETE FROM recipe")
    fun nukeRecipeTable()

    @Update
    fun updateRecipe(recipeEntry: RecipeEntry)

    @Delete
    fun deleteRecipe(recipeEntry: RecipeEntry)
}