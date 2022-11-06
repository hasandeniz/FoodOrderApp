package com.hasandeniz.bitirmeprojesi.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.hasandeniz.bitirmeprojesi.R
import com.hasandeniz.bitirmeprojesi.databinding.FragmentProfileBinding
import com.hasandeniz.bitirmeprojesi.ui.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var googleSignInClient : GoogleSignInClient

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profile,container,false)
        binding.profileFragmentObject = this

        val account = GoogleSignIn.getLastSignedInAccount(requireActivity())
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        binding.userEmail = user!!.email
        binding.userDisplayName = user.displayName
        showFoodImage(account!!.photoUrl)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()


        googleSignInClient = GoogleSignIn.getClient(requireActivity(),gso)
        return binding.root
    }

    private fun showFoodImage(imageUrl : Uri?){
        Glide.with(requireContext()).load(imageUrl).override(96,96).into(binding.ivUserPhoto)
    }

    fun logOutButton(){
        auth.signOut()
        googleSignInClient.signOut()
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)

    }
}