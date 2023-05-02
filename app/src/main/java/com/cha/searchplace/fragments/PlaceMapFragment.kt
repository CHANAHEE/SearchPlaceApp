package com.cha.searchplace.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cha.searchplace.activities.MainActivity
import com.cha.searchplace.activities.PlaceUrlActivity
import com.cha.searchplace.databinding.FragmentPlaceListBinding
import com.cha.searchplace.databinding.FragmentPlaceMapBinding
import com.cha.searchplace.model.Place
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import net.daum.mf.map.api.MapView.POIItemEventListener

class PlaceMapFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    lateinit var binding: FragmentPlaceMapBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaceMapBinding.inflate(inflater,container,false)
        return binding.root
    }

    val mapView: MapView by lazy { MapView(context) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.containerMapview.addView(mapView)

        // 순서가 중요!! 마커 또는 마커의 말풍선의 클릭 이벤트에 반응하는 리스너 등록. 반드시 마커 추가보다 먼저 등록해야만 함.
        mapView.setPOIItemEventListener(markerEventListener)

        // 지도 관련 설정 ( 지도위치, 마커 추가, 등등)
        setMapAndMarkers()
    }



    private fun setMapAndMarkers() {
        // 앱 중심점 변경 - 현재 내 위치의 위도와 경도를 좌표로 받아오자
        var lat: Double = (activity as MainActivity).myLocation?.latitude ?: 37.5663
        var lng: Double = (activity as MainActivity).myLocation?.longitude ?: 126.9779

        var myMapPoint: MapPoint = MapPoint.mapPointWithGeoCoord(lat,lng)
        mapView.setMapCenterPointAndZoomLevel(myMapPoint,2,true)
        mapView.zoomIn(true)
        mapView.zoomOut(true)

        // 내 위치 표시 마커 추가
        var marker = MapPOIItem()
        marker.apply {
            itemName = "ME"
            mapPoint = myMapPoint
            markerType = MapPOIItem.MarkerType.BluePin
            selectedMarkerType = MapPOIItem.MarkerType.YellowPin
        }
        mapView.addPOIItem(marker)

        // 검색 장소들의 마커를 추가
        val documents: MutableList<Place>? = (activity as MainActivity).searchPlaceResponse?.documents

        documents?.forEach{
            val point: MapPoint = MapPoint.mapPointWithGeoCoord(it.y.toDouble(),it.x.toDouble())

            val marker = MapPOIItem().apply {
                mapPoint = point
                itemName = it.place_name
                markerType = MapPOIItem.MarkerType.RedPin
                selectedMarkerType = MapPOIItem.MarkerType.YellowPin

                // 마커 객체에 보관하고 싶은 데이터가 있다면.
                // 즉, 해당 마커에 관련된 정보를 가지고 있는 객체를 마커에 저장 해놓기
                userObject = it
            }

            mapView.addPOIItem(marker)
        }

    }


    // 말풍선 , 마커의 클릭 이벤트 리스너
    val markerEventListener: POIItemEventListener = object : POIItemEventListener{
        override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem?) {
            // 마커를 클릭 했을 때
        }

        override fun onCalloutBalloonOfPOIItemTouched(p0: MapView?, p1: MapPOIItem?) {
            // deprecated 아래 메소드로 대체
        }

        override fun onCalloutBalloonOfPOIItemTouched(
            p0: MapView?,
            p1: MapPOIItem?,
            p2: MapPOIItem.CalloutBalloonButtonType?
        ) {
            // 말풍선 터치했을 때 발동
            //if(p1.userObject == null) return
            p1?.userObject ?: return

            val place: Place = p1?.userObject as Place

            val intent: Intent = Intent(context, PlaceUrlActivity::class.java)
            intent.putExtra("place_url",place.place_url)
            startActivity(intent)
        }

        override fun onDraggablePOIItemMoved(p0: MapView?, p1: MapPOIItem?, p2: MapPoint?) {
            TODO("Not yet implemented")
        }

    }

}