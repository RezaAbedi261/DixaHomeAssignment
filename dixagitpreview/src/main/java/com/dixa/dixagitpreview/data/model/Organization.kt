package com.dixa.dixagitpreview.data.model


data class Organization (
    var login                   : String?  = null,
    var id                      : Int?     = null,
    var node_id                  : String?  = null,
    var url                     : String?  = null,
    var repos_url                : String?  = null,
    var events_url               : String?  = null,
    var hooks_url                : String?  = null,
    var issues_url               : String?  = null,
    var members_url              : String?  = null,
    var public_members_url        : String?  = null,
    var avatar_url               : String?  = null,
    var description             : String?  = null,
    var name                    : String?  = null,
    var company                 : String?  = null,
    var blog                    : String?  = null,
    var location                : String?  = null,
    var email                   : String?  = null,
    var twitter_username         : String?  = null,
    var is_verified              : Boolean? = null,
    var has_organization_projects : Boolean? = null,
    var has_repository_projects   : Boolean? = null,
    var public_repos             : Int?     = null,
    var public_gists             : Int?     = null,
    var followers               : Int?     = null,
    var following               : Int?     = null,
    var html_url                 : String?  = null,
    var created_at               : String?  = null,
    var updated_at               : String?  = null,
    var type                    : String?  = null
)