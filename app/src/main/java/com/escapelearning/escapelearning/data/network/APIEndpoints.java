package com.escapelearning.escapelearning.data.network;

import com.escapelearning.escapelearning.data.models.AuthToken;
import com.escapelearning.escapelearning.data.models.Classroom;
import com.escapelearning.escapelearning.data.models.Parent;
import com.escapelearning.escapelearning.data.models.School;
import com.escapelearning.escapelearning.data.models.Student;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIEndpoints {

    @FormUrlEncoded
    @POST("users/token/")
    Single<AuthToken> login(@Field("username") String username,
                            @Field("password") String password,
                            @Field("role") String role);

    @GET("schools/")
    Single<List<School>> searchSchools(@Query("search") String name);

    @GET("schools/{school}/classrooms/")
    Single<List<Classroom>> searchClassrooms(@Path("school") String schoolName);

    @FormUrlEncoded
    @POST("students/register/")
    Single<Student> signupStudent(@Field("name") String name,
                                  @Field("school") String schoolName,
                                  @Field("school_code") String schoolCode,
                                  @Field("classroom") String classRoom,
                                  @Field("username") String username,
                                  @Field("password") String password);

    @FormUrlEncoded
    @POST("students/register/")
    Single<Parent> signupParent(@Field("name") String name,
                                @Field("child_code") String childCode,
                                @Field("username") String username,
                                @Field("password") String password);
}
