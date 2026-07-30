package com.dsv.listdetailsdemoapp.ui.detail

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
object DetailFragmentModule {
    @Provides
    @IntoMap
    @FragmentKey(DetailFragment::class)
    fun provideDetailFragment(): Fragment =
        DetailFragment { fragment ->
            ViewModelProvider(fragment)[DetailViewModelImpl::class.java]
        }
}
