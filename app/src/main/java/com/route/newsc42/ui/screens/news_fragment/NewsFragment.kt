package com.route.newsc42.ui.screens.news_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.route.newsc42.api.ApiManager
import com.route.newsc42.api.model.ArticleDM
import com.route.newsc42.api.model.ArticlesResponse
import com.route.newsc42.api.model.BaseErrorResponse
import com.route.newsc42.api.model.SourceDM
import com.route.newsc42.api.model.SourcesResponse
import com.route.newsc42.databinding.FragmentNewsBinding
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.forEach

class NewsFragment(val categoryId: String) : Fragment() {
    lateinit var binding: FragmentNewsBinding
    val articleAdapter = ArticlesAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadSources()
        setUpArticlesRecyclerView()
    }

    private fun setUpArticlesRecyclerView() {
        binding.articlesRecycler.adapter = articleAdapter
    }



    private fun showTabLayout(sources: List<SourceDM>) {
        binding.tabLayout.isVisible = true
        sources.forEach { source ->
            val tab = binding.tabLayout.newTab()
            tab.text = source.name
            tab.tag = source.id
            binding.tabLayout.addTab(tab)
        }
        loadArticles(sources[0].id ?: "")
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                loadArticles(tab!!.tag as String)
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {}
            override fun onTabReselected(p0: TabLayout.Tab?) {}
        })
    }



    private fun showArticlesList(articles: List<ArticleDM>) {
        hideLoading()
        articleAdapter.submitList(articles)
    }

    fun hideLoading() {
        binding.loadingProgress.isVisible = false
    }

    fun showLoading() {
        binding.loadingProgress.isVisible = true
    }

    fun showError(errorMessages: String, onRetryClick: () -> Unit) {
        binding.errorView.root.isVisible = true
        binding.errorView.errorMessage.text = errorMessages
        binding.errorView.retryButton.setOnClickListener {
            onRetryClick()
        }
    }

    fun hideError() {
        binding.errorView.root.isVisible = false
    }

}
class NewsViewModel : ViewModel() {

    val newsfragment : NewsFragment? = null
    private fun loadSources(categoryId: String) {
//        hideError()
//        showLoading()
        var isLoading= MutableLiveData<Boolean>()
        viewModelScope.launch { // instead of lifecycleScope.launch
            isLoading.value=true
            try {
                val response = ApiManager.getWebServices().loadSources(categoryId)
//                showTabLayout(response.sources ?: listOf())
//                hideLoading()
                isLoading.value=false
            } catch (e: Exception) {
                isLoading.value=false
//                hideLoading()
//                showError(e.localizedMessage) {
                    loadSources(categoryId)
                }
            }

        }
    }
    private fun loadArticles(sourceId: String) {
        showLoading()
        viewModelScope.launch {
            try {
                val response = ApiManager.getWebServices().loadArticles(sourceId)
                showArticlesList(response.articles ?: listOf())
            } catch (e: Exception) {
                hideLoading()
                showError(e.localizedMessage) {
                    loadArticles(sourceId)
                }
            }
        }
    }

}