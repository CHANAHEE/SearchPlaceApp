package com.example.tp_searchplaceapp.model

data class KakaoSearchPlaceResponse(var meta: PlaceMeta, var documents: MutableList<Place>)

data class PlaceMeta(var total_count: Int, var pageable_count: Int,var is_end: Boolean)

data class Place(
    var id: String,
    var place_name: String,
    var phone: String,
    var address_name: String,
    var road_address_name: String,
    var x: String, // longitude
    var y: String, // latitude
    var place_url: String,
    var distance: String, // 중심 좌표까지의 거리. 단, 요청 파라미터로 x,y 좌표를 준 경우에만 존재.
)
