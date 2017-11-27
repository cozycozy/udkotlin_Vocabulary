package kotlin_challenge.test.co.jp.myownflashcard

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_wor_list.*
import kotlinx.android.synthetic.main.activity_word_edit.*



class WordEditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_edit)

        val bundle = intent.extras

        if (bundle.getString(getString(R.string.intent_key_status))
                == R.string.status_add.toString()){
            textView_Status.text = getString(R.string.status_add)
        }else {
            textView_Status.text = getString(R.string.status_change)
        }

        //背景色の設定
        edit_layout.setBackgroundResource(intBackGroundColor)

        //登録ボタンをおした時
        buttonAddNewWord.setOnClickListener {

            if (bundle.getString(getString(R.string.intent_key_status))
                    == R.string.status_add.toString()){
                addNewWord()
            }else {
                editWord()
            }

        }

        button_back_edit.setOnClickListener {
            finish()
        }

    }

    private fun addNewWord() {

    }

    private fun editWord(){

    }
}
