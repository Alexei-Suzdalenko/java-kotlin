package com.example.simpleactivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_tab_layout.*

class TabLayout : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_layout)

        setSupportActionBar(toolbarCustom)

        val oneFragment = OneFragment()
        val twoFragment = TwoFragment()
        val thrFragment = ThreeFragment()

        tabLayot123.setupWithViewPager(viewPager)
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        tabLayot123.getTabAt(0)?.setIcon(R.drawable.ic_baseline_add_24 )

        viewPagerAdapter.addFragment(oneFragment, "one")
        viewPagerAdapter.addFragment(twoFragment, "two")
        viewPagerAdapter.addFragment(thrFragment, "three")

        viewPager.adapter = viewPagerAdapter
    }

    private class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        val fragments: MutableList<Fragment>    = ArrayList()
        val fragmentTitles: MutableList<String> = ArrayList()

        fun addFragment(fg: Fragment, title: String){
            fragments.add(fg)
            fragmentTitles += title
        }

        override fun getCount(): Int {
            return fragments.size
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return fragmentTitles[position]
        }
    }



}


