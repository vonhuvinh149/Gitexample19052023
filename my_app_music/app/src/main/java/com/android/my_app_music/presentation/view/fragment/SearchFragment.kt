package com.android.my_app_music.presentation.view.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.android.my_app_music.R
import com.android.my_app_music.common.AppConstance
import com.android.my_app_music.common.AppResource
import com.android.my_app_music.data.model.Song
import com.android.my_app_music.presentation.view.activity.PlaySongActivity
import com.android.my_app_music.presentation.view.adapter.SongAdapter
import com.android.my_app_music.presentation.viewmodel.SongViewModel
import com.android.my_app_music.utils.OnClickItem

class SearchFragment : Fragment() {

    private lateinit var view: View
    private var btnSearch: ImageView? = null
    private lateinit var songViewModel: SongViewModel
    private var recyclerView: RecyclerView? = null
    private lateinit var songAdapter: SongAdapter
    private var listSongs: MutableList<Song> = mutableListOf()
    private var tvNotFound: TextView? = null

    private var edtSearch: EditText? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_search, container, false)

        initView()
        observerData()
        event()
        return view
    }

    private fun observerData() {
        songViewModel = ViewModelProvider(requireActivity(), object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SongViewModel() as T
            }
        })[SongViewModel::class.java]

        songViewModel.getSongSearch().observe(viewLifecycleOwner) {
            when (it) {
                is AppResource.Success -> {
                    listSongs = it.data ?: mutableListOf()

                    songAdapter.updateData(listSongs)
                    recyclerView?.adapter = songAdapter

                    if (listSongs.size <= 0) {
                        tvNotFound?.visibility = View.VISIBLE
                    } else {
                        tvNotFound?.visibility = View.GONE
                    }
                }

                is AppResource.Error -> {
                    Log.e("BBB", it.message.toString())
                }
            }
        }
    }

    private fun event() {
        btnSearch?.setOnClickListener {
            val searchValue: String = edtSearch?.text.toString()
            if (searchValue.isNotBlank()) {
                songViewModel.executeSearchSong(searchValue)
            } else {
                edtSearch?.error = "input please !"
                return@setOnClickListener
            }
            edtSearch?.setText("")
            hideKeyboard()
        }

        edtSearch?.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                (event != null && event.action == KeyEvent.ACTION_DOWN &&
                        event.keyCode == KeyEvent.KEYCODE_ENTER)
            ) {
                performSearch()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        songAdapter.onClickItem(object : OnClickItem {
            override fun onClick(position: Int) {
                hideKeyboard()
                val intent = Intent(requireContext(), PlaySongActivity::class.java)
                val newLists: MutableList<Song> = mutableListOf(listSongs[position])
                intent.putExtra(AppConstance.POSITION_SONG_KEY, 0)
                intent.putExtra(AppConstance.LIST_SONG_KEY, ArrayList(newLists))
                startActivity(intent)
            }
        })

        edtSearch?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                val str = s.toString()
                if (str.isNotBlank()) {
                    songViewModel.executeSearchSong(str)
                }
            }

        })
    }

    private fun performSearch() {
        val str = edtSearch?.text.toString()
        if (str.isNotBlank()) {
            songViewModel.executeSearchSong(str)
            edtSearch?.error = "input please"
        }
        hideKeyboard()
    }

    private fun hideKeyboard() {
        val keyWatcher =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        keyWatcher.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun initView() {
        songViewModel = SongViewModel()
        btnSearch = view.findViewById(R.id.btn_search)
        recyclerView = view.findViewById(R.id.recycler_view_search)
        songAdapter = SongAdapter()
        recyclerView?.setHasFixedSize(false)
        edtSearch = view.findViewById(R.id.edt_search_name)
        tvNotFound = view.findViewById(R.id.tv_not_found)
    }

}