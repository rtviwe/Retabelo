package rtviwe.com.retabelo.database.recipe

import android.arch.paging.DataSource
import android.arch.persistence.room.*

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipe(recipeEntry: RecipeEntry)

    @Query("SELECT * FROM recipe")
    fun getAllRecipes(): DataSource.Factory<Int, RecipeEntry>

    @Query("SELECT * FROM recipe WHERE id = :id")
    fun getRecipeById(id: Int): DataSource.Factory<Int, RecipeEntry>

    @Query("SELECT * FROM recipe WHERE isFavorite = 1")
    fun getAllFavoriteRecipes(): DataSource.Factory<Int, RecipeEntry>

    @Query("SELECT * FROM recipe WHERE isFavorite = 1 AND id = :id")
    fun getFavoriteRecipeById(id: Int): DataSource.Factory<Int, RecipeEntry>

    @Query("SELECT * FROM recipe WHERE name = :name")
    fun findRecipeByName(name: String): RecipeEntry?

    @Query("DELETE FROM recipe WHERE name = :name")
    fun deleteRecipeByName(name: String)

    @Update
    fun updateRecipe(recipeEntry: RecipeEntry)

    @Delete
    fun deleteRecipe(recipeEntry: RecipeEntry)

    @Query("DELETE FROM recipe")
    fun nukeRecipeTable()
}