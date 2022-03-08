package recyclerview

import adapter.DiaryAdapter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myroutine.R
import com.example.myroutine.databinding.FragmentDiaryBinding
import data.Diary
import java.util.*


class DiaryFragment : Fragment() {

    // for fragment
    private var _binding: FragmentDiaryBinding? = null
    private val binding get() = _binding!!

    private val diaryList : MutableList<Diary> = mutableListOf()
    private lateinit var diaryAdapter : DiaryAdapter
    private val swipeBackground : ColorDrawable = ColorDrawable(Color.parseColor("#FF03DAC5"))
    private var deleteIcon : Drawable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDiaryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //deleteIcon = getDrawable(binding.root.context,R.drawable.ic_delete)
        //Button to add a fix cocktail to the list
        binding.fabAddDay.setOnClickListener( {
            val newDay = Diary("","","")
            diaryAdapter.addDay(newDay)
            diaryAdapter.notifyItemInserted(diaryList.size - 1)
        })
        fillDiary()

    }

    override fun onResume() {
        super.onResume()

        fillDiary()

        binding.rvDiary.layoutManager = LinearLayoutManager(binding.root.context, RecyclerView.VERTICAL, false)
        binding.rvDiary.addItemDecoration(DividerItemDecoration(binding.root.context, DividerItemDecoration.VERTICAL))

        //Add ItemTouchHelper for swipe2delete
        ItemTouchHelper(itemTouchHelperCb).attachToRecyclerView(binding.rvDiary)
        //Create the adapter and bind it to the recyclerview
        diaryAdapter = DiaryAdapter(diaryList)
        binding.rvDiary.setAdapter(diaryAdapter)

    }
    private val itemTouchHelperCb : ItemTouchHelper.SimpleCallback = object: ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            val startPosition = viewHolder.adapterPosition
            val endPosition = target.adapterPosition
            Collections.swap(diaryList, startPosition, endPosition)
            diaryAdapter.notifyItemMoved(startPosition, endPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            diaryAdapter.removeItem(viewHolder)
        }

        override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {
            deleteIcon?.let {

                val itemView = viewHolder.itemView
                val iconMargin: Int = (itemView.height - it.getIntrinsicHeight()) / 2
                if (dX > 0) {
                    swipeBackground.setBounds(
                        itemView.left,
                        itemView.top,
                        dX.toInt(),
                        itemView.bottom
                    )
                    it.setBounds(
                        itemView.left + iconMargin,
                        itemView.top + iconMargin,
                        itemView.left + iconMargin + it.getIntrinsicWidth(),
                        itemView.bottom - iconMargin
                    )
                } else {
                    swipeBackground.setBounds(
                        itemView.right + dX.toInt(),
                        itemView.top,
                        itemView.right,
                        itemView.bottom
                    )
                    it.setBounds(
                        itemView.right - iconMargin - it.getIntrinsicWidth(),
                        itemView.top + iconMargin,
                        itemView.right - iconMargin,
                        itemView.bottom - iconMargin
                    )
                }
                swipeBackground.draw(c)
                if (dX > 0) c.clipRect(
                    itemView.left,
                    itemView.top,
                    dX.toInt(),
                    itemView.bottom
                ) else c.clipRect(
                    itemView.right + dX.toInt(),
                    itemView.top,
                    itemView.right,
                    itemView.bottom
                )
                it.draw(c)
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }
    }


/*

    override fun onResume() {
        super.onResume()

        fillDiary()

        binding.rvDiary.layoutManager = LinearLayoutManager(binding.root.context, RecyclerView.VERTICAL, false)
        binding.rvDiary.addItemDecoration(DividerItemDecoration(binding.root.context, DividerItemDecoration.VERTICAL))

        //Add ItemTouchHelper for swipe2delete
        ItemTouchHelper(itemTouchHelperCb).attachToRecyclerView(binding.rvDiary)
        //Create the adapter and bind it to the recyclerview
        diaryAdapter = DiaryAdapter(diaryList)
        binding.rvDiary.setAdapter(diaryAdapter)

    }*/
    /*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        deleteIcon = getDrawable(R.drawable.ic_delete)

        fillDiary()

        //Set the layout manager for recyclerview
        binding.rvDiary.setLayoutManager(LinearLayoutManager(this, RecyclerView.VERTICAL, false))
        binding.rvDiary.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        //Add ItemTouchHelper for swipe2delete
        ItemTouchHelper(itemTouchHelperCb).attachToRecyclerView(binding.rvDiary)
        //Create the adapter and bind it to the recyclerview
        diaryAdapter = DiaryAdapter(diaryList)
        binding.rvDiary.setAdapter(diaryAdapter)


        //sortByPrice()

        //Button to add a fix cocktail to the list
        binding.fabAddDay.setOnClickListener( {
            val newDay = Diary("","","")
            diaryAdapter.addCocktail(newDay)
            diaryAdapter.notifyItemInserted(diaryList.size - 1)
        })*/


    private fun fillDiary() {
        diaryList.addAll(listOf(
            Diary("MEGA TAG!", "sehr gute Note für unsere App bekommen <3", "09.03.2022"))) }



}