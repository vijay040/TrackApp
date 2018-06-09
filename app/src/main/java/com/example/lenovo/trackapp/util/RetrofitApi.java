package com.example.lenovo.trackapp.util;

import com.example.lenovo.trackapp.model.ExpenseResMeta;
import com.example.lenovo.trackapp.model.LoginResMeta;
import com.example.lenovo.trackapp.model.MeetingModel;
import com.example.lenovo.trackapp.model.PreRequestResMeta;
import com.example.lenovo.trackapp.model.RequestTypeModel;
import com.example.lenovo.trackapp.model.ResMetaCurrency;
import com.example.lenovo.trackapp.model.ResMetaCustomer;
import com.example.lenovo.trackapp.model.ResMetaDepartment;
import com.example.lenovo.trackapp.model.ResMetaMeeting;
import com.example.lenovo.trackapp.model.ResMetaReqTypes;
import com.example.lenovo.trackapp.model.ResponseMeta;

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
    Call<ResMetaMeeting> addNewCustomer(@Field("user_id") String user_id, @Field("customer_name'") String customer_name,
                                        @Field("address") String address, @Field("email") String email, @Field("phone") String phone, @Field("pin'") String pin,
                                        @Field("customer_company") String customer_company, @Field("country") String country, @Field("tax_details") String details

    );
}
