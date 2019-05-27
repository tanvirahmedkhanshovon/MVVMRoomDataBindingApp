package com.mtb.mvvmroomdatabinding;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.mtb.mvvmroomdatabinding.databinding.ActivityMainBinding;
import com.mtb.mvvmroomdatabinding.model.Book;
import com.mtb.mvvmroomdatabinding.model.Category;
import com.mtb.mvvmroomdatabinding.viewmodel.MainActivityViewModel;
import com.mtb.mvvmroomdatabinding.viewmodel.MainActivityViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_BOOK_REQUEST_CODE = 1;
    public static final int EDIT_BOOK_REQUEST_CODE = 2;
    private static final String TAG = "MainActivity";
    private ArrayList<Category> categoryList;
    private ArrayList<Book> bookList;
    private MainActivityViewModel viewModel;
    private ActivityMainBinding binding;
    private Category selectedCategory;
    private RecyclerView booksRecyclerView;
    private BooksAdapter adapter;
    private int seletectedBookId;
    @Inject
    public MainActivityViewModelFactory mainActivityViewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Book Library");

        ClickHandlerFloatingButton clickHandlerFloatingButton = new ClickHandlerFloatingButton();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setFavclicked(clickHandlerFloatingButton);


        App.getApp().getEbookShopComponent().inject(this);

        //viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        viewModel = ViewModelProviders.of(this,mainActivityViewModelFactory).get(MainActivityViewModel.class);
        viewModel.getCategoryList().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {



                categoryList = (ArrayList<Category>) categories;
                for (Category c : categories) {
                    Log.i(TAG, c.getName());


                }

                showOnSpinner();
            }
        });


//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    private void loadRecylcerView() {

        booksRecyclerView = binding.secondaryLayout.rvBooks;
        booksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        booksRecyclerView.setHasFixedSize(true);
        adapter = new BooksAdapter();
        booksRecyclerView.setAdapter(adapter);
        adapter.setBooks(bookList);
        adapter.setListener(new BooksAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Book book) {
                seletectedBookId = book.getId();
                Intent intent = new Intent(MainActivity.this, AddAndEmptyActivity.class);
                intent.putExtra(AddAndEmptyActivity.BOOK_ID, seletectedBookId);
                intent.putExtra(AddAndEmptyActivity.BOOK_NAME, book.getBook_name());
                intent.putExtra(AddAndEmptyActivity.BOOK_PRICE, book.getUnit_price());
                startActivityForResult(intent, EDIT_BOOK_REQUEST_CODE);

            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
               viewModel.deleteBook(bookList.get(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(booksRecyclerView);
    }

    private void loadBooksArrayList(int categoryId) {
        viewModel.getBookList(categoryId).observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
//                for (Book b: books){
//
//
//                    Log.i(TAG,b.getBook_name());
//                }

                bookList = (ArrayList<Book>) books;

                loadRecylcerView();
            }
        });


    }

    private void showOnSpinner() {

        ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(this, R.layout.spinner_item, categoryList);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        binding.setSpinnerAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        int selectedCategoryid = selectedCategory.getCategory_id();

        if (requestCode == ADD_BOOK_REQUEST_CODE && resultCode == RESULT_OK) {
            Book book = new Book();
            book.setCategory_id(selectedCategoryid);
            book.setBook_name(data.getStringExtra(AddAndEmptyActivity.BOOK_NAME));
            book.setUnit_price(data.getStringExtra(AddAndEmptyActivity.BOOK_PRICE));
            viewModel.addNewBook(book);

        } else if (requestCode == EDIT_BOOK_REQUEST_CODE && resultCode == RESULT_OK) {
            Book book = new Book();
            book.setId(seletectedBookId);
            book.setCategory_id(selectedCategoryid);
            book.setBook_name(data.getStringExtra(AddAndEmptyActivity.BOOK_NAME));
            book.setUnit_price(data.getStringExtra(AddAndEmptyActivity.BOOK_PRICE));
            viewModel.updateBook(book);

        }


    }

    public class ClickHandlerFloatingButton {

        public void onAddClicked(View view) {

//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();


            Intent intent = new Intent(MainActivity.this, AddAndEmptyActivity.class);

            startActivityForResult(intent, ADD_BOOK_REQUEST_CODE);

        }


        public void onSelectItem(AdapterView<?> parent, View view, int pos, long id) {

            selectedCategory = (Category) parent.getItemAtPosition(pos);
            String message = "id is " + selectedCategory.getCategory_id() + "\n name is " + selectedCategory.getName() + "\n description is " + selectedCategory.getDescription();

//            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            loadBooksArrayList(selectedCategory.getCategory_id());
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.clear();
    }
}
