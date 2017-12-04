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
    lateinit var word_list : ArrayList<String>
    lateinit var adapter : ArrayAdapter<String>

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

        val intent = Intent(this@WorListActivity, WordEditActivity::class.java).apply {
            putExtra(getString(R.string.intent_key_question),selectedQuestion)
            putExtra(getString(R.string.intent_key_answer),selectedAnswer)
            putExtra(getString(R.string.intent_key_position),position)
            putExtra(getString(R.string.intent_key_status),getString(R.string.status_change))
        }
        startActivity(intent)
    }

    //長押しした場合
    override fun onItemLongClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long): Boolean {

        val selecteDBB = results[position]!!

        //DBから削除
        realm.beginTransaction()
        selecteDBB.deleteFromRealm()
        realm.commitTransaction()

        //Adapterからも削除
        word_list.removeAt(position)

        //Listviwに反映
        listview.adapter = adapter

        return true

    }


    override fun onResume() {
        super.onResume()
        realm = Realm.getDefaultInstance()

        results = realm.where(WordDB::class.java)
                .findAll()
                .sort(getString(R.string.db_field_question))

        word_list = ArrayList<String>()

        val count = results.size - 1

//        for (i in 0..count){
//            word_list.add(results[i]!!.answer + ":" + results[i]!!.question)
//        }

        results.forEach {
            word_list.add(it.answer + ":" + it.question)
        }

        adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,word_list)

        listview.adapter = adapter

    }

    override fun onPause() {
        super.onPause()
        realm.close()
    }

}
