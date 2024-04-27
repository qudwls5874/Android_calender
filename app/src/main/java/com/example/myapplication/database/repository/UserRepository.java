package com.example.myapplication.database.repository;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.database.UserDatabase;
import com.example.myapplication.database.dao.UserDao;
import com.example.myapplication.database.table.User;
import com.example.myapplication.database.view.UserJoin;
import com.example.myapplication.dataclass.UserProfile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class UserRepository {

    private MutableLiveData<List<User>> modelUserList = new MutableLiveData<>();
    private Application application;
    private UserDatabase db;
    private UserDao userDao;

    private List<UserProfile> userProfile;


    public UserRepository(Application application){
        db = UserDatabase.getInstance(application);
        userDao = db.getUserDao();
        this.application = application;
    }




    public void setAllUser(List<UserJoin> userList, List<UserProfile> userProfile, Context context){
        this.userProfile = userProfile;
        new InsertUserAsyncTask().execute(userList);
    }
    private class InsertUserAsyncTask extends AsyncTask<List<UserJoin>, Void, Void>{
        @Override
        protected Void doInBackground(List<UserJoin>... lists) {
            for (List<UserJoin> userJoins : lists) {
                for (UserJoin forData : userJoins) {
                    // @Transaction 어노테이션을 사용하여 트랜잭션 시작
                    Long index = userDao.insertUser(forData.user);
                    userDao.insertAllUser(forData.userTelList, forData.userAddressList, forData.userEventsList);

                    // 이미지 파일 저장
                    try {
                        UserProfile profile = userProfile.stream().filter(strData -> strData.getProfileId().equals(forData.user.getUserUrl())).findFirst().orElse(null);
                        if (profile != null){

                            String fileName = index+".jpg"; // 파일 이름 설정
                            File file = new File(application.getFilesDir(), fileName); // 내부 저장소에 파일 생성
                            FileOutputStream fos = new FileOutputStream(file);
                            profile.getProifle().compress(Bitmap.CompressFormat.JPEG, 100, fos); // 비트맵을 JPEG 형식으로 압축하여 파일에 저장
                            fos.close();

                            String profileImagePath = file.getAbsolutePath(); // 파일의 절대 경로를 변수에 저장
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
