package kotlin_challenge.test.co.jp.myownflashcard

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import io.realm.Realm
import io.realm.RealmResults
import io.realm.exceptions.RealmPrimaryKeyConstraintException
import kotlinx.android.synthetic.main.activity_word_edit.*



class WordEditActivity : AppCompatActivity() {

    //Realm
    lateinit var realm : Realm
    lateinit var results : RealmResults<WordDB>

    //全画面からの引き輪足
    var strQestion : String = ""
    var strAnswer : String = ""
    var position : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_edit)

        val bundle = intent.extras
        val status = bundle.getString(getString(R.string.intent_key_status))

        if (status == getString(R.string.status_add)){
            textView_Status.text = getString(R.string.add_message)
            editText_word.isEnabled = true
        }else {
            textView_Status.text = getString(R.string.edit_message)
            strQestion = bundle.getString(getString(R.string.intent_key_question))
            strAnswer = bundle.getString(getString(R.string.intent_key_answer))
            position = bundle.getInt(getString(R.string.intent_key_position))

            //値の設定
            editText_word.setText(strQestion)
            editText_translate.setText(strAnswer)
            editText_word.isEnabled = false

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

        val dialog = AlertDialog.Builder(this@WordEditActivity).apply {
                setTitle("登録")
                setMessage("登録していいですか？")
                setPositiveButton("はい"){ dialogInterface, i ->
                    //DBへの登録
                    realm.beginTransaction()
                    val wordDb : WordDB = realm.createObject(WordDB::class.java,editText_word.text.toString())
                    //wordDb.question = editText_word.text.toString()
                    wordDb.answer = editText_translate.text.toString()
                    wordDb.memory_flg = false

                    editText_translate.setText("")
                    editText_word.setText("")

                    //コミット
                    realm.commitTransaction()

                    //メッセージの表示
                    Toast.makeText(this@WordEditActivity,"登録が完了しました",Toast.LENGTH_SHORT).show()

                }
                setNegativeButton("いいえ"){ dialogInterface, i -> }
                show()




//            try {
//                setTitle("登録")
//                setMessage("登録していいですか？")
//                setPositiveButton("はい"){ dialogInterface, i ->
//                    //DBへの登録
//                    realm.beginTransaction()
//                    val wordDb : WordDB = realm.createObject(WordDB::class.java,editText_word.text.toString())
//                    //wordDb.question = editText_word.text.toString()
//                    wordDb.answer = editText_translate.text.toString()
//                    wordDb.memory_flg = false
//
//                    //メッセージの表示
//                    Toast.makeText(this@WordEditActivity,"登録が完了しました",Toast.LENGTH_SHORT).show()
//                }
//                setNegativeButton("いいえ"){ dialogInterface, i -> }
//                show()
//
//            } catch (e: RealmPrimaryKeyConstraintException) {
//                Toast.makeText(this@WordEditActivity,"その単語はすでに登録されています",
//                        Toast.LENGTH_SHORT).show()
//            } finally {
//                //テキストボックスの値をクリア
//                editText_translate.setText("")
//                editText_word.setText("")
//
//                //コミット
//                realm.commitTransaction()
//
//            }

        }
    }

    //単語の編集
    private fun editWord(){

        results = realm.where(WordDB::class.java).findAll().sort(getString(R.string.db_field_question))
        val selectedDB = results[position]!!

        val daialog = AlertDialog.Builder(this@WordEditActivity).apply {
            setTitle(selectedDB.answer + "の変更")
            setMessage("変更していいですか？")
            setPositiveButton("はい"){ dialogInterface,i ->

                realm.beginTransaction()
                //selectedDB.question = editText_word.text.toString()
                selectedDB.answer = editText_translate.text.toString()
                selectedDB.memory_flg = false
                realm.commitTransaction()

                //テキストボックスの値をクリア
                editText_translate.setText("")
                editText_word.setText("")

                Toast.makeText(this@WordEditActivity,"更新が完了しました",Toast.LENGTH_SHORT)

                //前の画面に戻る
                finish()

            }
            setNegativeButton("いいえ"){ dialogInterface, i ->  }
            show()
        }
    }

}
