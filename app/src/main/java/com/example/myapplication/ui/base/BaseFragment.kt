package com.example.myapplication.ui.base


import android.app.ActivityManager
import android.content.Context

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding



typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding>(
    private val inflate: Inflate<VB>
) : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObserve()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialEvent()
    }


    abstract fun initObserve()


    abstract fun initialEvent()




//
//    @SuppressLint("ResourceAsColor")
//    protected fun toasty(title: String, selectedMode: Int? = null) {
//        val layout = layoutInflater.inflate(
//            R.layout.toast_layout,
//            requireView().findViewById(R.id.toast_root)
//        )
//        when (selectedMode) {
//
//            MODE_TOAST_SUCCESS -> {
//                layout.findViewById<ImageView>(R.id.toast_img)
//                    .setImageResource(R.drawable.ic_corroct_toast)
//                layout.findViewById<ConstraintLayout>(R.id.toast_root)
//                    .setBackgroundResource(R.drawable.bg_corroct_toast)
//            }
//            MODE_TOAST_WARNING -> {
//                layout.findViewById<ImageView>(R.id.toast_img)
//                    .setImageResource(R.drawable.ic_warning_toast)
//                layout.findViewById<ConstraintLayout>(R.id.toast_root)
//                    .setBackgroundResource(R.drawable.bg_warning_toast)
//                layout.findViewById<TextView>(R.id.toast_txt).setTextColor(R.color.colorBlack)
//            }
//            MODE_TOAST_ERROR -> {
//                layout.findViewById<ImageView>(R.id.toast_img)
//                    .setImageResource(R.drawable.ic_error_toast)
//                layout.findViewById<ConstraintLayout>(R.id.toast_root)
//                    .setBackgroundResource(R.drawable.bg_error_toast)
//            }
//            else -> {
//                Toast.makeText(requireContext(), title, Toast.LENGTH_LONG).show()
//            }
//
//        }
//
//        layout.findViewById<TextView>(R.id.toast_txt).text = title
//        if (selectedMode != null) {
//            Toast(requireActivity()).apply {
//                setGravity(Gravity.BOTTOM, 0, 100)
//                duration = Toast.LENGTH_LONG
//                view = layout
//            }.show()
//        }
//    }
//
//    fun loadSvg(url: Int?,img: ImageView, size:Int?=null) {
//        Glide.with(this)
//            .load(url)
//            .apply(RequestOptions().override(size?:100, size?:100))
//            .into(img);
//    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return  _binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        const val MODE_TOAST_SUCCESS = 1
        const val MODE_TOAST_WARNING = 2
        const val MODE_TOAST_ERROR = 3
    }

}