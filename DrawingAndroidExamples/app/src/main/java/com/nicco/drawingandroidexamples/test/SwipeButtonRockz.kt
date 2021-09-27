package com.nicco.drawingandroidexamples.test

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity.CENTER
import android.view.MotionEvent
import android.view.MotionEvent.*
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.nicco.drawingandroidexamples.R
import android.util.TypedValue
import android.widget.ImageView


@SuppressLint("ClickableViewAccessibility")
class SwipeButtonRockz constructor(
    context: Context,
    attributeSet: AttributeSet
) : ConstraintLayout(context) {

    private lateinit var arrowRight: ImageView
    private var primaryText: TextView
    private var secondaryText: TextView
    private var swipeButtonActivated: Boolean = false
    private val initialMargin: Int = 8.dp
    private var controllerMargin: Int = 1.dp
    private lateinit var rootLayout: ConstraintLayout
    private lateinit var swipeButton: View
    private var initialSize: Int = 40.dp
    private var initialX: Int = 40.dp

    init {
        removeView(this)
        configureBackground(context)

        val swipeButton = configureSwipeButton(rootLayout, context)
        rootLayout.addView(swipeButton)

        val arrowRight = configureArrowRight(rootLayout, context)
        rootLayout.addView(arrowRight)

        primaryText = configureText(rootLayout, context, "Swipe to confirm", R.color.black)
        rootLayout.addView(primaryText)

        secondaryText = configureText(rootLayout, context, "Done", R.color.white)
        secondaryText.visibility = GONE
        rootLayout.addView(secondaryText)

        setOnTouchListener(getButtonTouchListener())
    }

    private fun configureArrowRight(rootViewGroup: ConstraintLayout, context: Context): View {
        val layoutParamsView = LayoutParams(
            40.dp,
            40.dp
        )
        layoutParamsView.startToStart = rootViewGroup.id
        layoutParamsView.topToTop = rootViewGroup.id
        layoutParamsView.bottomToBottom = rootViewGroup.id
        layoutParamsView.marginStart = initialMargin

        val view = ImageView(context)
        view.id = generateViewId()
        view.layoutParams = layoutParamsView
        view.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_arrow_right))
        this.arrowRight = view
        return view
    }

    private fun configureText(
        rootViewGroup: ConstraintLayout,
        context: Context,
        text: String,
        textColor: Int
    ): TextView {
        val layoutParamsView = LayoutParams(
            LayoutParams.MATCH_CONSTRAINT,
            LayoutParams.MATCH_CONSTRAINT
        )
        layoutParamsView.startToStart = rootViewGroup.id
        layoutParamsView.topToTop = rootViewGroup.id
        layoutParamsView.bottomToBottom = rootViewGroup.id
        layoutParamsView.endToEnd = rootViewGroup.id

        val textView = TextView(context)
        textView.id = generateViewId()
        textView.layoutParams = layoutParamsView
        textView.text = text
        textView.setTextColor(ContextCompat.getColor(context, textColor))
        textView.textSize = convertSpToPixels(8F, context)
        textView.gravity = CENTER

        return textView
    }

    private fun configureSwipeButton(
        rootViewGroup: ConstraintLayout,
        context: Context
    ): View {
        val layoutParamsView = LayoutParams(
            40.dp,
            40.dp
        )
        layoutParamsView.startToStart = rootViewGroup.id
        layoutParamsView.topToTop = rootViewGroup.id
        layoutParamsView.bottomToBottom = rootViewGroup.id
        layoutParamsView.marginStart = initialMargin

        val view = View(context)
        view.id = generateViewId()
        view.layoutParams = layoutParamsView
        view.background = ContextCompat.getDrawable(context, R.drawable.shape_rounded_blue)
        this.swipeButton = view
        return view
    }

    private fun configureBackground(context: Context): ConstraintLayout {
        rootLayout = ConstraintLayout(context)
        rootLayout.id = generateViewId()
        val constraintLayoutParams =
            LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 56.dp)
        rootLayout.layoutParams = constraintLayoutParams
        rootLayout.background = ContextCompat.getDrawable(context, R.drawable.shape_rounded_white)
        addView(rootLayout)

        return rootLayout
    }

    private fun getButtonTouchListener(): OnTouchListener {
        return OnTouchListener { view, event ->
            when (event.action) {
                ACTION_DOWN -> {
                    Log.d("getButtonTouchListener", "ACTION_DOWN")
                    return@OnTouchListener true
                }
                ACTION_MOVE -> {
                    val result = swipeButton.layoutParams

                    configInitialState(event)

                    if (checkIfMoveToRight(event, view)) {

                        arrowRight.visibility = GONE

                        if (checkIfSwipeButtonWidthIsInRangeOfView(result, view)) {
                            configRightMove(event.x, view)

                            initialX = event.x.toInt()
                        }
                    } else if (checkIfMoveToLeft(event)) {
                        if (checkIfWidthIsGreathenThanInitialSize(result)) {
                            configLeftMove(event.x, view)
                            initialX = event.x.toInt()
                        } else {
                            swipeButton.x = 8.dp.toFloat()
                            
                            arrowRight.visibility = VISIBLE

                            val layoutParamsView = LayoutParams(
                                initialSize,
                                initialSize
                            )
                            layoutParamsView.startToStart = rootLayout.id
                            layoutParamsView.topToTop = rootLayout.id
                            layoutParamsView.bottomToBottom = rootLayout.id

                            swipeButton.layoutParams = layoutParamsView
                        }
                    }

                    shouldChangeText(view)
//region used for debug
//                    Log.d("getButtonTouchListener", "ACTION_MOVE")
//                    Log.d("getButtonTouchListener", "event.x = ${event.x}")
//                    Log.d("getButtonTouchListener", "swipeButtonActivated = $swipeButtonActivated")
//                    Log.d("getButtonTouchListener", "controllerMargin = $controllerMargin")
//                    Log.d("getButtonTouchListener", "result.width = ${result.width.dp}")
//                    Log.d("getButtonTouchListener", "v.width = ${v.width.dp}")
//                    Log.d("getButtonTouchListener", "result.height = ${result.height.dp}")
//                    Log.d("getButtonTouchListener", "v.height = ${result.height.dp}")
// endregion
                    return@OnTouchListener true
                }

                ACTION_UP -> {
                    Log.d("getButtonTouchListener", "ACTION_UP")
                    return@OnTouchListener true
                }
            }
            false
        }
    }

    private fun shouldChangeText(view: View) {
        if (initialX.dp > view.width.dp / 2) {
            primaryText.visibility = GONE
            secondaryText.visibility = VISIBLE
        } else {
            primaryText.visibility = VISIBLE
            secondaryText.visibility = GONE
        }
    }

    private fun configInitialState(event: MotionEvent) {
        if (initialX == 0) {
            initialX = event.x.toInt()
        }
    }

    private fun configLeftMove(moving: Float, view: View) {
        var moveResult = calculateLenghtMove(moving.toInt().dp)

        moveResult = controllerMinimunWidthRange(moveResult, view)

        val layoutParamsView = LayoutParams(
            moveResult,
            view.height
        )
        layoutParamsView.startToStart = rootLayout.id
        layoutParamsView.topToTop = rootLayout.id
        layoutParamsView.bottomToBottom = rootLayout.id

        swipeButton.layoutParams = layoutParamsView
    }

    private fun controllerMinimunWidthRange(moveResult: Int, view: View): Int {
        var moveResult1 = moveResult
        if (moveResult1 < initialSize) {
            moveResult1 = initialSize
        }
        return moveResult1
    }

    private fun checkIfWidthIsGreathenThanInitialSize(result: ViewGroup.LayoutParams) =
        result.width > initialSize

    private fun configRightMove(moving: Float, view: View) {

        var moveResult = calculateLenghtMove(moving.toInt().dp)
        Log.d("getButtonTouchListener", "moveResult = $moveResult")

        moveResult = controllerMaxWidthRange(moveResult, view)

        val layoutParamsView = LayoutParams(
            moveResult,
            56.dp
        )

        if (controllerMargin < 8.dp)
            controllerMargin++

        swipeButton.x = 8.dp.toFloat() - controllerMargin
        layoutParamsView.startToStart = rootLayout.id
        layoutParamsView.topToTop = rootLayout.id
        layoutParamsView.bottomToBottom = rootLayout.id

        swipeButton.layoutParams = layoutParamsView
    }

    private fun controllerMaxWidthRange(moveResult: Int, view: View): Int {
        var moveResult1 = moveResult
        if (moveResult1 > view.width) {
            moveResult1 = view.width
        }
        return moveResult1
    }

    private fun calculateLenghtMove(eventMove: Int): Int {
        Log.d("getButtonTouchListener", "initialX = $initialX")
        Log.d("getButtonTouchListener", "eventMove = $eventMove")
        return if (initialX > eventMove) {
            initialX - eventMove
        } else {
            eventMove - initialX
        }
    }

    private fun checkIfSwipeButtonWidthIsInRangeOfView(
        result: ViewGroup.LayoutParams,
        v: View
    ) = result.width.dp <= v.width.dp

    private fun checkIfMoveToLeft(event: MotionEvent) = event.x < initialX && initialX > 0

    private fun checkIfMoveToRight(
        event: MotionEvent,
        v: View
    ) = event.x > initialX && initialX.dp < v.width.dp
}

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

fun convertSpToPixels(sp: Float, context: Context): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        sp,
        context.resources.displayMetrics
    )
}
/**
 * I will need this example maybe in future, how to configure constraints
 *
 *    val mConstraintSet = ConstraintSet()
mConstraintSet.clone(background)
mConstraintSet.connect(view.id, ConstraintSet.TOP, background.id, ConstraintSet.TOP)
mConstraintSet.connect(view.id, ConstraintSet.BOTTOM, background.id, ConstraintSet.BOTTOM)
mConstraintSet.applyTo(background)
 */