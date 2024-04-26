package com.example.myapplication.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.myapplication.database.table.User;
import com.example.myapplication.database.table.user.UserAddress;
import com.example.myapplication.database.table.user.UserEvent;
import com.example.myapplication.database.table.user.UserTel;

import java.util.List;

@Dao
public interface UserDao {

    /* 인설트 구문 */
    @Insert
    void insertAllUser(List<User> userList);
    @Insert
    void insertTel(List<UserTel> telList);
    @Insert
    void insertAddress(List<UserAddress> addressList);
    @Insert
    void insertEvent(List<UserEvent> eventList);

    @Insert
    void insertUser(User user);
    @Update
    void updateUser(User user);
    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    @Transaction
    @Insert
    void insertAllUser(User user, List<UserTel> telList, List<UserAddress> addressList, List<UserEvent> eventList);
/*
    @Transaction
    default void insertUserWithDetails(User user, List<UserTel> userTels, List<UserAddress> userAddresses, List<UserEvent> userEvents) {
        insertUser(user);
        for (UserTel userTel : userTels) {
//            userTel.setUserId(user.getUserId());
//            insertUserTel(userTel);
        }
        for (UserAddress userAddress : userAddresses) {
//            userAddress.setUserId(user.getUserId());
//            insertUserAddress(userAddress);
        }
        for (UserEvent userEvent : userEvents) {
//            userEvent.setUserId(user.getUserId());
//            insertUserEvent(userEvent);
        }
    }
*/
}
