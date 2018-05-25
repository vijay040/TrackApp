package com.example.lenovo.trackapp.util;

        import com.example.lenovo.trackapp.model.LoginModel;
        import com.example.lenovo.trackapp.model.LoginResMeta;
        import com.example.lenovo.trackapp.model.MeetingsModel;
        import com.example.lenovo.trackapp.model.PreRequestResMeta;
        import com.example.lenovo.trackapp.model.ResMetaCustomer;
        import com.example.lenovo.trackapp.model.ResMetaMeeting;
        import com.example.lenovo.trackapp.model.ResMetaReqTypes;
        import com.example.lenovo.trackapp.model.ResponseMeta;

        import retrofit2.Call;
        import retrofit2.Retrofit;
        import retrofit2.http.Field;
        import retrofit2.http.FormUrlEncoded;
        import retrofit2.http.POST;

public interface RetrofitApi {

    @FormUrlEncoded
    @POST("meeting_rest_api.php?request=savemeeting_data")
    Call<MeetingsModel> postMeeting(@Field("user_id") String user_id, @Field("purpose") String purpose,
                                    @Field("descreption") String descreption, @Field("customer") String customer, @Field("date") String date,
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
    @POST("msg.php?request=savemsg_data")
    Call<ResMetaMeeting> postPreRequest(@Field("user_id") String user_id, @Field("purpose") String purpose,
                                        @Field("descreption") String descreption, @Field("customer") String customer, @Field("date") String date,
                                        @Field("time") String time, @Field("agenda") String agenda, @Field("contact_person") String contact_person,
                                        @Field("address") String address, @Field("start_date") String start_date, @Field("start_time") String start_time
            , @Field("end_date") String end_date, @Field("end_time") String end_time, @Field("alarm_time") String alarm_time
    );


}
