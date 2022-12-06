package com.example.figmatest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.media3.common.MediaItem
import androidx.media3.common.Player.REPEAT_MODE_ONE
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Util
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.figmatest.databinding.MainFragmentBinding
import com.example.figmatest.domein.ItemModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment(), MainFragmentAdapter.ClickListner {

    private val viewModel: ItemViewModel by viewModel<ItemViewModel>()
    private var _binding: MainFragmentBinding? = null
    val binding get() = _binding!!
    private val adapter = MainFragmentAdapter(this)
    private var player: ExoPlayer? = null

    @UnstableApi
    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            initializePlayer()
        }
    }

    @UnstableApi
    override fun onResume() {
        super.onResume()
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer()
        }
    }

    public override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            releasePlayer()
        }
    }

    public override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            releasePlayer()
        }
    }

    @UnstableApi
    private fun initializePlayer() {
        player = ExoPlayer.Builder(requireContext())
            .build().also { player ->
                binding.videoView.player = player
                player.repeatMode = REPEAT_MODE_ONE
                player.prepare()
            }
    }

    private fun releasePlayer() {
        player?.let { exoPlayer ->
            exoPlayer.release()
        }
        player = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.textView.isVisible = false
        binding.button.setOnClickListener {
            if (!binding.textView.isVisible) {
                binding.textView.isVisible = true
            }
        }
        binding.textView.setOnLongClickListener {
            it.setOnTouchListener { view, motionEvent ->
                val minX = binding.videoView.x
                val maxX = minX + binding.videoView.width
                val minY = binding.videoView.y
                val maxY = minX + binding.videoView.height
                val x = motionEvent.x
                val y = motionEvent.y
                if (view.y + y >= minY && view.y + y + view.height <= maxY) {
                    view.y += y
                }
                if (view.x + x >= minX && view.x + x + view.width <= maxX)
                    view.x += x
                when (motionEvent.action) {
                    MotionEvent.ACTION_UP -> {
                        it.setOnTouchListener(null)
                    }
                }
                true
            }
            true
        }
        viewModel.listOfItemModel.observe(this.viewLifecycleOwner, { list ->
            list.let {
                adapter.setList(list)
                val mediaItem = MediaItem.fromUri(adapter.adapterList.first().video)
                player?.setMediaItem(mediaItem)
                player?.play()
            }
        })
    }

    override fun onClick(item: ItemModel) {
        val mediaItem = MediaItem.fromUri(item.video)
        player?.apply {
            setMediaItem(mediaItem)
            prepare()
            play()
        }
    }
}