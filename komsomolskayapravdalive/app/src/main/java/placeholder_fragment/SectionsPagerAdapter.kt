package placeholder_fragment
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import komsomolskaya.pravda.live.R


private val TAB_TITLES = arrayOf(R.string.top, R.string.glavnoe, R.string.lenta, R.string.blog, R.string.mnenie, R.string.statii, R.string.politica,R.string.youtube)
 class SectionsPagerAdapter(manager: FragmentManager, val c:Context) : FragmentPagerAdapter(manager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    override fun getItem(position: Int): Fragment {
        return PlaceholderFragment.newInstance(position + 1)
    }

     override fun getPageTitle(position: Int): CharSequence? {
         return c.resources.getString(TAB_TITLES[position])
     }

    override fun getCount(): Int {
        return TAB_TITLES.size
    }
}