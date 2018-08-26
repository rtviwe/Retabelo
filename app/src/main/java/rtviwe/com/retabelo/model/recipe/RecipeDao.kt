package rtviwe.com.retabelo.model.recipe

import androidx.paging.DataSource
import androidx.room.*

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipe(recipeEntry: RecipeEntry)

    @Query("SELECT * FROM recipe")
    fun getAllRecipes(): DataSource.Factory<Int, RecipeEntry>

    @Query("SELECT * FROM recipe WHERE name LIKE :name")
    fun getRecipesByNameForSearch(name: String): DataSource.Factory<Int, RecipeEntry>

    @Query("SELECT * FROM recipe WHERE id = :id")
    fun getRecipeById(id: Int): DataSource.Factory<Int, RecipeEntry>

    @Query("SELECT * FROM recipe WHERE isFavorite = 1")
    fun getAllFavoriteRecipes(): DataSource.Factory<Int, RecipeEntry>

    @Query("SELECT * FROM recipe WHERE isFavorite = 1 AND name LIKE :name")
    fun getFavoriteRecipesByName(name: String): DataSource.Factory<Int, RecipeEntry>

    @Query("SELECT * FROM recipe WHERE isFavorite  = 1 AND id = :id")
    fun getFavoriteRecipeById(id: Int): DataSource.Factory<Int, RecipeEntry>

    @Query("SELECT * FROM recipe WHERE name = :name")
    fun findRecipeByName(name: String): RecipeEntry?

    @Query("SELECT (isFavorite = 1) FROM recipe WHERE name = :name")
    fun isFavoriteByName(name: String): Boolean

    @Query("DELETE FROM recipe WHERE name = :name")
    fun deleteRecipeByName(name: String)

    @Update
    fun updateRecipe(recipeEntry: RecipeEntry)

    @Delete
    fun deleteRecipe(recipeEntry: RecipeEntry)

    @Query("DELETE FROM recipe")
    fun nukeRecipeTable()
}