import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
actual class AudioPlayer @androidx.annotation.OptIn(UnstableApi::class) constructor
    (context: Context) {

    private val mediaPlayer: ExoPlayer = ExoPlayer.Builder(context).build()

    init {
        mediaPlayer.setMediaSource(
            DefaultMediaSourceFactory(context)
                .createMediaSource(
                    MediaItem.fromUri("android.resource://${context.packageName}/raw/main_back_music")
                )
        )
        mediaPlayer.prepare()
    }

    actual fun playSound(id: Int) {
        println("played sound")
        mediaPlayer.play()
    }

    actual fun release() {
        mediaPlayer.pause()
    }
}
