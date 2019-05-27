package com.mtb.mvvmroomdatabinding.model;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

@Database(entities = {Category.class, Book.class}, version = 1, exportSchema = false)
public abstract class BookDatabase extends RoomDatabase {
    private static Observable<Book> bookObservable;


    private static Observable<Category> categoryObservable;

    private static CompositeDisposable compositeDisposable = new CompositeDisposable();
    private static BookDatabase instance;
    private static RoomDatabase.Callback callback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            final CategoryDAO categoryDAO = instance.categoryDAO();
            final BookDAO bookDAO = instance.bookDAO();

            categoryObservable = Observable.fromArray(getCurrentCategories().toArray(new Category[0]));
            compositeDisposable.add(categoryObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith( new DisposableObserver<Category>() {
                @Override
                public void onNext(final Category category) {


                    Completable.fromAction(new Action() {
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
                            });

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {


                }
            }));
            bookObservable = Observable.fromArray(getCurrentBooks().toArray(new Book[0]));
            compositeDisposable.add(bookObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith( new DisposableObserver<Book>() {
                @Override
                public void onNext(final Book book) {


                    Completable.fromAction(new Action() {
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
                            });
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            }));

            compositeDisposable.clear();
        }
    };


    public static synchronized BookDatabase getInstance(Context context) {


        if (instance == null) {


            instance = Room.databaseBuilder(context.getApplicationContext(), BookDatabase.class, "book_database").addCallback(callback).fallbackToDestructiveMigration().build();
        }

        return instance;
    }

    public static ArrayList<Category> getCurrentCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        Category category1 = new Category();
        category1.setName("Text Books");
        category1.setDescription("Text Books Description");
        categories.add(category1);

        Category category2 = new Category();
        category2.setName("Novels");
        category2.setDescription("Novels Description");
        categories.add(category2);
        Category category3 = new Category();
        category3.setName("Other Books");
        category3.setDescription("Text Books Description");
        categories.add(category3);

        return categories;

    }

    public static ArrayList<Book> getCurrentBooks() {
        ArrayList<Book> books = new ArrayList<>();
        Book book1 = new Book();
        book1.setBook_name("High school Java ");
        book1.setUnit_price("$150");
        book1.setCategory_id(1);
        books.add(book1);
        Book book2 = new Book();
        book2.setBook_name("Mathematics for beginners");
        book2.setUnit_price("$200");
        book2.setCategory_id(1);
        books.add(book2);
        Book book3 = new Book();
        book3.setBook_name("Object Oriented Androd App Design");
        book3.setUnit_price("$150");
        book3.setCategory_id(1);
        books.add(book3);
        Book book4 = new Book();
        book4.setBook_name("Astrology for beginners");
        book4.setUnit_price("$190");
        book4.setCategory_id(1);
        books.add(book4);

        Book book5 = new Book();
        book5.setBook_name("High school Magic Tricks ");
        book5.setUnit_price("$150");
        book5.setCategory_id(1);
        books.add(book5);
        Book book6 = new Book();
        book6.setBook_name("Chemistry  for secondary school students");
        book6.setUnit_price("$250");
        book6.setCategory_id(1);
        books.add(book6);
        Book book7 = new Book();
        book7.setBook_name("A Game of Cats");
        book7.setUnit_price("$19.99");
        book7.setCategory_id(2);
        books.add(book7);
        Book book8 = new Book();
        book8.setBook_name("The Hound of the New York");
        book8.setUnit_price("$16.99");
        book8.setCategory_id(2);
        books.add(book8);
        Book book9 = new Book();
        book9.setBook_name("Adventures of Joe Finn");
        book9.setUnit_price("$13");
        book9.setCategory_id(2);
        books.add(book9);
        Book book10 = new Book();
        book10.setBook_name("Arc of witches");
        book10.setUnit_price("$19.99");
        book10.setCategory_id(2);
        books.add(book10);
        Book book11 = new Book();
        book11.setBook_name("Can I run");
        book11.setUnit_price("$16.99");
        book11.setCategory_id(2);
        books.add(book11);
        Book book12 = new Book();
        book12.setBook_name("Story of a joker");
        book12.setUnit_price("$13");
        book12.setCategory_id(2);
        books.add(book12);

        Book book13 = new Book();
        book13.setBook_name("Notes of a alien life cycle researcher");
        book13.setUnit_price("$1250");
        book13.setCategory_id(3);
        books.add(book13);

        Book book14 = new Book();
        book14.setBook_name("Top 9 myths abut UFOs");
        book14.setUnit_price("$789");
        book14.setCategory_id(3);
        books.add(book14);

        Book book15 = new Book();
        book15.setBook_name("How to become a millionaire in 24 hours");
        book15.setUnit_price("$1250");
        book15.setCategory_id(3);
        books.add(book15);

        Book book16 = new Book();
        book16.setBook_name("1 hour work month");
        book16.setUnit_price("$199");
        book16.setCategory_id(3);
        books.add(book16);

        return books;

    }

    public abstract CategoryDAO categoryDAO();

    public abstract BookDAO bookDAO();


}

