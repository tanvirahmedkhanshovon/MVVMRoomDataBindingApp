package com.mtb.mvvmroomdatabinding.model;


import com.mtb.mvvmroomdatabinding.BR;

import java.util.Objects;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "book_table",foreignKeys = @ForeignKey(entity = Category.class,parentColumns = "id",childColumns = "category_id",onDelete = CASCADE),indices = {@Index("category_id")})
public class Book extends BaseObservable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "book_id")
    private int id;
    @ColumnInfo(name = "book_name")
    private String book_name;
    @ColumnInfo(name = "book_price")
    private String unit_price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return getId() == book.getId() &&
                getCategory_id() == book.getCategory_id() &&
                Objects.equals(getBook_name(), book.getBook_name()) &&
                Objects.equals(getUnit_price(), book.getUnit_price());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getBook_name(), getUnit_price(), getCategory_id());
    }

    @ColumnInfo(name = "category_id")
    private int category_id;

    @Ignore
    public Book() {
    }

    public Book(int id, String book_name, String unit_price, int category_id) {
        this.id = id;
        this.book_name = book_name;
        this.unit_price = unit_price;
        this.category_id = category_id;
    }

    @Bindable
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
        notifyPropertyChanged(BR.book_name);
    }

    @Bindable

    public String getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(String unit_price) {
        this.unit_price = unit_price;
        notifyPropertyChanged(BR.unit_price);
    }

    @Bindable
    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
        notifyPropertyChanged(BR.category_id);
    }
}
