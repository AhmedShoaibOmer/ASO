package aso.mo.asoplayer.main.explore

import android.app.Application
import androidx.lifecycle.LiveData
import aso.mo.asoplayer.main.albums.AlbumsViewModel


class ExploreViewModel(application: Application) : AlbumsViewModel(application) {
    private val recentlyPlayedRepository: RecentlyPlayedRepository
    val recentlyPlayed: LiveData<List<RecentlyPlayed>>

    override var sortOrder: String? = "RANDOM() LIMIT 5"

    init {
        val recentDao = RecentlyPlayedRoomDatabase.getDatabase(application).recentDao()
        recentlyPlayedRepository = RecentlyPlayedRepository(recentDao)
        recentlyPlayed = recentlyPlayedRepository.recentlyPlayed
    }
}
