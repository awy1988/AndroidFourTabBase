package com.demo.appmvp.data.db;

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
    public String token;
}
