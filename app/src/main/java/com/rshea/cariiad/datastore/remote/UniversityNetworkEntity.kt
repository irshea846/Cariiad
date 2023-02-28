package com.rshea.cariiad.datastore.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UniversityNetworkEntity(

    @SerializedName("name")
    @Expose
    var name: String = String(),

    @SerializedName("country")
    @Expose
    var country: String = String(),

    @SerializedName("web_pages")
    @Expose
    var webPagesListHolder: List<String>,

)