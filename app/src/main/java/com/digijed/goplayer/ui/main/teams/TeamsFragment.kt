package com.digijed.goplayer.ui.main.teams

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.digijed.goplayer.R
import com.digijed.goplayer.databinding.FragmentTeamsBinding
import com.digijed.goplayer.model.Teams
import com.digijed.goplayer.repository.*
import com.digijed.goplayer.ui.main.players.PlayersFragment
import com.firebase.ui.database.FirebaseRecyclerOptions
import kotlinx.android.synthetic.main.fragment_teams.*

class TeamsFragment : Fragment() {

    private lateinit var binding: FragmentTeamsBinding
    private lateinit var adapter: TeamAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTeamsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCardView()
    }

    private fun initCardView() {

        val options = FirebaseRecyclerOptions.Builder<Teams>()
            .setLifecycleOwner(this)
            .setQuery(TeamsRepository().getTeams(), Teams::class.java)
            .build()

        adapter = TeamAdapter(options, object : TeamAdapter.OnItemClickListener {
            override fun onItemClick(team: Teams, position: Int) {
                // список гравців команди, перехід до відповідного фрагмента
                // Navigate to the desired fragment and pass the teamId
                val fragment = PlayersFragment()
                val bundle = Bundle()
                bundle.putString("teamId", team.uid)
                fragment.arguments = bundle

                val transaction = parentFragmentManager.beginTransaction()
                transaction.replace(R.id.fragmentMenu, fragment)
                transaction.addToBackStack(null)
                transaction.commit()

            }
        })

        val layoutManager = LinearLayoutManager(requireContext())
        rvTeams.layoutManager = layoutManager
        rvTeams.adapter = adapter
    }
}