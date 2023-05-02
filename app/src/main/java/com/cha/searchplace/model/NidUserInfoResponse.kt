package com.cha.searchplace.model


// 73_ Gson 으로 받아올 데이터를 데이터 클래스에 저장하기 위해...
data class NidUserInfoResponse(var resultcode:String,var message:String,var response: NidUser)

data class NidUser(var id: String, var email: String)
