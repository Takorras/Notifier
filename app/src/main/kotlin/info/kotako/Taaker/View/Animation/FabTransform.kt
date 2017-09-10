package info.kotako.Taaker.View.Animation

import android.animation.Animator
import android.app.Activity
import android.graphics.Rect
import android.graphics.RectF
import android.transition.Transition
import android.transition.TransitionValues
import android.view.View
import android.view.ViewAnimationUtils.createCircularReveal
import android.view.ViewGroup

class FabTransform : Transition() {

    companion object {
        //  Viewの情報を保存しておくために、衝突を避けたキーを定義しておく
        val PROPNAME_BACKGROUND = "info.kotako.Taaker.View.Animation:FabTransform:background"
        val DEFAULT_DURATION = 240L
        fun setup(activity: Activity, target: View): Boolean {
            val sharedEnter = FabTransform()
            sharedEnter.addTarget(target)
            activity.window.sharedElementEnterTransition = sharedEnter
            return true
        }
    }

    private fun captureValues(transitionValues: TransitionValues?) {
//      欲しい情報をここでストアしておく
//      対象のコンポーネントの範囲を取得する
        val view = transitionValues!!.view
        transitionValues.values.put(PROPNAME_BACKGROUND,
                RectF(Rect(view.left, view.top, view.right, view.bottom)))
    }

    override fun captureStartValues(transitionValues: TransitionValues?) {
//      startの位置の定義
        captureValues(transitionValues)
    }

    override fun captureEndValues(transitionValues: TransitionValues?) {
//      endの位置の定義
        captureValues(transitionValues)
    }

    override fun createAnimator(sceneRoot: ViewGroup?, startValues: TransitionValues?, endValues: TransitionValues?): Animator {

        val startBounds = startValues!!.values[PROPNAME_BACKGROUND] as RectF
        val endBounds = endValues!!.values[PROPNAME_BACKGROUND] as RectF
        val fromFab = startBounds.width() > endBounds.width()

        val transitionTargetView = endValues!!.view

        val circularReveal: Animator

        if (fromFab) {
            circularReveal = createCircularReveal(
                    transitionTargetView,
                    transitionTargetView.width / 2,
                    transitionTargetView.height / 2,
                    startBounds.width() / 2,
                    Math.hypot((endBounds.width() / 2).toDouble(), (endBounds.height() / 2).toDouble()).toFloat()
            )
        } else {
            circularReveal = createCircularReveal(
                    transitionTargetView,
                    transitionTargetView.width / 2,
                    transitionTargetView.height / 2,
                    Math.hypot((startBounds.width() / 2).toDouble(), (startBounds.height() / 2).toDouble()).toFloat(),
                    endBounds.width() / 2
            )
        }

        circularReveal.duration = DEFAULT_DURATION

        return circularReveal
    }
}
