package com.se.music.uamp

import android.net.Uri
import androidx.recyclerview.widget.DiffUtil
import com.se.service.library.PlayState

data class MediaItemData(
        val mediaId: String,
        val title: String,
        val artist: String,
        val album: String,
        val albumArtUri: Uri,
        val browseable: Boolean,
        var playbackState: PlayState) {

    companion object {

        const val PLAYBACK_RES_CHANGED = 1

        val diffCallback = object : DiffUtil.ItemCallback<MediaItemData>() {
            override fun areItemsTheSame(oldItem: MediaItemData,
                                         newItem: MediaItemData): Boolean = oldItem.mediaId == newItem.mediaId

            override fun areContentsTheSame(oldItem: MediaItemData, newItem: MediaItemData) =
                    oldItem.mediaId == newItem.mediaId && oldItem.playbackState == newItem.playbackState

            override fun getChangePayload(oldItem: MediaItemData, newItem: MediaItemData) =
                    if (oldItem.playbackState != newItem.playbackState) {
                        PLAYBACK_RES_CHANGED
                    } else null
        }
    }
}

