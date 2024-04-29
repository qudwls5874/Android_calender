package com.example.myapplication.Activity.setting.tel;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.myapplication.Activity.setting.tel.details.SettingTelListHDialog;
import com.example.myapplication.R;
import com.example.myapplication.database.table.MenuList;
import com.example.myapplication.database.table.User;
import com.example.myapplication.database.table.user.UserAddress;
import com.example.myapplication.database.table.user.UserEvent;
import com.example.myapplication.database.table.user.UserTel;
import com.example.myapplication.database.view.MenuJoin;
import com.example.myapplication.database.view.UserJoin;
import com.example.myapplication.database.viewmodel.UserViewModel;
import com.example.myapplication.databinding.DialogSettingTelBinding;
import com.example.myapplication.dataclass.UserProfile;
import com.example.myapplication.dialog.LoadingDialog2;
import com.example.myapplication.event.HideKeyboardHelperDialog;
import com.example.myapplication.event.WatcherSearchText;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SettingTelMainDialog extends DialogFragment implements View.OnClickListener, WatcherSearchText.OnSearchChangeListener, SettingTelMainAdapter.OnTelItemClickListener {

    private static final String TAG = "SettingTelMainDialog";

    private DialogSettingTelBinding binding;
    private LoadingDialog2 loadingDialog;

    private UserViewModel viewModel;

    private SettingTelMainAdapter adapter;
    private List<UserJoin> dbUsertJoins;    // 데이터베이스 전체 리스트
    private List<UserJoin> calUserJoins;   // 캘린더 전체 리스트
    private List<UserJoin> filterList;     // 검색 리스트
    private List<UserJoin> insertList;     // 저장 리스트
    private List<UserProfile> profileList;   // 사진 리스트

    public SettingTelMainDialog(LoadingDialog2 loadingDialog) {
        this.loadingDialog = loadingDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogSettingTelBinding.inflate(inflater, container, false);
        initUI();
        initData();
        return binding.getRoot();
    }

    private void initUI() {

        // 키보드 숨기기
        HideKeyboardHelperDialog.setupUI(binding.getRoot(), super.getDialog());

        calUserJoins = new ArrayList<>();
        profileList = new ArrayList<>();
        filterList = new ArrayList<>();
        insertList = new ArrayList<>();

        adapter = new SettingTelMainAdapter(filterList, insertList, profileList, this);
        binding.settingTelRecyclerView.setAdapter(adapter);
        binding.settingTelRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));

        binding.settingTelCloseBtn.setOnClickListener(this);
        binding.settingTelAddBtn.setOnClickListener(this);
        binding.settingTelCheckPersonTextView.setOnClickListener(this);
        binding.settingTelAllPersonTextView.setOnClickListener(this);
        binding.settingTelEraseBtn.setOnClickListener(this);

         binding.settingTelSearchEditText.addTextChangedListener(new WatcherSearchText(this));

        // 뷰모델 초기화
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);


    }


    private void initData() {

        viewModel.getObUserJoinList().observe(this, list ->{
            dbUsertJoins = list;
            Log.i("옵서버1", ""+ list.size());
        });

        dbUsertJoins = viewModel.getUserJoinList();

        // 데이터 조회
        getContacts();

        // 로딩 끄기
        loadingDialog.dismiss();
    }



    private void getContacts() {

        // 연락처 URI
        String[] projection = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_URI
        };

        // ContentResolver를 통해 주소록에 접근
        ContentResolver contentResolver = getContext().getContentResolver();
        Cursor cursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                projection,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC"
        );

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                // 연락처 ID, 이름, 사진 가져오기
                int idIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);
                int displayNameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                int photoUriIndex = cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI);

                String id = cursor.getString(idIndex);
                String displayName = cursor.getString(displayNameIndex);
                String photoUri = cursor.getString(photoUriIndex);

                if (photoUri != null) {
                    try {
                        InputStream inputStream = getContext().getContentResolver().openInputStream(Uri.parse(photoUri));
                        profileList.add(new UserProfile(id, BitmapFactory.decodeStream(inputStream)));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }

                // 전화번호 가져오기
                Cursor phoneCursor = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        new String[]{id},
                        null
                );

                List<UserTel> tel = new ArrayList<>();
                if (phoneCursor != null) {
                    while (phoneCursor.moveToNext()) {
                        int phoneNumberIndex = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        String phoneNumber = phoneCursor.getString(phoneNumberIndex);
                        int phoneTypeIndex = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);
                        int phoneType = phoneCursor.getInt(phoneTypeIndex);

                        tel.add(new UserTel(getTypeLabel(phoneType), phoneNumber));
                    }
                    phoneCursor.close();
                }

                // 주소 가져오기
                Cursor addressCursor = contentResolver.query(
                        ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID + " = ?",
                        new String[]{id},
                        null
                );

                List<UserAddress> addressList = new ArrayList<>();
                if (addressCursor != null) {
                    int typeIndex = addressCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE);
                    int addressIndex = addressCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS);
                    while (addressCursor.moveToNext()){
                        int type = addressCursor.getInt(typeIndex);
                        String typeLabel = ContactsContract.CommonDataKinds.StructuredPostal.getTypeLabel(getResources(), type, "").toString();
                        String address = addressCursor.getString(addressIndex);
                        addressList.add(new UserAddress(typeLabel, address));
                        // Log.d("Contact", "mane: " + displayName +", type: "+typeLabel+", Address: " + address);
                    }
                    addressCursor.close();
                }

                // 기념일 정보 가져오기
                Cursor eventCursor = contentResolver.query(
                        ContactsContract.Data.CONTENT_URI,
                        null,
                        ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?",
                        new String[]{id, ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE},
                        null
                );

                List<UserEvent> eventList = new ArrayList<>();
                if (eventCursor != null) {
                    int eventTypeIndex = eventCursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.TYPE);
                    int eventDateIndex = eventCursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE);
                    while (eventCursor.moveToNext()) {
                        int eventType = eventCursor.getInt(eventTypeIndex);
                        String eventLabel = ContactsContract.CommonDataKinds.Event.getTypeLabel(getResources(), eventType, "").toString();
                        String eventDate = eventCursor.getString(eventDateIndex);
                        eventList.add(new UserEvent(eventLabel, eventDate));
                        // Log.d("Contact", "Name: " + displayName + ", Event Type: " + eventLabel + ", Event Date: " + eventDate);
                    }
                    eventCursor.close();
                }


                UserJoin result = new UserJoin();
                result.user = new User(displayName, id, "0");
                result.userTelList = tel;
                result.userAddressList = addressList;
                result.userEventsList = eventList;

                // dbUsertJoins
                UserJoin checkData = dbUsertJoins.stream()
                        .filter(strData -> strData.user.getName().equals(result.user.getName()))
                        .findFirst()
                        .orElse(null);

                if (checkData == null){
                    calUserJoins.add(result);
                    filterList.add(result);
                }
            }
            cursor.close();
        }
        binding.settingTelAllPersonTextView.setText("총: " + calUserJoins.size());
        adapter.setItemCheckList(insertList);
    }

    private String getTypeLabel(int type) {
        switch (type) {
            case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                return "집"; //  Home
            case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                return "휴대전화"; // Mobile
            case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                return "직장"; // Work
            case ContactsContract.CommonDataKinds.Phone.TYPE_OTHER:
                return "기타"; // Other
            default:
                return "맞춤설정"; // Custom
        }
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == binding.settingTelCloseBtn.getId()){
            // 닫기버튼
            dismiss();
        } else if (v.getId() == binding.settingTelAddBtn.getId()) {
            // 저장버튼
            Boolean result = viewModel.setUserList(insertList, profileList);

            if (result){
                for (UserJoin forData : insertList){
                    for (int i = 0; i < calUserJoins.size(); i ++){
                        if (calUserJoins.get(i).user.getUserUrl().equals(forData.user.getUserUrl())){
                            calUserJoins.remove(i);
                        }
                    }
                    for (int i = 0; i < filterList.size(); i ++){
                        if (filterList.get(i).user.getUserUrl().equals(forData.user.getUserUrl())){
                            filterList.remove(i);
                        }
                    }
                }
                insertList.clear();

                binding.settingTelAllPersonTextView.setText("총: " + calUserJoins.size());
                binding.settingTelCheckPersonTextView.setText("선택: " + insertList.size());
                adapter.setItemCheckList(insertList);
            }

        } else if (v.getId() == binding.settingTelCheckPersonTextView.getId()) {
            // 선택 텍스트
            onChoiceTextChanged();
        } else if (v.getId() == binding.settingTelAllPersonTextView.getId()) {
            // 올 텍스트
            onAllTextChanged();
        } else if (v.getId() == binding.settingTelEraseBtn.getId()){
            // 검색 지우기
            binding.settingTelSearchEditText.setText("");
        }

    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // XML 레이아웃을 사용하여 다이얼로그의 크기를 지정합니다.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // 다이얼로그의 배경을 투명하게 설정합니다.
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 다이얼로그를 백그라운드 클릭 시 닫히지 않도록 설정합니다.
        dialog.setCanceledOnTouchOutside(false);
        // 키보드가 활정화될 때 다이얼로그의 위치를 조정합니다.
