package com.dixa.dixagitpreview.view.cell

import android.content.Context
import android.util.AttributeSet
import com.dixa.basearchitecture.adapter.GenericAdapterView
import com.dixa.basearchitecture.view.BaseCustomView
import com.dixa.dixagitpreview.R
import com.dixa.dixagitpreview.data.model.Repository
import com.dixa.dixagitpreview.databinding.CellRepositoryBinding

class RepositoryCell : BaseCustomView<CellRepositoryBinding>, GenericAdapterView<Repository> {

    var onItemClick: ((String?, ClickType?, Repository?) -> Unit)? = null


    override fun layout(): Int = R.layout.cell_repository

    constructor(context: Context) : super(context)

    constructor(context: Context, onItemClick: ((String?, ClickType?, Repository?) -> Unit)?) : super(context) {
        this.onItemClick = onItemClick
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    override fun onBind(model: Repository, position: Int, extraObject: Any?) {
        binding.model = model
    }

    fun onClick(link: String) {
        onItemClick.let {
            onItemClick?.invoke(link, ClickType.LINK, binding.model)
        }
    }

    fun starClick() {
        binding.model?.stargazers_url?.replace("api.github.com/repos", "github.com")?.let {
            onClick(it)
        }
    }
    fun onLicenseClick() {
        onItemClick.let {
            onItemClick?.invoke(binding.model?.license?.url, ClickType.LICENSE, binding.model)
        }
    }

    fun onForkClicked() {
        onClick("https://github.com/${binding.model?.owner?.login}/${binding.model?.name}/network/members")
    }

    fun onPullRequestClick() {
        onClick("https://github.com/${binding.model?.owner?.login}/${binding.model?.name}/pulls")
    }

    enum class ClickType {
        LINK, LICENSE
    }
}
