package com.example.Sseudam

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.forEach
import androidx.databinding.DataBindingUtil
import androidx.viewbinding.ViewBindings
import com.example.Sseudam.databinding.ActivityMypageInquiryBinding
import com.example.Sseudam.databinding.FragmentMypageBinding

class MypageFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentMypageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMypageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
    }

    private fun setOnClickListener(){
        val mypageBtn = binding.fragmentMypage.mypageList
        mypageBtn.forEach { btn -> btn.setOnClickListener(this) }
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.manToman -> {
                val intent = Intent(context, Mypage_InquiryActivity::class.java)
                startActivity(intent)
            }
            R.id.logOut ->{
                val intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)
            }

        }
    }

}