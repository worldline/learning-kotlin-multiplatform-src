
import android.app.Application
import android.content.Context

class QuizApp : Application() {
    init {
        app = this
    }
    companion object {
        private lateinit var app: QuizApp
        fun context(): Context = app.applicationContext
    }
} 