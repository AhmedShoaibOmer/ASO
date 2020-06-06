
package aso.mo.asoplayer.main.playback


import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.os.Handler
import android.support.v4.media.session.PlaybackStateCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.addCallback
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionLayout.TransitionListener
import androidx.core.view.children
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import aso.mo.asoplayer.R
import aso.mo.asoplayer.databinding.FragmentPlaybackBinding
import aso.mo.asoplayer.databinding.FragmentPlaybackContentBinding
import aso.mo.asoplayer.main.common.callbacks.OnSeekBarChangeListener
import aso.mo.asoplayer.main.common.view.BaseFragment
import aso.mo.asoplayer.main.common.view.awaitTransitionComplete
import kotlinx.android.synthetic.main.fragment_playback_content.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.concurrent.TimeUnit


class PlaybackFragment : BaseFragment(), View.OnClickListener {

    private var userTouchingSeekBar = false
    private val viewModel: PlaybackViewModel by sharedViewModel()
    private lateinit var rotationAnimSet: AnimatorSet
    private val handler = Handler()
    private lateinit var contentBinding: FragmentPlaybackContentBinding
    private var isCurrentTracksFragmentVisible = false

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        requireActivity().onBackPressedDispatcher.addCallback(this) {

            if (isCurrentTracksFragmentVisible) showCurrentTracks()
            when (contentBinding.playbackContent.currentState) {
                R.id.end -> {
                    transitionFromEndToStart()
                }
                R.id.lyricsSet -> {
                    contentBinding.playbackContent.transitionToState(R.id.end)
                    transitionFromEndToStart()
                }
                R.id.loadingLyricsSet -> {
                    contentBinding.playbackContent.transitionToState(R.id.end)
                    transitionFromEndToStart()
                }
                else -> {
                    isEnabled = false
                    requireActivity().onBackPressed()
                }
            }
            /*   sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)*/
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentPlaybackBinding.inflate(inflater, container, false).let {
        contentBinding = it.playerContainer.binding
        contentBinding.viewModel = viewModel
        contentBinding.lifecycleOwner = this
        return it.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeViewData()
    }

    private fun observeViewData() {
        viewModel.mediaPosition.observe(viewLifecycleOwner, Observer {
            if (!userTouchingSeekBar) {
                contentBinding.playbackSeekBar.progress = it.toInt()
                contentBinding.playbackSeekBarSmall.progress = it.toInt()
            }
        })

        viewModel.currentItem.observe(viewLifecycleOwner, Observer {
            contentBinding.playbackSeekBar.max = it?.duration?.toInt() ?: 0
            contentBinding.playbackSeekBarSmall.max = it?.duration?.toInt() ?: 0
        })

        viewModel.playbackState.observe(viewLifecycleOwner, Observer { updateState(it) })
    }


    private fun setupView() {
        rotationAnimSet =
            AnimatorInflater.loadAnimator(activity, R.animator.album_art_rotation) as AnimatorSet
        rotationAnimSet.setTarget(albumArt)
        contentBinding.songTitle.children.forEach { (it as TextView).isSelected = true }
        contentBinding.sectionBackButton.setOnClickListener(this)
        contentBinding.lyricsButton.setOnClickListener(this)
        contentBinding.closeButton.setOnClickListener(this)
        contentBinding.moreOptions.setOnClickListener(this)
        contentBinding.playingTracks.setOnClickListener(this)
        contentBinding.albumArt.setOnClickListener(this)
        contentBinding.playbackSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener)
        contentBinding.playbackSeekBarSmall.setOnSeekBarChangeListener(onSeekBarChangeListener)
    }

    private fun updateState(state: PlaybackStateCompat) {
        if (state.isPlaying) {
            if (playPauseButton.currentView != pauseButton) playPauseButton.showNext()
            if (!rotationAnimSet.isStarted) rotationAnimSet.start() else rotationAnimSet.resume()
        } else {
            rotationAnimSet.pause()
            if (playPauseButton.currentView != playButton) playPauseButton.showPrevious()
        }
    }

