package com.mmcs.trackapp.util;

import com.mmcs.trackapp.model.ExpResListMeta;
import com.mmcs.trackapp.model.ExpenseResMeta;
import com.mmcs.trackapp.model.FeedbackResMeta;
import com.mmcs.trackapp.model.HomeItemResMeta;
import com.mmcs.trackapp.model.LoginResMeta;
import com.mmcs.trackapp.model.MeetingModel;
import com.mmcs.trackapp.model.MessageResMeta;
import com.mmcs.trackapp.model.PortResMeta;
import com.mmcs.trackapp.model.PreReqUpdateResMeta;
import com.mmcs.trackapp.model.PreRequestResMeta;
import com.mmcs.trackapp.model.ReportResMeta;
import com.mmcs.trackapp.model.RequestTypeModel;
import com.mmcs.trackapp.model.ResAttandance;
import com.mmcs.trackapp.model.ResMetaCurrency;
import com.mmcs.trackapp.model.ResMetaCustomer;
import com.mmcs.trackapp.model.ResMetaDepartment;
import com.mmcs.trackapp.model.ResMetaMeeting;
import com.mmcs.trackapp.model.ResMetaReqTypes;
import com.mmcs.trackapp.model.ResMetaUsers;
import com.mmcs.trackapp.model.ResponseMeta;
import com.mmcs.trackapp.model.RestMetaCompany;
import com.mmcs.trackapp.model.RestMetaCountry;
import com.mmcs.trackapp.model.RestMetaLocation;
import com.mmcs.trackapp.model.RestMetaPayTerm;
import com.mmcs.trackapp.model.UploadImageResMeta;
import com.mmcs.trackapp.model.VQuotationResMeta;

