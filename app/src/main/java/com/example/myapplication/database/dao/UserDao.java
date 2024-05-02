package com.example.myapplication.database.dao;

import android.util.Log;

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
import com.example.myapplication.database.view.UserJoin;

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
    Long insertUser(User user);
    @Update
    void updateUser(User user);
    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    /* 조인테이블 */
    @Query("SELECT * FROM users")
    List<UserJoin> getAllUserJoin();

    /* 카운트 */
    @Query("SELECT COUNT(*) FROM usertels WHERE userId = :userId")
    Long getTelCount(Long userId);
    @Query("SELECT COUNT(*) FROM useraddresss WHERE userId = :userId")
    Long getAddressCount(Long userId);
    @Query("SELECT COUNT(*) FROM userevents WHERE userId = :userId")
    Long getEventCount(Long userId);

    /* 리스트 전체 저장 */
    @Transaction
    @Insert
    void insertAllUser(List<UserTel> telList, List<UserAddress> addressList, List<UserEvent> eventList);

}
