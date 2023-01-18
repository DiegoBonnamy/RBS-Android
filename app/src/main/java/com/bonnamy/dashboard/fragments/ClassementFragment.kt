package com.bonnamy.dashboard.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bonnamy.dashboard.R
import com.bonnamy.dashboard.adapters.AthletePositionAdapter
import com.bonnamy.dashboard.bo.Classement
import com.bonnamy.dashboard.databinding.FragmentClassementBinding
import com.bonnamy.dashboard.viewmodels.AthletePositionViewModel

private const val ARG_POSITION = "position"
private const val ARG_CLSMT = "clsmt"

class ClassementFragment : Fragment() {

    private lateinit var fragmentClassementBinding: FragmentClassementBinding
    private lateinit var clsmtViewModel: AthletePositionViewModel

    private var position: Int? = null
    private var clsmt: Classement? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            clsmt = it.getParcelable(ARG_CLSMT)
        }

        fragmentClassementBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_classement, container, false)
        fragmentClassementBinding.classementRecyclerView.layoutManager = LinearLayoutManager(this.context)

        clsmtViewModel = ViewModelProvider(this).get(AthletePositionViewModel::class.java)

        val clsmtAdapter = AthletePositionAdapter()
        fragmentClassementBinding.classementRecyclerView.adapter = clsmtAdapter

        clsmtViewModel.positionData.observe(viewLifecycleOwner) {
            clsmtAdapter.submitList(it)
        }
        clsmtViewModel.loadPositions(clsmt)

        return fragmentClassementBinding.root
    }

    companion object {
        fun newInstance(position: Int, clsmt: Classement) =
            ClassementFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_POSITION, position)
                    putParcelable(ARG_CLSMT, clsmt)
                }
            }
    }
}