expect  class AudioPlayer {
    /** Plays a sound by id (see soundResList) */
    fun playSound(id: Int)
    fun release()
}

val soundResList = listOf(
    "drawable/main_back_music.mp3",
)