//        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return dialog;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onSearchTextChanged(String newText, Integer index) {
        filterList.clear();
        if (newText.isEmpty()) {
            // 검색 쿼리가 비어 있으면 전체 목록을 보여줍니다.
            if (binding.settingTelCheckPersonTextView.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.textPlusColor)){
                // 체크항목 조회
                filterList.addAll(insertList);
            } else {
                // 전체 조회
                filterList.addAll(calUserJoins);
            }
        } else {
            // 리스트를 순회하면서 검색어를 포함하는 항목을 찾습니다.
            if (binding.settingTelCheckPersonTextView.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.textPlusColor)){
                // 체크항목 조회
                for (UserJoin userJoin : insertList){
                    UserJoin filteredUserJoin = new UserJoin();
                    if (userJoin.user.getName().toLowerCase().contains(newText.toLowerCase())){
                        filteredUserJoin.user = userJoin.user;
                        filteredUserJoin.userTelList = userJoin.userTelList;
                        filteredUserJoin.userEventsList = userJoin.userEventsList;
                        filteredUserJoin.userAddressList = userJoin.userAddressList;
                        filterList.add(filteredUserJoin);
                    }
                }
            } else {
                // 전체 조회
                for (UserJoin userJoin : calUserJoins){
                    UserJoin filteredUserJoin = new UserJoin();
                    if (userJoin.user.getName().toLowerCase().contains(newText.toLowerCase())){
                        filteredUserJoin.user = userJoin.user;
                        filteredUserJoin.userTelList = userJoin.userTelList;
                        filteredUserJoin.userEventsList = userJoin.userEventsList;
                        filteredUserJoin.userAddressList = userJoin.userAddressList;
                        filterList.add(filteredUserJoin);
                    }
                }
            }
        }
        adapter.setItemCheckList(insertList);
    }

    // 선택 텍스트 클릭
    public void onChoiceTextChanged(){
        binding.settingTelSearchEditText.setText("");
        binding.settingTelCheckPersonTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.textPlusColor));
        binding.settingTelAllPersonTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.textNormalColor));
        filterList.clear();
        filterList.addAll(insertList);
        adapter.setItemCheckList(insertList);
    }

    // 총 텍스트 클릭
    public void onAllTextChanged(){
        binding.settingTelSearchEditText.setText("");
        binding.settingTelAllPersonTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.textPlusColor));
        binding.settingTelCheckPersonTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.textNormalColor));
        filterList.clear();
        filterList.addAll(calUserJoins);
        adapter.setItemCheckList(insertList);
    }

    @Override
    public void onTelItemClick(int position) {
        // 저장 리스트값
        UserJoin result = insertList.stream()
                .filter(streamData -> streamData.user.getUserUrl().equals(filterList.get(position).user.getUserUrl()))
                .findFirst()
                .orElse(null);

        if (result == null){
            insertList.add(filterList.get(position));
        } else {
            insertList.remove(result);
        }

        binding.settingTelCheckPersonTextView.setText("선택: " + insertList.size());
        if (!insertList.isEmpty()){
            binding.settingTelAddBtn.setTextColor(ContextCompat.getColor(getContext(), R.color.textPlusColor));
        } else {
            binding.settingTelAddBtn.setTextColor(ContextCompat.getColor(getContext(), R.color.textBeNormalColor));
        }
        adapter.setItemCheckList(insertList);
    }


    @Override
    public void onTelItemLongClick(UserJoin userJoin) {

        UserProfile detailsProfile = profileList.stream()
                .filter(strList -> strList.getProfileId().equals(userJoin.user.getUserUrl()))
                .findFirst()
                .orElse(null);

        // 주소 상세정보
        SettingTelListHDialog telListDDialog = new SettingTelListHDialog(userJoin, (detailsProfile == null) ? null : detailsProfile.getProifle());
        telListDDialog.show(getParentFragmentManager(), "tel_d_dialog");

    }



}
