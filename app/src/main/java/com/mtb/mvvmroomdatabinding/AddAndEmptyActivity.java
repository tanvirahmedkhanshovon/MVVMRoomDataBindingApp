package com.mtb.mvvmroomdatabinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.mtb.mvvmroomdatabinding.databinding.ActivityAddAndEmptyBinding;
import com.mtb.mvvmroomdatabinding.model.Book;

public class AddAndEmptyActivity extends AppCompatActivity {

    ActivityAddAndEmptyBinding binding;
    Book book;
    public static String BOOK_ID="bookid";
    public static String BOOK_NAME="bookname";
    public static String BOOK_PRICE="bookprice";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_and_empty);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_and_empty);

        AddAndEditClickHandler clickHandler = new AddAndEditClickHandler(this);
        binding.setSubmit(clickHandler);
        book = new Book();
        binding.setBook(book);
        Intent intent = getIntent();
        if(intent.hasExtra(BOOK_ID)){

setTitle("Edit Book");

book.setBook_name(intent.getStringExtra(BOOK_NAME));
book.setUnit_price(intent.getStringExtra(BOOK_PRICE));

        }
        else{
setTitle("Add New Book");


        }



    }


    public class AddAndEditClickHandler {

        Context context;

        public AddAndEditClickHandler(Context context) {
            this.context = context;
        }


        public void onSubmit(View view){

            if (TextUtils.isEmpty(book.getBook_name())) {

                Toast.makeText(getApplicationContext(), "Name can't be empty ", Toast.LENGTH_SHORT).show();
            } else {

//                String namevalue = book.getBook_name();
//                String emailvalue = student.getEmail();
//                String countryvalue = student.getCountry();

                Intent intent = new Intent();
                intent.putExtra(BOOK_NAME, book.getBook_name());
                intent.putExtra(BOOK_PRICE, book.getUnit_price());
                setResult(RESULT_OK, intent);
                finish();
            }


        }
    }
}
