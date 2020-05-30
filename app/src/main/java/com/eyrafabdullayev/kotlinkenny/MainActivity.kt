package com.eyrafabdullayev.kotlinkenny

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    var time : Long = 20000
    var score : Int = 0
    var runnable : Runnable = Runnable {  }
    var handler : Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //start the game
        timeInMessage()
        start()
    }

    fun start(){
        score = 0
        scoreView.text = "Score: ${score}"
        countDownTimer()
        showRandomObject()
    }

    fun countDownTimer(){
        object : CountDownTimer(time, 1000) {
            override fun onFinish() {
                timeView.text = "Time off!"
                //when game has finished
                timeOff();
                //send message to user
                timeOffMessage()
                //ask for restart
                askForRestart()
            }

            override fun onTick(millisUntilFinished: Long) {
                timeView.text = "Time: ${millisUntilFinished / 1000}"
            }

        }.start();
    }

    fun increaseScore(view : View){
        score = score + 1
        scoreView.text = "Score: ${score}"
    }

    fun showRandomObject(){
        runnable = object : Runnable {
            override fun run() {
                //get list of ImageViews
                var list = getListOfImageViews()
                //get random number between 0 and 10
                var id = getRundomNumber(0,9)
                //in first we should hide all images
                hideImageViews()
                //set image visibility
                setVisibility(list.get(id))

                handler.postDelayed(runnable,500)
            }

        }

        handler.post(runnable)
    }

    fun getListOfImageViews() : List<ImageView>{
        var list = listOf<ImageView>(imageView1,imageView2,imageView3,imageView4,imageView5
            ,imageView6,imageView7,imageView8,imageView9)
        return list
    }

    fun getRundomNumber(from:Int, to:Int) : Int{
        val rnds = (from until to).random()
        return rnds;
    }

    fun hideImageViews(){
        var list = getListOfImageViews()
        for(i in 0 until list.size step 1){
            list.get(i).visibility = View.INVISIBLE
        }
    }

    fun setVisibility(view : View){
        view.visibility = View.VISIBLE
    }

    fun timeOff(){
        handler.removeCallbacks(runnable)
        hideImageViews()
    }

    fun askForRestart(){
        var alert = AlertDialog.Builder(this)
        alert.setTitle("Restart")
        alert.setMessage("Are you sure restart the game?")
        alert.setPositiveButton("Yes"){dialog, which ->
            tryAgain()
        }
        alert.setNegativeButton("No"){dialog, which ->
            //something code
        }
        alert.show()
    }

    fun tryAgain(){
        start()
    }

    fun timeInMessage(){
        Toast.makeText(applicationContext,"Welcome",Toast.LENGTH_LONG).show()
    }

    fun timeOffMessage(){
        Toast.makeText(applicationContext,"Try Again",Toast.LENGTH_LONG).show()
    }
}
