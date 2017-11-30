package kotlin_challenge.test.co.jp.myownflashcard

import android.app.Application
import io.realm.Realm

/**
 * Created by koji_mitake on 2017/11/28.
 */
class MyApplication : Application () {

    override fun onCreate() {

        super.onCreate()
        Realm.init(this)

    }

    override fun onTerminate() {
        super.onTerminate()
    }


}