    @ExperimentalCoroutinesApi
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.sectionBackButton -> {
                if (isCurrentTracksFragmentVisible) showCurrentTracks()
                transitionFromEndToStart()
            }
            R.id.lyricsButton -> showFindingLyrics()
            R.id.closeButton -> closeLyrics()
            R.id.moreOptions -> showMenuBottomSheet()
            R.id.playingTracks -> showCurrentTracks()
            R.id.albumArt -> {
                when (contentBinding.playbackContent.currentState) {
                    R.id.start -> {
                        transitionFromStartToEnd()
                    }
                    R.id.medium -> {
                        contentBinding.playbackContent.setTransition(R.id.mediumToFullScreen)
                        contentBinding.playbackContent.transitionToEnd()
                    }
                }
            }
        }
    }

    private fun showCurrentTracks() {
        if ((playingTracks.drawable as Animatable).isRunning) {
            return
        }

        val fragment = childFragmentManager.findFragmentByTag("CurrentSongsFragment")
        val animDrawable = AnimatedVectorDrawableCompat.create(
            activity!!,
            if (fragment != null) R.drawable.anim_close_to_playlist_current else R.drawable.anim_playlist_current_to_close
        )
        contentBinding.playingTracks.setImageDrawable(animDrawable)
        (contentBinding.playingTracks.drawable as Animatable).start()
        if (fragment == null) {
            childFragmentManager.beginTransaction()
                .replace(R.id.currentSongsContainer, CurrentSongsFragment(), "CurrentSongsFragment")
                .commit()
            isCurrentTracksFragmentVisible = true
        } else {
            childFragmentManager.beginTransaction().remove(fragment).commit()
            isCurrentTracksFragmentVisible = false
        }

    }

    private fun showMenuBottomSheet() {
        val mediaItem = viewModel.currentItem.value ?: return
        val action =
            PlaybackFragmentDirections.actionPlaybackFragmentToSongsMenuBottomSheetDialogFragment(
                mediaItem.id.toLong()
            )
        findNavController().navigate(action)
    }

    private fun closeLyrics() {
        contentBinding.playbackContent.setTransition(R.id.lyricsToEnd)
        contentBinding.playbackContent.transitionToEnd()
    }

    private fun showFindingLyrics() {
        if (hasLyrics()) {
            showFoundLyrics()
            return
        }
        contentBinding.playbackContent.setTransition(R.id.endToLoadingLyrics)
        contentBinding.playbackContent.transitionToEnd()
        Handler().postDelayed({
            showFoundLyrics(
                getString(R.string.dummyLyrics),
                getString(R.string.dummyLyricsSource)
            )
        }, TimeUnit.SECONDS.toMillis(4))
    }

    private fun showFoundLyrics(lyrics: String? = null, source: String? = null) {
        val lyricsIsEmpty = lyrics.isNullOrEmpty()
        if (!lyricsIsEmpty) {
            lyricsText.text = lyrics
            lyricsSource.text = source
        }
        if (contentBinding.playbackContent.currentState == R.id.loadingLyricsSet) {
            contentBinding.playbackContent.setTransition(R.id.loadingLyricsToLyrics)
            contentBinding.playbackContent.transitionToEnd()
        } else {
            contentBinding.playbackContent.setTransition(R.id.endToLyrics)
            contentBinding.playbackContent.transitionToEnd()
        }
    }


    private fun hasLyrics() = !contentBinding.lyricsText.text.isNullOrEmpty()

    private val onSeekBarChangeListener = object : OnSeekBarChangeListener {

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
            userTouchingSeekBar = true
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            viewModel.seek(seekBar?.progress?.toLong() ?: 0)
            // Let's delay the updating of the userTouchingSeekBar to ensure the seek has taken effect before
            // continuing to update the progress bar
            handler.postDelayed({ userTouchingSeekBar = false }, 300)
        }

    }

    private fun transitionFromEndToStart() {
        contentBinding.playbackContent.setTransition(R.id.mediumToFullScreen)
        contentBinding.playbackContent.setTransitionListener(object : TransitionListener {
            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
            }

            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                contentBinding.playbackContent.setTransition(R.id.smallToMedium)
                contentBinding.playbackContent.transitionToStart()
            }

        })
        contentBinding.playbackContent.transitionToStart()

    }

    @ExperimentalCoroutinesApi
    private fun transitionFromStartToEnd() {
        contentBinding.playbackContent.setTransition(R.id.smallToMedium)
        contentBinding.playbackContent.setTransitionListener(object : TransitionListener {
            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
            }

            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                contentBinding.playbackContent.setTransition(R.id.mediumToFullScreen)
                contentBinding.playbackContent.transitionToEnd()
            }

        })
        contentBinding.playbackContent.transitionToEnd()

    }

    override fun onDestroyView() {
        rotationAnimSet.cancel()
        super.onDestroyView()
    }


}
