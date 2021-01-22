package novosti.rossii.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import novosti.rossii.R

private val TAB_TITLES = arrayOf(R.string.top, R.string.glavnoe, R.string.lenta, R.string.blog, R.string.mnenie, R.string.statii, R.string.politica,R.string.youtube)

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return PlaceholderFragment.newInstance(position + 1)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 8
    }
}