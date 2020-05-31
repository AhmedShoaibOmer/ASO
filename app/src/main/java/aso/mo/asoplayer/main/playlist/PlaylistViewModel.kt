package aso.mo.asoplayer.main.playlist

import android.app.Application
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.viewModelScope
import aso.mo.asoplayer.main.common.data.MediaStoreRepository
import aso.mo.asoplayer.main.common.view.BaseMediaStoreViewModel
import com.hunter.library.debug.HunterDebug
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


open class PlaylistViewModel(application: Application) :
    BaseMediaStoreViewModel<Playlist>(application) {

    override var repository: MediaStoreRepository<Playlist> = PlaylistRepository(application)

    override var sortOrder: String? =
        "${MediaStore.Audio.Playlists.DATE_MODIFIED} COLLATE NOCASE DESC"

    override var uri: Uri = basePlaylistUri

    override var projection: Array<String>? = basePlaylistProjection


    override fun init(vararg params: Any?) {
        if (params.isNotEmpty()) {
            selection = "${MediaStore.Audio.Playlists._ID} == ?"
            selectionArgs = arrayOf(params[0].toString())
        }
        super.init()
    }

    @HunterDebug
    override fun deliverResult(items: List<Playlist>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                items.forEach {
                    it.songsCount = (repository as PlaylistRepository).fetchSongCount(it.id)
                }
                if (data.value != items) data.postValue(items)
            }
        }
    }

    fun reverseSelection(index: Int): Boolean {
        return data.value?.let {
            if (it.size > index) {
                it[index].selected = !it[index].selected
                true
            } else false
        } ?: false

    }

}

val basePlaylistProjection = arrayOf(
    MediaStore.Audio.Playlists._ID,
    MediaStore.Audio.Playlists.NAME,
    MediaStore.Audio.Playlists.DATE_MODIFIED
)

val basePlaylistUri: Uri = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI
