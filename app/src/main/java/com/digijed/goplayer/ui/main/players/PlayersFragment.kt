package com.digijed.goplayer.ui.main.players

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.digijed.goplayer.databinding.FragmentPlayersBinding
import com.digijed.goplayer.model.Players
import com.digijed.goplayer.repository.*
import com.digijed.goplayer.ui.main.dialog.BaseDialog
import com.firebase.ui.database.FirebaseRecyclerOptions
import kotlinx.android.synthetic.main.fragment_players.*

class PlayersFragment : Fragment(), BaseDialog.Listener {

    private lateinit var binding: FragmentPlayersBinding
    private lateinit var adapter: PlayerAdapter
    private var teamId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayersBinding.inflate(layoutInflater, container, false)
        arguments?.let {
            teamId = it.getString("teamId")
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        if (teamId != null) {
            setupRecyclerViewTeamsPlayer(teamId.toString())
        }
    }

    private fun setupRecyclerView() {
        val options = FirebaseRecyclerOptions.Builder<Players>()
            .setLifecycleOwner(this)
            .setQuery(PlayersRepository().getPlayers(), Players::class.java)
            .build()

        adapter = PlayerAdapter(options, object : PlayerAdapter.OnItemClickListener {
            override fun onItemClick(player: Players) {
                // детальніше про гравця
                runDescriptionDialog(player)
            }
        })

        val layoutManager = LinearLayoutManager(requireContext())
        rvPlayers.layoutManager = layoutManager
        rvPlayers.adapter = adapter
        rvPlayers.addItemDecoration(PlayersDecoration(this))
    }

    private fun runDescriptionDialog(player: Players) {
        val teams: String = when (player.idTeam) {
            "1" -> "ФК Шахтар"
            "2" -> "ФК Динамо"
            "3" -> "ФК Десна"
            "4" -> "ФК Верес"
            else -> "Невідома команда"
        }
        val message =
            "Гравець: ${player.firstName} ${player.secondName}\nКоманда: ${teams}\nПозиція: ${player.position}\nНомер: ${player.number}"
        BaseDialog.newInstance(message, parentFragmentManager)
    }

    private fun setupRecyclerViewTeamsPlayer(idTeam: String) {
        val options = FirebaseRecyclerOptions.Builder<Players>()
            .setLifecycleOwner(this)
            .setQuery(PlayersRepository().getPlayersTeams(idTeam), Players::class.java)
            .build()

        adapter = PlayerAdapter(options, object : PlayerAdapter.OnItemClickListener {
            override fun onItemClick(player: Players) {
                // детальніше про гравця
                runDescriptionDialog(player)
            }
        })

        val layoutManager = LinearLayoutManager(requireContext())
        rvPlayers.layoutManager = layoutManager
        rvPlayers.adapter = adapter
        rvPlayers.addItemDecoration(PlayersDecoration(this))
    }

    override fun onClick() {
        Toast.makeText(activity, "Ok", Toast.LENGTH_SHORT).show()
    }
}