package aso.mo.asoplayer.main.common.network.image.mediaitemdata

import aso.mo.asoplayer.main.common.network.image.album.BaseAlbumLoader
import aso.mo.asoplayer.main.playback.MediaItemData
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.ModelLoader
import java.io.InputStream


class MediaItemDataLoader(concreteLoader: ModelLoader<GlideUrl, InputStream>) :
    BaseAlbumLoader<MediaItemData>(concreteLoader) {

    override fun artist(a: MediaItemData): String = a.subtitle

    override fun album(a: MediaItemData): String = a.description
}