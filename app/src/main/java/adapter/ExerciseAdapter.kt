package adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import data.Exercise
import com.bumptech.glide.Glide
import com.example.myroutine.R
import com.example.myroutine.databinding.ItemExerciseBinding


class ExerciseAdapter (val workout : List<Exercise>) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {
    inner class ExerciseViewHolder(val binding : ItemExerciseBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding : ItemExerciseBinding = ItemExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val context = holder.binding.root.context
        val w = workout[position]
        holder.binding.tvBodyPartInsertValue.text = w.bodyPartOfEx
        holder.binding.tvEquipmentInsertValue.text = w.equipmentForEx
        Glide.with(context).load(w.gifurlOfEx).error(R.drawable.giphy).placeholder(R.drawable.ic_baseline_sports_kabaddi_24).into(holder.binding.ivExerciseGif)
       // Picasso.get().load(w.gifurlOfEx).error(R.drawable.ic_error).placeholder(R.drawable.ic_baseline_sports_kabaddi_24).into(holder.binding.ivExerciseGif)
        holder.binding.tvExerciseName.text = w.nameOfEx
        holder.binding.tvTargetInsertValue.text = w.targetOfEx

        //?
/*
        holder.binding.root.setOnClickListener {
            val i = Intent(context, DetailsActivity::class.java)
            i.putExtra("id", w.id)
            context.startActivity(i)
        }*/
    }

    override fun getItemCount(): Int {
        return workout.size
    }

}