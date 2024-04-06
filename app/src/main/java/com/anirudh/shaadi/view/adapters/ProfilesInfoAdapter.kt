package com.anirudh.shaadi.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anirudh.shaadi.R
import com.anirudh.shaadi.data.model.ProfileInfo
import com.anirudh.shaadi.databinding.UserProfileItemBinding
import com.bumptech.glide.Glide

class ProfilesInfoAdapter(
    private val context: Context,
    private val onAccepted: (ProfileInfo) -> Unit, private val onDeclined: (ProfileInfo) -> Unit
) : RecyclerView.Adapter<ProfilesInfoAdapter.ResultsViewHolder>() {


    private var profileInfoList: ArrayList<ProfileInfo> = arrayListOf()

    fun updateList(info: List<ProfileInfo>) {
        profileInfoList = info as ArrayList<ProfileInfo>
        notifyDataSetChanged()
    }


    inner class ResultsViewHolder(val binding: UserProfileItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsViewHolder {
        return ResultsViewHolder(
            UserProfileItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return profileInfoList.size
    }

    override fun onBindViewHolder(holder: ResultsViewHolder, position: Int) {
        val user: ProfileInfo = profileInfoList[position]
        holder.binding.apply {
            Glide.with(holder.itemView.context).load(user.picture?.large).into(iv)
            tvName.text =
                String.format(
                    context.getString(R.string.userName),
                    user.name?.first,
                    user.name?.last
                )
            tvGender.text = user.gender
            tvState.text = user.location?.state
            tvCountry.text = user.location?.country
            ivCheck.setOnClickListener {
                onAccepted.invoke(user)
            }
            ivCross.setOnClickListener {
                onDeclined.invoke(user)
            }
        }
    }
}