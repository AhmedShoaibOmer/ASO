package aso.mo.asoplayer.main.common.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import aso.mo.asoplayer.R
import aso.mo.asoplayer.main.common.callbacks.OnItemClickListener
import aso.mo.asoplayer.main.common.data.Model
import aso.mo.asoplayer.main.playback.PlaybackViewModel
import kotlinx.android.synthetic.main.fragment_base_player.*
import kotlinx.android.synthetic.main.fragment_explore.navigationIcon
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

abstract class BasePlayerFragment<T : Model> : BaseFragment(), View.OnClickListener,
    OnItemClickListener {
    val playbackViewModel: PlaybackViewModel by sharedViewModel()
    var items = emptyList<T>()
    lateinit var viewModel: BaseMediaStoreViewModel<T>
    @get: IdRes
    abstract var navigationFragmentId: Int
    @get: PluralsRes
    open var numberOfDataRes: Int = -1
    @get: StringRes
    open var titleRes: Int = -1
    @get: LayoutRes
    abstract var itemLayoutId: Int
    abstract var viewModelVariableId: Int
    open var adapterItemAnimSet = setOf(R.anim.up_from_bottom, R.anim.down_from_top)
    open var longClickItems = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_base_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeViewModel()
        navigationIcon.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                navigationFragmentId
            )
        )
        playButton.setOnClickListener(this)
    }

    private fun observeViewModel() {
        if (items.isEmpty()) {
            viewModel.init()
            viewModel.items.observe(viewLifecycleOwner, Observer(::updateViews))
        } else {
            viewModel.overrideCurrentItems(items)
        }
    }


    @Suppress("UNCHECKED_CAST")
    private fun updateViews(items: List<T>) {
        this.items = items
        (dataRV.adapter as BaseAdapter<T>).updateItems(items)
        dataNum.text = resources.getQuantityString(numberOfDataRes, items.count(), items.count())
    }

    private fun setupView() {
        if (titleRes > -1) {
            sectionTitle.setText(titleRes)
        }
        val adapter =
            BaseAdapter(
                items, activity!!, itemLayoutId, viewModelVariableId, this,
                adapterItemAnimSet, longClickItems
            )
        dataRV.adapter = adapter
        dataRV.layoutManager = layoutManager()
    }

    open fun layoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(activity)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.playButton -> playbackViewModel.playAll()
        }
    }

    // Derived classed will be forced to implemebt this
    abstract override fun onItemClick(position: Int, sharableView: View?)

}