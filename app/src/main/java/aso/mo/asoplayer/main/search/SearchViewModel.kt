package aso.mo.asoplayer.main.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import aso.mo.asoplayer.main.albums.Album
import aso.mo.asoplayer.main.artists.Artist
import aso.mo.asoplayer.main.common.event.Event
import aso.mo.asoplayer.main.genres.Genre
import aso.mo.asoplayer.main.playlist.Playlist
import aso.mo.asoplayer.main.playlist.PlaylistRepository
import aso.mo.asoplayer.main.songs.Song
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private val _songs = MutableLiveData<List<Song>>()
    val songsResults: LiveData<List<Song>> get() = _songs

    private val _albums = MutableLiveData<List<Album>>()
    val albumsResults: LiveData<List<Album>> get() = _albums

    private val _artists = MutableLiveData<List<Artist>>()
    val artistsResults: LiveData<List<Artist>> get() = _artists

    private val _genres = MutableLiveData<List<Genre>>()
    val genresResults: LiveData<List<Genre>> get() = _genres

    private val _playlists = MutableLiveData<List<Playlist>>()
    val playlistResults: LiveData<List<Playlist>> get() = _playlists

    private val _resultSize = MutableLiveData<Int>()
    val resultSize: LiveData<Int> get() = _resultSize

    val repository = SearchRepository(application)
    private val playlistRepository = PlaylistRepository(application)


    fun query(query: String, ascend: Boolean) {
        viewModelScope.launch {
            val songs = async { repository.querySongs(query, ascend) }
            val albums = async { repository.queryAlbums(query, ascend) }
            val artists = async { repository.queryArtists(query, ascend) }
            val genres = async { repository.queryGenres(query, ascend) }
            val playlists = async {
                repository.queryPlaylists(query, ascend).apply {
                    this.forEach { it.songsCount = playlistRepository.fetchSongCount(it.id) }
                }
            }

            _songs.value = songs.await()
            _albums.value = albums.await()
            _artists.value = artists.await()
            _genres.value = genres.await()
            _playlists.value = playlists.await()
            val totalSize =
                (_songs.value?.size ?: 0) + (_albums.value?.size ?: 0) + (_artists.value?.size
                    ?: 0) + (_genres.value?.size ?: 0) + (_playlists.value?.size ?: 0)
            _resultSize.postValue(totalSize)
        }
    }


    private val _searchNavigation =
        MutableLiveData<Event<SearchNavigation>>()
    val searchNavigation: LiveData<Event<SearchNavigation>> get() = _searchNavigation

    fun navigateFrmSearchFragment(navigation: SearchNavigation) =
        _searchNavigation.postValue(Event(navigation))

}