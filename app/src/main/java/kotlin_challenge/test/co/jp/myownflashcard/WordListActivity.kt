package kotlin_challenge.test.co.jp.myownflashcard

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_wor_list.*

class WorListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wor_list)

        //背景色を取得して、色を変更する
        list_layout.setBackgroundResource(intBackGroundColor)

        //追加ボタンをおした時
        buttonAddNewWord.setOnClickListener {
            val intent : Intent = Intent(this@WorListActivity,WordEditActivity::class.java)
            intent.putExtra(getString(R.string.intent_key_status),getString(R.string.status_add))
            startActivity(intent)
        }

        //戻るボタンをおした時
        buttonBack.setOnClickListener {
            finish()
        }



    }
}
