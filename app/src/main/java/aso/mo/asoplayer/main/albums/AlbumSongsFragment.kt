package aso.mo.asoplayer.main.albums


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import aso.mo.asoplayer.BR
import aso.mo.asoplayer.R
import aso.mo.asoplayer.databinding.FragmentAlbumSongsBinding
import aso.mo.asoplayer.main.common.callbacks.OnItemClickListener
import aso.mo.asoplayer.main.common.view.BaseAdapter
import aso.mo.asoplayer.main.common.view.BaseFragment
import aso.mo.asoplayer.main.playback.PlaybackViewModel
import aso.mo.asoplayer.main.songs.Song
import kotlinx.android.synthetic.main.fragment_album_songs.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AlbumSongsFragment : BaseFragment(), OnItemClickListener, View.OnClickListener {

    private lateinit var viewModel: AlbumSongsViewModel
    private val playbackViewModel: PlaybackViewModel by sharedViewModel()
    private lateinit var album: Album
    private var items = emptyList<Song>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        album = arguments!!.getParcelable("album")!!
        viewModel = ViewModelProviders.of(this)[AlbumSongsViewModel::class.java]
        viewModel.init(album.id)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAlbumSongsBinding.inflate(inflater, container, false)
        binding.album = album
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setTransitionName(albumArt, arguments!!.getString("transitionName"))
        setupViews()
        observeViewModel()
        sectionBackButton.setOnClickListener { findNavController().popBackStack() }
    }

    private fun observeViewModel() {
        viewModel.items.observe(viewLifecycleOwner, Observer(this::updateViews))
    }

    @Suppress("UNCHECKED_CAST")
    private fun updateViews(items: List<Song>) {
        if (items.isEmpty()) {
            findNavController().popBackStack()
            return
        }
        this.items = items
        albumSongsDuration.text = getSongsTotalTime(items)
        (albumSongsRV.adapter as BaseAdapter<Song>).updateItems(items)
    }

    private fun setupViews() {
        val adapter = BaseAdapter(
            items,
            activity!!,
            R.layout.item_album_song,
            BR.song,
            itemClickListener = this
        )
        albumSongsRV.adapter = adapter
        albumSongsRV.layoutManager = LinearLayoutManager(activity!!)
        moreOptions.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.moreOptions -> findNavController().navigate(
                AlbumSongsFragmentDirections
                    .actionAlbumSongsFragmentToAlbumsMenuBottomSheetDialogFragment(album = album)
            )
        }
    }

    override fun onItemClick(position: Int, sharableView: View?) {
        playbackViewModel.playAlbum(album, items[position].id.toString())
    }
}
