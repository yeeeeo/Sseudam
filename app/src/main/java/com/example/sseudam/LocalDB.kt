package com.example.Sseudam

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.service.autofill.UserData
import android.system.Os.remove
import java.nio.file.Files.delete

/*
class LocalDB (
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version){
    override fun onCreate(db: SQLiteDatabase?) {
        // DB 생성시 실행
        if (db != null) {
            createDatabase(db)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // DB 버전 변경시 실행됨
        val sql : String = "DROP TABLE if exists ${LocalDatas.userData.TABLE_NAME}"

        if (db != null) {
            db.execSQL(sql)
            onCreate(db)
        } //  버전이 변경되면 기존 Table을 삭제후 재생성함.
    }

    fun createDatabase(db: SQLiteDatabase) {
        // 테이블이 존재하지 않는경우 생성
        var sql: String = "CREATE TABLE if not exists ${LocalDatas.userData.TABLE_NAME} (" +
                "${BaseColumns._ID} integer primary key autoincrement," +
                "${LocalDatas.userData.COLUMN_NAME_ID} varchar(15)," +
                "${LocalDatas.userData.COLUMN_NAME_PASSWORD} varchar(20),"+
                "${LocalDatas.userData.COLUMN_NAME_NAME} varchar(20),"+
                "${LocalDatas.userData.COLUMN_NAME_PHONE} varchar(20),"+
                "${LocalDatas.userData.COLUMN_NAME_LOCAL} varchar(20)" +
                ");"



        db.execSQL(sql)
    }

    fun checkIdExist(id: String): Boolean {
        val db = this.readableDatabase

        // 리턴받고자 하는 컬럼 값의 array
        val projection = arrayOf(BaseColumns._ID)
        //,LocalDatas.userData.COLUMN_NAME_ID, LocalDatas.userData.COLUMN_NAME_PASSWORD)


        //  WHERE "id" = id AND "password"=password 구문 적용하는 부분
        val selection = "${LocalDatas.userData.COLUMN_NAME_ID} = ?"
        val selectionArgs = arrayOf(id)

//         정렬조건 지정
//        val sortOrder = "${FeedEntry.COLUMN_NAME_SUBTITLE} DESC"

        val cursor = db.query(
            LocalDatas.userData.TABLE_NAME,   // 테이블
            projection,             // 리턴 받고자 하는 컬럼
            selection,              // where 조건
            selectionArgs,          // where 조건에 해당하는 값의 배열
            null,                   // 그룹 조건
            null,                   // having 조건
            null               // orderby 조건 지정
        )
        if(cursor.count>0){//  반환된 cursor 값이 존재
            return true;
        }else{//반환된 cursor 값이 없음
            return false;
        }

    }

    fun logIn(id: String, password:String): Boolean {
        val db = this.readableDatabase

        // 리턴받고자 하는 컬럼 값의 array
        val projection = arrayOf(BaseColumns._ID)
        //,LocalDatas.userData.COLUMN_NAME_ID, LocalDatas.userData.COLUMN_NAME_PASSWORD)


        //  WHERE "id" = id AND "password"=password 구문 적용하는 부분
        val selection = "${LocalDatas.userData.COLUMN_NAME_ID} = ?"
        val selectionArgs = arrayOf(id)

//         정렬조건 지정
//        val sortOrder = "${FeedEntry.COLUMN_NAME_SUBTITLE} DESC"

        val cursor = db.query(
            LocalDatas.userData.TABLE_NAME,   // 테이블
            projection,             // 리턴 받고자 하는 컬럼
            selection,              // where 조건
            selectionArgs,          // where 조건에 해당하는 값의 배열
            null,                   // 그룹 조건
            null,                   // having 조건
            null               // orderby 조건 지정
        )
        if(cursor.count>0){//  반환된 cursor의 0번째 값이 null이면
            return true;
        }else{
            return false;
        }

    }

    fun registerUser(id: String, password: String, name: String, phone: String, local: String){
        val db =this.writableDatabase
        val values = ContentValues().apply {// insert될 데이터값
            put(LocalDatas.userData.COLUMN_NAME_ID, id)
            put(LocalDatas.userData.COLUMN_NAME_PASSWORD, password)
            put(LocalDatas.userData.COLUMN_NAME_NAME, name)
            put(LocalDatas.userData.COLUMN_NAME_PHONE, phone)
        }
        val newRowId = db?.insert(LocalDatas.userData.TABLE_NAME, null, values)
        // 인서트후 인서트된 primary key column의 값(_id) 반환.
    }

    fun registerdelete(id: String) {
        val db = this.writableDatabase
        val selection = "${LocalDatas.userData.COLUMN_NAME_ID} LIKE ${id}"
        val selectionArgs = arrayOf(id)
        val deletedRows = db?.delete(LocalDatas.userData.TABLE_NAME, selection, selectionArgs)
    }




}

 */


