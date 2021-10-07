package com.nowadequacy.happenings.ui.feed.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.nowadequacy.happenings.R
import com.nowadequacy.happenings.response.Attributes
import com.nowadequacy.happenings.response.Card
import com.nowadequacy.happenings.response.Description
import com.nowadequacy.happenings.response.Title
import com.nowadequacy.happenings.databinding.ItemCardImageTitleDescBinding
import com.nowadequacy.happenings.databinding.ItemCardTextBinding
import com.nowadequacy.happenings.databinding.ItemCardTitleDescBinding
import javax.inject.Inject

class FeedItemAdapter @Inject constructor(
    private val listener: OnItemClickListener?
) : PagingDataAdapter<Card, RecyclerView.ViewHolder>(CARD_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
      return when(viewType) {
           TEXT_VIEWTYPE_ID -> {
              CardTextViewHolder( ItemCardTextBinding.inflate(
                   LayoutInflater.from(parent.context),
                   parent, false
               ))
           }
            TITLE_DESCRIPTION_VIEWTYPE_ID -> {
                CardTitleDescViewHolder(
                ItemCardTitleDescBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                ))
            }
            IMAGE_TITLE_DESCRIPTION_VIEWTYPE_ID -> {
                CardImageTitleDescViewHolder(
                ItemCardImageTitleDescBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                ))
            }
            else -> throw Exception("Unsupported View type")
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            when(currentItem.card_type){
               TEXT_VIEWTYPE -> (holder as CardTextViewHolder).bind(currentItem)
               TITLE_DESCRIPTION_VIEWTYPE -> (holder as CardTitleDescViewHolder).bind(currentItem)
               IMAGE_TITLE_DESCRIPTION_VIEWTYPE -> (holder as CardImageTitleDescViewHolder).bind(currentItem)
               else -> throw Exception("Unsupported View type")
            }
        }
    }
    override fun getItemViewType(position: Int) =
        when(getItem(position)?.card_type){
            TEXT_VIEWTYPE -> TEXT_VIEWTYPE_ID
            TITLE_DESCRIPTION_VIEWTYPE -> TITLE_DESCRIPTION_VIEWTYPE_ID
            IMAGE_TITLE_DESCRIPTION_VIEWTYPE ->IMAGE_TITLE_DESCRIPTION_VIEWTYPE_ID
            else -> 0
        }
    fun setTitle(tv: TextView, title: Title) {
        tv.text = title.value
        tv.setTextColor(Color.parseColor(title.attributes.text_color))
        tv.textSize = title.attributes.font.size.toFloat()
    }
    fun setValue(tv: TextView, value: String, attributes: Attributes) {
        tv.text = value
        tv.setTextColor(Color.parseColor(attributes.text_color))
        tv.textSize = attributes.font.size.toFloat()
    }
    fun setDesc(tv: TextView, desc: Description) {
        tv.text = desc.value
        tv.setTextColor(Color.parseColor(desc.attributes.text_color))
        tv.textSize = desc.attributes.font.size.toFloat()
    }
    inner class CardTextViewHolder(private val binding: ItemCardTextBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener?.onItemClick(item)
                    }
                }
            }
        }

        fun bind(card: Card) {
            binding.apply {
                card.card.value?.let {
                   setValue(tvValue, it, card.card.attributes)
                }
            }
        }
    }
    inner class CardTitleDescViewHolder(private val binding: ItemCardTitleDescBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener?.onItemClick(item)
                    }
                }
            }
        }

        fun bind(card: Card) {
            binding.apply {
                card.card.title?.let {
                    setTitle(tvTitle, it)
                }
                card.card.description?.let {
                    setDesc(tvDesc, it)
                }
            }
        }
    }
    inner class CardImageTitleDescViewHolder(private val binding: ItemCardImageTitleDescBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener?.onItemClick(item)
                    }
                }
            }
        }

        fun bind(card: Card) {
            binding.apply {
                card.card.title?.let {
                    setTitle(tvImageTitle, it)
                }
                card.card.description?.let {
                    setDesc(tvImageDesc, it)
                }
                val w = card.card.image?.size?.width ?: 0
                val h = card.card.image?.size?.height ?: 0

                card.card.image?.url?.let {
                    Glide.with(itemView)
                        .load(card.card.image.url)
                        .apply(RequestOptions().override(w, h))
                        .fitCenter()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .error(R.drawable.ic_error)
                        .into(ivImage)
                }
            }
        }
    }
    interface OnItemClickListener {
        fun onItemClick(card: Card)
    }
    companion object {
        private val CARD_COMPARATOR = object : DiffUtil.ItemCallback<Card>(){
            override fun areItemsTheSame(oldItem: Card, newItem: Card) =
                oldItem.card == newItem.card
            override fun areContentsTheSame(oldItem: Card, newItem: Card) =
                oldItem == newItem
        }

        const val TEXT_VIEWTYPE = "text"
        const val TITLE_DESCRIPTION_VIEWTYPE = "title_description"
        const val IMAGE_TITLE_DESCRIPTION_VIEWTYPE = "image_title_description"

        const val IMAGE_TITLE_DESCRIPTION_VIEWTYPE_ID= 1
        const val TITLE_DESCRIPTION_VIEWTYPE_ID = 2
        const val TEXT_VIEWTYPE_ID = 3
    }


}