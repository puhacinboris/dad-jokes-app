package com.borispuhacin.dadjokes.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.borispuhacin.dadjokes.R
import com.borispuhacin.dadjokes.databinding.FragmentJokeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JokesFragment : Fragment() {

    private val jokesViewModel: JokesViewModel by activityViewModels()

    private var _binding: FragmentJokeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJokeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        jokesViewModel.joke.observe(viewLifecycleOwner) {
            binding.textViewJoke.text = jokesViewModel.joke.value?.joke

            val joke = binding.textViewJoke.text.toString()

            binding.btnShare.setOnClickListener {
                shareJoke(joke)
            }
        }

        jokesViewModel.status.observe(viewLifecycleOwner) {
            when (jokesViewModel.status.value) {
                JokeApiStatus.ERROR -> {
                    binding.apply {
                        imageViewStatus.setImageResource(R.drawable.ic_connection_error)
                        imageViewAppIcon.visibility = View.GONE
                        textViewJoke.text = getString(R.string.no_internet_connection)
                        btnNextJoke.visibility = View.GONE
                        btnShare.visibility = View.GONE
                    }
                }
                JokeApiStatus.LOADING -> binding.imageViewStatus.setImageResource(R.drawable.loading_animation)
                else -> {
                    binding.imageViewStatus.visibility = View.GONE
                    binding.imageViewAppIcon.visibility = View.VISIBLE
                }
            }
        }

        binding.btnNextJoke.setOnClickListener {
            jokesViewModel.getRandomJoke()
        }


    }

    private fun shareJoke(joke: String) {
        val intent = Intent().apply {
            this.action = Intent.ACTION_SEND
            this.putExtra(Intent.EXTRA_TEXT, joke)
            this.type = "text/plain"
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}