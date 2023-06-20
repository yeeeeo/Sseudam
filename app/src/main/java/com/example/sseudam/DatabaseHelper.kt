package com.example.Sseudam

import User
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.view.View
import android.widget.Toast


class DatabaseHelper(
    val context: Context?,
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "USER_AUTH"

        // Table
        const val TABLE_NAME = "USER"
        const val UID = "UID"
        const val COL_ID = "ID"
        const val COL_PW = "PW"
        const val COL_NAME = "NAME"
        const val COL_PHONE = "PHONE"
        const val COL_POINT = "POINT"

        const val TABLE_NAME1 = "COMUNITY"
        const val UID1 = "UID1"
        const val COL_TITLE = "TITLE"
        const val COL_CONTENTS = "CONTENTS"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // USER 라는 이름의 테이블을 생성하고 column 은 ID, PW, NAME 3개를 생성했습니다.
        // UID 는 SQLite 를 사용하기 위해서 필수적으로 필요한 column
        var sql: String = "CREATE TABLE " +
                "$TABLE_NAME ($UID integer primary key autoincrement, " +
                "$COL_ID text, $COL_PW text, $COL_NAME text, $COL_PHONE text, $COL_POINT text);"

        var sql1: String = "CREATE TABLE " +
                "$TABLE_NAME1($UID1 integer primary key autoincrement,"+
                "$COL_TITLE text, $COL_CONTENTS text);"

        db.execSQL(sql)
        db.execSQL(sql1)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val sql = "DROP TABLE IF EXISTS $TABLE_NAME"
        val sql1 = "DROP TABLE IF EXISTS $TABLE_NAME1"
        db.execSQL(sql)
        db.execSQL(sql1)
        onCreate(db)
    }

    // allUsers 리스트에 getter 를 만들어서 db 에 저장되어있는 모든 유저 정보를 가져옵니다.
    val allUsers:List<User>
        @SuppressLint("Range")
        get() {
            val users = ArrayList<User>()
            val selectQueryHandler = "SELECT * FROM $TABLE_NAME"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQueryHandler,null)
            if(cursor.moveToFirst()){
                do{
                    val user = User()
                    user.id = cursor.getString(cursor.getColumnIndex(COL_ID))
                    user.pw = cursor.getString(cursor.getColumnIndex(COL_PW))
                    user.name = cursor.getString(cursor.getColumnIndex(COL_NAME))
                    user.phone = cursor.getString(cursor.getColumnIndex(COL_PHONE))
                    user.point = cursor.getString(cursor.getColumnIndex(COL_POINT))
                    users.add(user)



                }while(cursor.moveToNext())
            }
            db.close()
            return users
        }

    val allComunity:List<Comunity>
        @SuppressLint("Range")
        get() {
            val cms = ArrayList<Comunity>()
            val selectQueryHandler = "SELECT * FROM $TABLE_NAME1"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQueryHandler,null)
            if(cursor.moveToFirst()){
                do{
                    val cm = Comunity()
                    cm.title = cursor.getString(cursor.getColumnIndex(COL_TITLE))
                    cm.contents = cursor.getString(cursor.getColumnIndex(COL_CONTENTS))
                    cms.add(cm)

                }while(cursor.moveToNext())
            }
            db.close()
            return cms
        }


    fun checkIdExist(id: String): Boolean{
        val db = this.readableDatabase

        // 리턴 받고자 하는 컬럼 값의 array
        val projection = arrayOf(UID)

        // where "id"=id
        val selection= "$COL_ID = ?"
        val selectionArgs = arrayOf(id)

        // 정렬조건 지정
        val cursor = db.query(
            TABLE_NAME, // 테이블
            projection, // 리턴 받고자 하는 컬럼
            selection, // where 조건
            selectionArgs, // where 조건에 해당하는 값의 배열
            null, // 그룹 조건
            null, // having 조건
            null // orderby 조건 지정
        )
        // 반환된 cursor 값이 존재하면 아이디 중복(true), 존재하지 않으면 아이디 생성가능(false)
        return cursor.count > 0
    }



    // db 에 새로운 유저를 추가하는 메소드(회원가입)
    fun addUser(user: User){
        if(checkIdExist(user.id)) {
            Toast.makeText(this.context, "이미 존재하는 아이디 입니다.", Toast.LENGTH_SHORT).show()
            return
        }
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID, user.id)
        values.put(COL_PW, user.pw)
        values.put(COL_NAME, user.name)
        values.put(COL_PHONE, user.phone)

        db.insert(TABLE_NAME, null, values)
        db.close()
        Toast.makeText(this.context,"회원가입 성공", Toast.LENGTH_SHORT).show()
    }
    // 로그인 메소드
    @SuppressLint("Range")
    fun login(user: User) : Boolean{
        val db = this.readableDatabase

        // 리턴 받고자 하는 컬럼 값의 array
        val projection = arrayOf(UID)

        // where "id" = id and "pw" = pw 구문 적용하는 부분
        val selection = "$COL_ID = ? AND $COL_PW = ?"
        val selectionArgs = arrayOf(user.id, user.pw)

        // 정렬조건 지정
        val cursor = db.query(
            TABLE_NAME, // 테이블 이름
            projection, // 리턴 받고자 하는 컬럼
            selection, // 조건
            selectionArgs, // 조건에 해당하는 값의 배열
            null, // 그룹 조건
            null, // having 조건
            null // orderby 조건
        )
        if(cursor.moveToFirst()){ // 유저의 primary key(uid) 가져오기
            Log.d("testt", cursor.getInt(cursor.getColumnIndex(UID)).toString())
        }

        return cursor.count > 0 // 반환 값이 존재하면 로그인 성공(true)
    }

    @SuppressLint("Range")
    fun mantoman(cm: Comunity) : Boolean{
        val db = this.readableDatabase

        // 리턴 받고자 하는 컬럼 값의 array
        val projection = arrayOf(UID1)

        // where "id" = id and "pw" = pw 구문 적용하는 부분
        val selection = "$COL_TITLE = ? AND $COL_CONTENTS = ?"
        val selectionArgs = arrayOf(cm.title, cm.contents)

        // 정렬조건 지정
        val cursor = db.query(
            TABLE_NAME1, // 테이블 이름
            projection, // 리턴 받고자 하는 컬럼
            selection, // 조건
            selectionArgs, // 조건에 해당하는 값의 배열
            null, // 그룹 조건
            null, // having 조건
            null // orderby 조건
        )
        if(cursor.moveToFirst()){ // 유저의 primary key(uid) 가져오기
            Log.d("testt", cursor.getInt(cursor.getColumnIndex(UID1)).toString())
        }

        return cursor.count > 0 // 반환 값이 존재하면 로그인 성공(true)
    }

    // 유저 정보 업데이트 메소드
    fun updateUser(user: User): Int{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID, user.id)
        values.put(COL_PW, user.pw)
        values.put(COL_NAME, user.name)
        values.put(COL_NAME, user.phone)

        return db.update(TABLE_NAME, values, "$COL_ID=?", arrayOf(user.id))
    }

    fun deleteUser(user: User){
        val db = this.writableDatabase

        db.delete(TABLE_NAME, "$COL_ID=?", arrayOf(user.id))
        db.close()
    }

}

    /*

    companion object {
        const val DATABASE_NAME = "Sseudam.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "Sseudam_table"
        const val UID = "UID"
        const val COL1_ID = "id"
        const val COL2_PW = "password"
        const val COL3_NAME = "name"
        const val COL4_PHONE = "phone"
    }

    @Volatile
    private var instance: DatabaseHelper? = null
    fun getInstance(context: Context) =
        instance ?: synchronized(DatabaseHelper::class.java){
            instance ?: DatabaseHelper(context).also{
                instance = it
            }
        }


    override fun onCreate(db: SQLiteDatabase?) {
        val createQuery : String = " CREATE TABLE IF NOT EXISTS " + "$TABLE_NAME($UID integer primary key autoincrement, " +
                "$COL1_ID text, $COL2_PW text, $COL3_NAME text, $COL4_PHONE text)"
        db?.execSQL(createQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if(oldVersion != newVersion){
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
            onCreate(db)
        }
    }

    fun checkIdExist(id: String): Boolean {
        val db = this.readableDatabase

        // 리턴받고자 하는 컬럼 값의 array
        val projection = arrayOf(UID)
        //,LocalDatas.userData.COLUMN_NAME_ID, LocalDatas.userData.COLUMN_NAME_PASSWORD)


        //  WHERE "id" = id AND "password"=password 구문 적용하는 부분
        val selection = "${COL1_ID} = ?"
        val selectionArgs = arrayOf(id)

//         정렬조건 지정
//        val sortOrder = "${FeedEntry.COLUMN_NAME_SUBTITLE} DESC"

        val cursor = db.query(
            TABLE_NAME,   // 테이블
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

    fun addUser(user: User){
        if(checkIdExist(user.id)){
            Toast.makeText(this.context, "이미 존재하는 아이디 입니다.", Toast.LENGTH_SHORT).show()
            return
        }
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL1_ID, user.id)
        values.put(COL2_PW, user.pw)
        values.put(COL3_NAME, user.name)
        values.put(COL4_PHONE, user.phone)

        db.insert(TABLE_NAME, null, values)
        db.close()
        Toast.makeText(this.context, "회원가입 성공", Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("Range")
    fun logIn(user: User): Boolean {
        val db = this.readableDatabase

        // 리턴받고자 하는 컬럼 값의 array
        val projection = arrayOf(UID)
        //,LocalDatas.userData.COLUMN_NAME_ID, LocalDatas.userData.COLUMN_NAME_PASSWORD)


        //  WHERE "id" = id AND "password"=password 구문 적용하는 부분
        val selection = "${COL1_ID} = ? AND $COL2_PW = ?"
        val selectionArgs = arrayOf(user.id, user.pw)
//         정렬조건 지정
//        val sortOrder = "${FeedEntry.COLUMN_NAME_SUBTITLE} DESC"

        val cursor = db.query(
            TABLE_NAME,   // 테이블
            projection,             // 리턴 받고자 하는 컬럼
            selection,              // where 조건
            selectionArgs,          // where 조건에 해당하는 값의 배열
            null,                   // 그룹 조건
            null,                   // having 조건
            null               // orderby 조건 지정
        )
        if(cursor.moveToFirst()){
            Log.d("testt", cursor.getInt(cursor.getColumnIndex(UID)).toString())
        }
        return cursor.count >0

    }

    fun updateData(id: String, password: String, name: String, phone: String){
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COL1_ID, id)
            put(COL2_PW, password)
            put(COL3_NAME, name)
            put(COL4_PHONE, phone)
        }
        db.update(TABLE_NAME, contentValues, "$COL1_ID = ?", arrayOf(id))
    }

    fun deleteData(id: String){
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COL1_ID = ?", arrayOf(id))
    }


    fun insertData(id: String, password: String, name: String, phone: String){
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COL1_ID, id)
            put(COL2_PW, password)
            put(COL3_NAME, name)
            put(COL4_PHONE, phone)
        }
        db.insert(TABLE_NAME, null, contentValues)
        val newRowId = db?.insert(TABLE_NAME, null, contentValues)
    }


}

     */