import java.util.ArrayList;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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
    @POST("pay_term_get.php")
    Call<RestMetaPayTerm> getPayTermList(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("company_get_api.php")
    Call<RestMetaCompany> getCompanyList(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("country_get_api.php")
    Call<RestMetaCountry> getCountryList(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("location_get_api.php")
    Call<RestMetaLocation> getLocationList(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("fetch_matchDeatils.php")
    Call<ResMetaCustomer> getCustomerList(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("pre_requests.php")
    Call<PreRequestResMeta> getPrerequestMeetingList(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("apilogin.php")
    Call<LoginResMeta> login(@Field("email") String email, @Field("password") String password, @Field("device_token") String device_token, @Field("fcm_token") String fcm_token,@Field("userType") String userType,@Field("environment") String environment);

    @FormUrlEncoded
    @POST("fetch_meeting.php")
    Call<ResMetaMeeting> getMeetingsList(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("request_type.php")
    Call<ResMetaReqTypes> getRequestTypes(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("expense_type_get.php")
    Call<ResMetaReqTypes> getExpenseTypes(@Field("user_id") String user_id);


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
                                        ,@Field("reporting_manager_id") String reporting_manager_id

    );


    @Multipart
    @POST("post_expense.php?request=saveexpense_data")
    Call<ResMetaMeeting> postExpanse(@Part("user_id") RequestBody user_id, @Part("meeting_id") RequestBody meeting_id,
                                     @Part("amount") RequestBody amount,@Part("currency") RequestBody currency ,@Part("expense_type") RequestBody requesttypes, @Part("created_on") RequestBody createddate,
                                     @Part("comment") RequestBody comment ,@Part("image\"; filename=\"profile.jpg") RequestBody image

    );


    @Multipart
    @POST("update_expense_image.php?request=updateExpendseReceipt")
    Call<UploadImageResMeta> updateExpanseReceipt(@Part("expense_id") RequestBody user_id,@Part("image\"; filename=\"profile.jpg") RequestBody image

    );

    @Multipart
    @POST("update_user_profile_image.php?request=updateuserprofile")
    Call<UploadImageResMeta> updateUserProfile(@Part("user_id") RequestBody user_id, @Part("image\"; filename=\"profile.jpg") RequestBody image

    );

    @FormUrlEncoded
    @POST("fetch_expense.php")
    Call<ExpenseResMeta> getExpanseList(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("customer_post_api.php?request=savecustomer_data")
    Call<ResMetaMeeting> addNewCustomer(@Field("user_id") String user_id, @Field("customer_name") String customer_name,
                                        @Field("address") String address,@Field("place") String place,@Field("city") String city ,@Field("email") String email, @Field("phone") String phone, @Field("pin") String pin,@Field("customer_company") String customer_company,
                                        @Field("company_id") String company_id, @Field("country_id") String country_id,@Field("location_id") String location_id,@Field("pay_term_id") String pay_term_id ,@Field("tax_details") String details, @Field("admin_id") String admin_id);

    @FormUrlEncoded
    @POST("attendance_post_api.php?request=saveattendance_data")
    Call<ResMetaMeeting> postAttendance(@Field("user_id") String user_id, @Field("location") String location, @Field("date_time") String datetime, @Field("status") String status,@Field("date") String date

    );

    @FormUrlEncoded
    @POST("attendance_get_api.php")
    Call<ResAttandance> getAttandanceStatus(@Field("user_id") String user_id

    );

    @FormUrlEncoded
    @POST("login.php")
    Call<ResAttandance> test(@Field("email") String email, @Field("password") String password

    );


    @FormUrlEncoded
    @POST("pending_get_api.php")
    Call<PreRequestResMeta> getPendingsList(@Field("user_id") String user_id

    );

    @FormUrlEncoded
    @POST("pending_post_api.php?request=setPendingStatus")
    Call<PreRequestResMeta> postAcceptRejectPendings(@Field("user_id") String user_id, @Field("id") String id, @Field("manager_status") String manager_status

   ,@Field("rejection_message") String rejection_message );

    @FormUrlEncoded
    @POST("location_post_api.php?request=saveLocation")
    Call<PreRequestResMeta> postDeviceLocation(@Field("user_id") String user_id, @Field("latitude") String latitude, @Field("longitude") String longitude

    );


    @Multipart
    @POST("feedback_post_api.php?request=savefeedback_data")
    Call<PreRequestResMeta> postFeedback(@Part("user_id") RequestBody user_id, @Part("customer_id") RequestBody customer_id, @Part("feedback") RequestBody feedback, @Part("posted_on") RequestBody posted_on,  @Part("image\"; filename=\"profile.jpg") RequestBody image, @Part("smiley_status") RequestBody smiley_status);






    @FormUrlEncoded
    @POST("start_meeting_post.php?request=startMeeting")
    Call<PreRequestResMeta> updateMeedingStatus(@Field("user_id") String user_id, @Field("start_date_time") String start_date_time, @Field("end_date_time") String end_date_time, @Field("status") String status, @Field("meeting_id") String meeting_id
            , @Field("start_address") String start_address, @Field("end_address") String end_address,@Field("need_followup") String need_followup,@Field("mom") String mom,@Field("date") String date,@Field("time") String time
    );

    @FormUrlEncoded
    @POST("start_meeting_get.php")
    Call<ResMetaMeeting> getMeetingStatus(@Field("user_id") String user_id, @Field("meeting_id") String meeting_id
    );

    @FormUrlEncoded
    @POST("report_get.php")
    Call<ReportResMeta> getReportList(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("get_messeage.php")
    Call<MessageResMeta> getMessages(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("get_user_master.php")
    Call<ResMetaUsers> getUsers(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("post_message.php?request=saveMsg_data")
    Call<ResMetaMeeting> postMessage(@Field("from_user_id") String from_user_id,@Field("to_user_id") String to_user_id, @Field("text_msg") String text_msg
            , @Field("created_on") String created_on , @Field("user_name") String user_name
    );


    @FormUrlEncoded
    @POST("feedbacklistapi.php")
    Call<FeedbackResMeta> getFeedbackList(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("get_running_meetingcount_api.php")
    Call<ResMetaMeeting> getRunningMeetings(@Field("user_id") String user_id);
   /* @Multipart
    @POST("feedback_post_api.php?request=savefeedback_data")
    Call<PreRequestResMeta> postFeedback(@Part("user_id") RequestBody user_id, @Part("customer_id") RequestBody customer_id, @Part("feedback") RequestBody feedback, @Part("posted_on") RequestBody posted_on,  @Part("image\"; filename=\"profile.jpg") RequestBody image);


*/


    @FormUrlEncoded
    @POST("port_loading_get_api.php")
    Call<PortResMeta> getPOLAndPOD(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("quotation_get_api.php")
    Call<VQuotationResMeta> getVQuotationList(@Field("user_id") String user_id, @Field("pol") String pol, @Field("pod") String pod);


    @FormUrlEncoded
    @POST("update_expense_status.php?request=update_expense_Status")
    Call<VQuotationResMeta> updateExpense(@Field("id") String user_id, @Field("update_comment") String pol, @Field("final_status") String pod);


    @FormUrlEncoded
    @POST("mobile_menu_get.php")
    Call<HomeItemResMeta> getMenu(@Field("user_id") String user_id);



    @FormUrlEncoded
    @POST("expense_approval_get_api.php")
    Call<ExpResListMeta> getExpenseApprovalsList(@Field("user_id") String user_id

    );


    @FormUrlEncoded
    @POST("manager_status_getapi.php")
    Call<LoginResMeta> getManagerStatus(@Field("user_id") String user_id

    );


    @FormUrlEncoded
    @POST("expense_appr_post_api.php?request=setExpenseApproval")
    Call<PreRequestResMeta> postAcceptRejectExpense(@Field("user_id") String user_id, @Field("id") String id, @Field("manager_status") String manager_status,@Field("rejection_message") String rejection_message

    );
    @FormUrlEncoded
    @POST("expense_resubmit_post.php?request=resumit_msg")
    Call<PreRequestResMeta> postReSubmit(@Field("user_id") String user_id, @Field("id") String id, @Field("user_msg") String user_msg,@Field("edit_amount") String edit_amount,@Field("final_status") String final_status );




    @FormUrlEncoded
    @POST("pre_request_type_update_api.php")
    Call<PreReqUpdateResMeta> getPreRequestUpdate(@Field("id") String id, @Field("request_type") String request_type);


}
