package aso.mo.asoplayer.main.search

import android.app.Application
import android.provider.MediaStore
import androidx.annotation.WorkerThread
import aso.mo.asoplayer.main.albums.Album
import aso.mo.asoplayer.main.albums.baseAlbumProjection
import aso.mo.asoplayer.main.albums.baseAlbumUri
import aso.mo.asoplayer.main.artists.Artist
import aso.mo.asoplayer.main.artists.baseArtistProjection
import aso.mo.asoplayer.main.artists.baseArtistUri
import aso.mo.asoplayer.main.common.data.BaseMediaStoreRepository
import aso.mo.asoplayer.main.genres.Genre
import aso.mo.asoplayer.main.genres.baseGenreProjection
import aso.mo.asoplayer.main.genres.baseGenreUri
import aso.mo.asoplayer.main.playlist.Playlist
import aso.mo.asoplayer.main.playlist.basePlaylistProjection
import aso.mo.asoplayer.main.playlist.basePlaylistUri
import aso.mo.asoplayer.main.songs.*


class SearchRepository(application: Application) : BaseMediaStoreRepository(application) {

    @WorkerThread
    fun querySongs(query: String, ascend: Boolean): List<Song> {
        val selection = "$basicSongsSelection AND ${MediaStore.Audio.Media.TITLE} LIKE ?"
        val selectionArgs = arrayOf(basicSongsSelectionArg, "%$query%")
        val order =
            "${MediaStore.Audio.Media.TITLE} COLLATE NOCASE ${if (ascend) "ASC" else "DESC"}"
        return loadData(baseSongUri, baseSongsProjection, selection, selectionArgs, order, ::Song)
    }

    @WorkerThread
    fun queryAlbums(query: String, ascend: Boolean): List<Album> {
        val selection = "${MediaStore.Audio.Media.ALBUM} LIKE ?"
        val selectionArgs = arrayOf("%$query%")
        val order =
            "${MediaStore.Audio.Media.ALBUM} COLLATE NOCASE ${if (ascend) "ASC" else "DESC"}"
        return loadData(baseAlbumUri, baseAlbumProjection, selection, selectionArgs, order, ::Album)
    }

    @WorkerThread
    fun queryArtists(query: String, ascend: Boolean): List<Artist> {
        val selection = "${MediaStore.Audio.Media.ARTIST} LIKE ?"
        val selectionArgs = arrayOf("%$query%")
        val order =
            "${MediaStore.Audio.Media.ARTIST} COLLATE NOCASE ${if (ascend) "ASC" else "DESC"}"
        return loadData(
            baseArtistUri,
            baseArtistProjection,
            selection,
            selectionArgs,
            order,
            ::Artist
        )
    }

    @WorkerThread
    fun queryGenres(query: String, ascend: Boolean): List<Genre> {
        val selection = "${MediaStore.Audio.Genres.NAME} LIKE ?"
        val selectionArgs = arrayOf("%$query%")
        val order =
            "${MediaStore.Audio.Genres.NAME} COLLATE NOCASE ${if (ascend) "ASC" else "DESC"}"
        return loadData(baseGenreUri, baseGenreProjection, selection, selectionArgs, order, ::Genre)
    }


    @WorkerThread
    fun queryPlaylists(query: String, ascend: Boolean): List<Playlist> {
        val selection = "${MediaStore.Audio.Playlists.NAME} LIKE ?"
        val selectionArgs = arrayOf("%$query%")
        val order =
            "${MediaStore.Audio.Playlists.NAME} COLLATE NOCASE ${if (ascend) "ASC" else "DESC"}"
        return loadData(
            basePlaylistUri,
            basePlaylistProjection,
            selection,
            selectionArgs,
            order,
            ::Playlist
        )
    }

}