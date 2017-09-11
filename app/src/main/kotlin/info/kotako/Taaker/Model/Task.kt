package info.kotako.Taaker.Model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.util.*

@RealmClass
open class Task(
        open var content: String = "",
        open var genre: String = "",
        open var milestone: Date = Date(),
        open var isNotify: Boolean = true,
        open var isDone: Boolean = false,
        @PrimaryKey open var id: String = UUID.randomUUID().toString())
    : RealmObject()