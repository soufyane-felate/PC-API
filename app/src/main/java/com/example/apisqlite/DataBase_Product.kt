package com.example.apisqlite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DataBase_Product(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_PRODUCTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PRODUCTS")
        onCreate(db)
    }

    fun updateProducts(products: List<Product>) {
        val db = this.writableDatabase

        // Clear existing data
        db.delete(TABLE_PRODUCTS, null, null)

        // Insert new data
        for (product in products) {
            val values = ContentValues()
            values.put(COLUMN_NAME, product.nom)
            values.put(COLUMN_OS, product.systeme_exploitation)
            values.put(COLUMN_DATE, product.date_fin_os.toString())

            values.put(COLUMN_PRICE, product.prix)
            values.put(COLUMN_IMAGE, product.image)

            db.insert(TABLE_PRODUCTS, null, values)
        }

        db.close()
    }

    @SuppressLint("Range", "NewApi")
    fun getAllProducts(): List<Product> {
        val productList = mutableListOf<Product>()
        val db = this.readableDatabase

        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_PRODUCTS", null)

        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                val os = cursor.getString(cursor.getColumnIndex(COLUMN_OS))
                val dateString = cursor.getString(cursor.getColumnIndex(COLUMN_DATE))

                val formatter = DateTimeFormatter.ISO_LOCAL_DATE
                val date = LocalDate.parse(dateString, formatter)

                val price = cursor.getString(cursor.getColumnIndex(COLUMN_PRICE))
                val image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE))

                val product = Product(name, os, date, price, image)
                productList.add(product)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return productList
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "products_db"

        private const val TABLE_PRODUCTS = "products"
        private const val COLUMN_ID = "_id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_OS = "os"
        private const val COLUMN_DATE = "date"
        private const val COLUMN_PRICE = "price"
        private const val COLUMN_IMAGE = "image"

        private const val CREATE_PRODUCTS_TABLE = (
                "CREATE TABLE $TABLE_PRODUCTS ($COLUMN_ID INTEGER PRIMARY KEY, "
                        + "$COLUMN_NAME TEXT, $COLUMN_OS TEXT, $COLUMN_DATE TEXT, "
                        + "$COLUMN_PRICE TEXT, $COLUMN_IMAGE TEXT)"
                )
    }
}
