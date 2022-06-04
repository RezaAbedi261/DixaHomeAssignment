package com.dixa.dixagitpreview.activity

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.dixa.basearchitecture.activity.ArchBaseActivity
import com.dixa.basearchitecture.adapter.AppGenericAdapter
import com.dixa.dixagitpreview.R
import com.dixa.dixagitpreview.bottomsheet.webview.WebViewBottomSheet
import com.dixa.dixagitpreview.data.model.Repository
import com.dixa.dixagitpreview.databinding.ActivityDixaGitPreviewBinding
import com.dixa.dixagitpreview.view.cell.RepositoryCell
import com.dixa.dixagitpreview.view.cell.RepositoryCell.ClickType


class DixaGitPreviewActivity :
    ArchBaseActivity<ActivityDixaGitPreviewBinding, DixaGitPreviewActivityVM>() {

    private val repositoryAdapter = AppGenericAdapter().apply {
        provider { ctx ->
            RepositoryCell(ctx) { url, type, repo ->
                if (type == ClickType.LICENSE && url?.isNotBlank() == true)
                    viewModel.getLicenseInfo(url)
                else if (type == ClickType.LINK) {
                    openWebView(url)
                }
            }
        }
    }

    override val viewModel: DixaGitPreviewActivityVM by lazy {
        ViewModelProvider(this).get(DixaGitPreviewActivityVM::class.java)
    }

    override fun layout() = R.layout.activity_dixa_git_preview

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getRepoList("dixahq")
        viewModel.getOrganizationInfo("dixahq")
        binding.rvRepoList.adapter = repositoryAdapter
    }

    override fun registerObservers() {
        viewModel.repoAndOrganizationResponse {
            if (it.first.isSuccess() && it.second.isSuccess()) {
                binding.model = it.second?.data
                if (!it.first.data.isNullOrEmpty())
                    repositoryAdapter.setSectionsAndNotify<RepositoryCell, Repository>(it.first.data!!)
            }
        }

        viewModel.licenseInfo {
            openWebView(it.data?.html_url)
        }
    }

    private fun openWebView(url: String?) {
        url?.let {
            if (it.isNotBlank())
                WebViewBottomSheet(it).show(supportFragmentManager)
        }
    }


}