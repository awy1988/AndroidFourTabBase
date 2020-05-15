package com.demo.data.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import org.jetbrains.annotations.NotNull;

@Entity
public class User {
    @PrimaryKey
    @NotNull
    public String id;
    public String name;
    public String type;
}
