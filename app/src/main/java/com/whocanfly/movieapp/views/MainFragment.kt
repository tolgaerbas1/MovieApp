package com.whocanfly.movieapp.views

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.jackandphantom.carouselrecyclerview.CarouselLayoutManager
import com.jackandphantom.carouselrecyclerview.CarouselRecyclerview
import com.whocanfly.movieapp.R
import com.whocanfly.movieapp.api.Movie
import com.whocanfly.movieapp.databinding.MainFragmentBinding
import com.whocanfly.movieapp.extensions.RecyclerViewBindings
import com.whocanfly.movieapp.extensions.safeNavigate
import com.whocanfly.movieapp.views.movie.SearchResultAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Exception

class MainFragment : Fragment(), SearchResultAdapter.SearchAdapterListener {

    private val mainViewModel by sharedViewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return MainFragmentBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            mainViewModel.fetchAllMoviesFromApi()
            try {
                mainViewModel.getTrendingMovies().observe(viewLifecycleOwner) {
                    rvTrendingMoviesSlider.apply {
                        setTrendingResults(rvTrendingMoviesSlider, it!!, true)
                    }
                }
                mainViewModel.getPopularMovies().observe(viewLifecycleOwner) {
                    rvPopularMoviesSlider.apply {
                        setTrendingResults(rvPopularMoviesSlider, it!!, true)
                    }
                }


            } catch (ex: Exception) {
                Log.d("tag", "In main fragment : " + ex.message.toString())
            }

            carouselTabLayout = carouselTabs
            carouselItemClickListener = onCarouselItemClickListener
        }.root
    }

    private val onCarouselItemClickListener = object : RecyclerViewBindings.OnItemClickListener {
        override fun onItemClick(view: View, vararg data: Any) {
            val item = data[0] as Movie
            try {
                Navigation.findNavController(requireView())
                    .safeNavigate(
                        MainFragmentDirections
                            .actionToMovieDetail(item)
                    )
            } catch (ex: Exception) {
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setTrendingResults(
        trendingRv: CarouselRecyclerview,
        seriesList: List<Movie>,
        wideLayout: Boolean
    ) {
        Log.d("TAG", "Results are ${seriesList.size}")
        if (seriesList.isNotEmpty()) {

            trendingRv.visibility = View.VISIBLE
            val movieAdapter = SearchResultAdapter(wideLayout, this)
            movieAdapter.addList(seriesList)
            trendingRv.layoutManager = trendingRv.getCarouselLayoutManager()
            trendingRv.adapter = movieAdapter

            trendingRv.set3DItem(true)
            trendingRv.setInfinite(true)
            trendingRv.setAlpha(true)

        }
    }

    override fun startDetailsFragment(data: Movie) {
        Navigation.findNavController(requireView())
            .safeNavigate(
                MainFragmentDirections
                    .actionToMovieDetail(data)
            )
    }

}