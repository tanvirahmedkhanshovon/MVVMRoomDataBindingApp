package com.mtb.mvvmroomdatabinding.model;

import com.mtb.mvvmroomdatabinding.BR;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "category_table")
public class Category extends BaseObservable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name ="id")
    private int category_id;
    @ColumnInfo(name = "category_name")
    private String name;
    @ColumnInfo(name = "category_desc")
    private String description;

    @Ignore
    public Category() {
    }

    public Category(int id, String name, String description) {
        this.category_id = id;
        this.name = name;
        this.description = description;
    }

    @Bindable
    public int getId() {
        return category_id;
    }

    public void setId(int category_id) {
        this.category_id = category_id;
        notifyPropertyChanged(BR.category_id);
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }
}
