package com.bonnamy.dashboard.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.bonnamy.dashboard.bo.Classement
import com.bonnamy.dashboard.fragments.ClassementFragment

class ClassementAdapter(fm: FragmentManager, private var clsmt: List<Classement>) :
    FragmentStatePagerAdapter(fm)
{
    fun updateClsmt(newClsmt: List<Classement>) {
        this.clsmt = newClsmt
        this.notifyDataSetChanged()
    }

    override fun getPageTitle(position: Int): CharSequence = clsmt[position].date

    override fun getCount(): Int = clsmt.size

    override fun getItem(position: Int): Fragment = ClassementFragment.newInstance(position, clsmt[position])
}