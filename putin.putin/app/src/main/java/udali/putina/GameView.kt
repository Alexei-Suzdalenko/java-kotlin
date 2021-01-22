package udali.putina
import android.content.Context
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import putin.putin.GameThread
import java.lang.Exception

class GameView(c: Context) : SurfaceView(c), SurfaceHolder.Callback {
     lateinit var gameThread : GameThread
     val surfaceHolder: SurfaceHolder = holder

     fun initView() {
        surfaceHolder.addCallback(this)
        isFocusable = true
    }


    override fun surfaceCreated  (holder: SurfaceHolder?) {
        initView()
        if( !gameThread.isRunning()){
            gameThread = GameThread(surfaceHolder)
            gameThread.start()
        } else gameThread.start()
    }


    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        if(gameThread.isRunnig()){
            gameThread.setIsRunning(false)
            var retry = true
            while(retry){
                try{
                    gameThread.join(); retry = false;
                } catch (e: Exception){ Log.d("tag", "" + e.message)}
            }
        }
    }

    override fun surfaceChanged  (holder: SurfaceHolder?, format: Int, width: Int, height: Int) {}
}