package adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myroutine.databinding.ItemDiaryBinding
import data.Diary
import com.google.android.material.snackbar.Snackbar

class DiaryAdapter (var diaryList : MutableList<Diary>) : RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder>() {
    private var removedPos:Int = 0
    private var removedDiary:Diary? = null

    inner class DiaryViewHolder(val binding : ItemDiaryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        Log.d("DiaryAdapter", "onCreateViewHolder() called with: parent = $parent, viewType = $viewType")
        val binding : ItemDiaryBinding = ItemDiaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DiaryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        Log.d("CocktailAdapter", "onBindViewHolder() called with: holder = $holder, position = $position")
        val diary : Diary = diaryList[position]
        holder.binding.headline.setText(diary.headline)
        holder.binding.resume.setText(diary.txt)
        holder.binding.date.setText(diary.date)

    }

    override fun getItemCount(): Int {
        return diaryList.size
    }

    fun addDay(d:Diary){
        diaryList.add(d)
    }

    fun removeItem(viewHolder: RecyclerView.ViewHolder) {
        removedPos = viewHolder.adapterPosition
        //Short for get at removedPos
        removedDiary = diaryList[removedPos]
        diaryList.removeAt(removedPos)
        notifyItemRemoved(removedPos)
        //!! for accepting possible null value
        Snackbar.make(viewHolder.itemView, removedDiary!!.headline+ " deleted.", Snackbar.LENGTH_LONG)
            .setAction("UNDO", View.OnClickListener {
                diaryList.add(removedPos, removedDiary!!)
                notifyItemInserted(removedPos)
            }).show()
    }
}