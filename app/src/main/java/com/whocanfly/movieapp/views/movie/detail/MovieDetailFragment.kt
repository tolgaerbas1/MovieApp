package com.whocanfly.movieapp.views.movie.detail

import android.app.ProgressDialog.show
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.jama.carouselview.enums.IndicatorAnimationType
import com.jama.carouselview.enums.OffsetType
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.whocanfly.movieapp.R
import com.whocanfly.movieapp.databinding.MovieDetailFragmentBinding
import com.whocanfly.movieapp.views.movie.MainViewModel
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

                args.movie.id?.let { fetchMovieVideos(it) }

                videos.observe(viewLifecycleOwner) {
                    youtubeVideoPlayer.addYouTubePlayerListener(object :
                        AbstractYouTubePlayerListener() {

                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            youTubePlayer.loadVideo(it.key, 0f)
                            youTubePlayer.pause()
                        }
                    })

                }
            }
        }.root
    }
}