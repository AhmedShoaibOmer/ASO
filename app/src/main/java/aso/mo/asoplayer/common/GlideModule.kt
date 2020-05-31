// Copyright (c) 2019 . Wilberforce Uwadiegwu. All Rights Reserved.

package aso.mo.asoplayer.common

import android.content.Context
import aso.mo.asoplayer.main.albums.Album
import aso.mo.asoplayer.main.artists.Artist
import aso.mo.asoplayer.main.common.network.image.album.AlbumModelLoaderFactory
import aso.mo.asoplayer.main.common.network.image.artist.ArtistModelLoaderFactory
import aso.mo.asoplayer.main.common.network.image.genre.GenreModelLoaderFactory
import aso.mo.asoplayer.main.common.network.image.mediaitemdata.MediaItemDataLoaderFactory
import aso.mo.asoplayer.main.common.network.image.playlist.PlaylistModelLoaderFactory
import aso.mo.asoplayer.main.common.network.image.recentlyplayed.RecentlyPlayedLoaderFactory
import aso.mo.asoplayer.main.explore.RecentlyPlayed
import aso.mo.asoplayer.main.genres.Genre
import aso.mo.asoplayer.main.playback.MediaItemData
import aso.mo.asoplayer.main.playlist.Playlist
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import java.io.InputStream


@GlideModule
class MyAppGlideModule : AppGlideModule() {
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry.prepend(Album::class.java, InputStream::class.java, AlbumModelLoaderFactory())
        registry.prepend(Artist::class.java, InputStream::class.java, ArtistModelLoaderFactory())
        registry.prepend(
            Playlist::class.java,
            InputStream::class.java,
            PlaylistModelLoaderFactory()
        )
        registry.prepend(Genre::class.java, InputStream::class.java, GenreModelLoaderFactory())
        registry.prepend(
            MediaItemData::class.java,
            InputStream::class.java,
            MediaItemDataLoaderFactory()
        )
        registry.prepend(
            RecentlyPlayed::class.java,
            InputStream::class.java,
            RecentlyPlayedLoaderFactory()
        )
    }
}