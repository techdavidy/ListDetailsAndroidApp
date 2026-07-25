package com.dsv.listdetailsdemoapp.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.dsv.listdetailsdemoapp.ui.list.ListFragment
import com.dsv.listdetailsdemoapp.ui.list.ListViewModelImpl
import javax.inject.Inject

class AppFragmentFactory @Inject constructor() : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            ListFragment::class.java.name -> ListFragment { fragment ->
                ViewModelProvider(fragment)[ListViewModelImpl::class.java]
            }
            else -> super.instantiate(classLoader, className)
        }
    }
}