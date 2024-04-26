package com.example.myapplication.database.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.database.UserDatabase;
import com.example.myapplication.database.dao.UserDao;
import com.example.myapplication.database.table.User;
import com.example.myapplication.database.view.UserJoin;

import java.util.List;

public class UserRepository {

    private MutableLiveData<List<User>> modelUserList = new MutableLiveData<>();
    private UserDatabase db;
    private UserDao userDao;

    public UserRepository(Application application){
        db = UserDatabase.getInstance(application);
        userDao = db.getUserDao();
    }

    public void setAllUser(List<UserJoin> userList){}
    private class InsertUserAsyncTask extends AsyncTask<List<UserJoin>, Void, Void>{
        @Override
        protected Void doInBackground(List<UserJoin>... lists) {
            for (List<UserJoin> userJoins : lists) {
                for (UserJoin user : userJoins) {
                    // @Transaction 어노테이션을 사용하여 트랜잭션 시작
                    userDao.insertAllUser(user.user, user.userTelList, user.userAddressList, user.userEventsList);
                }
            }
            return null;
        }
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
