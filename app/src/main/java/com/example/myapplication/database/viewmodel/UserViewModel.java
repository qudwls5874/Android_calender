package com.example.myapplication.database.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.myapplication.database.UserDatabase;
import com.example.myapplication.database.dao.UserDao;
import com.example.myapplication.database.repository.UserRepository;
import com.example.myapplication.database.table.User;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private MutableLiveData<List<User>> modelUserList = new MutableLiveData<>();
    private UserRepository repository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
        repository.select();
    }


    // 옵져버
    public LiveData<List<User>> getUserList(){
        return modelUserList;
    }




}
