package kotlin_challenge.test.co.jp.myownflashcard

import io.realm.Realm
import io.realm.RealmObject

/**
 * Created by koji_mitake on 2017/11/28.
 */

open class WordDB : RealmObject() {

    //質問
    var question : String? = null

    //答え
    var answer : String? = null

}