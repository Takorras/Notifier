package kotako.java.info.notifyer.View

import android.app.DialogFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotako.java.info.notifyer.R

class TaskDialog : DialogFragment() {

//  static methods
    companion object {
        fun newInstance(): TaskDialog {
            val fragment: TaskDialog = TaskDialog()
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater!!.inflate(R.layout.dialog_task, container, false)
        // コンポーネントにイベントをセット
        return view
    }
}
