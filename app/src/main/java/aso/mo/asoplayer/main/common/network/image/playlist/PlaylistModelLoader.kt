package aso.mo.asoplayer.main.common.network.image.playlist

import android.net.Uri
import android.provider.MediaStore
import aso.mo.asoplayer.main.common.network.image.BaseCollageDataFetcher
import aso.mo.asoplayer.main.common.utils.ImageUtils
import aso.mo.asoplayer.main.playlist.Playlist
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.signature.ObjectKey
import java.io.File
import java.io.InputStream


class PlaylistModelLoader :
    ModelLoader<Playlist, InputStream> {

    override fun buildLoadData(
        model: Playlist,
        width: Int,
        height: Int,
        options: Options
    ): ModelLoader.LoadData<InputStream>? {
        return ModelLoader.LoadData(ObjectKey(model), PlaylistDataFetcher(model, width, height))
    }

    override fun handles(model: Playlist) = true


    inner class PlaylistDataFetcher(private val playlist: Playlist, width: Int, height: Int) :
        BaseCollageDataFetcher(playlist, width, height, true) {

        override var modelMemberMediaUri: Uri =
            MediaStore.Audio.Playlists.Members.getContentUri("external", playlist.id)

        override var imageFile = File(ImageUtils.getImagePathForModel(model, application))

        override fun hasValidData() = playlist.songsCount > 0

    }

}