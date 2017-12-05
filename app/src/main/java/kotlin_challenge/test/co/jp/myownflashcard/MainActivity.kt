package kotlin_challenge.test.co.jp.myownflashcard

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

//色を変更する
var intBackGroundColor = 0

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //テスト実施ボタンを押した場合
        button_test.setOnClickListener {
            intent = Intent(this@MainActivity,TestActivity::class.java)

            when(radioGroup.checkedRadioButtonId) {
                R.id.radioButton1 -> intent.putExtra(getString(R.string.intent_key_memory_flag), true)
                R.id.radioButton2 -> intent.putExtra(getString(R.string.intent_key_memory_flag),false )
            }
            startActivity(intent)
        }

        //編集ボタンをおした時
        button_edit.setOnClickListener {
            val intent : Intent = Intent(this@MainActivity, WorListActivity::class.java)
            startActivity(intent)
        }


        button_blue.setOnClickListener{
            intBackGroundColor = R.color.darkblue
            top_layout.setBackgroundColor(intBackGroundColor)
        }

        button_golden.setOnClickListener {
            intBackGroundColor = R.color.darkgoldenrod
            top_layout.setBackgroundResource(intBackGroundColor)
        }
        button_gray.setOnClickListener {
            intBackGroundColor = R.color.darkgray
            top_layout.setBackgroundResource(intBackGroundColor)
        }
        button_green.setOnClickListener{
            intBackGroundColor = R.color.darkgreen
            top_layout.setBackgroundResource(intBackGroundColor)
        }
        button_red.setOnClickListener{
            intBackGroundColor = R.color.darkred
            top_layout.setBackgroundResource(intBackGroundColor)
        }
        button_violet.setOnClickListener {
            intBackGroundColor = R.color.darkviolet
            top_layout.setBackgroundResource(intBackGroundColor)
        }

    }
}
