package com.whocanfly.movieapp.views.movie

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.facebook.shimmer.ShimmerFrameLayout
import com.jackandphantom.carouselrecyclerview.CarouselRecyclerview
import com.jama.carouselview.CarouselView
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.whocanfly.movieapp.R
import com.whocanfly.movieapp.api.Movie
import com.whocanfly.movieapp.databinding.MainFragmentBinding
import com.whocanfly.movieapp.extensions.safeNavigate
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.lang.Exception

class MainFragment : Fragment(), SearchResultAdapter.SearchAdapterListener {

    private val mainViewModel by sharedViewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return MainFragmentBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mainViewModel.apply {
                fetchAllMoviesFromApi()
                toolbar.setOnClickListener {
                    activity?.onBackPressedDispatcher?.onBackPressed()
                }
                getTrendingMovies().observe(viewLifecycleOwner) {
                    rvTrendingMoviesSlider.apply {
                        setTrendingResults(rvTrendingMoviesSlider, it!!, true)
                    }
                }
                getPopularMovies().observe(viewLifecycleOwner) {
                    setDataIntoUi(
                        popularMovieRecyclerView,
                        it,
                        R.layout.card_movie_item,
                        null
                    )
                }
                getUpcomingMovies().observe(viewLifecycleOwner) {

                }
                val sliderAdapter = SliderAdapter(context)
                imageSlider.setSliderAdapter(sliderAdapter)

                imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM);
                imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                imageSlider.startAutoCycle();

                getUpcomingMovies().observe(viewLifecycleOwner) {
                    sliderAdapter.addList(it)
                }
            }
        }.root
    }


    private fun setDataIntoUi(
        slider: CarouselView,
        it: List<Movie>?,
        cardView: Int,
        type: String?
    ) {
        try {
            if (it != null) {
                slider.apply {
                    size = it.size
                    resource = cardView
                    setCarouselViewListener { view, position ->

                        val imageView = view.findViewById<ImageView>(R.id.ivMovieImg)
                        val imdbRating = view.findViewById<TextView>(R.id.tvImdbRating)
                        imdbRating.text = it[position].voteAverage.toString()

                        val movie = it[position]
                        imageView.setOnClickListener {
                            Navigation.findNavController(requireView())
                                .safeNavigate(
                                    MainFragmentDirections.actionToMovieDetail(movie)
                                )
                        }
                        val shimmer: ShimmerFrameLayout = view.findViewById(R.id.shimmer)
                        setImg(
                            context,
                            it[position].getCompleteImageUrl(),
                            imageView,
                            shimmer
                        )
                    }
                    show()
                    hideIndicator(true)

                }
            }
        } catch (ex: Exception) {
            Log.d("TAG", "fun setDataIntoUi : " + ex.message.toString())
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

    private fun setImg(ctx: Context, url: String, imgView: ImageView, shimmer: ShimmerFrameLayout) {
        shimmer.showShimmer(true)
        Glide.with(ctx)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .placeholder(R.drawable.ic_loading)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    shimmer.hideShimmer()
                    return false
                }

            })
            .into(imgView)

    }

    override fun startDetailsFragment(data: Movie) {
        Navigation.findNavController(requireView())
            .safeNavigate(
                MainFragmentDirections.actionToMovieDetail(data)
            )
    }
}