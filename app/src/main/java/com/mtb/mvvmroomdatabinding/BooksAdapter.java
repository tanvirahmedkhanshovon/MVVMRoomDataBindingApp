package com.mtb.mvvmroomdatabinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.mtb.mvvmroomdatabinding.databinding.BookListItemBinding;
import com.mtb.mvvmroomdatabinding.model.Book;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {

    private OnItemClickListener listener;

    private ArrayList<Book> books = new ArrayList<>();
    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        BookListItemBinding bookListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.book_list_item,parent,false);
        return new BookViewHolder(bookListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
     Book book = books.get(position);
     holder.itemBinding.setBook(book);
    }

    @Override
    public int getItemCount() {

        if(!books.isEmpty()) {
            return books.size();
        }

        return 0;
    }

    class BookViewHolder extends RecyclerView.ViewHolder{
        private BookListItemBinding itemBinding;
        public BookViewHolder(@NonNull BookListItemBinding itemBinding) {
           super(itemBinding.getRoot());

            this.itemBinding = itemBinding;
            itemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   int clickedPosition = getAdapterPosition();
                   if(listener!=null && clickedPosition != RecyclerView.NO_POSITION) {
                       listener.onItemClick(books.get(clickedPosition));
                   }
                }
            });
        }
    }


    public interface OnItemClickListener{

void onItemClick(Book book);


    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setBooks(ArrayList<Book> newbooks) {
//        this.books = books;
//        notifyDataSetChanged();
final DiffUtil.DiffResult result = DiffUtil.calculateDiff(new BookDiffCallBack(books,newbooks),false);
books = newbooks;

result.dispatchUpdatesTo(BooksAdapter.this);

    }
}
