package com.example.mb7.sportappbp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * Created by MB7 on 07.01.2017.
 */

public class TbNotificationContent extends TabFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setTitle("Notifikationen");
        return inflater.inflate ( R.layout.tbtaskcontent, container, false);
    }
}
