package aso.mo.asoplayer.onBoarding

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import aso.mo.asoplayer.R
import aso.mo.asoplayer.common.GlideApp
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.android.synthetic.main.item_on_boarding.view.*

class OnBoardingAdapter(private val context: Context, private val boards: Array<Board>) :
    PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layout =
            LayoutInflater.from(context).inflate(R.layout.item_on_boarding, container, false)

        val board = boards[position]
        GlideApp.with(context)
            .load(board.image)
            .transform(MultiTransformation(CenterCrop(), RoundedCorners(15)))
            .into(layout.onBoardingImg)

        layout.onBoardingTitle.text = context.getString(board.title)
        layout.onBoardingDescription.text = context.getString(board.description)

        container.addView(layout)
        return layout
    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View)
    }

    override fun getCount(): Int {
        return boards.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.getString(boards[position].title)
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

}


