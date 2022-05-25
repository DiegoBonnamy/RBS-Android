package com.bonnamy.dashboard.ws

import com.bonnamy.dashboard.bo.*
import retrofit2.http.GET
import retrofit2.http.Query

interface WSInterface {

    @GET("api/mvp/")
    suspend fun getMvp(): Mvp

    @GET("api/distance/")
    suspend fun getDistance(): DistanceTotale

    @GET("api/medals/")
    suspend fun getMedals(): Array<Medals>

    @GET("api/events/")
    suspend fun getEvents(): Array<Event>

    @GET("api/athlete/")
    suspend fun getAthlete(
        @Query("a") param1: Int): Athlete

    @GET("api/athlete/")
    suspend fun getAthletes(): List<Athlete>

    @GET("api/athlete/color/")
    suspend fun setColor(
        @Query("a") a: Int,
        @Query("c") c: String): WSResponse

    @GET("api/athlete/objectif/cyclisme/")
    suspend fun setObjectifCyclisme(
        @Query("a") a: Int,
        @Query("v") v: Int): WSResponse

    @GET("api/athlete/objectif/running/")
    suspend fun setObjectifRunning(
        @Query("a") a: Int,
        @Query("v") v: Int): WSResponse

    @GET("api/segments/ascensions/")
    suspend fun getSegmentsAscensions(): Array<Segment>

    @GET("api/segments/sprints/")
    suspend fun getSegmentsSprints(): Array<Segment>

    @GET("api/records/")
    suspend fun getRecords(): List<Record>
}