package com.example.myapplication.Activity.setting.service.details;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.table.MenuCategory;
import com.example.myapplication.database.table.MenuList;
import com.example.myapplication.databinding.ViewServiceItemListDRowBinding;
import com.example.myapplication.dialog.ServiceUpdateDialog;
import com.example.myapplication.event.WatcherMoneyText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SettingServiceListDAdapter extends RecyclerView.Adapter<SettingServiceListDAdapter.ViewHolder> {

    private ViewServiceItemListDRowBinding binding;
    private ArrayList<MenuList> list;
    private MenuCategory menuCategory;
    private ServiceUpdateDialog.SetMenuUpdateLisner updateLisner;
    private setOnclickColorChangedLisner colorLisner;
    private List<Boolean> layoutColorChangeList;

    public SettingServiceListDAdapter(ArrayList<MenuList> list,
                                      ServiceUpdateDialog.SetMenuUpdateLisner updateLisner,
                                      setOnclickColorChangedLisner colorLisner,
                                      MenuCategory menuCategory){
        this.list = list;
        this.updateLisner = updateLisner;
        this.colorLisner = colorLisner;
        this.menuCategory = menuCategory;
        this.layoutColorChangeList = new ArrayList<>(Collections.nCopies(list.stream().mapToInt(menuList -> list.size()).sum(), false));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ViewServiceItemListDRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MenuList data = list.get(position);

        holder.binding.itemServiceDTextView1.setText(data.getMenuName());
        WatcherMoneyText watcherMoneyText = new WatcherMoneyText();
        holder.binding.itemServiceDTextView2.setText(watcherMoneyText.beforeMoneyTextChanged(String.valueOf(data.getMenuMoney())));

        // 버튼 UI 숨기기
        if (SettingServiceListHDialog.layoutCheck){
            holder.binding.itemServiceDDelBtn.setVisibility(View.VISIBLE);
            holder.binding.itemServiceDUpdateBtn.setVisibility(View.VISIBLE);
        } else {
            holder.binding.itemServiceDDelBtn.setVisibility(View.GONE);
            holder.binding.itemServiceDUpdateBtn.setVisibility(View.GONE);
        }

        // 리스트 항목 색상 변경
        if (layoutColorChangeList.get(position)){
            // 선택됨.
            holder.binding.itemServiceDLinearLayout.setBackgroundColor(ContextCompat.getColor(holder.binding.getRoot().getContext(), R.color.BackBaseInputBoxColor));
            /*
            holder.binding.itemServiceDDelBtn.setImageResource(imageList.get(position));
            RotationBtn rotationBtn = new RotationBtn(holder.binding.itemServiceDDelBtn);
            holder.binding.itemServiceDDelBtn.setImageResource(R.drawable.ic_cansel_btn);
            rotationBtn.startAnimation();
            */
        } else {
            // 취소됨.
            holder.binding.itemServiceDLinearLayout.setBackgroundColor(ContextCompat.getColor(holder.binding.getRoot().getContext(), R.color.white));
            holder.binding.itemServiceDDelBtn.setImageResource(R.drawable.ic_btn_minus);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ViewServiceItemListDRowBinding binding;

        public ViewHolder(@NonNull ViewServiceItemListDRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            // 클릭 이벤트
            binding.itemServiceDDelBtn.setOnClickListener(this);
            binding.itemServiceDUpdateBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == binding.itemServiceDDelBtn.getId()){
                // 삭제버튼 클릭.
                if (layoutColorChangeList.get(getBindingAdapterPosition())){
                    layoutColorChangeList.set(getBindingAdapterPosition(), false);
                } else {
                    layoutColorChangeList.set(getBindingAdapterPosition(), true);
                }

                colorLisner.setOnclickColorChangedLisner(list.get(getBindingAdapterPosition()));
                notifyDataSetChanged();

            } else if (v.getId() == binding.itemServiceDUpdateBtn.getId()) {
                // 레이아웃 클릭 (수정) > 수정상황일때만
                if (SettingServiceListHDialog.layoutCheck){
                    ServiceUpdateDialog updateDialog = new ServiceUpdateDialog(binding.getRoot().getContext(), list.get(getBindingAdapterPosition()), menuCategory, updateLisner);
                    updateDialog.show();
                }
            }
        }
    }

    // 삭제할 데이터 로드
    public interface setOnclickColorChangedLisner{
        void setOnclickColorChangedLisner(MenuList menuList);
    }


}
