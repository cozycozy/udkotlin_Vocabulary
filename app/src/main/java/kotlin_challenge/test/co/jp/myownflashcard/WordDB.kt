package kotlin_challenge.test.co.jp.myownflashcard

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by koji_mitake on 2017/11/28.
 */

open class WordDB : RealmObject() {

    //質問
    @PrimaryKey
    open var question : String? = null

    //答え
    open var answer : String? = null

    //暗記済みフラグ
    open var memory_flg : Boolean = false

}