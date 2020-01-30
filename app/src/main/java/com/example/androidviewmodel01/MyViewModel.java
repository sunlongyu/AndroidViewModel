package com.example.androidviewmodel01;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;

public class MyViewModel extends AndroidViewModel {

    private SavedStateHandle handle;
    String keyNumber = getApplication().getString(R.string.key_number);
    String shpData = getApplication().getString(R.string.shp_data);

    public MyViewModel(@NonNull Application application, SavedStateHandle handle) {
        super(application);
        this.handle = handle;
        if (!handle.contains(keyNumber)) {
            load();
        }
    }

    public LiveData<Integer> getNumber() {
        return handle.getLiveData(keyNumber);
    }

    private void load() {
        SharedPreferences shp = getApplication().getSharedPreferences(shpData, Context.MODE_PRIVATE);
        int x = shp.getInt(keyNumber, 0);
        handle.set(keyNumber, x);
    }

    public void save() {
        SharedPreferences shp = getApplication().getSharedPreferences(shpData, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shp.edit();
        editor.putInt(keyNumber, getNumber().getValue());
        editor.apply();
    }

    public void add(int x) {
        handle.set(keyNumber, getNumber().getValue() + x);
        save();
    }
}
