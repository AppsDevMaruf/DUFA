
package org.dufa.dufa9596.ui

import android.content.pm.PackageInfo
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.dufa.dufa9596.R
import org.dufa.dufa9596.databinding.FragmentSplashScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class SplashScreenFragment : Fragment() {

    var binding: FragmentSplashScreenBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashScreenBinding.inflate(inflater)
        val fadeAnimation = AnimationUtils.loadAnimation(
            context,
            R.anim.fade
        )
        val slideAnimation = AnimationUtils.loadAnimation(
            context,
            R.anim.fade
        )
        binding!!.logo.startAnimation(fadeAnimation)

        try {
            val pinfo: PackageInfo? =
                context?.packageName?.let { context?.packageManager?.getPackageInfo(it, 0) }
            val version: String = pinfo!!.versionName
            binding!!.dufaText.text="Version $version"
            binding!!.dufaText.startAnimation(slideAnimation)
        } catch (
            e: Exception
        ) {
            e.printStackTrace();
        }

        /*try {
    PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
    String version = pInfo.versionName;
} catch (PackageManager.NameNotFoundException e) {

}*/

        Handler().postDelayed({ // This method will be executed once the timer is over
//         //   Navigation.findNavController(container!!).navigate(R.id.logInFragment)
        }, 3000)
        // Inflate the layout for this fragment
        return binding!!.root
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }


}