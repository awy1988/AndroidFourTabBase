package com.demo.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    LiveData<List<User>> getAll();

    @Query("SELECT * FROM user WHERE token = (:token)")
    User getUserInfoByToken(String token);

    @Query("SELECT * FROM user WHERE id = (:id)")
    User getUserInfoById(String id);
    //
    //@Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
    //User findByName(String first, String last);

    @Insert
    void insertAll(User... users);

    @Update
    public void updateUsers(User... users);

    @Delete
    void delete(User user);
}
