package com.example.myapplication.Activity.setting.tel;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
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
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.myapplication.R;
import com.example.myapplication.database.table.MenuList;
import com.example.myapplication.database.view.MenuJoin;
import com.example.myapplication.databinding.DialogSettingTelBinding;
import com.example.myapplication.dialog.LoadingDialog2;
import com.example.myapplication.event.HideKeyboardHelperDialog;
import com.example.myapplication.event.WatcherSearchText;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SettingTelMainDialog extends DialogFragment implements View.OnClickListener, WatcherSearchText.OnSearchChangeListener {

    private static final String TAG = "MainActivity";

    private DialogSettingTelBinding binding;
    private LoadingDialog2 loadingDialog;

    private SettingTelListAdapter adapter;
    private List<TelData> telDataList;
    private ArrayList<TelData> filterList;

    public SettingTelMainDialog(LoadingDialog2 loadingDialog) {
        this.loadingDialog = loadingDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogSettingTelBinding.inflate(inflater, container, false);

        initUI();
        initDate();

        return binding.getRoot();
    }

    private void initUI() {

        // 키보드 숨기기
        HideKeyboardHelperDialog.setupUI(binding.getRoot(), super.getDialog());

        telDataList = new ArrayList<>();
        filterList = new ArrayList<>();
        adapter = new SettingTelListAdapter(filterList);
        binding.settingTelRecyclerView.setAdapter(adapter);
        binding.settingTelRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));

        binding.settingTelCloseBtn.setOnClickListener(this);
        binding.settingTelAddBtn.setOnClickListener(this);

        binding.settingTelSearchEditText.addTextChangedListener(new WatcherSearchText(this));


    }


    private void initDate() {
        getContacts();
        loadingDialog.dismiss();
    }


    private void getContacts() {

        // 연락처 URI
        String[] projection = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_URI
        };

        // 연락처 쿼리
        ContentResolver contentResolver = getContext().getContentResolver();
        Cursor cursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                projection,
                null,
                null,
                null
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

                Bitmap photo = null;
                if (photoUri != null) {
                    try {
                        InputStream inputStream = getContext().getContentResolver().openInputStream(Uri.parse(photoUri));
                        photo = BitmapFactory.decodeStream(inputStream);
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


                List<String> tel = new ArrayList<>();
                if (phoneCursor != null) {
                    while (phoneCursor.moveToNext()) {
                        int phoneNumberIndex = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        String phoneNumber = phoneCursor.getString(phoneNumberIndex);

                        int phoneTypeIndex = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);
                        int phoneType = phoneCursor.getInt(phoneTypeIndex);

                        tel.add(getTypeLabel(phoneType) + ":" + phoneNumber);

                        String contactInfo = "ID: " + id + "\nName: " + displayName + "\nType: " + getTypeLabel(phoneType) + "\nNumber: " + phoneNumber;
                        Log.i(TAG, contactInfo);
                    }
                    phoneCursor.close();
                }

                telDataList.add(new TelData(Integer.parseInt(id),displayName, tel, photo, false));
                filterList.add(new TelData(Integer.parseInt(id),displayName, tel, photo, false));

            }
            cursor.close();
        }

        binding.settingTelAllPersonTextView.setText("총 " + telDataList.size() + "명");
        adapter.notifyDataSetChanged();

    }

    private String getTypeLabel(int type) {
        switch (type) {
            case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                return "Home";
            case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                return "Mobile";
            case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                return "Work";
            case ContactsContract.CommonDataKinds.Phone.TYPE_OTHER:
                return "Other";
            default:
                return "Custom";
        }
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == binding.settingTelCloseBtn.getId()){
            // 닫기버튼
            dismiss();
        } else if (v.getId() == binding.settingTelAddBtn.getId()) {
            // 저장버튼
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

    @Override
    public void onSearchTextChanged(String newText, Integer index) {
        filterList.clear();

        if (newText.isEmpty()) {
            // 검색 쿼리가 비어 있으면 전체 목록을 보여줍니다.
            filterList.addAll(telDataList);
        } else {

            // 리스트를 순회하면서 검색어를 포함하는 항목을 찾습니다.
            List<TelData> filteredTelDatas = telDataList.stream()
                    .filter(data -> data.getName().toLowerCase().contains(newText.toLowerCase()))
                    .collect(Collectors.toList());

            filterList.addAll(filteredTelDatas);

        }
        adapter.notifyDataSetChanged();
    }





}
