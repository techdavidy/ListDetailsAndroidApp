package com.dsv.listdetailsdemoapp.ui.list

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dsv.listdetailsdemoapp.di.FragmentKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(SingletonComponent::class)
object ListFragmentModule {
    @Provides
    @IntoMap
    @FragmentKey(ListFragment::class)
    fun provideListFragment(): Fragment =
        ListFragment { fragment ->
            ViewModelProvider(fragment)[ListViewModelImpl::class.java]
        }
}
