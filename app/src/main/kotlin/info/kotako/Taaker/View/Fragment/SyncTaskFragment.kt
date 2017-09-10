package info.kotako.Taaker.View.Fragment

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import info.kotako.Taaker.R

class SyncTaskFragment: Fragment() {

    companion object {
        fun newInstance(): Fragment {
            return SyncTaskFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_sync, container, false)
        return view
    }
}