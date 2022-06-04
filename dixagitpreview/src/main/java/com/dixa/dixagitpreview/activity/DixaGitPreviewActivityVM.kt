package com.dixa.dixagitpreview.activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.dixa.basearchitecture.extension.zip
import com.dixa.basearchitecture.viewmodel.BaseViewModel
import com.dixa.dixagitpreview.data.model.License
import com.dixa.dixagitpreview.data.model.Organization
import com.dixa.dixagitpreview.data.model.Repository
import com.dixa.dixagitpreview.data.model.Resource
import com.dixa.dixagitpreview.extension.emit
import com.dixa.dixagitpreview.repository.GitRepo
import kotlinx.coroutines.launch


class DixaGitPreviewActivityVM : BaseViewModel() {

    val repo = GitRepo
    val repoList = MutableLiveData<Resource<List<Repository>?>>()
    val organizationInfo = MutableLiveData<Resource<Organization?>>()
    val licenseInfo = MutableLiveData<Resource<License?>>()
    val repoAndOrganizationResponse = repoList.zip(organizationInfo)

    fun getRepoList(orgName: String) {
        lifecycleScope.launch {
            val response = repo.getRepoList(orgName)
            repoList.emit(response)
        }
    }

    fun getOrganizationInfo(orgName: String) {
        lifecycleScope.launch {
            val response = repo.getOrganizationInfo(orgName)
            organizationInfo.emit(response)
        }
    }

    fun getLicenseInfo(url: String) {
        lifecycleScope.launch {
            val response = repo.getLicenseInfo(url)
            licenseInfo.emit(response)
        }
    }


}