package com.example.mis_internee.atendence_app_android.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.mis_internee.atendence_app_android.Fragments.Emp_Approved_notification;
import com.example.mis_internee.atendence_app_android.Fragments.Emp_Pending_notification;

/**
 * Created by MIS-Internee on 01-Jun-18.
 */

public class Fragment_emp_notification_apdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;

    public Fragment_emp_notification_apdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Emp_Pending_notification EPN = new Emp_Pending_notification();
                return EPN;
            case 1:
                Emp_Approved_notification EAN = new Emp_Approved_notification();
                return EAN;




            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}