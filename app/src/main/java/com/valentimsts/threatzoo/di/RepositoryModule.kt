package com.valentimsts.threatzoo.di

import android.content.ContentResolver
import com.valentimsts.threatzoo.data.repository.ContactRepositoryImpl
import com.valentimsts.threatzoo.data.repository.MessageRepositoryImpl
import com.valentimsts.threatzoo.data.repository.NotificationRepositoryImpl
import com.valentimsts.threatzoo.domain.repository.ContactRepository
import com.valentimsts.threatzoo.domain.repository.MessageRepository
import com.valentimsts.threatzoo.domain.repository.NotificationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import javax.inject.Singleton
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMessageRepository(contentResolver: ContentResolver): MessageRepository {
        return MessageRepositoryImpl(contentResolver)
    }

    @Provides
    @Singleton
    fun provideContactRepository(contentResolver: ContentResolver): ContactRepository {
        return ContactRepositoryImpl(contentResolver)
    }


    @Provides
    @Singleton
    fun provideOtpRepository(): NotificationRepository {
        return NotificationRepositoryImpl()
    }

    /**

    @Binds
    @Singleton
    abstract fun bindMessageRepository(impl: MessageRepositoryImpl): MessageRepository

    @Binds
    @Singleton
    abstract fun bindOtpRepository(impl: OtpRepositoryImpl): OtpRepository

     **/
}
