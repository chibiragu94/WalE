package com.chibi.waie.ui.main.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.RequestManager
import com.chibi.waie.databinding.ActivityAstronomyBinding
import com.chibi.waie.extension.toastLong
import com.chibi.waie.ui.base.BaseActivity
import com.chibi.waie.ui.main.viewmodel.AstronomyViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Activity where Binding and functionality of the Astronomy is handled
 */
@AndroidEntryPoint
class AstronomyActivity : BaseActivity() {

    private lateinit var mBinding: ActivityAstronomyBinding

    private val mViewModel: AstronomyViewModel by viewModels()

    @Inject
    lateinit var glide: RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityAstronomyBinding.inflate(layoutInflater)
        val view = mBinding.root
        setContentView(view)

        setViewModelObserver()

        getAstronomyDetail()
    }

    /**
     * Holds all the livedata variables from viewmodel and Observe it to the view
     */
    private fun setViewModelObserver(){
        mViewModel.isLoading.observe(this,{
            setProgressBarVisibilityView(it)
        })

        mViewModel.errorMessage.observe(this,{
            toastLong(it)
        })

        mViewModel.astronomyDetails.observe(this,{ astronomy ->
            astronomy?.let {
                setTitleValue(astronomy.title)
                setExplanationValue(astronomy.explanation)
                setAstronomyImage(astronomy.url)
            }
        })
    }

    /**
     * Set progress bar visibility
     * @param isShow  returns either true or false
     */
    private fun setProgressBarVisibilityView(isShow : Boolean){
        if (isShow){
            mBinding.progressBar.visibility = View.VISIBLE
        }else{
            mBinding.progressBar.visibility = View.GONE
        }
    }

    /**
     * Set Title value to the UI
     * @param title value of the title
     */
    private fun setTitleValue(title : String){
        mBinding.tvTitle.text = title
    }

    /**
     * Set Explanation value to the UI
     * @param explanation value holds the explanation
     */
    private fun setExplanationValue(explanation : String){
        mBinding.tvExplanation.text = explanation
    }

    /**
     * Set image using the url through Glide
     * @param imageUrl
     */
    private fun setAstronomyImage(imageUrl : String){
        glide.load(imageUrl)
            .into(mBinding.imgAstronomy)
            .clearOnDetach()
    }

    /**
     * Get Astronomy details based on the Internet connection, If yes make the API call
     * If No - Check the local data with current day, month and year
     * or else - results the last inserted item in the Local data
     */
    private fun getAstronomyDetail(){

        if(isNetworkAvailable()){
            mViewModel.getAstronomyDetailRemote()
        }else{
            mViewModel.getAstronomyFromLocal()
        }
    }


}