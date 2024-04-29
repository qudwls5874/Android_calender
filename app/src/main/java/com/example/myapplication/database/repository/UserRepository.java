package com.example.myapplication.database.repository;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.database.UserDatabase;
import com.example.myapplication.database.dao.UserDao;
import com.example.myapplication.database.table.User;
import com.example.myapplication.database.view.UserJoin;
import com.example.myapplication.dataclass.UserProfile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserRepository {

    private MutableLiveData<List<User>> modelUserList = new MutableLiveData<>();
    public MutableLiveData<List<UserJoin>> userJoinList = new MutableLiveData<>();
    private Application application;
    private UserDatabase db;
    private UserDao userDao;

    private List<UserProfile> userProfile;


    public UserRepository(Application application){
        db = UserDatabase.getInstance(application);
        userDao = db.getUserDao();
        this.application = application;
    }




    public Boolean setAllUser(List<UserJoin> userList, List<UserProfile> userProfile){
        this.userProfile = userProfile;
        try {
            return new InsertUserAsyncTask().execute(userList).get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private class InsertUserAsyncTask extends AsyncTask<List<UserJoin>, Void, Boolean>{
        @Override
        protected Boolean doInBackground(List<UserJoin>... lists) {
            for (List<UserJoin> userJoins : lists) {
                for (UserJoin forData : userJoins) {
                    // 사용자 삽입
                    Long index = userDao.insertUser(forData.user);
                    userDao.insertAllUser(forData.userTelList, forData.userAddressList, forData.userEventsList);
                    try {
                        UserProfile profile = userProfile.stream().filter(strData -> strData.getProfileId().equals(forData.user.getUserUrl())).findFirst().orElse(null);
                        if (profile != null){
                            String fileName = index+".jpg"; // 파일 이름 설정
                            File file = new File(application.getFilesDir(), fileName); // 내부 저장소에 파일 생성
                            FileOutputStream fos = new FileOutputStream(file);
                            profile.getProifle().compress(Bitmap.CompressFormat.JPEG, 100, fos); // 비트맵을 JPEG 형식으로 압축하여 파일에 저장
                            fos.close();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return true;
        }
    }


    // getAllUserJoin
    public List<UserJoin> selectUserJoin(){
        try {
            List<UserJoin> result = new SelectJoinUserAsyncTask().execute().get();
            userJoinList.setValue(result);
            return result; // AsyncTask의 결과를 가져옴
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public class SelectJoinUserAsyncTask extends AsyncTask<Void, Void, List<UserJoin>>{
        @Override
        protected List<UserJoin> doInBackground(Void... voids) {
            return userDao.getAllUserJoin();
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
