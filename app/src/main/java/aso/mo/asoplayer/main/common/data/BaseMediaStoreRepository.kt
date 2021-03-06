// Copyright (c) 2019 . Wilberforce Uwadiegwu. All Rights Reserved.

package aso.mo.asoplayer.main.common.data

import android.app.Application
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.annotation.WorkerThread


/**
 *
 *
 * Base repository for repositories that fetch data from the [MediaStore]
 */
abstract class BaseMediaStoreRepository(private val application: Application) {

    @WorkerThread
    fun <T> loadData(
        uri: Uri,
        projection: Array<String>? = null,
        selection: String? = null,
        selectionArgs: Array<String>? = null,
        sortOrder: String? = null,
        transform: (cursor: Cursor) -> T
    ): List<T> {
        val results = mutableListOf<T>()
        val cursor = query(uri, projection, selection, selectionArgs, sortOrder)
        cursor?.use {
            while (it.moveToNext()) {
                results.add(transform(it))
            }
        }
        return results
    }

    fun query(
        uri: Uri,
        projection: Array<String>? = null,
        selection: String? = null,
        selectionArgs: Array<String>? = null,
        sortOrder: String? = null
    ): Cursor? =
        application.contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)

}