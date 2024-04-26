package com.example.myapplication.database.view;


import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.myapplication.database.table.User;
import com.example.myapplication.database.table.user.UserAddress;
import com.example.myapplication.database.table.user.UserEvent;
import com.example.myapplication.database.table.user.UserTel;

import java.util.List;

public class UserJoin {

    @Embedded
    public User user;

    @Relation(parentColumn = "userId", entityColumn = "userId")
    public List<UserTel> userTelList;

    @Relation(parentColumn = "userId", entityColumn = "userId")
    public List<UserAddress> userAddressList;

    @Relation(parentColumn = "userId", entityColumn = "userId")
    public List<UserEvent> userEventsList;

}
