package com.dsv.listdetailsdemoapp.di

import androidx.fragment.app.Fragment
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * Map key used to register a [Fragment] provider into the multibound map consumed by
 * `AppFragmentFactory`. Retention must be runtime for Dagger to read the key.
 */
@MapKey
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class FragmentKey(
    val value: KClass<out Fragment>,
)
