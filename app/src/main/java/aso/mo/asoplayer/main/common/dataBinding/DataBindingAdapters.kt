package aso.mo.asoplayer.main.common.dataBinding

import android.support.v4.media.session.PlaybackStateCompat
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import aso.mo.asoplayer.R
import aso.mo.asoplayer.common.GlideApp
import aso.mo.asoplayer.main.albums.Album
import aso.mo.asoplayer.main.artists.Artist
import aso.mo.asoplayer.main.common.image.CircularTransparentCenter
import aso.mo.asoplayer.main.explore.RecentlyPlayed
import aso.mo.asoplayer.main.genres.Genre
import aso.mo.asoplayer.main.playback.MediaItemData
import aso.mo.asoplayer.main.playlist.Playlist
import aso.mo.asoplayer.main.songs.Song
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners


object DataBindingAdapters {

    @JvmStatic
    val centerCrop = CenterCrop()
    @JvmStatic
    val circleCrop = CircleCrop()

    @BindingAdapter("android:src")
    @JvmStatic
    fun setAlbumCover(view: ImageView, song: Song?) {
        GlideApp.with(view)
            .load(song?.album)
            .transform(
                MultiTransformation(centerCrop, circleCrop)
            )
            .placeholder(R.drawable.thumb_circular_default)
            .into(view)
    }

    @BindingAdapter("android:src")
    @JvmStatic
    fun setAlbumCover(view: ImageView, mediaItem: MediaItemData?) {
        GlideApp.with(view)
            .load(mediaItem)
            .transform(
                MultiTransformation(centerCrop, circleCrop)
            )
            .placeholder(R.drawable.thumb_circular_default)
            .into(view)
    }

    @BindingAdapter("android:src")
    @JvmStatic
    fun setAlbumCover(view: ImageView, item: RecentlyPlayed?) {
        GlideApp.with(view)
            .load(item)
            .transform(
                MultiTransformation(centerCrop, circleCrop)
            )
            .placeholder(R.drawable.thumb_circular_default)
            .into(view)
    }


    @BindingAdapter("mediaSrc")
    @JvmStatic
    fun setAlbumCoverCompat(view: ImageView, item: MediaItemData?) {
        GlideApp.with(view)
            .load(item)
            .transform(
                MultiTransformation(centerCrop, circleCrop, CircularTransparentCenter(.3F))
            )
            .placeholder(R.drawable.thumb_circular_default_hollow)
            .into(view)
    }

    @BindingAdapter("android:src")
    @JvmStatic
    fun setArtistAvatar(view: ImageView, artist: Artist) {
        GlideApp.with(view)
            .load(artist)
            .transform(
                MultiTransformation(centerCrop, circleCrop)
            )
            .placeholder(R.drawable.thumb_circular_default)
            .into(view)
    }

    @BindingAdapter("android:src")
    @JvmStatic
    fun setAlbumCover(view: ImageView, album: Album) {
        GlideApp.with(view)
            .load(album)
            .transform(
                MultiTransformation(centerCrop, RoundedCorners(10))
            )
            .placeholder(R.drawable.thumb_default)
            .into(view)
    }

    @BindingAdapter("artistSrc")
    @JvmStatic
    fun setAlbumSrc(view: ImageView, album: Album) {
        GlideApp.with(view)
            .load(album)
            .transform(
                MultiTransformation(centerCrop, RoundedCorners(10))
            )
            .placeholder(R.drawable.thumb_default_short)
            .into(view)
    }

    @BindingAdapter("android:src")
    @JvmStatic
    fun setPlaylistCover(view: ImageView, playlist: Playlist) {
        GlideApp.with(view)
            .load(playlist.modForViewWidth(view.measuredWidth))
            .transform(
                MultiTransformation(centerCrop, circleCrop)
            )
            .placeholder(R.drawable.thumb_circular_default)
            .into(view)
    }

    @BindingAdapter("playlistSrc")
    @JvmStatic
    fun setPlaylistSrc(view: ImageView, playlist: Playlist) {
        GlideApp.with(view)
            .load(playlist.modForViewWidth(view.measuredWidth))
            .transform(
                MultiTransformation(centerCrop, RoundedCorners(10))
            )
            .placeholder(R.drawable.thumb_default_short)
            .into(view)
    }

    @BindingAdapter("genreSrc")
    @JvmStatic
    fun setGenreSrc(view: ImageView, genre: Genre) {
        GlideApp.with(view)
            .load(genre)
            .transform(
                MultiTransformation(centerCrop, RoundedCorners(10))
            )
            .placeholder(R.drawable.thumb_default_short)
            .into(view)
    }

    @BindingAdapter("repeatSrc")
    @JvmStatic
    fun setRepeatModeSrc(view: ImageView, repeat: Int?) {
        val src = when (repeat) {
            PlaybackStateCompat.REPEAT_MODE_ALL -> R.drawable.ic_repeat_all
            PlaybackStateCompat.REPEAT_MODE_ONE -> R.drawable.ic_repeat_once
            else -> R.drawable.ic_repeat_none
        }
        view.setImageResource(src)
    }

    @BindingAdapter("shuffleSrc")
    @JvmStatic
    fun setShuffleModeSrc(view: ImageView, shuffle: Int?) {
        val src = if (shuffle == PlaybackStateCompat.SHUFFLE_MODE_NONE) {
            R.drawable.ic_shuffle_off
        } else {
            R.drawable.ic_shuffle_on
        }
        view.setImageResource(src)
    }

    @BindingAdapter("android:src")
    @JvmStatic
    fun setImageResource(imageView: ImageView, resource: Int) {
        imageView.setImageResource(resource)
    }

    @BindingAdapter("android:background")
    @JvmStatic
    fun setBackgroundResource(view: View, @DrawableRes resource: Int) {
        view.setBackgroundResource(resource)
    }

    @BindingAdapter("enabled")
    @JvmStatic
    fun setEnabled(view: View, enabled: Boolean) {
        view.isEnabled = enabled
    }

}