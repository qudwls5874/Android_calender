package com.example.myapplication.database.viewmodel;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.myapplication.database.UserDatabase;
import com.example.myapplication.database.dao.UserDao;
import com.example.myapplication.database.repository.UserRepository;
import com.example.myapplication.database.table.User;
import com.example.myapplication.database.view.UserJoin;
import com.example.myapplication.dataclass.UserProfile;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private MutableLiveData<List<UserJoin>> userJoinList;
    private UserRepository repository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
        repository.selectUserJoin();
        userJoinList = repository.userJoinList;
    }


    // 옵져버 select
    public LiveData<List<UserJoin>> getObUserJoinList(){
        return userJoinList;
    }

    // 동적 select
    public List<UserJoin> getUserJoinList(){
        List<UserJoin> result = repository.selectUserJoin();
        userJoinList.setValue(result);
        return result;
    }

    // 동적 데이터 저장 (리스트)
    public Boolean setUserList(List<UserJoin> userJoin, List<UserProfile> userProfile){
        return repository.setAllUser(userJoin, userProfile);
    }

    // 동적 데이터 저장(단독)
    public Boolean setUser(UserJoin userJoin, UserProfile userProfile){
        return repository.setUser(userJoin, userProfile);
    }







}
