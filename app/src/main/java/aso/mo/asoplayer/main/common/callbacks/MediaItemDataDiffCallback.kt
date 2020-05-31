// Copyright (c) 2019 . Wilberforce Uwadiegwu. All Rights Reserved.

package aso.mo.asoplayer.main.common.callbacks

import aso.mo.asoplayer.main.playback.MediaItemData


/**
 * Created by Wilberforce on 2019-10-05 at 02:49.
 */
class MediaItemDataDiffCallback(
    private val oldList: List<MediaItemData>,
    private val newList: List<MediaItemData>
) :
    BaseDiffCallback<MediaItemData>(oldList, newList) {

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        newList[newItemPosition].areContentsTheSame(oldList[oldItemPosition])
}