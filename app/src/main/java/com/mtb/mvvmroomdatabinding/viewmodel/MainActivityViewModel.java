package com.mtb.mvvmroomdatabinding.viewmodel;

import android.app.Application;

import com.mtb.mvvmroomdatabinding.model.Book;
import com.mtb.mvvmroomdatabinding.model.Category;
import com.mtb.mvvmroomdatabinding.model.EBookShopRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MainActivityViewModel extends AndroidViewModel {


    private EBookShopRepository repository;
    private LiveData<List<Category>> categoryList;
    private LiveData<List<Book>> bookList;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new EBookShopRepository(application);
    }


    public LiveData<List<Category>> getCategoryList() {

        categoryList = repository.getCategoryList();

        return categoryList;
    }

    public LiveData<List<Book>> getBookList(int cateroyId) {
        bookList = repository.getBookList(cateroyId);
        return bookList;
    }

    public void addNewBook(Book book) {

        repository.insertBook(book);
    }

    public void updateBook(Book book) {

        repository.updateBook(book);
    }

    public void deleteBook(Book book) {

        repository.deleteBook(book);
    }

    public void addNewCategory(Category category) {

        repository.insertCategory(category);
    }

    public void updateCategory(Category category) {

        repository.updateCategory(category);
    }

    public void deleteCategory(Category category) {

        repository.deleteCategory(category);
    }


}
