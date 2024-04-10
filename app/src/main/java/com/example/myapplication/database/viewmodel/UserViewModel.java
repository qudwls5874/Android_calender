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
import com.example.myapplication.database.table.User;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private MutableLiveData<List<User>> modelUserList = new MutableLiveData<>();
    private UserDao userDao;

    public UserViewModel(@NonNull Application application) {
        super(application);
        UserDatabase db = UserDatabase.getInstance(application);
        userDao = db.getUserDao();
        select();
    }

    public LiveData<List<User>> getUserList(){
        return modelUserList;
    }

    public void select(){new SelectUserAsyncTask().execute();}

    public class SelectUserAsyncTask extends AsyncTask<Void, Void, List<User>> {
        @Override
        protected List<User> doInBackground(Void... voids) {
            return userDao.getAllUsers();
        }
        @Override
        protected void onPostExecute(List<User> userList) {
            super.onPostExecute(userList);
            modelUserList.setValue(userList);

        }
    }

    public void insert(User user){ new InserUserAsyncTask().execute(user); }

    public class InserUserAsyncTask extends AsyncTask<User, Void, Void> {
        @Override
        protected Void doInBackground(User... users) {
            userDao.insertUser(users[0]);
            return null;
        }
        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            select();
        }
    }

    public void update(User user) {
        new UpdateUserAsyncTask().execute(user);
    }

    private class UpdateUserAsyncTask extends AsyncTask<User, Void, Void> {
        @Override
        protected Void doInBackground(User... users) {
            userDao.updateUser(users[0]);
            return null;
        }
        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            select();
        }
    }

    public void delete(User user) {
        new DeleteUserAsyncTask().execute(user);
    }

    private class DeleteUserAsyncTask extends AsyncTask<User, Void, Void> {
        @Override
        protected Void doInBackground(User... users) {
            userDao.deleteUser(users[0]);
            return null;
        }
        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            select();
        }
    }

}
