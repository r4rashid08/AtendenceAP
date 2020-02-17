package com.example.mis_internee.atendence_app_android.Network;


import com.example.mis_internee.atendence_app_android.Model.CurrentDate.CurrentDataInfo;
import com.example.mis_internee.atendence_app_android.Model.FcmModel.FcmResult;
import com.example.mis_internee.atendence_app_android.Model.Leaves.LeaveCode;
import com.example.mis_internee.atendence_app_android.Model.Leaves.LeaveModel;
import com.example.mis_internee.atendence_app_android.Model.Login.LoginModel;
import com.example.mis_internee.atendence_app_android.Model.RefReimbursementModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by mharis on 29/09/2017.
 */

public interface ServerAPI {

 //   @POST(UrlUtils.LOGIN_API)
 //   Call<> login(@Body JsonObject params);

//    st_nature,st_place,st_incaseofothers,st_medbils,st_otherbils,st_totlbills
    @FormUrlEncoded
    @POST(UrlUtils.INSERT_REIMBURSEMENT_FORM)
    Call<RefReimbursementModel>  insertData(
            @Field("REIMB_NATURE") String reimb_nature,
            @Field("POV") String pov,
            @Field("AMOUNT_MEDICAL") int amount_medical,
            @Field("AMOUNT_OTHERS") int amount_others,
            @Field("TOTAL_AMOUNT") String total_amount,
            @Field("FROM_USER") String from_user,
            @Field("USER_ID") String user_id,
            @Field("TO_USER_ID") int to_user_id,
            @Field("FILES_STATUS") String status



    );



    @FormUrlEncoded
    @POST(UrlUtils.HOD_ATTANDENT)
    Call<LeaveModel>  hod_attandance(

            @Field("emp_id") String emp_id,
            @Field("date") String date
    );


    @FormUrlEncoded
    @POST(UrlUtils.GET_CURRENT_DATE_IFNO)
    Call<CurrentDataInfo>  get_current_date(

            @Field("EMP_ID") String emp_id,
            @Field("date") String date
    );




    @FormUrlEncoded
    @POST(UrlUtils.GET_LEAVE_INFO)
    Call<LeaveCode>  get_leave_info(

            @Field("unit_code") String unit_id
    );





    @FormUrlEncoded
    @POST(UrlUtils.FCM_TOKEN)
    Call<FcmResult>  FcmToken(

            @Field("EMP_ID") String emp_id,
            @Field("TOKEN") String token
    );



    @FormUrlEncoded
    @POST(UrlUtils.INSERT_LEAVE_BALANCE)
    Call<FcmResult>  InsertLeaveBalance(

            @Field("EMP_ID") String emp_id
    );

    @FormUrlEncoded
    @POST(UrlUtils.UPDATE_LEAVE_BALANCE)
    Call<FcmResult>  UpdateLeaveBlance(

            @Field("EMP_ID") String emp_id,
            @Field("CONSUMED") String consumed,
            @Field("ATTND_FLAG") String leave_code
    );

    @FormUrlEncoded
    @POST(UrlUtils.LOGIN_API)
    Call<LoginModel>  loginApi(

            @Field("USERNAME") String user_name,

            @Field("PASSWORD") String password
    );


















//
//    @GET(UrlUtils.DASH_BOARD    )
//    Call<Search>  Get_dashboard_info();
//
//
   @Multipart
    @POST(UrlUtils.UPLOAD_FILE)
    Call<RefReimbursementModel> uploadFile(
            @Part MultipartBody.Part file,
            @Part("PARENT_ID") RequestBody id



   );

//
//
//    @GET
//    Call<String> getLoaction
//            (@Url String url);
//



}
