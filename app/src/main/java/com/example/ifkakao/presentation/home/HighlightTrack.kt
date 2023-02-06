package com.example.ifkakao.presentation.home

import androidx.annotation.DrawableRes
import com.example.ifkakao.R

enum class HighlightTrack(
    val alias: String,
    val aliasTrackEnum: String,
    @DrawableRes
    val resId: Int
) {
    Ai("AI", "ai", R.drawable.icon_ai),
    BackEnd("Backend", "be", R.drawable.icon_be),
    Cloud("Cloud", "cloud", R.drawable.icon_cd),
    DevOps("DevOps", "devops", R.drawable.icon_do),
    BlockChain("Blockchain", "blockchain", R.drawable.icon_bc),
    Data("Data", "bigdata", R.drawable.icon_dt),
    FrontEnd("Frontend", "fe", R.drawable.icon_fe),
    Mobile("Mobile", "mobile", R.drawable.icon_m)
}