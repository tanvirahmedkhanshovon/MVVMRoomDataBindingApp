package com.mtb.mvvmroomdatabinding.model;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Category.class,Book.class},version = 1,exportSchema = false)
public abstract class BookDatabase extends RoomDatabase {

    public abstract CategoryDAO categoryDAO();

    public abstract BookDAO bookDAO();

    private static BookDatabase instance;

    public static synchronized BookDatabase getInstance(Context context) {


        if (instance == null) {


            instance = Room.databaseBuilder(context.getApplicationContext(), BookDatabase.class, "book_database").addCallback(callback).fallbackToDestructiveMigration().build();
        }

        return instance;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new InitialDataAsyncTask(instance).execute();
        }
    };

    private static class InitialDataAsyncTask extends AsyncTask<Void, Void, Void> {

        private CategoryDAO categoryDAO;
        private BookDAO bookDAO;

        public InitialDataAsyncTask(BookDatabase bookDatabase) {
            categoryDAO = bookDatabase.categoryDAO();
            bookDAO = bookDatabase.bookDAO();


        }

        @Override
        protected Void doInBackground(Void... voids) {

            Category category1 = new Category();
            category1.setName("Text Books");
            category1.setDescription("Text Books Description");

            Category category2 = new Category();
            category2.setName("Novels");
            category2.setDescription("Novels Description");

            Category category3 = new Category();
            category3.setName("Other Books");
            category3.setDescription("Text Books Description");

            categoryDAO.insert(category1);
            categoryDAO.insert(category2);
            categoryDAO.insert(category3);

            Book book1 = new Book();
            book1.setBook_name("High school Java ");
            book1.setUnit_price("$150");
            book1.setCategory_id(1);

            Book book2 = new Book();
            book2.setBook_name("Mathematics for beginners");
            book2.setUnit_price("$200");
            book2.setCategory_id(1);

            Book book3 = new Book();
            book3.setBook_name("Object Oriented Androd App Design");
            book3.setUnit_price("$150");
            book3.setCategory_id(1);

            Book book4 = new Book();
            book4.setBook_name("Astrology for beginners");
            book4.setUnit_price("$190");
            book4.setCategory_id(1);

            Book book5 = new Book();
            book5.setBook_name("High school Magic Tricks ");
            book5.setUnit_price("$150");
            book5.setCategory_id(1);

            Book book6 = new Book();
            book6.setBook_name("Chemistry  for secondary school students");
            book6.setUnit_price("$250");
            book6.setCategory_id(1);

            Book book7 = new Book();
            book7.setBook_name("A Game of Cats");
            book7.setUnit_price("$19.99");
            book7.setCategory_id(2);

            Book book8 = new Book();
            book8.setBook_name("The Hound of the New York");
            book8.setUnit_price("$16.99");
            book8.setCategory_id(2);

            Book book9 = new Book();
            book9.setBook_name("Adventures of Joe Finn");
            book9.setUnit_price("$13");
            book9.setCategory_id(2);

            Book book10 = new Book();
            book10.setBook_name("Arc of witches");
            book10.setUnit_price("$19.99");
            book10.setCategory_id(2);

            Book book11 = new Book();
            book11.setBook_name("Can I run");
            book11.setUnit_price("$16.99");
            book11.setCategory_id(2);

            Book book12 = new Book();
            book12.setBook_name("Story of a joker");
            book12.setUnit_price("$13");
            book12.setCategory_id(2);

            Book book13 = new Book();
            book13.setBook_name("Notes of a alien life cycle researcher");
            book13.setUnit_price("$1250");
            book13.setCategory_id(3);

            Book book14 = new Book();
            book14.setBook_name("Top 9 myths abut UFOs");
            book14.setUnit_price("$789");
            book14.setCategory_id(3);

            Book book15 = new Book();
            book15.setBook_name("How to become a millionaire in 24 hours");
            book15.setUnit_price("$1250");
            book15.setCategory_id(3);

            Book book16 = new Book();
            book16.setBook_name("1 hour work month");
            book16.setUnit_price("$199");
            book16.setCategory_id(3);

            bookDAO.insert(book1);
            bookDAO.insert(book2);
            bookDAO.insert(book3);
            bookDAO.insert(book4);
            bookDAO.insert(book5);
            bookDAO.insert(book6);
            bookDAO.insert(book7);
            bookDAO.insert(book8);
            bookDAO.insert(book9);
            bookDAO.insert(book10);
            bookDAO.insert(book11);
            bookDAO.insert(book12);
            bookDAO.insert(book13);
            bookDAO.insert(book14);
            bookDAO.insert(book15);
            bookDAO.insert(book16);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }




}

