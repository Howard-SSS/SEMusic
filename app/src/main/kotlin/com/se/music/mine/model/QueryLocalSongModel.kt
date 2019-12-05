package com.se.music.mine.model

import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.se.music.base.BaseActivity
import com.se.music.database.metadata.*
import com.se.music.singleton.SharePreferencesUtils

/**
 *Author: gaojin
 *Time: 2018/5/24 下午11:49
 */

class QueryLocalSongModel(presenter: com.se.router.mvp.MvpPresenter, private var modelId: Int, private var from: Int) : com.se.router.mvp.BaseModel<Cursor>(presenter, modelId), LoaderManager.LoaderCallbacks<Cursor> {
    override fun load() {
        LoaderManager.getInstance(getActivity() as BaseActivity).initLoader(modelId, null, this)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return when (from) {
            START_FROM_LOCAL -> {
                CursorLoader(getContext()!!, localMusicUri, infoMusic, songSelection, null, SharePreferencesUtils.getSongSortOrder())
            }
            START_FROM_ARTIST -> {
                val songSelectionAppend = "$songSelection and ${MediaStore.Audio.Media.ARTIST_ID} = $id"
                CursorLoader(getContext()!!, localMusicUri, infoMusic, songSelectionAppend, null, SharePreferencesUtils.getArtistSortOrder())
            }
            START_FROM_ALBUM -> {
                val songSelectionAppend = "$songSelection and ${MediaStore.Audio.Media.ALBUM_ID} = $id"
                CursorLoader(getContext()!!, localMusicUri, infoMusic, songSelectionAppend, null, SharePreferencesUtils.getAlbumSortOrder())
            }
            else -> {
                CursorLoader(getContext()!!, localMusicUri, infoMusic, songSelection, null, SharePreferencesUtils.getSongSortOrder())
            }
        }
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        if (data != null) {
            dispatchData(data)
        }
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
    }
}