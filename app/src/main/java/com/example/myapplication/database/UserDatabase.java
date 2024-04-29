package com.example.myapplication.database;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
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
import com.example.myapplication.database.table.user.UserAddress;
import com.example.myapplication.database.table.user.UserEvent;
import com.example.myapplication.database.table.user.UserTel;

import java.util.concurrent.Executors;


@Database(entities =
        {
                User.class, UserTel.class, UserAddress.class, UserEvent.class, MoneyName.class, MenuCategory.class, MenuList.class,
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

    // 초기 데이터 설정
    private static void insertInitialData(UserDatabase  userDatabase) {
        MoneyNameDao moneyNameDao = userDatabase.getMoneyNameDao();
        moneyNameDao.insert(new MoneyName("정액제"));
        moneyNameDao.insert(new MoneyName("차감"));
        moneyNameDao.insert(new MoneyName("결제"));

        MenuCategoryDao menuCategoryDao = userDatabase.getMenuCategoryDao();
        menuCategoryDao.insert(new MenuCategory("네일", "N", 0));
        menuCategoryDao.insert(new MenuCategory("헤어", "N", 0));
        menuCategoryDao.insert(new MenuCategory("애견미용", "N", 0));
        menuCategoryDao.insert(new MenuCategory("속눈썹", "N", 0));
        menuCategoryDao.insert(new MenuCategory("피부", "N", 0));
        menuCategoryDao.insert(new MenuCategory("메이크업", "N", 0));
        menuCategoryDao.insert(new MenuCategory("왁싱", "N", 0));
        menuCategoryDao.insert(new MenuCategory("타투/문신", "N", 0));
        menuCategoryDao.insert(new MenuCategory("정액권", "Y", 1));
        menuCategoryDao.insert(new MenuCategory("할인권", "Y", 2));

        MenuListDao menuListDao = userDatabase.getMenuListDao();
        menuListDao.insert(new MenuList(1, "기본케어", 10000));
        menuListDao.insert(new MenuList(1, "매니큐어", 20000));
        menuListDao.insert(new MenuList(1, "페디큐어", 30000));
        menuListDao.insert(new MenuList(1, "프렌치", 30000));
        menuListDao.insert(new MenuList(1, "아크릴", 50000));
        menuListDao.insert(new MenuList(1, "젤", 40000));
        menuListDao.insert(new MenuList(1, "스톤/주얼리", 50000));
        menuListDao.insert(new MenuList(1, "3D네일 아트", 60000));
        menuListDao.insert(new MenuList(1, "네일 확장/팁", 70000));
        menuListDao.insert(new MenuList(1, "워터마블/그라데이션/오므브", 40000));
        menuListDao.insert(new MenuList(1, "피디큐어", 40000));
        menuListDao.insert(new MenuList(1, "네일 디자인 커스터마이징", 50000));

        menuListDao.insert(new MenuList(2, "기본 컷 & 스타일링", 20000));
        menuListDao.insert(new MenuList(2, "펌 & 매직펌", 50000));
        menuListDao.insert(new MenuList(2, "염색 & 헤어 칼라링", 40000));
        menuListDao.insert(new MenuList(2, "헤어 트리트먼트 & 스파", 30000));
        menuListDao.insert(new MenuList(2, "헤어 확장 & 웨딩 헤어", 100000));
        menuListDao.insert(new MenuList(2, "헤어 퍼머 & 스트레이트닝", 50000));
        menuListDao.insert(new MenuList(2, "헤어 업스타일 & 브레이딩", 40000));
        menuListDao.insert(new MenuList(2, "키즈 컷", 15000));
        menuListDao.insert(new MenuList(2, "바버샵 서비스", 20000));
        menuListDao.insert(new MenuList(2, "트리트먼트", 30000));
        menuListDao.insert(new MenuList(2, "헤어 마사지", 20000));

        menuListDao.insert(new MenuList(3, "기본 컷 & 스타일링", 30000));
        menuListDao.insert(new MenuList(3, "목욕 & 드라이", 20000));
        menuListDao.insert(new MenuList(3, "전신 트리밍", 40000));
        menuListDao.insert(new MenuList(3, "발톱 깍기", 10000));
        menuListDao.insert(new MenuList(3, "트리트먼트 & 스파", 30000));
        menuListDao.insert(new MenuList(3, "털 염색", 50000));
        menuListDao.insert(new MenuList(3, "디-멜링 & 브러싱", 30000));
        menuListDao.insert(new MenuList(3, "데쉬 & 퍼프(스타일링)", 40000));
        menuListDao.insert(new MenuList(3, "피부 케어", 40000));
        menuListDao.insert(new MenuList(3, "귀 청소 & 이빨 청소", 10000));
        menuListDao.insert(new MenuList(3, "애견 마사지", 20000));

        menuListDao.insert(new MenuList(4, "속눈썹 연장 (일반)", 50000));
        menuListDao.insert(new MenuList(4, "볼륨 라쉬", 80000));
        menuListDao.insert(new MenuList(4, "나치럴 라쉬", 60000));
        menuListDao.insert(new MenuList(4, "속눈썹 펌", 40000));
        menuListDao.insert(new MenuList(4, "속눈썹 틴팅", 30000));
        menuListDao.insert(new MenuList(4, "속눈썹 리무버", 20000));
        menuListDao.insert(new MenuList(4, "속눈썹 케어", 30000));
        menuListDao.insert(new MenuList(4, "속눈썹 마스크", 30000));
        menuListDao.insert(new MenuList(4, "속눈썹 라인", 20000));
        menuListDao.insert(new MenuList(4, "속눈썹 컬", 40000));
        menuListDao.insert(new MenuList(4, "속눈썹 컬러링", 30000));

        menuListDao.insert(new MenuList(5, "기본 피부 관리", 50000));
        menuListDao.insert(new MenuList(5, "딥 클렌징", 60000));
        menuListDao.insert(new MenuList(5, "피부 페셜", 70000));
        menuListDao.insert(new MenuList(5, "아쿠아 피부 관리", 80000));
        menuListDao.insert(new MenuList(5, "안티에이징 관리", 100000));
        menuListDao.insert(new MenuList(5, "화이트닝 & 브라이트닝", 80000));
        menuListDao.insert(new MenuList(5, "자극 없는 피부 관리", 70000));
        menuListDao.insert(new MenuList(5, "블랙헤드 & 모공 관리", 60000));
        menuListDao.insert(new MenuList(5, "모공 수축 관리", 70000));
        menuListDao.insert(new MenuList(5, "산소 피부 관리", 80000));
        menuListDao.insert(new MenuList(5, "피부 레이저 관리", 100000));
        menuListDao.insert(new MenuList(5, "피부 필링", 80000));
        menuListDao.insert(new MenuList(5, "피부 자극 완화 팩", 70000));
        menuListDao.insert(new MenuList(5, "피부 케어 컨설팅", 40000));

        menuListDao.insert(new MenuList(6, "베이스 메이크업", 50000));
        menuListDao.insert(new MenuList(6, "파티 메이크업", 70000));
        menuListDao.insert(new MenuList(6, "웨딩 메이크업", 100000));
        menuListDao.insert(new MenuList(6, "스모키 아이 메이크업", 70000));
        menuListDao.insert(new MenuList(6, "데이 메이크업", 50000));
        menuListDao.insert(new MenuList(6, "피부관리 포함 메이크업", 80000));
        menuListDao.insert(new MenuList(6, "특수 메이크업", 100000));
        menuListDao.insert(new MenuList(6, "메이크업 컨설팅", 40000));
        menuListDao.insert(new MenuList(6, "브라이덜 피팅", 70000));
        menuListDao.insert(new MenuList(6, "메이크업 레슨", 100000));
        menuListDao.insert(new MenuList(6, "메이크업 리허설", 80000));
        menuListDao.insert(new MenuList(6, "메이크업 리페어", 20000));
        menuListDao.insert(new MenuList(6, "컬러 코렉션 메이크업", 80000));

        menuListDao.insert(new MenuList(7, "풀 바디 왁싱", 150000));
        menuListDao.insert(new MenuList(7, "하프 바디 왁싱", 80000));
        menuListDao.insert(new MenuList(7, "브라질리언 왁싱", 70000));
        menuListDao.insert(new MenuList(7, "비키니 왁싱", 50000));
        menuListDao.insert(new MenuList(7, "언더암 왁싱", 40000));
        menuListDao.insert(new MenuList(7, "레그 왁싱", 70000));
        menuListDao.insert(new MenuList(7, "암피트리 왁싱", 30000));
        menuListDao.insert(new MenuList(7, "페이셜 왁싱", 40000));
        menuListDao.insert(new MenuList(7, "체스트 왁싱", 50000));
        menuListDao.insert(new MenuList(7, "백 왁싱", 50000));
        menuListDao.insert(new MenuList(7, "손,발 왁싱", 20000));
        menuListDao.insert(new MenuList(7, "이브로우 왁싱", 20000));

        menuListDao.insert(new MenuList(8, "소형 타투", 50000));
        menuListDao.insert(new MenuList(8, "중형 타투", 100000));
        menuListDao.insert(new MenuList(8, "대형 타투", 300000));
        menuListDao.insert(new MenuList(8, "미니타투", 30000));
        menuListDao.insert(new MenuList(8, "컬러 타투", 20000));
        menuListDao.insert(new MenuList(8, "워터컬러 타투", 150000));
        menuListDao.insert(new MenuList(8, "블랙 앤 그레이 타투", 100000));
        menuListDao.insert(new MenuList(8, "리얼리즘 타투", 200000));
        menuListDao.insert(new MenuList(8, "트래디셔널 타투", 100000));
        menuListDao.insert(new MenuList(8, "미니멀리즘 타투", 50000));
        menuListDao.insert(new MenuList(8, "플래시 타투", 50000));
        menuListDao.insert(new MenuList(8, "타투 컨설팅", 20000));
        menuListDao.insert(new MenuList(8, "타투 커버업", 100000));
        menuListDao.insert(new MenuList(8, "타투 제거", 200000));


        SettingDateDao settingDateDao = userDatabase.getSettingDao();
        settingDateDao.insert(new SettingDate("1100"));
        settingDateDao.insert(new SettingDate("2100"));
        settingDateDao.insert(new SettingDate("30"));
        settingDateDao.insert(new SettingDate("NNNNNNN"));

    }


}




