package com.artemkopan.data.repository

import android.content.res.Resources
import android.os.Build
import com.artemkopan.domain.repository.ResourceRepository
import java.util.*
import javax.inject.Inject

class AndroidResourceRepository @Inject constructor(private val resource: Resources)
    : ResourceRepository {

    override fun getCurrentLocale(): Locale? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            resource.configuration.locales?.let {
                if (!it.isEmpty) {
                    return it[0]
                }
            }
        } else {
            @Suppress("DEPRECATION")
            return resource.configuration.locale
        }
        return null
    }

}