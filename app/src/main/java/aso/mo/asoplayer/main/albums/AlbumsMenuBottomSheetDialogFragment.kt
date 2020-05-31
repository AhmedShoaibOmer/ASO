package aso.mo.asoplayer.main.albums


import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import aso.mo.asoplayer.R
import aso.mo.asoplayer.main.common.view.BaseMenuBottomSheet
import aso.mo.asoplayer.main.playback.PlaybackViewModel
import aso.mo.asoplayer.main.songs.basicSongsSelection
import aso.mo.asoplayer.main.songs.basicSongsSelectionArg
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class AlbumsMenuBottomSheetDialogFragment : BaseMenuBottomSheet() {

    lateinit var album: Album
    @IdRes
    var popUpTo: Int = 0
    private val viewModel: PlaybackViewModel by sharedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        album = arguments!!.getParcelable("album")!!
        popUpTo = arguments!!.getInt("popUpTo")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_albums_menu_bottom_sheet_dialog, container, false)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.play -> play()
            R.id.playNext -> playNext()
            R.id.addToPlayList -> addToPlayList()
            R.id.share -> share()
        }
    }

    private fun play() {
        viewModel.playAlbum(album)
        findNavController().popBackStack()
    }

    private fun playNext() {
        findNavController().popBackStack()
    }

    private fun addToPlayList() {
        val selection = "$basicSongsSelection AND ${MediaStore.Audio.Media.ALBUM_ID} = ?"
        val selectionArgs = arrayOf(basicSongsSelectionArg, album.id.toString())
        val action = AlbumsMenuBottomSheetDialogFragmentDirections
            .actionAlbumsMenuBottomSheetDialogFragmentToAddSongsToPlaylistsFragment(
                selectionArgs,
                selection
            )
        val navOptions = NavOptions.Builder().setPopUpTo(popUpTo, false).build()

        findNavController().navigate(action, navOptions)
    }

    private fun share() {
        findNavController().popBackStack()
    }
}
