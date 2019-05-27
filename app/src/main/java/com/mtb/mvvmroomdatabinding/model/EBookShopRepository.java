package com.mtb.mvvmroomdatabinding.model;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

public class EBookShopRepository {
    private CategoryDAO categoryDAO;
    private BookDAO bookDAO;
    private MutableLiveData<List<Category>> categoryList = new MutableLiveData<>();
    private MutableLiveData<List<Book>> bookList = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public EBookShopRepository(Application application) {


        BookDatabase bookDatabase = BookDatabase.getInstance(application);

        categoryDAO = bookDatabase.categoryDAO();
        bookDAO = bookDatabase.bookDAO();


    }

    public MutableLiveData<List<Category>> getCategoryList() {
        compositeDisposable.add(categoryDAO.getAllCategories()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Category>>() {
                    @Override
                    public void accept(List<Category> categories) throws Exception {

                        categoryList.postValue(categories);
                    }
                }));


        return categoryList;
    }

    public LiveData<List<Book>> getBookList(int categoryId) {


        compositeDisposable.add(bookDAO.getBookByCategory(categoryId)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Book>>() {
                    @Override
                    public void accept(List<Book> books) throws Exception {

                        bookList.postValue(books);
                    }
                }));
        return bookList;
    }

    public void insertCategory(final Category category) {


        compositeDisposable.add(Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {

                categoryDAO.insert(category);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }));


    }

    public void insertBook(final Book book) {

        compositeDisposable.add(Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                bookDAO.insert(book);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }));

    }

    public void deleteCategory(final Category category) {
        compositeDisposable.add(Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {

                categoryDAO.delete(category);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }));


    }

    public void deleteBook(final Book book) {

        compositeDisposable.add(Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                bookDAO.delete(book);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }));

    }

    public void updateCategory(final Category category) {
        compositeDisposable.add(Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {

                categoryDAO.update(category);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }));


    }

    public void updateBook(final Book book) {
        compositeDisposable.add(Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                bookDAO.update(book);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }));


    }

    public void clear(){


        compositeDisposable.clear();
    }

}
