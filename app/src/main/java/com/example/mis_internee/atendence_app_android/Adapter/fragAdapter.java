package com.example.mis_internee.atendence_app_android.Adapter;

/**
 * Created by MIS-Internee on 24-May-18.
 */


/**
 * Created by MIS-Internee on 28-Apr-18.
 */


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.mis_internee.atendence_app_android.Fragments.Emp_Approved_list;
import com.example.mis_internee.atendence_app_android.Fragments.Emp_Pending_list;
import com.example.mis_internee.atendence_app_android.Fragments.hr_leave_notification;

public class fragAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;

    public fragAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new Emp_Pending_list();
            case 1:
                return new Emp_Approved_list();

            case 2:
                return new hr_leave_notification();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}