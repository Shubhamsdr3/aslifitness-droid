package com.aslifitness.fitracker.filedownloader

import android.app.Notification
import androidx.media3.common.util.Util
import androidx.media3.exoplayer.offline.Download
import androidx.media3.exoplayer.offline.DownloadManager
import androidx.media3.exoplayer.offline.DownloadService
import androidx.media3.exoplayer.scheduler.PlatformScheduler
import androidx.media3.exoplayer.scheduler.Scheduler
import com.aslifitness.fitracker.R
import com.aslifitness.fitracker.filedownloader.DownloadUtil.DOWNLOAD_NOTIFICATION_CHANNEL_ID

private const val JOB_ID = 1223
private const val FOREGROUND_NOTIFICATION_ID = 1222

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
class MediaDownloadService : DownloadService(
    FOREGROUND_NOTIFICATION_ID,
    DEFAULT_FOREGROUND_NOTIFICATION_UPDATE_INTERVAL,
    DOWNLOAD_NOTIFICATION_CHANNEL_ID,
    R.string.exo_download_notification_channel_name, 0) {

    override fun getDownloadManager(): DownloadManager {
        return DownloadUtil.getDownloadManager(context = this)
    }

    override fun getScheduler(): Scheduler? {
        return if(Util.SDK_INT >= 21) PlatformScheduler(this, JOB_ID) else null
    }

    override fun getForegroundNotification(downloads: MutableList<Download>, notMetRequirements: Int): Notification {
        return DownloadUtil.getDownloadNotificationHelper(this)
            .buildProgressNotification(
                this,
                R.drawable.ic_download_24,
                null,
                null,
                downloads,
                notMetRequirements
            )
    }
}