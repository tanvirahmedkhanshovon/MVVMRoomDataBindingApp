package com.mtb.mvvmroomdatabinding.model;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import io.reactivex.Flowable;

@Dao
public interface CategoryDAO {
    @Insert
    void insert (Category category);
    @Update
    void update(Category category);
    @Delete
    void delete (Category category);
    @Query("SELECT * FROM category_table")
    Flowable<List<Category>> getAllCategories();
}
