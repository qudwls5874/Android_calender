package com.example.myapplication.database;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.myapplication.database.dao.MenuCategoryDao;
import com.example.myapplication.database.dao.MenuListDao;
import com.example.myapplication.database.dao.MoneyNameDao;
import com.example.myapplication.database.dao.SettingDateDao;
import com.example.myapplication.database.dao.UserDao;
import com.example.myapplication.database.table.MenuCategory;
import com.example.myapplication.database.table.MenuList;
import com.example.myapplication.database.table.MoneyName;
import com.example.myapplication.database.table.SettingDate;
import com.example.myapplication.database.table.User;

import java.util.concurrent.Executors;


@Database(entities =
        {
                User.class, MoneyName.class, MenuCategory.class, MenuList.class,
                SettingDate.class
        }, version = 1, exportSchema = false )

public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDao getUserDao();
    public abstract MoneyNameDao getMoneyNameDao();
    public abstract MenuCategoryDao getMenuCategoryDao();
    public abstract MenuListDao getMenuListDao();
    public abstract SettingDateDao getSettingDao();


    private static UserDatabase instance;
    public static synchronized UserDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),UserDatabase.class, "userDatabase")
                    .fallbackToDestructiveMigration()
                    .addCallback(new RoomDatabase.Callback(){
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            Executors.newSingleThreadExecutor().execute(() -> {
                                insertInitialData(instance);
                            });
                        }
                    })
                    .build();
        }
        return instance;
    }

    private static void insertInitialData(UserDatabase  userDatabase) {
        MoneyNameDao moneyNameDao = userDatabase.getMoneyNameDao();
        moneyNameDao.insert(new MoneyName("정액제"));
        moneyNameDao.insert(new MoneyName("차감"));
        moneyNameDao.insert(new MoneyName("결제"));

        MenuCategoryDao menuCategoryDao = userDatabase.getMenuCategoryDao();
        menuCategoryDao.insert(new MenuCategory("네일", "N"));
        menuCategoryDao.insert(new MenuCategory("헤어", "N"));
        menuCategoryDao.insert(new MenuCategory("애견미용", "N"));
        menuCategoryDao.insert(new MenuCategory("속눈썹", "N"));
        menuCategoryDao.insert(new MenuCategory("피부", "N"));
        menuCategoryDao.insert(new MenuCategory("메이크업", "N"));
        menuCategoryDao.insert(new MenuCategory("왁싱", "N"));
        menuCategoryDao.insert(new MenuCategory("타투/문신", "N"));

        MenuListDao menuListDao = userDatabase.getMenuListDao();
        menuListDao.insert(new MenuList(0, "기본케어", "10000"));
        menuListDao.insert(new MenuList(0, "매니큐어", "20000"));
        menuListDao.insert(new MenuList(0, "페디큐어", "30000"));
        menuListDao.insert(new MenuList(0, "프렌치", "30000"));
        menuListDao.insert(new MenuList(0, "아크릴", "50000"));
        menuListDao.insert(new MenuList(0, "젤", "40000"));
        menuListDao.insert(new MenuList(0, "스톤/주얼리", "50000"));
        menuListDao.insert(new MenuList(0, "3D네일 아트", "60000"));
        menuListDao.insert(new MenuList(0, "네일 확장/팁", "70000"));
        menuListDao.insert(new MenuList(0, "워터마블/그라데이션/오므브", "40000"));
        menuListDao.insert(new MenuList(0, "피디큐어", "40000"));
        menuListDao.insert(new MenuList(0, "네일 디자인 커스터마이징", "50000"));

        menuListDao.insert(new MenuList(1, "기본 컷 & 스타일링", "20000"));
        menuListDao.insert(new MenuList(1, "펌 & 매직펌", "50000"));
        menuListDao.insert(new MenuList(1, "염색 & 헤어 칼라링", "40000"));
        menuListDao.insert(new MenuList(1, "헤어 트리트먼트 & 스파", "30000"));
        menuListDao.insert(new MenuList(1, "헤어 확장 & 웨딩 헤어", "100000"));
        menuListDao.insert(new MenuList(1, "헤어 퍼머 & 스트레이트닝", "50000"));
        menuListDao.insert(new MenuList(1, "헤어 업스타일 & 브레이딩", "40000"));
        menuListDao.insert(new MenuList(1, "키즈 컷", "15,000"));
        menuListDao.insert(new MenuList(1, "바버샵 서비스", "20000"));
        menuListDao.insert(new MenuList(1, "트리트먼트", "30000"));
        menuListDao.insert(new MenuList(1, "헤어 마사지", "20000"));

        menuListDao.insert(new MenuList(2, "기본 컷 & 스타일링", "30000"));
        menuListDao.insert(new MenuList(2, "목욕 & 드라이", "20000"));
        menuListDao.insert(new MenuList(2, "전신 트리밍", "40000"));
        menuListDao.insert(new MenuList(2, "발톱 깍기", "10000"));
        menuListDao.insert(new MenuList(2, "트리트먼트 & 스파", "30000"));
        menuListDao.insert(new MenuList(2, "털 염색", "50000"));
        menuListDao.insert(new MenuList(2, "디-멜링 & 브러싱", "30000"));
        menuListDao.insert(new MenuList(2, "데쉬 & 퍼프(스타일링)", "40000"));
        menuListDao.insert(new MenuList(2, "피부 케어", "40000"));
        menuListDao.insert(new MenuList(2, "귀 청소 & 이빨 청소", "10000"));
        menuListDao.insert(new MenuList(2, "애견 마사지", "20000"));

        menuListDao.insert(new MenuList(3, "속눈썹 연장 (일반)", "50000"));
        menuListDao.insert(new MenuList(3, "볼륨 라쉬", "80000"));
        menuListDao.insert(new MenuList(3, "나치럴 라쉬", "60000"));
        menuListDao.insert(new MenuList(3, "속눈썹 펌", "40000"));
        menuListDao.insert(new MenuList(3, "속눈썹 틴팅", "30000"));
        menuListDao.insert(new MenuList(3, "속눈썹 리무버", "20000"));
        menuListDao.insert(new MenuList(3, "속눈썹 케어", "30000"));
        menuListDao.insert(new MenuList(3, "속눈썹 마스크", "30000"));
        menuListDao.insert(new MenuList(3, "속눈썹 라인", "20000"));
        menuListDao.insert(new MenuList(3, "속눈썹 컬", "40000"));
        menuListDao.insert(new MenuList(3, "속눈썹 컬러링", "30000"));

        menuListDao.insert(new MenuList(4, "기본 피부 관리", "50000"));
        menuListDao.insert(new MenuList(4, "딥 클렌징", "60000"));
        menuListDao.insert(new MenuList(4, "피부 페셜", "70000"));
        menuListDao.insert(new MenuList(4, "아쿠아 피부 관리", "80000"));
        menuListDao.insert(new MenuList(4, "안티에이징 관리", "100000"));
        menuListDao.insert(new MenuList(4, "화이트닝 & 브라이트닝", "80000"));
        menuListDao.insert(new MenuList(4, "자극 없는 피부 관리", "70000"));
        menuListDao.insert(new MenuList(4, "블랙헤드 & 모공 관리", "60000"));
        menuListDao.insert(new MenuList(4, "모공 수축 관리", "70000"));
        menuListDao.insert(new MenuList(4, "산소 피부 관리", "80000"));
        menuListDao.insert(new MenuList(4, "피부 레이저 관리", "100000"));
        menuListDao.insert(new MenuList(4, "피부 필링", "80000"));
        menuListDao.insert(new MenuList(4, "피부 자극 완화 팩", "70000"));
        menuListDao.insert(new MenuList(4, "피부 케어 컨설팅", "40000"));

        menuListDao.insert(new MenuList(5, "베이스 메이크업", "50000"));
        menuListDao.insert(new MenuList(5, "파티 메이크업", "70000"));
        menuListDao.insert(new MenuList(5, "웨딩 메이크업", "100000"));
        menuListDao.insert(new MenuList(5, "스모키 아이 메이크업", "70000"));
        menuListDao.insert(new MenuList(5, "데이 메이크업", "50000"));
        menuListDao.insert(new MenuList(5, "피부관리 포함 메이크업", "80000"));
        menuListDao.insert(new MenuList(5, "특수 메이크업", "100000"));
        menuListDao.insert(new MenuList(5, "메이크업 컨설팅", "40000"));
        menuListDao.insert(new MenuList(5, "브라이덜 피팅", "70000"));
        menuListDao.insert(new MenuList(5, "메이크업 레슨", "100000"));
        menuListDao.insert(new MenuList(5, "메이크업 리허설", "80000"));
        menuListDao.insert(new MenuList(5, "메이크업 리페어", "20000"));
        menuListDao.insert(new MenuList(5, "컬러 코렉션 메이크업", "80000"));

        menuListDao.insert(new MenuList(6, "풀 바디 왁싱", "150000"));
        menuListDao.insert(new MenuList(6, "하프 바디 왁싱", "80000"));
        menuListDao.insert(new MenuList(6, "브라질리언 왁싱", "70000"));
        menuListDao.insert(new MenuList(6, "비키니 왁싱", "50000"));
        menuListDao.insert(new MenuList(6, "언더암 왁싱", "40000"));
        menuListDao.insert(new MenuList(6, "레그 왁싱", "70000"));
        menuListDao.insert(new MenuList(6, "암피트리 왁싱", "30000"));
        menuListDao.insert(new MenuList(6, "페이셜 왁싱", "40000"));
        menuListDao.insert(new MenuList(6, "체스트 왁싱", "50000"));
        menuListDao.insert(new MenuList(6, "백 왁싱", "50000"));
        menuListDao.insert(new MenuList(6, "손,발 왁싱", "20000"));
        menuListDao.insert(new MenuList(6, "이브로우 왁싱", "20000"));

        menuListDao.insert(new MenuList(7, "소형 타투", "50000"));
        menuListDao.insert(new MenuList(7, "중형 타투", "100000"));
        menuListDao.insert(new MenuList(7, "대형 타투", "300000"));
        menuListDao.insert(new MenuList(7, "미니타투", "30000"));
        menuListDao.insert(new MenuList(7, "컬러 타투", "20000"));
        menuListDao.insert(new MenuList(7, "워터컬러 타투", "150000"));
        menuListDao.insert(new MenuList(7, "블랙 앤 그레이 타투", "100000"));
        menuListDao.insert(new MenuList(7, "리얼리즘 타투", "200000"));
        menuListDao.insert(new MenuList(7, "트래디셔널 타투", "100000"));
        menuListDao.insert(new MenuList(7, "미니멀리즘 타투", "50000"));
        menuListDao.insert(new MenuList(7, "플래시 타투", "50000"));
        menuListDao.insert(new MenuList(7, "타투 컨설팅", "20000"));
        menuListDao.insert(new MenuList(7, "타투 커버업", "100000"));
        menuListDao.insert(new MenuList(7, "타투 제거", "200000"));


        SettingDateDao settingDateDao = userDatabase.getSettingDao();
        settingDateDao.insert(new SettingDate("1100"));
        settingDateDao.insert(new SettingDate("2100"));
        settingDateDao.insert(new SettingDate("30"));
        settingDateDao.insert(new SettingDate("NNNNNNN"));
/*
* 네일
헤어
애견미용
속눈썹
피부
메이크업
왁싱
아트타투, 헤나타투
미용문신(반영구)

바버
마사지
드라이
태닝
눈썹
실제모
*/
    }


}




