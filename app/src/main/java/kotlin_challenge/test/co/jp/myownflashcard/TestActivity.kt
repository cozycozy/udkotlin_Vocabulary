package kotlin_challenge.test.co.jp.myownflashcard

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_test.*
import java.util.*
import kotlin.collections.ArrayList

class TestActivity : AppCompatActivity(), View.OnClickListener {

    var booleStatas : Boolean = false

    //Realm
    lateinit var realm : Realm
    lateinit var results : RealmResults<WordDB>
    lateinit var word_list : ArrayList<WordDB>
    var intLength : Int = 0
    //現在の問題数
    var intCount : Int = 0

    //問題を暗記済みにするかどうかのFLG
    var memoryFlg : Boolean = false

//    テスト状態
    var intState: Int = 0
    val BEFORE_START : Int = 1
    val RUNNING_QUESTION : Int = 2
    val RUNNING_ANSWER : Int = 3
    val TEST_FINISHED : Int = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        //インテント受取
        val bundle = intent.extras
        booleStatas = bundle.getBoolean(getString(R.string.intent_key_memory_flag))

        //背景の設定
        constraintLayout_test.setBackgroundResource(intBackGroundColor)

        //テスト状態設定
        intState = BEFORE_START
        imageViewQuestion.visibility = View.INVISIBLE
        imageViewAnser.visibility = View.INVISIBLE

        //ボタンのイメージ設定
        buttonNext.setBackgroundResource(R.drawable.image_button_test_start)
        buttonQuit.setBackgroundResource(R.drawable.image_button_end_test)

        //リスナー登録
        buttonNext.setOnClickListener(this)
        buttonQuit.setOnClickListener(this)
        checkBox.setOnClickListener {
            memoryFlg = checkBox.isChecked
        }

    }

    override fun onClick(view: View?) {

        when(view?.id){

            R.id.buttonNext ->

                when(intState) {
                    BEFORE_START -> {
                        intState = RUNNING_QUESTION
                        showQuestion()
                    }

                    RUNNING_QUESTION -> {
                        intState = RUNNING_ANSWER
                        showQuestion()
                    }

                    RUNNING_ANSWER -> {
                        intState = RUNNING_QUESTION
                        showAnser()
                    }

                }

        }


    }

    override fun onResume() {
        super.onResume()

        realm = Realm.getDefaultInstance()

        if(booleStatas){
            //暗記済みを除外
            results = realm.where(WordDB::class.java)
                    .equalTo(getString(R.string.db_field_memory_flg),false)
                    .findAll()
        } else {
            //すべてを取得
            results = realm.where(WordDB::class.java).findAll()
        }
//        残り問題数の表示
        intLength = results.size
        textViewRemaining.text = intLength.toString()

        //問題をシャッフルする
        word_list = ArrayList(results)
        Collections.shuffle(word_list)

    }

    override fun onPause() {
        super.onPause()
        realm.close()
    }

    private fun showQuestion() {

        //前の問題を南紀済みフラグをDB登録
        if (intCount > 0 ) {
            val selectDB = realm.where(WordDB::class.java).equalTo(getString(R.string.db_field_question),
                    word_list[intCount - 1].question).findAll()
            realm.beginTransaction()

            realm.commitTransaction()
        }

        //残りの問題数を1つ減らす
        intCount ++
        textViewRemaining.text = (intLength - intCount).toString()

        //見せないようにする
        imageViewAnser.visibility = View.INVISIBLE
        textViewAnswer.text = ""
        imageViewQuestion.visibility = View.VISIBLE
        textViewQuestion.text = word_list[intCount - 1].question

        //ボタンを答えを見えるボタンに変更
        buttonNext.setBackgroundResource(R.drawable.image_button_go_answer)

        //問題が暗記済みの場合はチェックを入れる
        checkBox.isChecked = word_list[intCount - 1].memory_flg
        memoryFlg = checkBox.isChecked
    }

    private fun showAnser() {
    }


}
