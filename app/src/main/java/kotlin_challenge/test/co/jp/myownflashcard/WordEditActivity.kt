package kotlin_challenge.test.co.jp.myownflashcard

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_word_edit.*



class WordEditActivity : AppCompatActivity() {

    //Realm
    lateinit var realm : Realm

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
        button_resister.setOnClickListener {

            if (bundle.getString(getString(R.string.intent_key_status))
                    == getString(R.string.status_add)){
                addNewWord()
            }else {
                editWord()
            }

        }

        button_back_edit.setOnClickListener {
            finish()
        }

    }

    override fun onResume() {
        super.onResume()

        //Realのインスタンスを取得
        realm = Realm.getDefaultInstance()

    }

    override fun onPause() {
        super.onPause()

        //Realのクローズ
        realm.close()
    }

    //単語の登録
    private fun addNewWord() {

        //DBへの登録
        realm.beginTransaction()
        val wordDb : WordDB = realm.createObject(WordDB::class.java)
        wordDb.question = editText_word.text.toString()
        wordDb.answer = editText_translate.text.toString()
        realm.commitTransaction()

        //テキストボックスの値をクリア
        editText_translate.setText("")
        editText_word.setText("")

        //メッセージの表示
        Toast.makeText(this@WordEditActivity,"登録が完了しました",Toast.LENGTH_SHORT).show()
    }

    //単語の編集
    private fun editWord(){

    }
}
