package com.example.mis_internee.atendence_app_android.Adapter.LeavePending;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.mis_internee.atendence_app_android.Model.Leaves.Result;
import com.example.mis_internee.atendence_app_android.R;


/**
 * Created by mRashid on 15-march-18.
 */

@RequiresApi(api = Build.VERSION_CODES.M)
public class LeavePendingViewHolder extends RecyclerView.ViewHolder {
    private Result mTask;



     ImageView mHodImage,mHrImage;
     TextView mSerialNumber,mDate,mLeaveCode;
     String mHodStatus="empty", mHrStatus="empty";

     public  static  int serialNum=0;



      CardView mBackgroundColor;
    public  Context mContex;


    public LeavePendingViewHolder(final View itemView) {
        super(itemView);

        mContex =itemView.getContext();
        //session = new SessionManager(mContex);


        mHodImage =  itemView.findViewById(R.id.pending_item_hod_status);
        mHrImage = itemView.findViewById(R.id.pending_item_hr_status);
        mSerialNumber = itemView.findViewById(R.id.pending_item_ser_number);
        mDate = itemView.findViewById(R.id.pending_item_date);
        mLeaveCode = itemView.findViewById(R.id.pending_item_leave_code);








    }





    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void bindTask(Result task ,int postion) {
         mTask = task;

       if(mTask.getDATEFROM()!=null){
            mDate.setText(mTask.getDATEFROM());
        }else {
            mDate.setText(" ");
       }
       if(mTask.getNOTIFCAITONTRANTYPE()!=null){
           mLeaveCode.setText( mTask.getNOTIFCAITONTRANTYPE());

       }else {
           mLeaveCode.setText(" ");
       }
      int k =   postion+1;
       mSerialNumber.setText(""+k);
      /* if(task.getrEGION()!=null){
           mRegion.setText(mTask.getrEGION());
       }else {
           mRegion.setText(" ");
       }*/


      if(mTask.getHODFLAG()!=null){
          mHodStatus = mTask.getHODFLAG();
      }else {
          mHodStatus ="empty";
      }


      if(mTask.getHRFLAG()!=null){
          mHrStatus = mTask.getHRFLAG().toString();
      }else {
          mHrStatus = "empty";

      }


        switch (mHodStatus) {
            case "APPROVED":

                Glide.with(mContex).load(R.drawable.green_dot)

                        .into(mHodImage);
                break;
            case "empty":
                Glide.with(mContex).load(R.drawable.dot_orange)

                        .into(mHodImage);

                break;
            case "DISAPPROVED":
                Glide.with(mContex).load(R.drawable.red_dot)

                        .into(mHodImage);

                break;
        }



        switch (mHrStatus) {
            case "APPROVED":

                Glide.with(mContex).load(R.drawable.green_dot)

                        .into(mHrImage);
                break;
            case "empty":
                Glide.with(mContex).load(R.drawable.dot_orange)

                        .into(mHrImage);

                break;
            case "DISAPPROVED":
                Glide.with(mContex).load(R.drawable.red_dot)

                        .into(mHrImage);

                break;
        }




        String  image = null;
    /* if(mTask.getJobImage()!=null){
         image = mTask.getJobImage();
    }*/




     /*  RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.user_image)
               .error(R.drawable.user_image)
               .circleCrop()
               .diskCacheStrategy(DiskCacheStrategy.ALL)
               .priority(Priority.HIGH);


      Glide.with(mContex).load(image)
                .apply(options)
                .into(mImage);*/

    }











}

