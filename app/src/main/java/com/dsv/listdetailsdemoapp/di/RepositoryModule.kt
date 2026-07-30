package com.dsv.listdetailsdemoapp.di

import com.dsv.listdetailsdemoapp.data.repository.PostRepository
import com.dsv.listdetailsdemoapp.data.repository.PostRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindPostRepository(impl: PostRepositoryImpl): PostRepository
}
