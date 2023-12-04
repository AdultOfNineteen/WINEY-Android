package com.teamwiney.data.network.service

import com.teamwiney.core.common.base.ResponseWrapper
import com.teamwiney.core.common.`typealias`.BaseResponse
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.PagingResponse
import com.teamwiney.data.network.model.response.TasteAnalysis
import com.teamwiney.data.network.model.response.TastingNote
import com.teamwiney.data.network.model.response.TastingNoteDetail
import com.teamwiney.data.network.model.response.TastingNoteFilters
import com.teamwiney.data.network.model.response.TastingNoteIdRes
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface TastingNoteService {

    /** 내 취향 분석 API */
    @GET("/tasting-notes/taste-analysis")
    suspend fun getTasteAnalysis(): ApiResult<ResponseWrapper<TasteAnalysis>>

    /** 테이스팅 노트 조회 API */
    @GET("/tasting-notes")
    suspend fun getTastingNotes(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("order") order: Int,
        @Query("countries") countries: List<String>,
        @Query("wineTypes") wineTypes: List<String>,
        @Query("buyAgain") buyAgain: Int
    ): ApiResult<ResponseWrapper<PagingResponse<List<TastingNote>>>>

    /** 테이스팅 노트 필터 목록 조회 API */
    @GET("/tasting-notes/filter")
    suspend fun getTastingNoteFilters(): ApiResult<ResponseWrapper<TastingNoteFilters>>

    /** 테이스팅 노트 상세 조회 API */
    @GET("/tasting-notes/{noteId}")
    suspend fun getTastingNoteDetail(
        @Path("noteId") noteId: Int
    ): ApiResult<ResponseWrapper<TastingNoteDetail>>

    /** 테이스팅 노트 삭제 API */
    @DELETE("/tasting-notes/{noteId}")
    suspend fun deleteTastingNote(
        @Path("noteId") noteId: Int
    ): ApiResult<BaseResponse>

    /** 테이스팅 노트 작성 API */
    @Multipart
    @POST("/tasting-notes")
    suspend fun postTastingNote(
        @Part("request") request: RequestBody,
        @Part multipartFiles: List<MultipartBody.Part>,
    ): ApiResult<ResponseWrapper<TastingNoteIdRes>>
}