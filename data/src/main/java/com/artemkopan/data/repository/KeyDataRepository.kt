package com.artemkopan.data.repository

import android.content.res.Resources
import com.artemkopan.data.R
import com.artemkopan.domain.repository.KeyRepository
import javax.inject.Inject

class KeyDataRepository @Inject constructor(private val resource: Resources) : KeyRepository {

    // todo implement more secure store
    override fun getApiKey(): String = resource.getString(R.string.api_key)

}