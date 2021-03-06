package aso.mo.asoplayer.main.playlist

import android.app.Application
import android.content.ContentProviderOperation
import android.content.ContentValues
import android.net.Uri
import android.provider.MediaStore
import androidx.annotation.StringRes
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import aso.mo.asoplayer.R
import aso.mo.asoplayer.main.common.event.Event
import aso.mo.asoplayer.main.songs.SongsRepository
import aso.mo.asoplayer.main.songs.baseSongUri
import aso.mo.asoplayer.main.songs.basicSongsSelection
import aso.mo.asoplayer.main.songs.basicSongsSelectionArgs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AddSongsToPlaylistsViewModel(application: Application) : PlaylistViewModel(application) {
    private val songsRepository = SongsRepository(application)
    private val insertionData = MutableLiveData<Event<InsertionResult>>()
    val mediatorItems = MediatorLiveData<Any>()
    private var matchingSongsIds = emptyList<Long>()
    private lateinit var songsUri: Uri
    private lateinit var songsSelection: String
    private lateinit var songsSelectionArgs: Array<String>

    @Suppress("UNCHECKED_CAST")
    override fun init(
        vararg params: Any?
    ) {
        this.songsUri = params[0] as Uri? ?: baseSongUri
        this.songsSelection = params[1] as String? ?: basicSongsSelection
        this.songsSelectionArgs = params[2] as Array<String>? ?: basicSongsSelectionArgs

        mediatorItems.addSource(items) { mediatorItems.value = it }
        mediatorItems.addSource(insertionData) { mediatorItems.value = it }
        super.init()
        loadSongs()
    }

    private fun loadSongs() {
        viewModelScope.launch {
            val ids = withContext(Dispatchers.IO) {
                songsRepository.fetchMatchingIds(
                    songsUri,
                    arrayOf(MediaStore.Audio.Media._ID),
                    songsSelection,
                    songsSelectionArgs
                )
            }
            matchingSongsIds = ids
        }
    }

    fun addToPlaylist() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val selected = data.value?.filter { it.selected }
                if (selected == null || selected.isEmpty()) {
                    insertionData.postValue(Event(InsertionResult()))
                    return@withContext
                }

                val songIds = matchingSongsIds
                if (songIds.isEmpty()) {
                    insertionData.postValue(Event(InsertionResult(R.string.sth_went_wrong, false)))
                    return@withContext
                }
                val operations = arrayListOf<ContentProviderOperation>()

                selected.forEach { playlist ->
                    songIds.forEach { id ->
                        val values = ContentValues()
                        values.put(
                            MediaStore.Audio.Playlists.Members.PLAY_ORDER,
                            playlist.songsCount + 1
                        )
                        values.put(MediaStore.Audio.Playlists.Members.AUDIO_ID, id)
                        val uri = MediaStore.Audio.Playlists.Members.getContentUri(
                            "external",
                            playlist.id
                        )
                        val cpo = ContentProviderOperation.newInsert(uri).withValues(values).build()
                        operations.add(cpo)
                    }
                }
                val result = getApplication<Application>().contentResolver.applyBatch(
                    MediaStore.AUTHORITY,
                    operations
                )
                if (result.size == operations.size) {
                    insertionData.postValue(Event(InsertionResult(R.string.success, true)))
                } else {
                    insertionData.postValue(Event(InsertionResult(R.string.sth_went_wrong, false)))
                }
            }
        }
    }

}

internal data class InsertionResult(
    @StringRes val message: Int? = null,
    val success: Boolean? = null
)