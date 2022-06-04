package com.dixa.dixagitpreview.repository

import com.dixa.dixagitpreview.data.exception.Error
import com.dixa.dixagitpreview.data.model.License
import com.dixa.dixagitpreview.data.model.Organization
import com.dixa.dixagitpreview.data.model.Repository
import com.dixa.dixagitpreview.data.model.Resource
import com.dixa.dixagitpreview.network.ApiDispatcher

object GitRepo {

    val apiDispatcher = ApiDispatcher()

    suspend fun getRepoList(orgName: String): Resource<List<Repository>?> =
        apiDispatcher.suspend() {
            apiDispatcher.appApi.getRepoList(orgName)
        }

    suspend fun getOrganizationInfo(orgName: String): Resource<Organization?> =
        apiDispatcher.suspend() {
            apiDispatcher.appApi.getOrganizationInfo(orgName)
        }

    suspend fun getLicenseInfo(url: String): Resource<License?> =
        apiDispatcher.suspend() {
            apiDispatcher.appApi.getLicenseInfo(url)
        }



}