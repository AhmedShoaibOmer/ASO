package aso.mo.asoplayer.main.common.network.image.genre

import android.net.Uri
import android.provider.MediaStore
import aso.mo.asoplayer.main.common.network.image.BaseCollageDataFetcher
import aso.mo.asoplayer.main.common.utils.ImageUtils
import aso.mo.asoplayer.main.genres.Genre
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.signature.ObjectKey
import java.io.File
import java.io.InputStream

class GenreModelLoader : ModelLoader<Genre, InputStream> {
    override fun buildLoadData(
        model: Genre,
        width: Int,
        height: Int,
        options: Options
    ): ModelLoader.LoadData<InputStream>? {
        return ModelLoader.LoadData(ObjectKey(model), GenreDataFetcher(model, width, height))
    }

    override fun handles(model: Genre) = true

    inner class GenreDataFetcher(model: Genre, width: Int, height: Int) :
        BaseCollageDataFetcher(model, width, height, false) {


        override var modelMemberMediaUri: Uri =
            MediaStore.Audio.Genres.Members.getContentUri("external", model.id)

        override var imageFile = File(ImageUtils.getImagePathForModel(model, application))
    }
}