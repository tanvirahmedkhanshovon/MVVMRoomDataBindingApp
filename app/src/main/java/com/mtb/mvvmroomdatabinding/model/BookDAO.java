package com.mtb.mvvmroomdatabinding.model;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface BookDAO {

    @Insert
    void insert (Book book);
    @Update
    void update (Book book);
    @Delete
    void delete (Book book);
    @Query("SELECT * FROM book_table")
    LiveData<List<Book>> getAllBooks();
    @Query("SELECT * From book_table WHERE category_id = :id")
    LiveData<List<Book>> getBookByCategory(int id);

}
