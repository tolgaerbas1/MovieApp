package com.whocanfly.movieapp.views.movie

import android.app.ProgressDialog.show
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.jama.carouselview.enums.IndicatorAnimationType
import com.jama.carouselview.enums.OffsetType
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.whocanfly.movieapp.R
import com.whocanfly.movieapp.databinding.MovieDetailFragmentBinding
import com.whocanfly.movieapp.views.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailFragment : Fragment() {

    private val args by navArgs<MovieDetailFragmentArgs>()
    private var detail_type = "Movie"
    private val mainViewModel by sharedViewModel<MainViewModel>()
    private val detailViewModel by viewModel<MovieDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return MovieDetailFragmentBinding.inflate(inflater, container, false).apply {
            item = args.movie
            viewModel = detailViewModel.apply {
                getVideoContainer().observe(viewLifecycleOwner) {
                    videoSlider.apply {
                        size = it.results!!.size
                        resource = R.layout.video_card
                        indicatorAnimationType = IndicatorAnimationType.DROP
                        carouselOffset = OffsetType.CENTER

                        setCarouselViewListener { view, position ->
                            val videoView =
                                view.findViewById<YouTubePlayerView>(R.id.youtubeVideoPlayer)
                            videoView.addYouTubePlayerListener(object :
                                AbstractYouTubePlayerListener() {
                                override fun onReady(youTubePlayer: YouTubePlayer) {
                                    youTubePlayer.loadVideo(it.results[position].key, 0f)
                                    youTubePlayer.pause()
                                }
                            })
                        }
                        // After you finish setting up, show the CarouselView
                        show()
                    }

                }
            }
        }.root
    }
}