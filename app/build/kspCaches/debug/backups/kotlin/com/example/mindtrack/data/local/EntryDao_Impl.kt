package com.example.mindtrack.`data`.local

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import javax.`annotation`.processing.Generated
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class EntryDao_Impl(
  __db: RoomDatabase,
) : EntryDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfEntry: EntityInsertAdapter<Entry>
  init {
    this.__db = __db
    this.__insertAdapterOfEntry = object : EntityInsertAdapter<Entry>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `entries` (`id`,`title`,`body`,`mood`,`tagsCsv`,`photoUri`,`createdAt`,`updatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: Entry) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.title)
        statement.bindText(3, entity.body)
        statement.bindLong(4, entity.mood.toLong())
        statement.bindText(5, entity.tagsCsv)
        val _tmpPhotoUri: String? = entity.photoUri
        if (_tmpPhotoUri == null) {
          statement.bindNull(6)
        } else {
          statement.bindText(6, _tmpPhotoUri)
        }
        statement.bindLong(7, entity.createdAt)
        statement.bindLong(8, entity.updatedAt)
      }
    }
  }

  public override suspend fun upsert(entry: Entry): Long = performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfEntry.insertAndReturnId(_connection, entry)
    _result
  }

  public override fun observeAll(): Flow<List<Entry>> {
    val _sql: String = "SELECT * FROM entries ORDER BY createdAt DESC"
    return createFlow(__db, false, arrayOf("entries")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfBody: Int = getColumnIndexOrThrow(_stmt, "body")
        val _columnIndexOfMood: Int = getColumnIndexOrThrow(_stmt, "mood")
        val _columnIndexOfTagsCsv: Int = getColumnIndexOrThrow(_stmt, "tagsCsv")
        val _columnIndexOfPhotoUri: Int = getColumnIndexOrThrow(_stmt, "photoUri")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: MutableList<Entry> = mutableListOf()
        while (_stmt.step()) {
          val _item: Entry
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpBody: String
          _tmpBody = _stmt.getText(_columnIndexOfBody)
          val _tmpMood: Int
          _tmpMood = _stmt.getLong(_columnIndexOfMood).toInt()
          val _tmpTagsCsv: String
          _tmpTagsCsv = _stmt.getText(_columnIndexOfTagsCsv)
          val _tmpPhotoUri: String?
          if (_stmt.isNull(_columnIndexOfPhotoUri)) {
            _tmpPhotoUri = null
          } else {
            _tmpPhotoUri = _stmt.getText(_columnIndexOfPhotoUri)
          }
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _item = Entry(_tmpId,_tmpTitle,_tmpBody,_tmpMood,_tmpTagsCsv,_tmpPhotoUri,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun observe(id: Long): Flow<Entry?> {
    val _sql: String = "SELECT * FROM entries WHERE id = ?"
    return createFlow(__db, false, arrayOf("entries")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfBody: Int = getColumnIndexOrThrow(_stmt, "body")
        val _columnIndexOfMood: Int = getColumnIndexOrThrow(_stmt, "mood")
        val _columnIndexOfTagsCsv: Int = getColumnIndexOrThrow(_stmt, "tagsCsv")
        val _columnIndexOfPhotoUri: Int = getColumnIndexOrThrow(_stmt, "photoUri")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: Entry?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpBody: String
          _tmpBody = _stmt.getText(_columnIndexOfBody)
          val _tmpMood: Int
          _tmpMood = _stmt.getLong(_columnIndexOfMood).toInt()
          val _tmpTagsCsv: String
          _tmpTagsCsv = _stmt.getText(_columnIndexOfTagsCsv)
          val _tmpPhotoUri: String?
          if (_stmt.isNull(_columnIndexOfPhotoUri)) {
            _tmpPhotoUri = null
          } else {
            _tmpPhotoUri = _stmt.getText(_columnIndexOfPhotoUri)
          }
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _result = Entry(_tmpId,_tmpTitle,_tmpBody,_tmpMood,_tmpTagsCsv,_tmpPhotoUri,_tmpCreatedAt,_tmpUpdatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteById(id: Long) {
    val _sql: String = "DELETE FROM entries WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
