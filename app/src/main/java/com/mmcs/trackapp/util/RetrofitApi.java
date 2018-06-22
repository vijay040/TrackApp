package com.mmcs.trackapp.util;

import com.mmcs.trackapp.model.ExpenseResMeta;
import com.mmcs.trackapp.model.LoginResMeta;
import com.mmcs.trackapp.model.MeetingModel;
import com.mmcs.trackapp.model.PreRequestResMeta;
import com.mmcs.trackapp.model.RequestTypeModel;
import com.mmcs.trackapp.model.ResAttandance;
import com.mmcs.trackapp.model.ResMetaCurrency;
import com.mmcs.trackapp.model.ResMetaCustomer;
import com.mmcs.trackapp.model.ResMetaDepartment;
import com.mmcs.trackapp.model.ResMetaMeeting;
import com.mmcs.trackapp.model.ResMetaReqTypes;
import com.mmcs.trackapp.model.ResponseMeta;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitApi {

    @FormUrlEncoded
    @POST("meeting_rest_api.php?request=savemeeting_data")
    Call<MeetingModel> postMeeting(@Field("user_id") String user_id, @Field("purpose") String purpose,
                                   @Field("descreption") String descreption, @Field("customer_id") String customer, @Field("date") String date,
                                   @Field("time") String time, @Field("agenda") String agenda, @Field("contact_person") String contact_person,
                                   @Field("address") String address, @Field("start_date") String start_date, @Field("start_time") String start_time
            , @Field("end_date") String end_date, @Field("end_time") String end_time, @Field("alarm_time") String alarm_time
    );

    @FormUrlEncoded
    @POST("purpose.php")
    Call<ResponseMeta> getPurposeList(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("fetch_matchDeatils.php")
    Call<ResMetaCustomer> getCustomerList(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("pre_requests.php")
    Call<PreRequestResMeta> getPrerequestMeetingList(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("apilogin.php")
    Call<LoginResMeta> login(@Field("email") String email, @Field("password") String password, @Field("device_token") String device_token, @Field("fcm_token") String fcm_token);

    @FormUrlEncoded
    @POST("fetch_meeting.php")
    Call<ResMetaMeeting> getMeetingsList(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("request_type.php")
    Call<ResMetaReqTypes> getRequestTypes(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("get_department_api.php")
    Call<ResMetaDepartment> getDepartmentList(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("get_currency_api.php")
    Call<ResMetaCurrency> getCurrencyList(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("prerequestsapi.php?request=prerequests_data")
    Call<ResMetaMeeting> postPreRequest(@Field("user_id") String user_id,
                                        @Field("advance") String advance, @Field("currency") String currency, @Field("department") String department, @Field("meeting_id") String meeting_id,
                                        @Field("description") String description, @Field("address") String address, @Field("date") String date, @Field("requesttype[]") ArrayList<RequestTypeModel> requesttypes

    );


    @FormUrlEncoded
    @POST("post_expense.php?request=saveexpense_data")
    Call<ResMetaMeeting> postExpanse(@Field("user_id") String user_id, @Field("meeting_id") String meeting_id,
                                     @Field("amount") String amount, @Field("expense_type[]") ArrayList<RequestTypeModel> requesttypes, @Field("created_on") String createddate,
                                    @Field("comment") String comment

    );


    @FormUrlEncoded
    @POST("fetch_expense.php")
    Call<ExpenseResMeta> getExpanseList(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("customer_post_api.php?request=savecustomer_data")
    Call<ResMetaMeeting> addNewCustomer(@Field("user_id") String user_id, @Field("customer_name") String customer_name,
                                        @Field("address") String address, @Field("email") String email, @Field("phone") String phone, @Field("pin'") String pin,
                                        @Field("customer_company") String customer_company, @Field("country") String country, @Field("tax_details") String details

    );

    @FormUrlEncoded
    @POST("attendance_post_api.php?request=saveattendance_data")
    Call<ResMetaMeeting> postAttendance(@Field("user_id") String user_id, @Field("location") String location,@Field("date_time") String datetime,@Field("status") String status

    );

    @FormUrlEncoded
    @POST("attendance_get_api.php")
    Call<ResAttandance> getAttandanceStatus(@Field("user_id") String user_id

    );

    @FormUrlEncoded
    @POST("location_map.php")
    Call<ResAttandance> test(@Field("user_id") String user_id

    );


    @FormUrlEncoded
    @POST("pending_get_api.php")
    Call<PreRequestResMeta> getPendingsList(@Field("user_id") String user_id

    );

    @FormUrlEncoded
    @POST("pending_post_api.php?request=setPendingStatus")
    Call<PreRequestResMeta> postAcceptRejectPendings(@Field("user_id") String user_id,@Field("id") String id,@Field("manager_status") String manager_status

    );

    @FormUrlEncoded
    @POST("location_post_api.php?request=saveLocation")
    Call<PreRequestResMeta> postDeviceLocation(@Field("user_id") String user_id,@Field("latitude") String latitude,@Field("longitude") String longitude

    );


    @FormUrlEncoded
    @POST("feedback_post_api.php?request=savefeedback_data")
    Call<PreRequestResMeta> postFeedback(@Field("user_id") String user_id,@Field("customer_id") String customer_id,@Field("feedback") String feedback,@Field("posted_on") String posted_on

    );
}