package com.dixa.dixagitpreview.network

import android.provider.ContactsContract
import com.dixa.dixagitpreview.activity.DixaGitPreviewActivity
import com.dixa.dixagitpreview.data.model.License
import com.dixa.dixagitpreview.data.model.Organization
import com.dixa.dixagitpreview.data.model.Repository
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface AppApi {

    companion object {
        const val REPO_LIST = "orgs/{org}/repos"
        const val ORGANIZATION_INFO = "orgs/{org}"
    }

    @GET(REPO_LIST)
    suspend fun getRepoList(@Path("org") organization: String): List<Repository>

    @GET(ORGANIZATION_INFO)
    suspend fun getOrganizationInfo(@Path("org") organization: String): Organization

    @GET
    suspend fun getLicenseInfo(@Url url: String): License

}