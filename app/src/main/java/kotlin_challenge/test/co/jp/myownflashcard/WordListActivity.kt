package kotlin_challenge.test.co.jp.myownflashcard

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_wor_list.*

class WorListActivity : AppCompatActivity(), AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {


    lateinit var realm : Realm
    lateinit var results : RealmResults<WordDB>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wor_list)

        //背景色を取得して、色を変更する
        list_layout.setBackgroundResource(intBackGroundColor)

        listview.onItemClickListener = this
        listview.onItemLongClickListener = this


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

    //タップした場合
    override fun onItemClick(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {

        val selectDB = results[position]!!

        val selectedQuestion = selectDB.question
        val selectedAnswer = selectDB.answer

        val intent = Intent(this@WorListActivity, WordEditActivity::class.java)
        intent.putExtra(getString(R.string.intent_key_question),selectedQuestion)
        intent.putExtra(getString(R.string.intent_key_answer),selectedAnswer)
        intent.putExtra(getString(R.string.intent_key_position),position)
        intent.putExtra(getString(R.string.intent_key_status),getString(R.string.status_change))
        startActivity(intent)

    }

    //長押しした場合
    override fun onItemLongClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun onResume() {
        super.onResume()
        realm = Realm.getDefaultInstance()

        results = realm.where(WordDB::class.java)
                .findAll()
                .sort(getString(R.string.db_field_question))

        val word_list = ArrayList<String>()

        val count = results.size - 1

//        for (i in 0..count){
//            word_list.add(results[i]!!.answer + ":" + results[i]!!.question)
//        }

        results.forEach {
            word_list.add(it.answer + ":" + it.question)
        }

        val adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,word_list)

        listview.adapter = adapter

    }

    override fun onPause() {
        super.onPause()
        realm.close()
    }

}
